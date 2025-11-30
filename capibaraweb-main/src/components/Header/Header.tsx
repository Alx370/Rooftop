import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Header.css";
import logoBlack from "../../assets/images/global/LogoBlack.png";

import { getMe } from "../../api/authApi.js";

function Header() {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  // Controllo se utente è loggato
  useEffect(() => {
    const checkUser = async () => {
      const token = localStorage.getItem("token");
      if (!token) return;

      try {
        const me = await getMe();
        setUser(me);
      } catch (err) {
        console.warn("Token non valido. Logout.");
        localStorage.removeItem("token");
      }
    };

    checkUser();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    setUser(null);
    navigate("/");
  };

  // Gestione ruolo → redirect pagina corretta
  const goDashboard = () => {
    const ruolo = user?.authorities?.[0];
    if (ruolo === "ROLE_AGENTE") navigate("/agente");
    else navigate("/utente");
  };

  return (
    <header className="header">
      <div className="header-container">

        {/* LOGO */}
        <div className="logo">
          <Link to="/">
            <img src={logoBlack} alt="Capibara Web Logo" className="logo-image" />
          </Link>
        </div>

        {/* NAVIGATION LINKS */}
        <nav className="nav-links">
          <Link to="/">Home</Link>
          <Link to="/chi-siamo">Chi Siamo</Link>
          <Link to="/valutazione">Valuta</Link>
          <Link to="/faq">FAQ</Link>

          {/* SE NON LOGGATO */}
          {!user && (
            <Link to="/login" className="login-link">
              Login
            </Link>
          )}

          {/* SE LOGGATO */}
          {user && (
            <>
              <button className="dashboard-btn" onClick={goDashboard}>
                Dashboard
              </button>

              <button className="logout-btn" onClick={handleLogout}>
                Logout
              </button>
            </>
          )}
        </nav>
      </div>
    </header>
  );
}

export default Header;
