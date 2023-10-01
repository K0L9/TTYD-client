import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { Provider } from "react-redux";
import "./styles/theme.scss";
import "./index.css";

//fonts
import "./fonts/OpenSans/OpenSans-VariableFont_wdth.ttf";
import configureStore from "./store/configureStore.ts";

const store = configureStore(history);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <Provider store={store}>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </Provider>
);
