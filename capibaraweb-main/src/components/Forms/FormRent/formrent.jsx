import { useEffect, useState } from "react";
import { Routes, Route, useNavigate, Navigate } from "react-router-dom";

import Step0 from "./Step0/step0";
import Step1 from "./Step1/step1";
import Step2 from "./Step2/step2";
import Step3 from "./Step3/step3";
import Step4 from "./Step4/step4";
import Step5 from "./Step5/step5";
import Step6 from "./Step6/step6";
import Step7 from "./Step7/step7";
import Step8 from "./Step8/step8";
import Step9 from "./Step9/step9";
import Step10 from "./Step10/step10";

export default function FormRent({ manual = false }) {
  const navigate = useNavigate();

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
   contractType: "",
  message: "",
  password: "",
});


  const basePath = manual ? "/formrent-manuale" : "/formrent";
  const nextStep = (currentStep) => navigate(`${basePath}/step${currentStep + 1}`);
  const prevStep = (currentStep) => navigate(`${basePath}/step${currentStep - 1}`);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) navigate(`/login?redirect=${basePath}/step0`);
  }, [basePath, navigate]);

  

  return (
    <div className="form-container">
      <Routes>

        {/* Redirect iniciale */}
        <Route index element={<Navigate to="step0" replace />} />

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

        <Route
          path="step8"
          element={
            <Step8
              formData={formData}
              setFormData={setFormData}
              nextStep={() => nextStep(8)}
              prevStep={() => prevStep(8)}
            />
          }
        />

        <Route
          path="step9"
          element={
            <Step9
              formData={formData}
              setFormData={setFormData}
              nextStep={() => nextStep(9)}
              prevStep={() => prevStep(9)}
            />
          }
        />

        <Route
          path="step10"
          element={
            <Step10
              formData={formData}
              setFormData={setFormData}
              manual={manual}
              prevStep={() => prevStep(10)}
            />
          }
        />
      </Routes>
    </div>
  );
}
