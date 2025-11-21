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
