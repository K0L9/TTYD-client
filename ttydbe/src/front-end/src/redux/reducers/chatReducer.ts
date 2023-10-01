import { ChatAction, ChatState, ChatActionTypes } from "../types/types";

const initialState: ChatState = {
  loadingState: false,
  currentChat: "",
  history: [],
};

export const roomReducer = (state = initialState, action: ChatAction) => {
  switch (action.type) {
    case ChatActionTypes.WRITE_LOADING_STATE: {
      return {
        ...state,
        loadingState: action.payload,
      };
    }
    case ChatActionTypes.CHANGE_CURRENT_CHAT: {
      return {
        ...state,
        currentChat: action.payload,
      };
    }
    case ChatActionTypes.UPLOAD_HISTORY: {
      return {
        ...state,
        history: action.payload,
      };
    }
    default: {
      return state;
    }
  }
};
