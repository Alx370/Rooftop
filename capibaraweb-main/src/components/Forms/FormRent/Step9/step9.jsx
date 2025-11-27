import React from "react";
import ProgressBar from "../ProgressBar/ProgressBar";
import styles from "./step9.module.css";

export default function Step9({ nextStep }) {
  return (
    <div className={styles.container}>
      <ProgressBar currentStep={9} totalSteps={10} />
    </div>
  );
}