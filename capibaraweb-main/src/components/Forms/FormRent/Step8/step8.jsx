import React from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./step8.module.css";

export default function Step8({ nextStep }) {
  return (
    <div className={styles.container}>
      <ProgressBar currentStep={8} totalSteps={10} />
    </div>
  );
}