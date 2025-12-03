import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./Header.css";
// usa percorso originale

function Header() {
  const [menuOpen, setMenuOpen] = useState(false);

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
          <Link to="/login" className="login-link">Login</Link>
        </nav>
      </div>

      {/* MOBILE NAV */}
      {menuOpen && (
        <nav className="mobile-menu">
          <Link to="/" onClick={() => setMenuOpen(false)}>Home</Link>
          <Link to="/chi-siamo" onClick={() => setMenuOpen(false)}>Chi Siamo</Link>
          <Link to="/valutazione" onClick={() => setMenuOpen(false)}>Valutazione</Link>
          <Link to="/faq" onClick={() => setMenuOpen(false)}>FAQ</Link>
          <Link to="/login" className="login-link" onClick={() => setMenuOpen(false)}>Login</Link>
        </nav>
      )}
    </header>
  );
}

export default Header;
