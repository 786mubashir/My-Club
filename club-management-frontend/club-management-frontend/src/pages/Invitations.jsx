import { useState } from "react";
import api from "../services/api";

function Invitations() {
    const [email, setEmail] = useState("");
    const [sent, setSent] = useState([]);
    const [lastInvitation, setLastInvitation] = useState(null);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const sendInvitation = async (event) => {
        event.preventDefault();
        setError("");
        setLoading(true);
        try {
            const response = await api.post("/invitation", null, { params: { email } });
            const invitation = { ...response.data, email };
            setSent([invitation, ...sent]);
            setLastInvitation(invitation);
            setEmail("");
        } catch (err) {
            setError(err.response?.data?.message || "Unable to send invitation.");
        } finally {
            setLoading(false);
        }
    };

    const registrationUrl = lastInvitation ? `${window.location.origin}/register/player?token=${lastInvitation.token}` : "";
    const clubName = lastInvitation?.club?.name || "your club";
    const message = `You have been invited to join ${clubName}. Register your player account here: ${registrationUrl}`;

    const copyInvitation = async () => {
        await navigator.clipboard.writeText(message);
    };

    const openMail = () => {
        window.location.href = `mailto:${lastInvitation.email}?subject=${encodeURIComponent(`Invitation to join ${clubName}`)}&body=${encodeURIComponent(message)}`;
    };

    return <section className="page-shell"><div className="page-heading"><div><p className="eyebrow">Grow the squad</p><h1>Invitations</h1><p className="muted">Create a private registration link for a new player.</p></div></div><div className="split-layout"><form className="form-panel" onSubmit={sendInvitation}><h2>Invite a player</h2><label>Player email<input type="email" value={email} onChange={(event) => setEmail(event.target.value)} placeholder="player@example.com" required /></label>{error && <p className="alert error-message">{error}</p>}<button className="button primary" disabled={loading}>{loading ? "Creating invitation..." : "Create invitation"}</button></form><div className="form-panel"><h2>Send the message</h2>{lastInvitation ? <><p className="invite-message">{message}</p><div className="form-actions"><button className="button primary" type="button" onClick={openMail}>Open email app</button><button className="button secondary" type="button" onClick={copyInvitation}>Copy message</button></div><p className="invite-expiry">This invitation expires in 7 days.</p></> : <p className="empty-state">Create an invitation to generate a registration link and message.</p>}</div></div><div className="form-panel invitation-history"><h2>Created this session</h2>{sent.length ? <div className="invite-list">{sent.map((invite, index) => <div className="invite-row" key={invite.id || index}><span>{invite.email}</span><span className="status pending">Pending</span></div>)}</div> : <p className="empty-state">Invitation history will appear here after you create one.</p>}</div></section>;
}

export default Invitations;
