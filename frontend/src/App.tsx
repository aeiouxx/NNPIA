import './App.css'
import config from './config'
import { useAuthentication } from './security/authentication-context.tsx'

function App() {
  const {isAuthenticated} = useAuthentication();
  return (
    <h1 className="text-3xl font-bold underline">
      Hello world, api path = ${config.apiBaseUrl}, auth status = {isAuthenticated ? 'Authenticated' : 'Not authenticated'}
    </h1>
  )
}

export default App
