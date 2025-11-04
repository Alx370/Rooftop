import { NavLink } from "react-router-dom";
import "./Header.css";

const Header = () => {
  return (
    <header className="header">
      <div className="header-container">
        <div className="header-logo">Capibara</div>

        <nav className="header-nav">
          <NavLink to="/" className="nav-link">
            Home
          </NavLink>
          <NavLink to="/catalogo" className="nav-link">
            Catalogo
          </NavLink>
          <NavLink to="/chi-siamo" className="nav-link">
            Chi Siamo
          </NavLink>
          <NavLink to="/Valutazione" className="nav-link">
            Valuta
          </NavLink>
          <NavLink to="/login" className="nav-link">
            Login
          </NavLink>
        </nav>
      </div>
    </header>
  );
};

export default Header;
