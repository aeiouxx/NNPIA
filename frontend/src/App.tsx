import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import './App.css'
import { AuthenticationProvider } from './security/authentication-context.tsx'
import AuthPage from './components/auth-page.tsx';
import { ProtectedRoute } from './security/protected-route.tsx';
import NotFoundPage from './components/not-found-page.tsx';
import CategoryManager from './components/managers/category/category-manager.tsx';
import ActivityForm from './components/managers/activity/activity-manager.tsx';
import { Activity } from './types/entities.ts';


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
        element: <div>Home</div>
      },
      {
        path: "category",
        element: <CategoryManager />
      },
      {
        path: "activity",
        element: <ActivityForm onSubmit={(activity: Partial<Activity>) => console.log(activity)} />
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
      <RouterProvider router = {router} />
    </AuthenticationProvider>
  )
}
export default App
