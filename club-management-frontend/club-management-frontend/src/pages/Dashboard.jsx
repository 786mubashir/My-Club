import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../services/api";

function Dashboard() {
    const [players, setPlayers] = useState([]);
    const [club, setClub] = useState(null);

    useEffect(() => {
        Promise.allSettled([api.get("/players"), api.get("/clubs")]).then(([playerResult, clubResult]) => {
            if (playerResult.status === "fulfilled") setPlayers(playerResult.value.data || []);
            if (clubResult.status === "fulfilled") setClub(clubResult.value.data);
        });
    }, []);

    const activePlayers = players.filter((player) => player.active !== false).length;

    return(
        <section className="page-shell">
            <div className="page-heading"><div><p className="eyebrow">Manager overview</p><h1>Good to see you back.</h1><p className="muted">A quick read on your club today.</p></div><Link className="button primary" to="/invitations">Invite a player</Link></div>
            <div className="stats-grid"><div className="stat-card"><span>Total players</span><strong>{players.length}</strong><small>Roster size</small></div><div className="stat-card accent"><span>Active players</span><strong>{activePlayers}</strong><small>Available in squad</small></div><div className="stat-card"><span>Club status</span><strong>{club ? "Active" : "Setup"}</strong><small>{club ? club.name : "Create your club profile"}</small></div><div className="stat-card"><span>Next action</span><strong>{club ? "Invite" : "Club"}</strong><small>{club ? "Grow your roster" : "Complete your setup"}</small></div></div>
            <div className="dashboard-grid"><div className="feature-panel"><div><p className="eyebrow">Club workspace</p><h2>{club ? club.name : "Your club is waiting"}</h2><p className="muted">{club ? "Keep club details and your squad in one place." : "Set up your club profile before building your squad."}</p></div><Link className="button secondary" to="/club">{club ? "View club" : "Set up club"}</Link></div><div className="feature-panel"><div><p className="eyebrow">Squad activity</p><h2>{players.length ? `${activePlayers} players active` : "Start your roster"}</h2><p className="muted">Invite players and manage their details from the squad directory.</p></div><Link className="button secondary" to="/players">Open players</Link></div></div>
        </section>
    );
}

export  default Dashboard;