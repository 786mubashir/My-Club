import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login.jsx";
import Dashboard from "./pages/Dashboard.jsx";
import Club from "./pages/Club.jsx";
import Players from "./pages/Players.jsx";
import PlayerRegistration from "./pages/PlayerRegistration.jsx";
import Invitations from "./pages/Invitations.jsx";
import ProtectedRoute from "./components/auth/ProtectedRoute.jsx";
import AppLayout from "./components/layout/AppLayout.jsx";
import Register from "./pages/Register";
import "./App.css";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/register/player" element={<PlayerRegistration />} />

                <Route element={<ProtectedRoute />}>
                    <Route element={<AppLayout />}>
                        <Route path="/dashboard" element={<Dashboard />} />
                        <Route path="/club" element={<Club />} />
                        <Route path="/players" element={<Players />} />
                        <Route path="/players/:playerId" element={<Players />} />
                        <Route path="/invitations" element={<Invitations />} />
                    </Route>
                </Route>

                <Route path="/" element={<Navigate to="/dashboard" replace />} />
                <Route path="*" element={<Navigate to="/dashboard" replace />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
