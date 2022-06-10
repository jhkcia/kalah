// Use from this source: https://codepen.io/HardBuffet/details/vYdJPQg
import './ResponsiveHeader.css';
export function ResponsiveHeader(): JSX.Element {

    return (
        <>
            <header className="header">
                <a href="/" className="logo">ONLINE KALAH GAME</a>
                <button className="header__btn_open-topnav header__btn"><span className="icon-menu-open"></span></button>
                <ul className="topnav topnav_mobile_show">
                    <button className="header__btn_close-topnav header__btn"><span className="icon-menu-close"></span></button>
                    <li className="topnav__item">
                        <a href="" className="topnav__link">Features</a>
                    </li>
                    <li className="topnav__item">
                        <a href="#" className="topnav__link">Company</a>
                    </li>
                    <li className="topnav__item">
                        <a href="#" className="topnav__link">Careers</a>
                    </li>
                    <li className="topnav__item">
                        <a href="#" className="topnav__link">About</a>
                    </li>
                </ul>
            </header>
        </>
    );
} 