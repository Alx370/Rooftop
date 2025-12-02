import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Header.css";

import { getMe } from "../../api/authApi.js";

interface UserMe {
  authorities: string[];
  nome: string;
  cognome: string;
  email: string;
}


function Header() {
  const [menuOpen, setMenuOpen] = useState(false);
  const navigate = useNavigate();
  const [user, setUser] = useState<UserMe | null>(null);


  // Carica utente autenticato
  useEffect(() => {
    const loadUser = async () => {
      try {
        const data = await getMe();
        setUser(data);
      } catch (e) {
        setUser(null);
      }
    };

    loadUser();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    setUser(null);
    navigate("/");
  };

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
            <img
              src="/src/assets/images/LogoBlack.png"
              alt="Capibara Web Logo"
              className="logo-image"
            />
          </Link>
        </div>

        {/* HAMBURGER MENU */}
        <div
          className="hamburger"
          onClick={() => setMenuOpen(!menuOpen)}
        >
          <div className={menuOpen ? "bar bar1 active" : "bar bar1"}></div>
          <div className={menuOpen ? "bar bar2 active" : "bar bar2"}></div>
          <div className={menuOpen ? "bar bar3 active" : "bar bar3"}></div>
        </div>

        {/* DESKTOP NAV */}
        <nav className="nav-links">
          <Link to="/">Home</Link>
          <Link to="/chi-siamo">Chi Siamo</Link>
          <Link to="/valutazione">Valutazione</Link>
          <Link to="/faq">FAQ</Link>

          {/* LOGIN / DASHBOARD SWITCH */}
          {!user ? (
            <Link to="/login" className="login-link">Login</Link>
          ) : (
            <>
              <button className="login-link" onClick={goDashboard}>
                Dashboard
              </button>
              <button className="login-link logout" onClick={handleLogout}>
                Logout
              </button>
            </>
          )}
        </nav>
      </div>

      {/* MOBILE MENU */}
      {menuOpen && (
        <nav className="mobile-menu">
          <Link to="/" onClick={() => setMenuOpen(false)}>Home</Link>
          <Link to="/chi-siamo" onClick={() => setMenuOpen(false)}>Chi Siamo</Link>
          <Link to="/valutazione" onClick={() => setMenuOpen(false)}>Valutazione</Link>
          <Link to="/faq" onClick={() => setMenuOpen(false)}>FAQ</Link>

          {/* LOGIN / DASHBOARD MOBILE VERSION */}
          {!user ? (
            <Link
              to="/login"
              className="login-link"
              onClick={() => setMenuOpen(false)}
            >
              Login
            </Link>
          ) : (
            <>
              <button
                className="login-link"
                onClick={() => {
                  goDashboard();
                  setMenuOpen(false);
                }}
              >
                Dashboard
              </button>

              <button
                className="login-link logout"
                onClick={() => {
                  handleLogout();
                  setMenuOpen(false);
                }}
              >
                Logout
              </button>
            </>
          )}
        </nav>
      )}
    </header>
  );
}

export default Header;
