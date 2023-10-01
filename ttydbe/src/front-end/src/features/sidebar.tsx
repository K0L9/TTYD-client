import { IConversation } from "../types/types";

export interface ISidebarProps {
  data: Array<IConversation>;
}

const Sidebar = ({ data }: ISidebarProps) => {
  return (
    <>
      <div className="sidebar-container white-container border-radius-container"></div>
    </>
  );
};

Sidebar.defaultProps = { data: { data: [] } };

export default Sidebar;
