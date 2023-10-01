import { IConversation } from "../types/types";
import StorySideItem from "./storySideItem";

export interface ISidebarProps {
  data: Array<IConversation>;
}

const StoriesContainer = ({ data }: ISidebarProps) => {
  return (
    <>
      {data.map((x, id) => (
        <StorySideItem conversation={x} key={id}></StorySideItem>
      ))}
    </>
  );
};

export default StoriesContainer;
