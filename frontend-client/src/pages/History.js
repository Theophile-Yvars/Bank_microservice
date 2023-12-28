import Nav from "../components/Nav";
import React, {useEffect, useState} from "react";
import axios from "axios";
import "./styles/transaction.css"
import primaBankLogo from "../res/img/primaBankLogo.png";
import {useParams} from "react-router-dom";

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

function HistoryPage() {

    const clientId = localStorage.getItem("client");
    const { idClient } = useParams();

    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);
    console.log(localStorage.getItem("client"))

    useEffect(() => {
        // Utilisez le clientId extrait de l'URL pour récupérer les transactions du client.
        console.log(clientId);
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
            <div className="header-offers">
                {/* Ajoutez votre image et titre ici */}
                <div className="header-img">
                    <img src={primaBankLogo} alt="Description de l'image" />
                </div>
                <div className="header-title">
                    <h1>Historique des Transactions</h1>
                </div>
            </div>

            <h1 className="offers-title">Client ID : {idClient}</h1>
            <div className="transactions-list">
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
            </div>
        </div>
    );
}

export default HistoryPage;