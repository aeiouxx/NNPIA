import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import './App.css'
import { AuthenticationProvider } from './security/authentication-context.tsx'
import AuthPage from './components/auth-page.tsx';
import { ProtectedRoute } from './security/protected-route.tsx';
import NotFoundPage from './components/not-found-page.tsx';
import CategoryManager from './components/managers/category/category-manager.tsx';
import ActivityManager from './components/managers/activity/activity-manager.tsx';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import FullPageSpinner from './components/page-spinner.tsx';
import ActivityEntryManager from './components/managers/activity-entry/entry-manager.tsx';

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
        element: <FullPageSpinner />
      },
      {
        path: "category",
        element: <CategoryManager />
      },
      {
        path: "activity",
        element: <ActivityManager />
      },
      {
        path: "entry",
        element: <ActivityEntryManager />
      }
    ],
  },
  {
    path: "*",
    element: <NotFoundPage />,
  }
]);

function App() {
  return (
    <AuthenticationProvider>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <RouterProvider router = {router} />
      </LocalizationProvider>
    </AuthenticationProvider>
  )
}
export default App
