import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import "./styles/transaction.css"

const containerStyle = {
  maxWidth: '800px',
  margin: '0 auto',
  padding: '20px',
};

const titleStyle = {
  fontSize: '2rem',
  textAlign: 'center',
};

const listItemStyle = {
  border: '1px solid #ccc',
  margin: '10px 0',
  padding: '10px',
  backgroundColor: '#f8f8f8',
};

const transactionDetailsStyle = {
  fontSize: '1.5rem',
  margin: '0',
};

const loadingStyle = {
  fontSize: '1.2rem',
  textAlign: 'center',
  marginTop: '20px',
};

function TransactionList() {
  const { clientId } = useParams();
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);

  console.log(clientId)

  useEffect(() => {
    // Utilisez le clientId extrait de l'URL pour récupérer les transactions du client.
    axios.get(`http://localhost:8088/clients/transaction/getTransactions/${clientId}`).then((response) => {
      setTransactions(response.data);
      setLoading(false);
    });
  }, [clientId]);

  if (loading) {
    return <div style={loadingStyle}>Loading...</div>;
  }

  return (
    <div>
      <h1 style={titleStyle}>Liste des Transactions du Client (ID: {clientId})</h1>
      {transactions.length === 0 ? (
        <p style={transactionDetailsStyle}>Aucune transaction pour ce client.</p>
      ) : (
        <ul className="transactions-list">
          {transactions.map((transaction, index) => (
              <div key={index} className="transaction-item">
                <h2><b>{transaction.name}</b></h2>
                <p><b>Prix :</b> {transaction.price} €</p>
                <p><b>Pays :</b> {transaction.country}</p>
                <p><b>Type :</b> {transaction.type}</p>
                <p><b>Origine :</b> {transaction.origine}</p>
                {/*<p><b>Client Cible :</b> {transaction.targetClient}</p>*/}
              </div>
          ))}
        </ul>
      )}
    </div>
  );
}

export default TransactionList;
