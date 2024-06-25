import { Navigate, Outlet, useLocation, useNavigate } from "react-router-dom";
import { useAuthentication } from "./authentication-context";
import Header from "../components/protected/header";
import Sidebar from "../components/protected/sidebar";


export const ProtectedRoute = () => {
  const {logout, isAuthenticated, user} = useAuthentication();
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
    <div className="flex h-screen flex-col">
      <Header 
        isAuthenticated={isAuthenticated}
        onLogout={onLogout}
        user={user!}/>
      <div className="flex flex-1 flex-col mt-16 w-screen">
        <Sidebar />
        <main id="page-wrapper"
          className="flex-1 ml-36 border-x-2 border-y-2">
            <Outlet context={{ user }}/>
          </main>
      </div>
    </div>
  );
};
