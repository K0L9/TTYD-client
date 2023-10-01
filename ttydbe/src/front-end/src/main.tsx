import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./styles/theme.scss";
import "./index.css";

//fonts
import "./fonts/OpenSans/OpenSans-VariableFont_wdth.ttf";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
