import icon from "../assets/message-bot-icon.svg";

export interface IMessageProps {
  value: string;
  isBot: boolean;
}

const Message = ({ value, isBot }: IMessageProps) => {
  const assignedClassName = isBot ? "bot-message" : "my-message";

  return (
    <>
      <div className={`message-container ${assignedClassName}`}>
        {isBot === true && <img src={icon}></img>}
        <div className="message">{value}</div>
      </div>
    </>
  );
};

Message.defaultProps = { value: "", isBot: true };

export default Message;
