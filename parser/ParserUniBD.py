import psycopg2
import requests
from bs4 import BeautifulSoup
import time
import re
import os

# Подключение к PostgreSQL
DB_PARAMS = {
    "dbname": os.getenv("DB_NAME", "dev_db"),
    "user": os.getenv("DB_USER", "dev_user"),
    "password": os.getenv("DB_PASSWORD", "dev_password"),
    "host": os.getenv("DB_HOST", "db"),
    "port": os.getenv("DB_PORT", "5432"),
}


def connect_db():
    return psycopg2.connect(**DB_PARAMS)

def ensure_tables_exist():
        conn = connect_db()
        with conn.cursor() as cur:
            cur.execute("""
                CREATE TABLE IF NOT EXISTS university (
                    id SERIAL PRIMARY KEY,
                    name TEXT UNIQUE NOT NULL,
                    website TEXT
                );
            """)
            cur.execute("""
                CREATE TABLE IF NOT EXISTS field (
                    id SERIAL PRIMARY KEY,
                    university_id INTEGER REFERENCES university(id) ON DELETE CASCADE,
                    cost INTEGER,
                    free_place_quantity INTEGER,
                    last_update_date DATE DEFAULT CURRENT_DATE,
                    code TEXT
                );
            """)
            conn.commit()
        conn.close()

# Функция для добавления университета
def get_or_create_university(conn, name, website):
    with conn.cursor() as cur:
        cur.execute("SELECT id FROM university WHERE name = %s", (name,))
        result = cur.fetchone()
        if result:
            return result[0]

        website = website if website and website != "N/A" else "https://unknown.com"
        cur.execute("INSERT INTO university (name, website) VALUES (%s, %s) RETURNING id",
                    (name, website))
        conn.commit()
        return cur.fetchone()[0]


# Функция для добавления или получения кода программы

def get_or_create_code(conn, title, description):
    with conn.cursor() as cur:
        cur.execute("SELECT id FROM code WHERE title = %s", (title,))
        result = cur.fetchone()
        if result:
            cur.execute("UPDATE code SET description = %s WHERE id = %s", (description, result[0]))
            conn.commit()
            return result[0]
        cur.execute("INSERT INTO code (title, description) VALUES (%s, %s) RETURNING id",
                    (title, description))
        conn.commit()
        return cur.fetchone()[0]


# Функция парсинга числового кода программы из заголовка страницы

def extract_program_code(program_url):
    full_url = f"https://spb.ucheba.ru{program_url}"
    response = requests.get(full_url, headers=HEADERS)
    if response.status_code != 200:
        print(f"Ошибка при получении страницы {full_url}")
        return None, None

    soup = BeautifulSoup(response.text, 'html.parser')
    title_text = soup.title.text if soup.title else ""

    match = re.search(r"(\d{2}\.\d{2}\.\d{2})", title_text)
    numeric_code = match.group(0) if match else None

    # Обрезаем description до ", магистратура"
    description = title_text.replace(numeric_code, "").strip(" ,-") if numeric_code else title_text
    description = re.split(r", магистратура", description, maxsplit=1)[0].strip()

    return numeric_code, description


# Функция парсинга программ
BASE_URL = "https://spb.ucheba.ru/for-specialists/master-programs?_lt=t&page="
HEADERS = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"}


def clean_number(value):
    """Очищает строку от лишних символов и преобразует в число."""
    return int(re.sub(r"[^0-9]", "", value)) if re.search(r"[0-9]", value) else 0


def parse_programs():
    ensure_tables_exist()
    conn = connect_db()
    for page in range(1, 6):  # Парсим первые 5 страниц
        url = f"{BASE_URL}{page}"
        response = requests.get(url, headers=HEADERS)
        if response.status_code != 200:
            print(f"Ошибка при получении страницы {url}")
            break

        soup = BeautifulSoup(response.text, 'html.parser')
        programs = soup.select('.card')
        if not programs:
            print("Больше нет программ для парсинга.")
            break

        for item in programs:
            program_name = item.select_one('.card__title a').text.strip() if item.select_one(
                '.card__title a') else "N/A"
            program_url = item.select_one('.card__title a')['href'] if item.select_one(
                '.card__title a') else None
            university = item.select_one('.card__uz a').text.strip() if item.select_one(
                '.card__uz a') else "N/A"
            website = item.select_one('.card__uz a')['href'] if item.select_one(
                '.card__uz a') else "N/A"
            cost = item.select_one('.important dd').text.strip().replace('\xa0', '').replace(
                'р. в год', '').replace('от', '').strip() if item.select_one(
                '.important dd') else "0"
            budget_places = item.select_one(
                'dl:-soup-contains("Бюдж. мест") dd').text.strip() if item.select_one(
                'dl:-soup-contains("Бюдж. мест") dd') else "0"

            university_id = get_or_create_university(conn, university, website)
            program_code, description = extract_program_code(program_url) if program_url else (
            None, None)

            if program_code:
                code_id = get_or_create_code(conn, program_code, description)
            else:
                code_id = None

            with conn.cursor() as cur:
                cur.execute(
                    """
                    SELECT id FROM field WHERE university_id = %s AND cost = %s
                    AND free_place_quantity = %s AND code IS NOT DISTINCT FROM %s
                    """, (university_id, clean_number(cost), clean_number(budget_places), code_id))
                existing = cur.fetchone()
                if existing:
                    print(f"Программа уже существует: {program_name} ({university})")
                    continue

                cur.execute(
                    "INSERT INTO field (university_id, cost, free_place_quantity, last_update_date, code) VALUES (%s, %s, %s, CURRENT_DATE, %s)",
                    (university_id, clean_number(cost), clean_number(budget_places), code_id)
                )
                conn.commit()

            print(
                f"Добавлена программа: {program_name} ({university}) - {cost} руб, {budget_places} бюджетных мест, Код: {program_code}")
            time.sleep(1)

    conn.close()


if __name__ == "__main__":
    parse_programs()