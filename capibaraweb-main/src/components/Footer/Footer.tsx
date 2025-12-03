import { useState } from "react";
import { Link } from "react-router-dom";
import "./Footer.css";

import Facebook from "../../assets/icons/facebook.png";
import Instagram from "../../assets/icons/instagram.png";
import Tiktok from "../../assets/icons/tiktok.png";
import Logo from "../../assets/images/global/Logo.png";

const Footer = () => {
  const [open, setOpen] = useState(null);

  const toggle = (section) => {
    setOpen(open === section ? null : section);
  };

  return (
    <footer className="footer">
      <div className="footer-container">

        {/* DESKTOP/TABLET */}
        <div className="footer-desktop">

          {/* Logo + copyright */}
          <div className="footer-left">
            <div className="footer-logo">
              <Link to="/">
                <img src={Logo} alt="Logo" className="logo-image" />
              </Link>
            </div>
            <p>© 2025 Immobiliaris. Tutti i diritti riservati.</p>
          </div>

          {/* Colonne Desktop */}
          <div className="footer-columns-desktop">

            {/* Colonna 1 */}
            <div className="footer-col">
              <Link to="/">Home</Link>
              <Link to="/chi-siamo">Chi siamo</Link>
              <Link to="/valutazione">Valutazione</Link>
              <Link to="/faq">FAQ</Link>
            </div>

            {/* Colonna 2 */}
            <div className="footer-col">
              <Link to="/agente">Agenti</Link>
              <Link to="/login-admin">Accesso Dashboard</Link>

              {/* Non cliccabili */}
              <span className="disabled-link">Privacy</span>
              <span className="disabled-link">Policy</span>
            </div>

            {/* Colonna Social */}
            <div className="footer-col">
              <div className="footer-socials">
                <div className="icon"><img src={Instagram} alt="Instagram" /></div>
                <div className="icon"><img src={Tiktok} alt="TikTok" /></div>
                <div className="icon"><img src={Facebook} alt="Facebook" /></div>
              </div>
            </div>

          </div>
        </div>

        {/* ===========================
            MOBILE – Accordion
        ============================ */}
        <div className="footer-cards-mobile">

          {/* Chi siamo */}
          <div className="footer-card">
            <div className="footer-card-header" onClick={() => toggle("chi")}>
              <h4>Chi siamo</h4>
              <span className="arrow">{open === "chi" ? "−" : "+"}</span>
            </div>
            <div className={`footer-card-content ${open === "chi" ? "open" : ""}`}>
              <Link to="/chi-siamo">Chi siamo</Link>
            </div>
          </div>

          {/* Area utenti */}
          <div className="footer-card">
            <div className="footer-card-header" onClick={() => toggle("area")}>
              <h4>Area utenti</h4>
              <span className="arrow">{open === "area" ? "−" : "+"}</span>
            </div>
            <div className={`footer-card-content ${open === "area" ? "open" : ""}`}>
              <Link to="/">Home</Link>
              <Link to="/agente">Agenti</Link>
              <Link to="/login-admin">Accesso Dashboard</Link>
            </div>
          </div>

          {/* Valutazione */}
          <div className="footer-card">
            <div className="footer-card-header" onClick={() => toggle("val")}>
              <h4>Valutazione</h4>
              <span className="arrow">{open === "val" ? "−" : "+"}</span>
            </div>
            <div className={`footer-card-content ${open === "val" ? "open" : ""}`}>
              <Link to="/valutazione">Valutazione</Link>
            </div>
          </div>

          {/* FAQ */}
          <div className="footer-card">
            <div className="footer-card-header" onClick={() => toggle("faq")}>
              <h4>FAQ</h4>
              <span className="arrow">{open === "faq" ? "−" : "+"}</span>
            </div>
            <div className={`footer-card-content ${open === "faq" ? "open" : ""}`}>
              <Link to="/faq">FAQ</Link>
            </div>
          </div>

          {/* Documenti */}
          <div className="footer-card">
            <div className="footer-card-header" onClick={() => toggle("doc")}>
              <h4>Documenti</h4>
              <span className="arrow">{open === "doc" ? "−" : "+"}</span>
            </div>
            <div className={`footer-card-content ${open === "doc" ? "open" : ""}`}>
              <span className="disabled-link">Privacy</span>
              <span className="disabled-link">Policy</span>
            </div>
          </div>

          {/* Contatti */}
          <div className="footer-card">
            <div className="footer-card-header" onClick={() => toggle("cont")}>
              <h4>Contatti</h4>
              <span className="arrow">{open === "cont" ? "−" : "+"}</span>
            </div>
            <div className={`footer-card-content ${open === "cont" ? "open" : ""}`}>
              <div className="footer-socials">
                <div className="icon"><img src={Instagram} alt="Instagram" /></div>
                <div className="icon"><img src={Tiktok} alt="TikTok" /></div>
                <div className="icon"><img src={Facebook} alt="Facebook" /></div>
              </div>
            </div>
          </div>

        </div>

      </div>
    </footer>
  );
};

export default Footer;
