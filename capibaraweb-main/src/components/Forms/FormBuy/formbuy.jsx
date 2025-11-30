import { useState } from "react";
import { Routes, Route, useNavigate } from "react-router-dom";

// Import degli step
import Step0 from "./Step0/step0";
import Step1 from "./Step1/step1";
import Step2 from "./Step2/step2";
import Step3 from "./Step3/step3";
import Step4 from "./Step4/step4";
import Step5 from "./Step5/step5";
import Step6 from "./Step6/step6";
import Step7 from "./Step7/step7";
import Step8 from "./Step8/step8";

// API
import { inviaValutazioneApi } from "@api/formBuyApi.js";

export default function FormBuy() {
  const navigate = useNavigate();

  // -----------------------------
  // STATO CENTRALE DEL FORM
  // -----------------------------
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

  // -----------------------------
  // NAVIGAZIONE TRA GLI STEP
  // -----------------------------
  const nextStep = (currentStep) => navigate(`/formbuy/step${currentStep + 1}`);
  const prevStep = (currentStep) => navigate(`/formbuy/step${currentStep - 1}`);

  // -----------------------------
  // SUBMIT FINALE DEL FORM
  // -----------------------------
  const handleSubmit = async () => {
    try {
      console.log("Invio dati:", formData);

      const response = await inviaValutazioneApi(formData);

      console.log("Risposta API:", response);

      navigate("/valutazione/successo");

    } catch (error) {
      console.error("Errore invio form:", error);
      alert("Errore durante l’invio della richiesta.");
    }
  };

  // -----------------------------
  // RENDER
  // -----------------------------
  return (
    <div className="form-container">

      <Routes>

        {/* STEP 0 */}
        <Route
          path="step0"
          element={
            <Step0
              formData={formData}
              setFormData={setFormData}
              nextStep={() => nextStep(0)}
            />
          }
        />

        {/* STEP 1 */}
        <Route
          path="step1"
          element={
            <Step1
              formData={formData}
              setFormData={setFormData}
              nextStep={() => nextStep(1)}
              prevStep={() => prevStep(1)}
            />
          }
        />

        {/* STEP 2 */}
        <Route
          path="step2"
          element={
            <Step2
              formData={formData}
              setFormData={setFormData}
              nextStep={() => nextStep(2)}
              prevStep={() => prevStep(2)}
            />
          }
        />

        {/* STEP 3 */}
        <Route
          path="step3"
          element={
            <Step3
              formData={formData}
              setFormData={setFormData}
              nextStep={() => nextStep(3)}
              prevStep={() => prevStep(3)}
            />
          }
        />

        {/* STEP 4 */}
        <Route
          path="step4"
          element={
            <Step4
              formData={formData}
              setFormData={setFormData}
              nextStep={() => nextStep(4)}
              prevStep={() => prevStep(4)}
            />
          }
        />

        {/* STEP 5 */}
        <Route
          path="step5"
          element={
            <Step5
              formData={formData}
              setFormData={setFormData}
              nextStep={() => nextStep(5)}
              prevStep={() => prevStep(5)}
            />
          }
        />

        {/* STEP 6 */}
        <Route
          path="step6"
          element={
            <Step6
              formData={formData}
              setFormData={setFormData}
              nextStep={() => nextStep(6)}
              prevStep={() => prevStep(6)}
            />
          }
        />

        {/* STEP 7 */}
        <Route
          path="step7"
          element={
            <Step7
              formData={formData}
              setFormData={setFormData}
              nextStep={() => nextStep(7)}
              prevStep={() => prevStep(7)}
            />
          }
        />

        {/* STEP 8 (INVIO FINALE) */}
        <Route
          path="step8"
          element={
            <Step8
              formData={formData}
              setFormData={setFormData}
              prevStep={() => prevStep(8)}
              onSubmit={handleSubmit}
            />
          }
        />

      </Routes>

    </div>
  );
}
