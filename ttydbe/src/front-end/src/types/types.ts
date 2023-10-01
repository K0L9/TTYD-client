export interface IRestResponse<T> {
  data: T;
}

export interface IMessageArray {
  conversations: Array<IConversation>;
}

export interface IConversation {
  timestamp: string;
  conversationId: string;
  exchanges: Array<IMessagePair>;
}

export interface IMessagePair {
  nlQuery: string;
  sqlQuery: string;
}

export interface ISendRequestResponse {
  sqlQueryText: string;
  conversationId: string;
}
