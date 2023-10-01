import { useState } from "react";

export interface IInputProps {
  onSubmit: (text: string) => void;
}

const Input = ({ onSubmit }: IInputProps) => {
  const [inputText, setInputText] = useState<string>("");

  const onButtonHandle = () => {
    onSubmit(inputText);
  };

  const onInputChange = (event: any) => {
    setInputText(event.target.value);
  };

  return (
    <>
      <div className="input-container white-container border-radius-container">
        <div className="input-box">
          <input
            onChange={onInputChange}
            type="text"
            placeholder="Type request..."
            value={inputText}
          />
        </div>
        <div className="button-box">
          <button onClick={onButtonHandle}>{"Wyślij >"}</button>
        </div>
      </div>
    </>
  );
};

Input.defaultProps = { onChange: () => {} };

export default Input;
