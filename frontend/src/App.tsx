import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import './App.css'
import { AuthenticationProvider } from './security/authentication-context.tsx'
import AuthPage from './components/auth-page.tsx';
import { ProtectedRoute } from './security/protected-route.tsx';
import NotFoundPage from './components/not-found-page.tsx';
import FullPageSpinner from './components/page-spinner.tsx';


const router = createBrowserRouter([
  {
    path: "/auth",
    element: <AuthPage />,
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
  {
    path: "/spin",
    element: <FullPageSpinner />
  },
  {
    path: "*",
    element: <NotFoundPage />,
  }
]);

function App() {
  return (
    <AuthenticationProvider>
      <RouterProvider router = {router} />
    </AuthenticationProvider>
  )
}
export default App
