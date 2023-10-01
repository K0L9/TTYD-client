import { useEffect, useState } from "react";
import Input from "../components/input";
import Chat from "./chat";
import Header from "./header";
import Sidebar from "./sidebar";
import http from "../http_common";
import {
  IConversation,
  IRestResponse,
  IMessageArray,
  ISendRequestResponse,
  IMessagePair,
} from "../types/types";
import { useTypedSelector } from "../hooks/useTypedSelector";
import { useActions } from "../hooks/useActions";
import { changeCurrentChat } from "../redux/actions/chatActions";

const MainPage = () => {
  const [history, setHistory] = useState<Array<IConversation>>([]);

  const { currentChat } = useTypedSelector((x) => x.room);

  const { changeLoadingState, uploadHistory } = useActions();

  useEffect(() => {
    http.get("history").then((x: IRestResponse<IMessageArray>) => {
      setHistory(x.data.conversations);
      uploadHistory(x.data.conversations);
    });
  }, []);

  const onSendSubmit = async (value: string) => {
    const data = { nlQueryText: value };

    changeLoadingState(true);

    const result = await http.post<ISendRequestResponse>("nl-2-sql", data);

    const messagePair: IMessagePair = {
      sqlQuery: result.data.sqlQueryText,
      nlQuery: value,
    };

    const historyTmp = history.slice();
    const ind = historyTmp.findIndex((x) => x.conversationId === currentChat);
    historyTmp[ind].exchanges.push(messagePair);

    setHistory(historyTmp);
    // changeCurrentChat()

    changeLoadingState(false);

    // http.post<ISendRequestResponse>("nl-2-sql", data).then((x) => {
    //   console.log(x.data.sqlQueryText);
    //   console.log(x.status);
    // });
  };

  return (
    <div className="view-port-container">
      <Header></Header>
      <div className="main-block-container">
        <div className="main-block">
          <div className="sidebar-container">
            <Sidebar data={history}></Sidebar>
          </div>
          <div className="chat-input-container">
            <Chat></Chat>
            <Input onSubmit={onSendSubmit}></Input>
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
