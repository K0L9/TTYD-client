import { useEffect, useState } from "react";
import Message, { IMessageProps } from "../components/message";

const Chat = () => {
  const [messages, setMessages] = useState<Array<IMessageProps>>([]);

  useEffect(() => {
    setMessages([
      { value: "Hello", isBot: true },
      {
        value:
          "Dla każdego podatnika (NIP) dla każdego miesiąca (ROKMC) oraz identyfikatora nagłówka (NAGLOWEK_ID) oblicz sumy kontrolne: liczbę faktur po stronie zakupów (DOWOD_ZAKUPU) , liczbę faktur po stronie sprzedaży (DOWOD_SPRZEDAZY), liczbę dostawców (NR_DOSTAWCY), liczbę kontrahentów (NR_KONTRAHENTA), wartość netto sprzedaży (P_96)",
        isBot: false,
      },
      { value: "Hello123", isBot: true },
      {
        value:
          "Dla każdego podatnika (NIP) dla każdego miesiąca (ROKMC) oraz identyfikatora nagłówka (NAGLOWEK_ID) oblicz sumy kontrolne: liczbę faktur po stronie zakupów (DOWOD_ZAKUPU) , liczbę faktur po stronie sprzedaży (DOWOD_SPRZEDAZY), liczbę dostawców (NR_DOSTAWCY), liczbę kontrahentów (NR_KONTRAHENTA), wartość netto sprzedaży (P_96)",
        isBot: false,
      },
      {
        value:
          "Dla każdego podatnika (NIP) dla każdego miesiąca (ROKMC) oraz identyfikatora nagłówka (NAGLOWEK_ID) oblicz sumy kontrolne: liczbę faktur po stronie zakupów (DOWOD_ZAKUPU) , liczbę faktur po stronie sprzedaży (DOWOD_SPRZEDAZY), liczbę dostawców (NR_DOSTAWCY), liczbę kontrahentów (NR_KONTRAHENTA), wartość netto sprzedaży (P_96)",
        isBot: false,
      },
    ]);
  }, []);

  return (
    <>
      <div className="chat-container white-container border-radius-container">
        <div className="chat">
          {messages.map((element: IMessageProps) => (
            <Message value={element.value} isBot={element.isBot}></Message>
          ))}
        </div>
      </div>
    </>
  );
};

export default Chat;
