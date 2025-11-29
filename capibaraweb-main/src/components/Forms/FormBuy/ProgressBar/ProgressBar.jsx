import styles from "./ProgressBar.module.css";

export default function ProgressBar({ currentStep = 1, totalSteps = 9 }) {
  const steps = Array.from({ length: totalSteps }, (_, i) => i + 1);

  return (
    <div className={styles.container}>
      
      {/* Linea lilla di base */}
      <div className={styles.baseLine} />

      {/* Linea arancione progressiva */}
      <div
        className={styles.progressLine}
        style={{
          width: `${((currentStep - 1) / (totalSteps - 1)) * 100}%`,
        }}
      />

      <div className={styles.steps}>
        {steps.map((step) => (
          <div key={step} className={styles.stepWrapper}>
            <div
              className={`${styles.step} ${
                step <= currentStep ? styles.active : ""
              }`}
            >
              {step}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
