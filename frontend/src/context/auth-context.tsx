import { createContext, useState, useEffect } from 'react';

// FIXME: this is dumb 
interface AuthContextType {
  isAuthed: boolean;
  setAuthed: (status: boolean) => void;
}

export const AuthContext = createContext<AuthContextType | null>(null);

interface AuthProviderProps {
  children: React.ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ( { children } ) => {
  const [isAuthed, setAuthed] = useState<boolean>(!!localStorage.getItem('token'));

  const setAuthStatus = (status: boolean) => {
    setAuthed(status);
  };

  useEffect(() => {
    // could also add event listeners or perform actions when the auth status changes
    const token = localStorage.getItem('token');
    setAuthed(!!token);
  }, []);

  return (
    <AuthContext.Provider value={{ isAuthed, setAuthed: setAuthStatus }}>
      {children}
    </AuthContext.Provider>
  );
};
