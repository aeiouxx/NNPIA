import { NavLink, NavLinkProps } from 'react-router-dom';

const Sidebar: React.FC = () => {
  const linkClasses = ({ isActive }: { isActive: boolean }): string =>
    `block p-2 rounded ${isActive ? 'font-bold' : 'hover:font-bold'}`;

  return (
      <aside className="fixed pt-8 top-16 h-screen w-36 shadow-sm z-50 bg-blue-400 text-white">
      <nav>
        <ul>
          <li className="mb-4">
            <NavLink to="/category" className={linkClasses}>
              Category
            </NavLink>
            <NavLink to="/activity" className={linkClasses}>
              Activity
            </NavLink>
          </li>
        </ul>
      </nav>
    </aside>
  );
};

export default Sidebar;