import icon from "../assets/message-bot-icon.svg";
import { IMessagePair } from "../types/types";

const Message = ({ sqlQuery, nlQuery }: IMessagePair) => {
  return (
    <>
      <div className={`message-container my-message`}>
        <div className="message">{nlQuery}</div>
      </div>
      <div className={`message-container bot-message`}>
        <img src={icon}></img>
        <div className="message">{sqlQuery}</div>
      </div>
    </>
  );
};

export default Message;
