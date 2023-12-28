import React from "react";
import primaBankLogo from '../res/img/primaBankLogo.png'
import {Link, useNavigate} from "react-router-dom";
import "./styles/nav.css"

function Nav() {
  const navigate = useNavigate();
  const client = localStorage.getItem("client");

  const disconnect = () => {
    localStorage.setItem("client", null);
    navigate("/login");
  };

  return (
      <div className="header-offers">
        {/* Ajoutez votre image et titre ici */}
        <div className="header-img">
          <img src={primaBankLogo} alt="Description de l'image" />
        </div>
        <div className="home-title">
          <h1>PrimaBank</h1>
        </div>

        <div className="home-menu">
          <div className="vertical-menu">
              <div><Link to="/products">Products</Link></div>
              <div><Link to="/accounts">Accounts</Link></div>
              <div><Link to="/statistics">Statistics</Link></div>
          </div>
        </div>

      </div>
  )
      ;
}

export default Nav;
