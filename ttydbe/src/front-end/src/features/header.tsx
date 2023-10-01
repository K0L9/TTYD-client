import logo from "../assets/logo.svg";

const Header = () => {
  return (
    <header>
      <div className="header-upper-line"></div>
      <div className="header-container">
        <div className="header-container-left">
          <img src={logo} alt="Logo" />
          <span>
            Baza danych: nazwa2023 | Ostatnia aktualizacja: 15-10-2023
          </span>
        </div>
        <div className="header-container-right">
          <a href="#">Jak korzystać z narzędzia?</a>
        </div>
      </div>
    </header>
  );
};

export default Header;
