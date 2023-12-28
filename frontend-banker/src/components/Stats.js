import React, { useEffect, useState } from 'react';
import axios from 'axios';

const containerStyle = {
  maxWidth: '800px',
  margin: '0 auto',
  padding: '20px',
};

const titleStyle = {
  fontSize: '2rem',
  textAlign: 'center',
  marginBottom: '20px',
};

const statisticListStyle = {
  listStyle: 'none',
  padding: '0',
};

const statisticItemStyle = {
  border: '1px solid #ccc',
  marginBottom: '10px',
  backgroundColor: '#f8f8f8',
  padding: '15px',
};

const statisticNameStyle = {
  fontSize: '1.2rem',
  fontWeight: 'bold',
  marginBottom: '5px',
};

const statisticValueStyle = {
  fontSize: '1rem',
};

function Statistics() {
  const [statistics, setStatistics] = useState({});

  useEffect(() => {
    axios.get('http://localhost:8088/bankers/bankers/viewStatistics')
      .then(async (response) => {
        console.log(response.data);
        await setStatistics(response.data);
      })
      .catch((error) => {
        console.error('Error fetching statistics:', error);
      });
  }, []);

  console.log(statistics);

  return (
    <div style={containerStyle}>
      <h1 style={titleStyle}>Statistiques</h1>
      <ul style={statisticListStyle}>
        {Object.keys(statistics).map((key) => (
          <li key={key} style={statisticItemStyle}>
            <div style={statisticNameStyle}>{key}</div>
            <div style={statisticValueStyle}>{statistics[key]}</div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Statistics;
