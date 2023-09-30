import "./App.css";
import http from "./http_common";
import { useState } from "react";

function App() {
  const [response, setResponse] = useState<string>("Start");
  const handleButton = async () => {
    const body = { nlQueryText: "Gdzie jest nemo?" };
    http
      .post("nl-2-sql", body)
      .then((x) => {
        console.log(x);
        setResponse(x.toString());
      })
      .catch((x) => {
        console.log(x);
      });
  };

  return (
    <>
      <button onClick={handleButton}>Test button</button>

      <h1>{response}</h1>
    </>
  );
}

export default App;
