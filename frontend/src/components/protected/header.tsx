import { useAuthentication } from "../../security/authentication-context"

type HeaderProps = {
  username: string;
  onLogout: () => void;
  isAuthenticated: boolean;
}

const Header = ({username, onLogout, isAuthenticated} : HeaderProps) => {
  if (!isAuthenticated) return null;
  return (
    <header
      className="flex justify-between items-center p-4 bg-blue-500 text-white">
      <h1 className="text-xl font-bold">Welcome, {username}</h1>
      <button onClick={onLogout} className="bg-red-500 hover:bg-red-600 px-4 py-2 rounded">
        Logout
      </button>
    </header>
  )
}

export default Header