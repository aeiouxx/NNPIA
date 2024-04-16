import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAuthentication } from "./authentication-context";


export const ProtectedRoute = () => {
  const {isAuthenticated} = useAuthentication();
  const location = useLocation();
  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }
  return <Outlet />;
};
