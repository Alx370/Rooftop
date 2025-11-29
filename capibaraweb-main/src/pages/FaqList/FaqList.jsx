import React, { useEffect, useState } from "react";

export default function FaqList() {
  const [faq, setFaq] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch("http://localhost:8080/api/faq")
      .then(res => res.json())
      .then(data => {
        setFaq(data);
        setLoading(false);
      })
      .catch(err => {
        console.error("Errore GET FAQ:", err);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Caricamento FAQ...</p>;

  return (
    <div style={{ padding: "30px" }}>
      <h1>Lista FAQ</h1>

      {faq.length === 0 && <p>Nessuna FAQ trovata.</p>}

      <ul>
        {faq.map(item => (
          <li key={item.id} style={{ marginBottom: "15px" }}>
            <strong>{item.domanda}</strong>
            <p>{item.risposta}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}