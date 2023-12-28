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
            <div>
              <Link aria-current="page"
                    className=""
                    to="/">Home</Link>
            </div>
              <div>
              <Link
                  className=""
                  to={`/history/${client}`}>History</Link>
            </div>

            <div className="home-login">
              {localStorage.getItem("client") !== null ? (
                  /* Render different content when the user is authenticated */
                  <>
                    <div
                        className=""
                    >
                      Account [ {localStorage.getItem("client")} ]
                    </div>
                    <div
                        className=""
                        onClick={disconnect}
                    >
                      Disconnect
                    </div>
                  </>
              ) : (
                  /* Render Login and Signup links when the user is not authenticated */
                  <>
                    <Link
                        className=""
                        to="/login"
                    >
                      Login
                    </Link>
                    <Link
                        className=""
                        to="/signup"
                    >
                      Signup
                    </Link>
                  </>
              )}
            </div>
          </div>

      </div>
  )
    ;
}

export default Nav;
