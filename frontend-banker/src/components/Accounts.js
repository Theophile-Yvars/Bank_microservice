import React, { useEffect, useState } from 'react';
import axios from 'axios';

const containerStyle = {
  maxWidth: '800px',
  margin: '0 auto',
};

const titleStyle = {
  fontSize: '2rem',
  textAlign: 'center',
};

const listItemStyle = {
  border: '1px solid #ccc',
  margin: '10px 0',
  padding: '10px',
};

const clientNameStyle = {
  fontSize: '1.5rem',
  margin: '0',
};

const amountStyle = {
  fontWeight: 'bold',
};

function AccountList() {
  const [accounts, setAccounts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios.get('http://localhost:8088/clients/clients/getAllAccounts').then((response) => {
      setAccounts(response.data);
      setLoading(false);
    });
  }, []);

  if (loading) {
    return <div style={titleStyle}>Loading...</div>;
  }

  return (
    <div style={containerStyle}>
      <h1 style={titleStyle}>Liste des Comptes</h1>
      <ul>
        {accounts.map((account, index) => (
          <li style={listItemStyle} key={index}>
            <h2 style={clientNameStyle}>Client: {account.clientName}</h2>
            <p style={amountStyle}>Montant: {account.amount} €</p>
            <p>Numéro de carte de crédit: {account.creditCardNumber}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default AccountList;
