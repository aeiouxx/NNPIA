import { createContext, useContext, useEffect, useState } from "react";
import config from "../config.ts";
import axios from "axios";


interface AuthenticationContextType {
  isAuthenticated: boolean;
  login: (token : string) => void;
  logout: () => void;
}
const AuthenticationContext = createContext<AuthenticationContextType | null>(null);

interface AuthenticationProviderProps {
  children: React.ReactNode;
}

// CORS: Cross-Origin Resource Sharing
// Umožňuje webovým aplikacím z různých domén přistupovat k zdrojům na jiných doménách.
// CORS je mechanismus, který umožňuje serveru říct prohlížeči, zda může nebo nemůže sdílet zdroje s webovou stránkou, která požaduje zdroje.
// CORS funguje tak, že server přidá do odpovědi HTTP hlavičku Access-Control-Allow-Origin, která obsahuje doménu, která může přistupovat k zdrojům.
// Pokud je doména, ze které je zdroj požadován, obsažena v hlavičce Access-Control-Allow-Origin, prohlížeč zobrazí zdroj.
// Pokud není obsažena, prohlížeč zobrazí chybu.
// CORS je bezpečnostní mechanismus, který zabraňuje útokům typu Cross-Site Request Forgery (CSRF).
// CORS je standardní mechanismus, který je podporován všemi moderními prohlížeči.
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

const validateToken = async (token: string): Promise<boolean> => {
  const url = `${config.apiBaseUrl}/auth/validate-token`;
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
