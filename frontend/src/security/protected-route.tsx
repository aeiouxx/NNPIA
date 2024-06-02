import { Navigate, Outlet, useLocation, useNavigate } from "react-router-dom";
import { useAuthentication } from "./authentication-context";
import Header from "../components/protected/header";


export const ProtectedRoute = () => {
  const {logout, isAuthenticated} = useAuthentication();
  const navigate = useNavigate();
  const location = useLocation();
  if (!isAuthenticated) {
    return <Navigate to="/auth" state={{ from: location }} replace />;
  }
  const onLogout = () => {
    logout();
    navigate("/auth");
  }
  return (
    <div>
      <Header 
        isAuthenticated={isAuthenticated}
        onLogout={onLogout}
        username="Placeholder"/>
      <main className="p-4">
        <Outlet />
      </main>
    </div>
  )
};
