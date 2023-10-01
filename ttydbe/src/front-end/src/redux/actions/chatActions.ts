import { IConversation } from "../../types/types";
import { ChatAction, ChatActionTypes } from "../types/types";

export const changeLoadingState = (loadingState: boolean) => {
  return async (dispatch: React.Dispatch<ChatAction>) => {
    dispatch({
      type: ChatActionTypes.WRITE_LOADING_STATE,
      payload: loadingState,
    });
  };
};

export const changeCurrentChat = (chatId: string) => {
  return async (dispatch: React.Dispatch<ChatAction>) => {
    dispatch({
      type: ChatActionTypes.CHANGE_CURRENT_CHAT,
      payload: chatId,
    });
  };
};

export const uploadHistory = (chatId: Array<IConversation>) => {
  return async (dispatch: React.Dispatch<ChatAction>) => {
    dispatch({
      type: ChatActionTypes.UPLOAD_HISTORY,
      payload: chatId,
    });
  };
};
