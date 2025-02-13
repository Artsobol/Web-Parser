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


# Функция парсинга программ
URL = "https://spb.ucheba.ru/for-specialists/master-programs?_lt=t"
HEADERS = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"}


def clean_number(value):
    """Очищает строку от лишних символов и преобразует в число."""
    return int(re.sub(r"[^0-9]", "", value)) if re.search(r"[0-9]", value) else 0


def parse_programs():
    ensure_tables_exist()

    response = requests.get(URL, headers=HEADERS)
    if response.status_code != 200:
        print("Ошибка при получении страницы")
        return

    soup = BeautifulSoup(response.text, 'html.parser')

    conn = connect_db()
    for item in soup.select('.card'):
        program_name = item.select_one('.card__title a').text.strip() if item.select_one(
            '.card__title a') else "N/A"
        university = item.select_one('.card__uz a').text.strip() if item.select_one(
            '.card__uz a') else "N/A"
        website = item.select_one('.card__uz a')['href'] if item.select_one(
            '.card__uz a') else "N/A"
        cost = item.select_one('.important dd').text.strip().replace('\xa0', '').replace('р. в год',
                                                                                         '').replace(
            'от', '').strip() if item.select_one('.important dd') else "0"
        budget_places = item.select_one(
            'dl:-soup-contains("Бюдж. мест") dd').text.strip() if item.select_one(
            'dl:-soup-contains("Бюдж. мест") dd') else "0"
        code = item.select_one('.card__code').text.strip() if item.select_one(
            '.card__code') else None

        if code is None:
            code = 000000

        university_id = get_or_create_university(conn, university, website)


        with conn.cursor() as cur:
            cur.execute(
                "SELECT id FROM field WHERE university_id = %s AND cost = %s AND free_place_quantity = %s AND code IS NOT DISTINCT FROM %s",
                (university_id, clean_number(cost), clean_number(budget_places), code))
            existing = cur.fetchone()
            if existing:
                print(f"Программа уже существует: {program_name} ({university})")
                continue

            cur.execute(
                "INSERT INTO field (university_id, cost, free_place_quantity, last_update_date, code) VALUES (%s, %s, %s, CURRENT_DATE, %s)",
                (university_id, clean_number(cost), clean_number(budget_places), code)
            )
            conn.commit()

        print(
            f"Добавлена программа: {program_name} ({university}) - {cost} руб, {budget_places} бюджетных мест")
        time.sleep(1)

    conn.close()


if __name__ == "__main__":
    parse_programs()