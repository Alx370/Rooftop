import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Header.css";

import { getSession, clearSession } from "../../utils/session.js";

// Resolve logo path via Vite so it works in dev and production builds
const logoUrl = new URL('/src/assets/images/global/LogoBlack.png', import.meta.url).href;

/* ===========================
   TIPI DELLA SESSIONE
=========================== */
interface SessionData {
  userId: string;
  ruolo: string;
  email: string;
  issuedAt: number;
  expiresAt: number;
  token: string;
}

/* ===========================
   COMPONENTE HEADER
=========================== */
export default function Header() {
  const [menuOpen, setMenuOpen] = useState(false);
  const [session, setSession] = useState<SessionData | null>(null);

  const navigate = useNavigate();

  /* ===========================
     CARICA SESSIONE AL MOUNT
  =========================== */
  useEffect(() => {
    const s = getSession();
    setSession(s);
  }, []);

  /* ===========================
     LOGOUT
  =========================== */
  const handleLogout = () => {
    clearSession();
    setSession(null);
    navigate("/");
  };

  /* ===========================
     REDIRECT DASHBOARD
  =========================== */
  const goDashboard = () => {
    const ruolo = session?.ruolo;

    switch (ruolo) {
      case "AGENTE":
        navigate("/agente");
        break;
      case "AMMINISTRATORE":
        navigate("/admin");
        break;
      case "VALUTATORE":
        navigate("/agente");
        break;
      case "PROPRIETARIO":
        navigate("/utente");
        break;
      default:
        navigate("/");
    }
  };

  /* ===========================
     RENDER COMPONENT
  =========================== */
  return (
    <header className="header">
      <div className="header-container">

        {/* LOGO */}
        <div className="logo">
          <Link to="/">
            <img
              src={logoUrl}
              alt="Capibara Web Logo"
              className="logo-image"
            />
          </Link>
        </div>

        {/* HAMBURGER */}
        <div
          className="hamburger"
          onClick={() => setMenuOpen(!menuOpen)}
        >
          <div className={menuOpen ? "bar bar1 active" : "bar bar1"}></div>
          <div className={menuOpen ? "bar bar2 active" : "bar bar2"}></div>
          <div className={menuOpen ? "bar bar3 active" : "bar bar3"}></div>
        </div>

        {/* NAV DESKTOP */}
        <nav className="nav-links">
          <Link to="/">Home</Link>
          <Link to="/chi-siamo">Chi Siamo</Link>
          <Link to="/valutazione">Valutazione</Link>
          <Link to="/faq">FAQ</Link>

          {/* SWITCH LOGIN / DASHBOARD */}
          {!session ? (
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

          {!session ? (
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
