import Input from "../components/input";
import Chat from "./chat";
import Header from "./header";
import Sidebar from "./sidebar";

const MainPage = () => {
  return (
    <div className="view-port-container">
      <Header></Header>
      <div className="main-block-container">
        <div className="main-block">
          <div className="sidebar-container">
            <Sidebar></Sidebar>
          </div>
          <div className="chat-input-container">
            <Chat></Chat>
            <Input></Input>
          </div>
        </div>
        <div className="footer-container">
          <span>Projekt zespołu: Nocne pTAGi</span>
          <span>Hackyeah 2023</span>
        </div>
      </div>
    </div>
  );
};

export default MainPage;
