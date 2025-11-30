import React from "react";
import { Link } from "react-router-dom";
import "./Header.css";
import logoBlack from "../../assets/images/global/LogoBlack.png";

function Header() {
  return (
    <header className="header">
      <div className="header-container">
        <div className="logo">
          <Link to="/">
            <img
              src={logoBlack}
              alt="Capibara Web Logo"
              className="logo-image"
            />
          </Link>
        </div>
        <nav className="nav-links">
          <Link to="/">Home</Link>
          <Link to="/chi-siamo">Chi Siamo</Link>
          <Link to="/valutazione">Valuta</Link>
          <Link to="/faq">FAQ</Link>
          <Link to="/login" className="login-link">Login</Link>
        </nav>
      </div>
    </header>
  );
}

export default Header;
