import { useState } from "react";
import { Link } from "react-router-dom";
import "./Footer.css";

import Facebook from "../../assets/icons/facebook.png";
import Instagram from "../../assets/icons/instagram.png";
import Linkedin from "../../assets/icons/linkedin.png";
import Tiktok from "../../assets/icons/tiktok.png";
import Logo from "../../assets/images/Logo.png";

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
            <p>© 2025 ImmobiliareS. Tutti i diritti riservati.</p>
          </div>

          {/* Colonne desktop */}
          <div className="footer-columns-desktop">

            {/* Chi siamo */}
            <div className="footer-col">
              <h4>Chi siamo</h4>
              <Link to="/login-admin">Accesso Dashboard</Link>
              <Link to="/agente">Agenti</Link>          
              <a href="#">Link here</a>
              <a href="#">Link here</a>
            </div>

            {/* Valutazione */}
            <div className="footer-col">
              <h4>Valutazione</h4>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
            </div>

            {/* FAQ */}
            <div className="footer-col">
              <h4>FAQ</h4>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
            </div>

            {/* Contatti */}
            <div className="footer-col">
              <h4>Contatti</h4>
              <div className="footer-socials">
                <div className="icon"><img src={Linkedin} alt="Linkedin" /></div>
                <div className="icon"><img src={Instagram} alt="Instagram" /></div>
                <div className="icon"><img src={Tiktok} alt="TikTok" /></div>
                <div className="icon"><img src={Facebook} alt="Facebook" /></div>
              </div>
            </div>

          </div>
        </div>

        {/* ===========================
            AREE MOBILE (accordion)
        ============================ */}
        <div className="footer-cards-mobile">

          {/* Chi siamo */}
          <div className="footer-card">
            <div className="footer-card-header" onClick={() => toggle("chi")}>
              <h4>Chi siamo</h4>
              <span className="arrow">{open === "chi" ? "−" : "+"}</span>
            </div>

            <div className={`footer-card-content ${open === "chi" ? "open" : ""}`}>
              <Link to="/login-admin">Accesso Dashboard</Link>

              {/* AGGIUNTO IN MOBILE */}
              <Link to="/agente">Agenti</Link>

              <a href="#">Link here</a>
              <a href="#">Link here</a>
            </div>
          </div>

          {/* Valutazione */}
          <div className="footer-card">
            <div className="footer-card-header" onClick={() => toggle("val")}>
              <h4>Valutazione</h4>
              <span className="arrow">{open === "val" ? "−" : "+"}</span>
            </div>

            <div className={`footer-card-content ${open === "val" ? "open" : ""}`}>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
            </div>
          </div>

          {/* FAQ */}
          <div className="footer-card">
            <div className="footer-card-header" onClick={() => toggle("faq")}>
              <h4>FAQ</h4>
              <span className="arrow">{open === "faq" ? "−" : "+"}</span>
            </div>

            <div className={`footer-card-content ${open === "faq" ? "open" : ""}`}>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
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
                <div className="icon"><img src={Linkedin} alt="Linkedin" /></div>
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
