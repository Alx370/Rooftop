import { useState } from "react";
import { Routes, Route, useNavigate } from "react-router-dom";

import Step0 from "./Step0/step0";
import Step1 from "./Step1/step1";
import Step2 from "./Step2/step2";
import Step3 from "./Step3/step3";
import Step4 from "./Step4/step4";
import Step5 from "./Step5/step5";
import Step6 from "./Step6/step6";
import Step7 from "./Step7/step7";
import Step8 from "./Step8/step8";

export default function FormBuy() {
  const navigate = useNavigate();

  // Stato centrale con tutti i campi del form
  const [formData, setFormData] = useState({
    nome: "",
    cognome: "",
    email: "",
    telefono: "",
    indirizzo: "",
    tipologia: "",
    rooms: "",
    bathrooms: "",
    furnishing: "",
    doubleExposure: "",
    surface: "",
    floor: "",
    externalFeatures: [],
    serviceType: "",
    message: "",
    password: "",
  });

  // Funzioni per navigare tra gli step
  const nextStep = (currentStep) => navigate(`/formbuy/step${currentStep + 1}`);
  const prevStep = (currentStep) => navigate(`/formbuy/step${currentStep - 1}`);

  return (
    <div className="form-container">
      <Routes>
        <Route
          path="step0"
          element={<Step0 formData={formData} setFormData={setFormData} nextStep={() => nextStep(0)} />}
        />
        <Route
          path="step1"
          element={<Step1 formData={formData} setFormData={setFormData} nextStep={() => nextStep(1)} prevStep={() => prevStep(1)} />}
        />
        <Route
          path="step2"
          element={<Step2 formData={formData} setFormData={setFormData} nextStep={() => nextStep(2)} prevStep={() => prevStep(2)} />}
        />
        <Route
          path="step3"
          element={<Step3 formData={formData} setFormData={setFormData} nextStep={() => nextStep(3)} prevStep={() => prevStep(3)} />}
        />
        <Route
          path="step4"
          element={<Step4 formData={formData} setFormData={setFormData} nextStep={() => nextStep(4)} prevStep={() => prevStep(4)} />}
        />
        <Route
          path="step5"
          element={<Step5 formData={formData} setFormData={setFormData} nextStep={() => nextStep(5)} prevStep={() => prevStep(5)} />}
        />
        <Route
          path="step6"
          element={<Step6 formData={formData} setFormData={setFormData} nextStep={() => nextStep(6)} prevStep={() => prevStep(6)} />}
        />
        <Route
          path="step7"
          element={<Step7 formData={formData} setFormData={setFormData} nextStep={() => nextStep(7)} prevStep={() => prevStep(7)} />}
        />
        <Route
          path="step8"
          element={<Step8 formData={formData} setFormData={setFormData} prevStep={() => prevStep(8)} />}
        />
      </Routes>
    </div>
  );
}
