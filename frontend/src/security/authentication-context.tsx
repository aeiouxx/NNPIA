import { createContext, useContext, useEffect, useState } from "react";
import config from "../config.ts";
import axios from "axios";


export interface UserInfo {
  username: string;
}

interface AuthenticationContextType {
  isAuthenticated: boolean;
  user: UserInfo | null;
  login: (token : string) => void;
  logout: () => void;
}
const AuthenticationContext = createContext<AuthenticationContextType | null>(null);

interface AuthenticationProviderProps {
  children: React.ReactNode;
}

export const AuthenticationProvider: React.FC<AuthenticationProviderProps> = ({children}) => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
  const [user, setUser] = useState<UserInfo | null>({ username: "Placeholder"});

  const login = (token: string) => {
    localStorage.setItem("token", token);
    setIsAuthenticated(true);
  };
  const logout = () => {
    localStorage.removeItem("token");
    setIsAuthenticated(false);
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      validateToken(token).then((result) => {
        setIsAuthenticated(result);
        console.log("Is current token valid: ", result);
        if (!result) {
          logout();
        }
      });
    }
  }, []);

const validateToken = async (token: string): Promise<boolean> => {
  const url = `${config.authBaseUrl}/validate-token`;
  console.log("Validating token at url = ", url);
  try {
    const response = await axios.post(url, { token }, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    return response.status === 200;
  } catch (error) {
    console.error("Error validating token", error);
    return false;
  }
};

  return (
    <AuthenticationContext.Provider value= {{ isAuthenticated, login, logout, user }}>
      {children}
    </AuthenticationContext.Provider>
  );
};
export const useAuthentication = () => {
  const context = useContext(AuthenticationContext);
  if (context === null) {
    throw new Error("useAuthentication must be used within a AuthenticationProvider");
  }
  return context;
};
