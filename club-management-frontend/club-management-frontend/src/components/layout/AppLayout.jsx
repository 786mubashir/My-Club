import { NavLink, Outlet, useNavigate } from "react-router-dom";
import { getUserRole, removeToken } from "../../utils/auth";
import "./AppLayout.css";

function AppLayout() {
    const navigate = useNavigate();
    const role = getUserRole();

    const handleLogout = () => {
        removeToken();
        navigate("/login", { replace: true });
    };

    return (
        <div className="app-layout">
            <aside className="sidebar">
                <div className="sidebar-brand">
                    <span className="sidebar-logo">⚽</span>
                    <div>
                        <p className="sidebar-title">Elite FC</p>
                        <p className="sidebar-subtitle">Club Management</p>
                    </div>
                </div>

                <nav className="sidebar-nav">
                    <NavLink to="/dashboard" className="nav-link">
                        Dashboard
                    </NavLink>
                    {role !== "PLAYER" && <>
                        <NavLink to="/club" className="nav-link">Club</NavLink>
                        <NavLink to="/players" className="nav-link">Players</NavLink>
                        <NavLink to="/invitations" className="nav-link">Invitations</NavLink>
                    </>}
                </nav>

                <button type="button" className="logout-btn" onClick={handleLogout}>
                    Logout
                </button>
            </aside>

            <main className="main-content">
                <Outlet />
            </main>
        </div>
    );
}

export default AppLayout;
