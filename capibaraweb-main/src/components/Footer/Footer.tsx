import { Link } from "react-router-dom";
import "./Footer.css";
import logo from "@assets/images/global/Logo.png";
import linkedinIcon from "@assets/icons/linkedin.png";
import instagramIcon from "@assets/icons/instagram.png";
import tiktokIcon from "@assets/icons/tiktok.png";
import facebookIcon from "@assets/icons/facebook.png";

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-container">
        {/* Colonna sinistra: logo e copyright */}
        <div className="footer-left">
          <div className="footer-logo"><Link to="/">
            <img
              src={logo}
              alt="Capibara Web Logo"
              className="logo-image"
            />
          </Link></div>
          <p>© 2025 ImmobiliareS. Tutti i diritti riservati.</p>
        </div>

        {/* Colonna: Chi siamo */}
        <div className="footer-column">
          <h4>Chi siamo</h4>
          <Link to="/agente">Accesso Dashboard</Link>
          <a href="#">Link here</a>
          <a href="#">Link here</a>
          <a href="#">Link here</a>
        </div>

        {/* Colonna: Valutazione */}
        <div className="footer-column">
          <h4>Valutazione</h4>
          <a href="#">Link here</a>
          <a href="#">Link here</a>
          <a href="#">Link here</a>
          <a href="#">Link here</a>
        </div>

        {/* Colonna: FAQ */}
        <div className="footer-column">
          <h4>FAQ</h4>
          <a href="#">Link here</a>
          <a href="#">Link here</a>
          <a href="#">Link here</a>
          <a href="#">Link here</a>
        </div>

        {/* Colonna: Contatti con cerchi */}
        <div className="footer-column">
          <div className="contact">
            <h4>Contatti</h4>
            <div className="footer-socials">
              <div className="icon">
                <a href="">
                  <img src={linkedinIcon} alt="" />
                </a>
              </div>
              <div className="icon">
                <a href="">
                  <img src={instagramIcon} alt="" />
                </a>
              </div>
              <div className="icon">
                <a href="">
                  <img src={tiktokIcon} alt="" />
                </a>
              </div>
              <div className="icon">
                <a href="">
                  <img src={facebookIcon} alt="" />
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
