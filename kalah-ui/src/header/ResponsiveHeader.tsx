// Use from this source: https://codepen.io/HardBuffet/details/vYdJPQg
import { Link } from 'react-router-dom';
import './ResponsiveHeader.css';

type NavBarItem = {
    title: string;
    link?: string;
    onClick?: () => void;
}

type IResponsiveHeaderProps = {
    title?: string;
    titleLink?: string;
    items?: NavBarItem[];
}

export function ResponsiveHeader({ title, titleLink, items }: IResponsiveHeaderProps): JSX.Element {

    return (
        <>
            <header className="header">
                {titleLink ? <Link to="/" className="logo">{title}</Link> :
                    <div className="logo"> {title}</div>
                }

                <button className="header__btn_open-topnav header__btn"><span className="icon-menu-open"></span></button>
                <ul className="topnav topnav_mobile_show">
                    <button className="header__btn_close-topnav header__btn"><span className="icon-menu-close"></span></button>

                    {
                        items && items.map((item, index) => {
                            return (<li className="topnav__item" key={index}>
                                {
                                    item.link ?
                                        <Link to={item.link} className="topnav__link" onClick={item.onClick}>{item.title} </Link>
                                        :
                                        <span className="topnav__link" onClick={item.onClick}>{item.title}</span>
                                }
                            </li>)

                        })
                    }

                </ul>
            </header>
        </>
    );
} 