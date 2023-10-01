import { IConversation } from "../../types/types";

export enum ChatActionTypes {
  WRITE_LOADING_STATE = "WRITE_LOADING_STATE",
  CHANGE_CURRENT_CHAT = "CHANGE_CURRENT_CHAT",
  UPLOAD_HISTORY = "UPLOAD_HISTORY",
}

export interface ChatState {
  loadingState: boolean | null;
  currentChat: string | null;
  history: Array<IConversation>;
}

export interface writeLoadingStateAction {
  type: ChatActionTypes.WRITE_LOADING_STATE;
  payload: boolean;
}
export interface changeCurrentChat {
  type: ChatActionTypes.CHANGE_CURRENT_CHAT;
  payload: string;
}
export interface uploadHistory {
  type: ChatActionTypes.UPLOAD_HISTORY;
  payload: Array<IConversation>;
}

export type ChatAction =
  | writeLoadingStateAction
  | changeCurrentChat
  | uploadHistory;
