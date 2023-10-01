import { useEffect, useState } from "react";
import Message from "../components/message";
import emptyIcon from "../assets/empty-conversation-icon.svg";
import { IMessagePair } from "../types/types";
import { useTypedSelector } from "../hooks/useTypedSelector";

const Chat = () => {
  const [messages, setMessages] = useState<Array<IMessagePair>>([]);
  const { history } = useTypedSelector((x) => x.room);

  const { currentChat } = useTypedSelector((x) => x.room);

  useEffect(() => {
    const messages = history.find((x) => x.conversationId == currentChat);

    if (messages) setMessages(messages.exchanges);
  }, [currentChat]);

  return (
    <>
      <div className="chat-container white-container border-radius-container">
        <div className="chat">
          {messages && messages.length > 0 ? (
            messages.map((element: IMessagePair) => (
              <Message
                nlQuery={element.nlQuery}
                sqlQuery={element.sqlQuery}
              ></Message>
            ))
          ) : (
            <center>
              <img id="empty-data-icon" src={emptyIcon} alt=""></img>
            </center>
          )}
        </div>
      </div>
    </>
  );
};

export default Chat;
