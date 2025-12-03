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

/**
 * FAQ List Component for Immobiliaris Real Estate Agency.
 *
 * Renders a list of frequently asked questions fetched from the backend API.
 * Displays questions and answers in a simple list format with loading states
 * and error handling for failed API requests.
 *
 * Features:
 * - Fetches FAQ data from REST API on component mount
 * - Loading state while data is being fetched
 * - Error handling with console logging
 * - Simple list rendering of questions and answers
 * - Empty state message when no FAQs are available
 *
 * @component
 * @returns {JSX.Element} The rendered FAQ list page
 *
 * @example
 * <FaqList />
 *
 * @typedef {Object} FAQ
 * @property {number} id - Unique identifier for the FAQ
 * @property {string} domanda - The question text
 * @property {string} risposta - The answer text
 *
 * API endpoint: GET http://localhost:8080/api/faq
 */

