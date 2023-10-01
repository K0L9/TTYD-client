import { IConversation } from "../types/types";
import { useActions } from "../hooks/useActions";

export interface IStorySideItemProps {
  conversation: IConversation;
}

const StorySideItem = ({ conversation }: IStorySideItemProps) => {
  const { changeCurrentChat } = useActions();

  const handleItemClick = () => {
    changeCurrentChat(conversation.conversationId);
  };

  return (
    <>
      <div className="story-side-item-container" onClick={handleItemClick}>
        <h3>{conversation.exchanges[0]?.nlQuery}</h3>
      </div>
    </>
  );
};

export default StorySideItem;
