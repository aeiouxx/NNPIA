import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import './App.css'
import { AuthenticationProvider, useAuthentication } from './security/authentication-context.tsx'
import LoginPage from './components/login-page.tsx';
import { ProtectedRoute } from './security/protected-route.tsx';


const router = createBrowserRouter([
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/",
    element: <ProtectedRoute />,
    children: [
      {
        path: "home",
        element: <div>Home</div>,
      },
    ],
  },
]);

function App() {
  return (
    <AuthenticationProvider>
      <RouterProvider router = {router} />
    </AuthenticationProvider>
  )
}
export default App
