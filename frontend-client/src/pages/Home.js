import Nav from "../components/Nav";
import {useState} from "react";
import { Link } from "react-router-dom";
import "./styles/home.css"

// ... le reste de votre code

function HomePage() {

  const client = localStorage.getItem("client")

  return (
    <div>
      <Nav/>
      <div className="info-client-content">
        <div className="info-client">
          {
            client !== null ? (
                <div className="infos">
                    <div className="info-id">
                        <div>Welcome : {client}</div>
                    </div>
                    <div className="info-offers">
                        <Link to={`/offers/${client}`}>
                            <button className="info-button">Go to Offers</button>
                        </Link>
                    </div>
                </div>
            ) : (
              <div>Please log in or create an account.</div>
            )
          }
        </div>
      </div>
    </div>
  );
}

export default HomePage;