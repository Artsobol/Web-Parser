import React, { useState } from "react";
import { ArrowUpDown, ArrowUp, ArrowDown } from "lucide-react";
import "../styles.css"; 

function SearchWithSort({ handleQueryChange, handleSort }) {
  const [showSortOptions, setShowSortOptions] = useState(false);

  return (
    <div className="search-sort-container">
      <div className="search-container">
        <input
          type="text"
          className="search-input"
          placeholder="Поиск"
          onChange={handleQueryChange}
        />
        <button className="search-button" onClick={() => setShowSortOptions(!showSortOptions)}>
          <ArrowUpDown className="search-icon" />
        </button>
      </div>

      {showSortOptions && (
        <div className="sort-options">
            <div key="cost" className="sort-option">
                  <button className="sort-button" onClick={() => handleSort("cost", "asc")}>
                    Стоимость <ArrowUp size={16} />
                  </button>
                  <button className="sort-button" onClick={() => handleSort("cost", "desc")}>
                    Стоимость <ArrowDown size={16} />
                  </button>
            </div>

          <div className="sort-option">
            <div className="sort-group">
              <button className="sort-button" onClick={() => handleSort("paidPlaceQuantity", "desc")}>
                Платные места <ArrowDown size={16} />
              </button>
              <button className="sort-button" onClick={() => handleSort("freePlaceQuantity", "desc")}>
                Бюджетные места <ArrowDown size={16} />
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default SearchWithSort;
