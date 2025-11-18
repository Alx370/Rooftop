import React from "react";
import styles from "./ProgressBar.module.css"; // IMPORT corretto per CSS Module

export default function ProgressBar({ currentStep, totalSteps }) {
  const progressPercent = (currentStep / totalSteps) * 100;

  return (
    <div className={styles.container}>
      <div
        className={styles.fill}
        style={{ width: `${progressPercent}%` }}
      />
    </div>
  );
}
