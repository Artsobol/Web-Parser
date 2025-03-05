import React from "react";
import "../styles.css";

const Section1 = () => {
  return (
    <section className="section1" id="sec-4deb">
      <div className="section1-overlay"></div>
      <div className="section1-content">
        <h1 className="section1-title">
          <span>Магистратура</span>
          <br />
          <span className="section1-subtitle">в Санкт-Петербурге</span>
          <br />
        </h1>
        <p className="section1-text">
          Планируете поступление на магистратуру в СПб? Изучите направления, оцените стоимость
          обучения и узнайте, сколько мест предоставляется по желаемой специальности.
        </p>
      </div>
    </section>
  );
};

export default Section1;
