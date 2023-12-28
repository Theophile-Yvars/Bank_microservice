import Nav from "../components/Nav";
import { useState } from "react";
import "./styles/home.css";
import "./styles/offers.css";
import { Link, useNavigate } from 'react-router-dom';

function HomePage() {
    const [clientId, setClientId] = useState(null); // Add state for clientId
    const client = localStorage.getItem("client");

    const handleClientIdChange = (e) => {
        setClientId(e.target.value);
    };

    const handleTransactionsClick = () => {
        if (clientId) {
            // You might want to add logic for handling transactions
        }
    };

    const handleOffersClick = () => {
        if (clientId) {
            // You might want to add logic for handling offers
        }
    };

    return (
        <div>
            <Nav />
            <div className="info-client-content">
                <div className="info-client">
                    <div className="home-home">
                        <div className="home-input">
                            <div>
                                <label>
                                    <p className="input-style">Enter Client ID:</p>
                                    <input className="input" type="text" value={clientId} onChange={handleClientIdChange} />
                                </label>
                            </div>
                        </div>
                        <div className="home-info">
                            {clientId !== null && (
                                <div className="home-info-link">
                                    <Link to={`/transactions/${clientId}`}>
                                        <button className="info-button">Go to Transactions</button>
                                    </Link>
                                    <Link to={`/offers/${clientId}`}>
                                        <button className="info-button">Go to Offers</button>
                                    </Link>
                                </div>
                            )}
                            {clientId === null && <div className="empty-id">Please, enter a client id.</div>}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );

}

export default HomePage;
