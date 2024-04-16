import { createContext, useContext, useEffect, useState } from "react";
import config from "../config.ts";


interface AuthenticationContextType {
  isAuthenticated: boolean;
  login: (token : string) => void;
  logout: () => void;
}
const AuthenticationContext = createContext<AuthenticationContextType | null>(null);

interface AuthenticationProviderProps {
  children: React.ReactNode;
}

export const AuthenticationProvider: React.FC<AuthenticationProviderProps> = ({children}) => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);


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
        if (!result) {
          logout();
        }
      });
    }
  }, []);

  const validateToken = async (token: string) : Promise<boolean> => {
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ token }),
    };
    try {
      const url = `${config.apiBaseUrl}/auth/validate-token`;
      console.log("Validating token at url = ", url);
      const response = await fetch(url, options);
      if (response.ok) {
        return true;
      } else {
        return false;
      }
    } catch (error) {
      console.error("Error validating token", error);
      return false;
    }
  };

  return (
    <AuthenticationContext.Provider value= {{ isAuthenticated, login, logout }}>
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
