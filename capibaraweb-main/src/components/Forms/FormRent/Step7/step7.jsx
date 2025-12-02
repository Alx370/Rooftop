import { useState } from "react";
import styles from "./step7.module.css";
import ProgressBar from "../ProgressBar/Progressbar";

// Icone
import balconyIcon from "../../../../assets/icons/balcony.png";
import patioIcon from "../../../../assets/icons/patio.png";
import parkingIcon from "../../../../assets/icons/parking.png";
import cellarIcon from "../../../../assets/icons/cellar.png";
import gardenIcon from "../../../../assets/icons/garden.png";
import poolIcon from "../../../../assets/icons/pool.png";
import courtyardIcon from "../../../../assets/icons/courtyard.png";
import garageIcon from "../../../../assets/icons/garage.png";
import verandaIcon from "../../../../assets/icons/veranda.png";

export default function Step7({ formData, setFormData, nextStep, prevStep }) {
  const [selectedFeatures, setSelectedFeatures] = useState(
    formData.externalFeatures || []
  );
  const [error, setError] = useState("");

  const features = [
    { id: "balcony", label: "Balcone / Terrazzo", icon: balconyIcon },
    { id: "patio", label: "Patio / Portico coperto", icon: patioIcon },
    { id: "parking", label: "Posto auto scoperto", icon: parkingIcon },
    { id: "cellar", label: "Cantina / Deposito", icon: cellarIcon },
    { id: "garden", label: "Giardino privato", icon: gardenIcon },
    { id: "pool", label: "Piscina privata", icon: poolIcon },
    { id: "courtyard", label: "Cortile comune", icon: courtyardIcon },
    { id: "garage", label: "Box auto / Garage", icon: garageIcon },
    { id: "veranda", label: "Veranda / Pergolato", icon: verandaIcon },
  ];

  const toggleFeature = (id) => {
    setSelectedFeatures((prev) =>
      prev.includes(id) ? prev.filter((f) => f !== id) : [...prev, id]
    );
  };

  const handleContinue = () => {
    if (selectedFeatures.length === 0) {
      setError("Seleziona almeno una dotazione esterna");
      return;
    }

    setError("");
    setFormData({ ...formData, externalFeatures: selectedFeatures });
    nextStep();
  };

  return (
    <div className={styles.container}>
      <ProgressBar currentStep={7} totalSteps={10} />

      <h2 className={styles.title}>Quali sono le dotazioni esterne dell'immobile?</h2>

      <div className={styles.grid}>
        {features.map((feature) => (
          <div
            key={feature.id}
            className={`${styles.featureCard} ${
              selectedFeatures.includes(feature.id) ? styles.selected : ""
            }`}
            onClick={() => toggleFeature(feature.id)}
          >
            <img src={feature.icon} alt={feature.label} className={styles.icon} />
            <span>{feature.label}</span>
          </div>
        ))}
      </div>

      {error && <div className={styles.error}>{error}</div>}

      <div className={styles.buttonContainer}>
        <button className={styles.backButton} onClick={prevStep}>
          Indietro
        </button>
        <button className={styles.continueButton} onClick={handleContinue}>
          Continua
        </button>
      </div>
    </div>
  );
}
