import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {useParams} from "react-router-dom";
import "./styles/listOffers.css"

const offerContainerStyle = {
  maxWidth: '800px',
  margin: '0 auto',
};

const offerListTitleStyle = {
  fontSize: '2rem',
  textAlign: 'center',
};

const offerItemStyle = {
  border: '1px solid #ccc',
  margin: '10px',
  padding: '10px',
  display: 'inline-block', // Affiche les produits côte à côte
  width: '45%', // Définir la largeur pour deux produits côte à côte
  verticalAlign: 'top', // Aligner les produits en haut
  boxSizing: 'border-box', // Inclure les bordures dans la largeur
};

const offerTitleStyle = {
  fontSize: '1.5rem',
  margin: '0',
};

const offerDescriptionStyle = {
  color: '#555',
};

const offerPriceStyle = {
  fontWeight: 'bold',
};

const loadingStyle = {
  fontSize: '1.2rem',
  textAlign: 'center',
  marginTop: '20px',
};

function OfferList() {
  const { clientId } = useParams();
  const [offers, setOffers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios.get(`http://localhost:8088/clients/clients/showProduct/${clientId}`).then((response) => {
      setOffers(response.data);
      setLoading(false);
    });
  }, []);

  if (loading) {
    return <div style={loadingStyle}>Loading...</div>;
  }

  return (
    <div style={offerContainerStyle}>
      <h1 style={offerListTitleStyle}>Liste des offres pour le client {clientId}</h1>
      <ul className="list-offre-client">
        {offers.map((offer) => (
          <li style={offerItemStyle} key={offer.productId}>
            <h2 style={offerTitleStyle}>{offer.productName}</h2>
            <p style={offerDescriptionStyle}>{offer.productDescription}</p>
            <p style={offerPriceStyle}>Prix : {offer.productPrice} €</p>
            <p>Mode : {offer.productMode}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default OfferList;
