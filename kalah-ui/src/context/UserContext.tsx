import React from "react";
import { UserContextType } from "./UserContextType";

const STPRAGE_KEY = "username";

export const UserContext = React.createContext<UserContextType>({
    user: null,
    logout: () => { },
    login: () => { }
});


export type IUSerProviderProps = {
    children?: React.ReactNode,
}

const UserProvider = ({ children }: IUSerProviderProps) => {
    const [user, setUser] = React.useState<string | null>(localStorage.getItem(STPRAGE_KEY));

    const login = (username: string) => {
        localStorage.setItem(STPRAGE_KEY, username);
        setUser((user) => username);
    };

    const logout = () => {
        localStorage.removeItem(STPRAGE_KEY);
        setUser((user) => null);
    };

    return (
        <UserContext.Provider value={{ user, login, logout }}>
            {children}
        </UserContext.Provider>
    );
};

export default UserProvider;