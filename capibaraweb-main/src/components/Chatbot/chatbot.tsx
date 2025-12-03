import React, { useState } from 'react';
import './chatbot.css';
import chatbotIcon from '../../assets/icons/chat_Icon.jpg';
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

/**
 * @fileoverview Chatbot component for real-time user assistance
 * 
 * @description
 * This component implements an interactive chatbot interface that allows users to communicate
 * with an AI assistant. It features a toggleable chat window, message history, loading states,
 * and markdown support for bot responses.
 * 
 * @component Chatbot
 * @returns {React.FC} A functional React component that renders a chatbot interface
 * 
 * @state {boolean} isOpen - Controls the visibility of the chat window
 * @state {Array<{text: string, sender: 'user'|'bot'}>} messages - Array storing the conversation history
 * @state {string} inputMessage - Current text input from the user
 * @state {boolean} isLoading - Indicates whether the bot is processing a request
 * 
 * @function toggleChat
 * @description Toggles the visibility of the chat window
 * @returns {void}
 * 
 * @function handleSendMessage
 * @async
 * @description Handles sending a user message to the chatbot API and processing the response
 * @returns {Promise<void>}
 * @throws {Error} Displays an error message if the API call fails
 * 
 * @function handleKeyPress
 * @description Handles keyboard events, specifically triggering message send on Enter key
 * @param {React.KeyboardEvent} e - The keyboard event object
 * @returns {void}
 * 
 * @dependencies
 * - react: Core React library for component creation
 * - react-markdown: Library for rendering markdown content in bot messages
 * - ../../api/chatBotApi: API module for sending messages to the backend
 * 
 * @styling
 * - ./chatbot.css: Contains all component-specific styles
 * 
 * @assets
 * - chatbotIcon: Icon image displayed on the chatbot toggle button
 * 
 * @example
 * // Basic usage
 * import Chatbot from './components/Chatbot/chatbot';
 * 
 * function App() {
 *   return (
 *     <div>
 *       <Chatbot />
 *     </div>
 *   );
 * }
 * 
 * @author Rooftop Development Team
 * @version 1.0.0
 */

