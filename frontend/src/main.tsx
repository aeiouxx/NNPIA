import React from 'react'
import ReactDOM from 'react-dom/client'
import {
  createBrowserRouter,
  RouterProvider,
  RouteObject,
  Navigate
} from 'react-router-dom'

import { AuthProvider, AuthContext } from './context/auth-context.tsx'
import { LoginForm } from './components/login-form.tsx'


// todo: add components, protected routes with redirects and auth context
const router = createBrowserRouter([
  {
    path: "/",
  },
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router}/>
    </AuthProvider>
  </React.StrictMode>,
)
