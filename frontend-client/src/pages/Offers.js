import React from "react";
import { useParams } from "react-router-dom";
import Offers from "../components/Offers";
import primaBankLogo from "../res/img/primaBankLogo.png";
import "./styles/offers.css"

export default function OffersPage() {
    const { idClient } = useParams();

    return (
        <>
            <div className="header-offers">
                {/* Ajoutez votre image et titre ici */}
                <div className="header-img">
                    <img src={primaBankLogo} alt="Description de l'image" />
                </div>
                <div className="header-title">
                    <h1>List des offres</h1>
                </div>
            </div>

            <Offers idClient={idClient} />
        </>
    );
}