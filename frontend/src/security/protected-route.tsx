import { Navigate, useLocation } from "react-router-dom";
import { useAuthentication } from "./authentication-context";


interface ProtectedRouteProps {
  children: JSX.Element;
}


export const ProtectedRoute: React.FC<ProtectedRouteProps> = ({children}) => {
  const {isAuthenticated} = useAuthentication();
  const location = useLocation();
  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }
  return children;
};
