const Input = () => {
  return (
    <>
      <div className="input-container white-container border-radius-container">
        <div className="input-box">
          <input type="text" placeholder="Wpisz zapytanie..." />
        </div>
        <div className="button-box">
          <button>{"Wyślij >"}</button>
        </div>
      </div>
    </>
  );
};

export default Input;
