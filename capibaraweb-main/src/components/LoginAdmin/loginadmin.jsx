import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "./loginadmin.css";

export default function LoginAdmin() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    function handleLogin(e) {
        e.preventDefault();
        setError("");

        if (!email.endsWith("@immobiliaris.it")) {
            setError("Accesso riservato agli agenti immobiliari.");
            return;
        }

        // Salvo la sessione finta
        localStorage.setItem("adminLogged", "true");

        navigate("/agente");
    }

    return (
        <div className="login-admin">
            <section>
                <h1 className="title-admin">Accesso Amministrazione</h1>
                <div className="login-card-admin">
                    <h2 className="login-title-admin">Accedi ora</h2>
                    <p className="login-subtitle-admin">Benvenuto nell'area amministrativa</p>

                    <form className="login-form-admin" onSubmit={handleLogin}>
                        <div className="form-group-admin">
                            <label>Email</label>
                            <input
                                type="email"
                                placeholder="Inserisci la tua email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </div>

                        <div className="form-group-admin">
                            <label>Password</label>
                            <input
                                type="password"
                                placeholder="Inserisci la tua password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>

                        {error && <p className="login-error-admin">{error}</p>}

                        <div className="options-admin">
                            <a href="#" className="forgot-password-admin">
                                Password dimenticata?
                            </a>
                        </div>

                        <button type="submit" className="login-button-admin">
                            Login
                        </button>
                    </form>
                </div>
            </section>
        </div>
    );
}

/**
 * @fileoverview Admin login component for real estate agents
 * 
 * @description
 * This component provides a specialized login interface for administrative users,
 * specifically real estate agents with @immobiliaris.it email addresses. It validates
 * email domains and manages authentication sessions for administrative access.
 * 
 * @component LoginAdmin
 * @returns {JSX.Element} A functional React component that renders the admin login form
 * 
 * @state {string} email - The email input value entered by the user
 * @state {string} password - The password input value entered by the user
 * @state {string} error - Error message displayed when validation fails
 * 
 * @function handleLogin
 * @description Handles the login form submission with email validation and session creation
 * @param {Event} e - The form submission event
 * @returns {void}
 * @validation
 * - Email must end with "@immobiliaris.it"
 * - Displays error message if validation fails
 * @sideEffects
 * - Sets "adminLogged" flag in localStorage on successful validation
 * - Navigates to "/agente" route after successful login
 * 
 * @dependencies
 * - react: Core React library with hooks (useState)
 * - react-router-dom: Routing library for navigation (useNavigate, Link)
 * 
 * @styling
 * - ./loginadmin.css: Contains all component-specific styles
 * 
 * @features
 * - Domain-based email validation (@immobiliaris.it)
 * - Error handling with user feedback
 * - Session persistence via localStorage
 * - Password recovery link (placeholder)
 * - Automatic redirection after successful login
 * 
 * @security
 * - Client-side email domain validation
 * - Password field with masked input
 * 
 * @example
 * // Basic usage
 * import LoginAdmin from './components/LoginAdmin/loginadmin';
 * 
 * function App() {
 *   return (
 *     <div>
 *       <LoginAdmin />
 *     </div>
 *   );
 * }
 * 
 * @author Rooftop Development Team
 * @version 1.0.0
 */
