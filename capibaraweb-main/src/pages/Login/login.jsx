import React from "react";
import FormLogin from "../../components/Forms/FormLogin/FormLogin";
import "./login.css";


const Login = () => {
  return (
    <div className="login-container">
      <h2 className="login-title">Accedi alla tua area</h2>
      <FormLogin />
    </div>
  );
};

export default Login;
