import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Section1 from './components/Section1';
import Footer from './components/Footer';
import Table from './components/Table';
import Search from './components/Search';
import './App.css';

function App() {
  const [universities, setUniversities] = useState([]);
  const [programs, setPrograms] = useState([]);
  const [query, setQuery] = useState("");

  useEffect(() => {
    axios.get('/api/universities')
      .then((response) => setUniversities(response.data))
      .catch((error) => console.error(error));

    axios.get('/api/fields')
      .then((response) => {
        setPrograms(response.data);
      })
      .catch((error) => console.error(error));
  }, []);

  const handleQueryChange = (e) => {
    setQuery(e.target.value);
  };

  const handleSort = (key, direction) => {
    var sortedData = null;
    if (direction === "asc"){
      sortedData = [...programs].sort((a, b) => a.fieldDto[key] - b.fieldDto[key]);
    } else {
      sortedData = [...programs].sort((a, b) => b.fieldDto[key] - a.fieldDto[key]);
    }
    setPrograms(sortedData);
  };

  return (
    <div className="app">
      <Section1 />
      <Search handleQueryChange={handleQueryChange} handleSort={handleSort} />
      <Table universities={universities} programs={programs} query={query}/>
      <Footer />
    </div>
  );
}

export default App;
