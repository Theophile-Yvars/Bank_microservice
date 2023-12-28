import React, { useEffect, useState } from 'react';
import axios from 'axios';

const productContainerStyle = {
  maxWidth: '800px',
  margin: '0 auto',
};

const productListTitleStyle = {
  fontSize: '2rem',
  textAlign: 'center',
};

const productItemStyle = {
  border: '1px solid #ccc',
  margin: '10px',
  padding: '10px',
  display: 'inline-block', // Affiche les produits côte à côte
  width: '45%', // Définir la largeur pour deux produits côte à côte
  verticalAlign: 'top', // Aligner les produits en haut
  boxSizing: 'border-box', // Inclure les bordures dans la largeur
  height: '190px',
};

const productTitleStyle = {
  fontSize: '1.5rem',
  margin: '0',
};

const productDescriptionStyle = {
  color: '#555',
};

const productPriceStyle = {
  fontWeight: 'bold',
};

const loadingStyle = {
  fontSize: '1.2rem',
  textAlign: 'center',
  marginTop: '20px',
};

function ProductList() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios.get('http://localhost:8088/bankers/bankers/getProduces').then((response) => {
      setProducts(response.data);
      setLoading(false);
    });
  }, []);

  if (loading) {
    return <div style={loadingStyle}>Loading...</div>;
  }

  return (
    <div style={productContainerStyle}>
      <h1 style={productListTitleStyle}>Liste de Produits</h1>
      <ul>
        {products.map((product) => (
          <li style={productItemStyle} key={product.id}>
            <h2 style={productTitleStyle}>{product.name}</h2>
            <p style={productDescriptionStyle}>{product.description}</p>
            <p style={productPriceStyle}>Prix : {product.price} €</p>
            <p>Mode : {product.mode}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ProductList;
