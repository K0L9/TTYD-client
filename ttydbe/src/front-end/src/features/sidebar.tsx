import { IConversation } from "../types/types";
import help from "../assets/help.svg";
import history from "../assets/history.svg";
import StoriesContainer from "./storiesContainer";

export interface ISidebarProps {
  data: Array<IConversation>;
}

const Sidebar = ({ data }: ISidebarProps) => {
  return (
    <>
      <div className="sidebar-container white-container border-radius-container">
        <div className="sidebar">
          <div className="display-flex help-header-container">
            <img src={help} alt="Help icon" />
            <h3>Jak stworzyć zapytanie?</h3>
          </div>
          <p className="help-container">
            Wskaż nazwy kolumn z bazy danych oraz akcję w języku angielskim np.
            Give the average value of the tax base (P_96) of sales invoices that
            contain the word ‘FV’ in their name, and their tax base (P_96) is
            greater than 1000 PLN.
          </p>
          <div className="display-flex help-header-container story-header-container">
            <img src={history} alt="Help icon" />
            <h3>Historia zapytań</h3>
          </div>
          <StoriesContainer data={data}></StoriesContainer>
        </div>
      </div>
    </>
  );
};

Sidebar.defaultProps = { data: { data: [] } };

export default Sidebar;
