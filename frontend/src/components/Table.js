import React from 'react';
import "../styles.css";

function CombinedTable({ universities, programs, query }) {
  return (
    <div>
      <h1>Найденные программы:</h1>
      <div className="data-container">
        {programs
          .map((p) => ({
            ...p,
            universityData: universities.find((u) => p.fieldDto.universityId === u.id),
          }))
          .filter((p) => p.universityData) // Ensure university data exists
          .filter((p) => {
            const normalizedQuery = query?.toLowerCase().trim() || "";
            const normalizedUniversityName = p.universityData.name.toLowerCase().trim();
            const normalizedProgramDescription = p.codeDto.description.toLowerCase().trim();
            return (
              normalizedUniversityName.includes(normalizedQuery) ||
              normalizedProgramDescription.includes(normalizedQuery)
            );
          })
          .map((p) => (
            <div key={`program-${p.fieldDto.id}`} className="program-card">
              <div className="program-header">
                <div>
                  <h2>{p.codeDto.description}</h2>
                  <p className="faculty">{p.codeDto.title}</p>
                </div>
                <div className="university-badge">{p.universityData.name}</div>
              </div>

              <div className="program-content">
                <div className="program-details">
                  <p>
                    <strong>Бюджетные места:</strong>{" "}
                    {p.fieldDto.freePlaceQuantity ?? "N/A"}
                  </p>
                  <p>
                    <strong>Платные места:</strong>{" "}
                    {p.fieldDto.paidPlaceQuantity ?? "N/A"}
                  </p>
                  <p className="cost">
                    <strong>Цена за семестр:</strong>{" "}
                    {p.fieldDto.cost?.toLocaleString() ?? "N/A"} ₽
                  </p>
                </div>

                <div className="university-info">
                  <p>
                    <strong>Сайт:</strong>{" "}
                    <a
                      href={"https://spb.ucheba.ru" + p.universityData.website}
                      target="_blank"
                      rel="noopener noreferrer"
                    >
                      {p.universityData.website}
                    </a>
                  </p>
                </div>
              </div>
            </div>
          ))}
      </div>
    </div>
  );
}

export default CombinedTable;