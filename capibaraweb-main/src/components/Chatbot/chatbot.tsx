import React, { useState } from 'react';
import './chatbot.css';
import chatbotIcon from '../../assets/icons/chatbot.png';

const Chatbot: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [messages, setMessages] = useState<Array<{ text: string; sender: 'user' | 'bot' }>>([]);
  const [inputMessage, setInputMessage] = useState('');

  const toggleChat = () => {
    setIsOpen(!isOpen);
  };

  const handleSendMessage = () => {
    if (inputMessage.trim() === '') return;

    // Aggiungi il messaggio dell'utente
    setMessages([...messages, { text: inputMessage, sender: 'user' }]);
    
    // Simula una risposta del bot (qui puoi integrare la tua API)
    setTimeout(() => {
      setMessages(prev => [...prev, { 
        text: 'Grazie per il tuo messaggio! Come posso aiutarti?', 
        sender: 'bot' 
      }]);
    }, 500);

    setInputMessage('');
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') {
      handleSendMessage();
    }
  };

  return (
    <div className="chatbot-container">
      {/* Icona circolare */}
      <div className="chatbot-icon" onClick={toggleChat}>
        <img src={chatbotIcon} alt="Chatbot" />
      </div>

      {/* Menu a tendina della chat */}
      {isOpen && (
        <div className="chatbot-dropdown">
          <div className="chatbot-header">
            <h3>Chat Assistente</h3>
            <button className="chatbot-close" onClick={toggleChat}>
              ✕
            </button>
          </div>

          <div className="chatbot-messages">
            {messages.length === 0 ? (
              <div className="chatbot-welcome">
                <p>Ciao! Come posso aiutarti oggi?</p>
              </div>
            ) : (
              messages.map((msg, index) => (
                <div key={index} className={`chatbot-message ${msg.sender}`}>
                  {msg.text}
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
            <button className="chatbot-send" onClick={handleSendMessage}>
              ➤
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Chatbot;