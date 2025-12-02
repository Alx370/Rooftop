import React, { useState } from 'react';
import './chatbot.css';
import chatbotIcon from '../../assets/icons/chatbot.png';
import { sendMessage } from '../../api/chatBotApi';
import ReactMarkdown from 'react-markdown';

const Chatbot: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [messages, setMessages] = useState<Array<{ text: string; sender: 'user' | 'bot' }>>([]);
  const [inputMessage, setInputMessage] = useState('');
  const [isLoading, setIsLoading] = useState(false); // ⬅️ nuovo

  const toggleChat = () => {
    setIsOpen(!isOpen);
  };

  const handleSendMessage = async () => {
    if (inputMessage.trim() === '') return;

    const userMessage = inputMessage;

    // Mostra subito il messaggio dell’utente
    setMessages(prev => [...prev, { text: userMessage, sender: 'user' }]);
    setInputMessage('');

    // Mostra "Sto pensando..."
    setIsLoading(true);
    setMessages(prev => [...prev, { text: "Sto pensando...", sender: 'bot' }]);

    try {
      // Chiamata API reale
      const response = await sendMessage(userMessage);

      // Rimuovi messaggio "Sto pensando..."
      setMessages(prev => prev.filter(msg => msg.text !== "Sto pensando..."));

      // Aggiungi risposta reale
      if (response?.response) {
        setMessages(prev => [...prev, { text: response.response, sender: 'bot' }]);
      }
    } catch (error) {
      setMessages(prev =>
        [...prev.filter(msg => msg.text !== "Sto pensando..."),
         { text: "Errore nel contattare il server. Riprova più tardi.", sender: 'bot' }]
      );
    } finally {
      setIsLoading(false);
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') handleSendMessage();
  };

  return (
    <div className="chatbot-container">
      <div className="chatbot-icon" onClick={toggleChat}>
        <img src={chatbotIcon} alt="Chatbot" />
      </div>

      {isOpen && (
        <div className="chatbot-dropdown">
          <div className="chatbot-header">
            <h3>Chat Assistente</h3>
            <button className="chatbot-close" onClick={toggleChat}>✕</button>
          </div>

          <div className="chatbot-messages">
            {messages.length === 0 ? (
              <div className="chatbot-welcome">
                <p>Ciao! Come posso aiutarti oggi?</p>
              </div>
            ) : (
              messages.map((msg, index) => (
                <div key={index} className={`chatbot-message ${msg.sender}`}>
                  {msg.sender === 'bot' ? (
                    <ReactMarkdown>{msg.text}</ReactMarkdown>
                  ) : (
                    msg.text
                  )}
                </div>
              ))
            )}
          </div>

          <div className="chatbot-input-container">
            <input
              type="text"
              className="chatbot-input"
              placeholder="Scrivi un messaggio..."
              value={inputMessage}
              onChange={(e) => setInputMessage(e.target.value)}
              onKeyPress={handleKeyPress}
            />
            <button className="chatbot-send" onClick={handleSendMessage}>➤</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Chatbot;
