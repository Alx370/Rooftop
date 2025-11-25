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

        {/* DESKTOP/TABLET WRAPPER */}
        <div className="footer-desktop">

          {/* Logo */}
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

            <div className="footer-col">
              <h4>Chi siamo</h4>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
            </div>

            <div className="footer-col">
              <h4>Valutazione</h4>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
            </div>

            <div className="footer-col">
              <h4>FAQ</h4>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
              <a href="#">Link here</a>
            </div>

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

        {/* MOBILE — CARD A TENDINA */}
        <div className="footer-cards-mobile">

          {[
            { id: "chi", title: "Chi siamo", links: ["Link here", "Link here", "Link here", "Link here"] },
            { id: "val", title: "Valutazione", links: ["Link here", "Link here", "Link here", "Link here"] },
            { id: "faq", title: "FAQ", links: ["Link here", "Link here", "Link here", "Link here"] },
          ].map((section) => (
            <div key={section.id} className="footer-card">
              <div className="footer-card-header" onClick={() => toggle(section.id)}>
                <h4>{section.title}</h4>
                <span className="arrow">{open === section.id ? "−" : "+"}</span>
              </div>

              <div className={`footer-card-content ${open === section.id ? "open" : ""}`}>
                {section.links.map((l, i) => (
                  <a key={i} href="#">{l}</a>
                ))}
              </div>
            </div>
          ))}

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
