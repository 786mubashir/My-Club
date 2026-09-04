import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import api from "../services/api";

const emptyForm = { dominantFoot: "RIGHT", position: "", dateOfBirth: "", category: "" };

function playerName(player) {
    const user = player.user || player.users || {};
    return [user.firstName, user.lastName].filter(Boolean).join(" ") || user.email || `Player #${player.id}`;
}

function Players() {
    const { playerId } = useParams();
    const navigate = useNavigate();
    const [players, setPlayers] = useState([]);
    const [form, setForm] = useState(emptyForm);
    const [query, setQuery] = useState("");
    const [filter, setFilter] = useState("all");
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");

    const loadPlayers = async () => {
        try {
            setLoading(true);
            const response = await api.get("/players");
            setPlayers(response.data || []);
        } catch (err) {
            setError(err.response?.data?.message || "Unable to load players.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        const loadInitialPlayers = async () => {
            try {
                setLoading(true);
                const response = await api.get("/players");
                setPlayers(response.data || []);
            } catch (err) {
                setError(err.response?.data?.message || "Unable to load players.");
            } finally {
                setLoading(false);
            }
        };

        loadInitialPlayers();
    }, []);

    useEffect(() => {
        if (!playerId) return;
        const loadPlayer = async () => {
            try {
                const response = await api.get(`/players/${playerId}`);
                setForm({
                    dominantFoot: response.data.dominantFoot || "RIGHT",
                    position: response.data.position || "",
                    dateOfBirth: response.data.dateOfBirth || "",
                    category: response.data.category || "",
                });
            } catch (err) {
                setError(err.response?.data?.message || "Unable to load player.");
            }
        };
        loadPlayer();
    }, [playerId]);

    const updateForm = (event) => setForm({ ...form, [event.target.name]: event.target.value });

    const savePlayer = async (event) => {
        event.preventDefault();
        setError("");
        setSaving(true);
        try {
            await api.put(`/players/${playerId}`, { ...form, dateOfBirth: form.dateOfBirth || null });
            await loadPlayers();
            navigate("/players");
        } catch (err) {
            setError(err.response?.data?.message || "Unable to save player.");
        } finally {
            setSaving(false);
        }
    };

    const deletePlayer = async (id) => {
        if (!window.confirm("Delete this player from the club?")) return;
        try {
            await api.delete(`/players/${id}`);
            setPlayers(players.filter((player) => player.id !== id));
            if (String(id) === playerId) navigate("/players");
        } catch (err) {
            setError(err.response?.data?.message || "Unable to delete player.");
        }
    };

    if (playerId) {
        return <section className="page-shell"><Link className="back-link" to="/players">Back to players</Link><div className="page-heading"><div><p className="eyebrow">Player profile</p><h1>Edit player</h1></div></div>{error && <p className="alert error-message">{error}</p>}<form className="form-panel compact-form" onSubmit={savePlayer}><div className="field-grid"><label>Position<input name="position" value={form.position} onChange={updateForm} maxLength="30" /></label><label>Category<input name="category" value={form.category} onChange={updateForm} maxLength="10" /></label><label>Date of birth<input type="date" name="dateOfBirth" value={form.dateOfBirth} onChange={updateForm} /></label><label>Dominant foot<select name="dominantFoot" value={form.dominantFoot} onChange={updateForm}><option value="LEFT">Left</option><option value="RIGHT">Right</option><option value="BOTH">Both</option></select></label></div><div className="form-actions"><button className="button primary" disabled={saving}>{saving ? "Saving..." : "Save changes"}</button><Link className="button secondary" to="/players">Cancel</Link></div></form></section>;
    }

    const visiblePlayers = players.filter((player) => {
        const matchesQuery = playerName(player).toLowerCase().includes(query.toLowerCase()) || (player.position || "").toLowerCase().includes(query.toLowerCase());
        return matchesQuery && (filter === "all" || (filter === "active" ? player.active !== false : player.active === false));
    });

    return <section className="page-shell"><div className="page-heading"><div><p className="eyebrow">Squad directory</p><h1>Players</h1><p className="muted">Keep your roster current and easy to scan.</p></div><Link className="button primary" to="/invitations">Invite player</Link></div>{error && <p className="alert error-message">{error}</p>}<div className="toolbar"><input aria-label="Search players" placeholder="Search by name or position" value={query} onChange={(event) => setQuery(event.target.value)} /><select aria-label="Filter players" value={filter} onChange={(event) => setFilter(event.target.value)}><option value="all">All players</option><option value="active">Active only</option><option value="inactive">Inactive only</option></select></div>{loading ? <p className="loading-state">Loading players...</p> : <div className="table-wrap"><table><thead><tr><th>Name</th><th>Position</th><th>Category</th><th>Status</th><th aria-label="Actions" /></tr></thead><tbody>{visiblePlayers.map((player) => <tr key={player.id}><td><Link className="table-link" to={`/players/${player.id}`}>{playerName(player)}</Link><small>{player.user?.email || player.users?.email || ""}</small></td><td>{player.position || "Unassigned"}</td><td>{player.category || "-"}</td><td><span className={`status ${player.active === false ? "inactive" : "active"}`}>{player.active === false ? "Inactive" : "Active"}</span></td><td><button className="text-button danger" onClick={() => deletePlayer(player.id)}>Delete</button></td></tr>)}</tbody></table>{!visiblePlayers.length && <p className="empty-state">No players match this view.</p>}</div>}</section>;
}

export default Players;
