import { Link } from "react-router-dom";
import "./Footer.css";

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-container">
        {/* Colonna sinistra: logo e copyright */}
        <div className="footer-left">
          <div className="footer-logo"><Link to="/">
            <img
              src="/src/assets/images/Logo.png"
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
                  <img src="src/assets/icons/linkedin.png" alt="" />
                </a>
              </div>
              <div className="icon">
                <a href="">
                  <img src="src/assets/icons/instagram.png" alt="" />
                </a>
              </div>
              <div className="icon">
                <a href="">
                  <img src="src/assets/icons/tiktok.png" alt="" />
                </a>
              </div>
              <div className="icon">
                <a href="">
                  <img src="src/assets/icons/facebook.png" alt="" />
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
