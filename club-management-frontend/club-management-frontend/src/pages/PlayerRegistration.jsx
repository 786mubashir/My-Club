import { useEffect, useState } from "react";
import { Link, useSearchParams } from "react-router-dom";
import api from "../services/api";

const initialForm = {
    firstName: "",
    lastName: "",
    password: "",
    confirmPassword: "",
    dominantFoot: "RIGHT",
    position: "",
    dateOfBirth: "",
    category: "",
};

function PlayerRegistration() {
    const [searchParams] = useSearchParams();
    const token = searchParams.get("token") || "";
    const [invitation, setInvitation] = useState(null);
    const [form, setForm] = useState(initialForm);
    const [state, setState] = useState({ loading: true, saving: false, error: "", success: false });

    useEffect(() => {
        if (!token) {
            setState({ loading: false, saving: false, error: "This invitation link is missing its token.", success: false });
            return;
        }

        api.get(`/invitation/${token}`)
            .then((response) => setInvitation(response.data))
            .catch((error) => setState((current) => ({ ...current, error: error.response?.data?.message || "This invitation is invalid or expired." })))
            .finally(() => setState((current) => ({ ...current, loading: false })));
    }, [token]);

    const updateForm = (event) => setForm({ ...form, [event.target.name]: event.target.value });

    const submit = async (event) => {
        event.preventDefault();
        if (form.password !== form.confirmPassword) {
            setState((current) => ({ ...current, error: "Passwords do not match." }));
            return;
        }
        setState((current) => ({ ...current, saving: true, error: "" }));
        try {
            await api.post("/auth/register/player", {
                token,
                email: invitation.email,
                firstName: form.firstName,
                lastName: form.lastName,
                password: form.password,
                dominantFoot: form.dominantFoot,
                position: form.position || null,
                dateOfBirth: form.dateOfBirth || null,
                category: form.category || null,
            });
            setState((current) => ({ ...current, saving: false, success: true }));
        } catch (error) {
            setState((current) => ({ ...current, saving: false, error: error.response?.data?.message || "Registration failed." }));
        }
    };

    if (state.loading) return <div className="public-page"><p className="loading-state">Checking invitation...</p></div>;
    if (state.success) return <div className="public-page"><div className="form-panel success-panel"><p className="eyebrow">Welcome to the club</p><h1>Account created</h1><p className="muted">Your player account is ready. You can now sign in.</p><Link className="button primary" to="/login">Go to login</Link></div></div>;
    if (state.error && !invitation) return <div className="public-page"><div className="form-panel"><h1>Invitation unavailable</h1><p className="alert error-message">{state.error}</p><Link className="button secondary" to="/login">Back to login</Link></div></div>;

    return (
        <div className="public-page">
            <form className="form-panel registration-panel" onSubmit={submit}>
                <p className="eyebrow">Join your club</p>
                <h1>Create player account</h1>
                <p className="muted">Complete your profile to accept the invitation.</p>
                <label>Email<input type="email" value={invitation.email} readOnly autoComplete="email" /></label>
                <div className="field-grid">
                    <label>First name<input name="firstName" value={form.firstName} onChange={updateForm} required autoComplete="given-name" /></label>
                    <label>Last name<input name="lastName" value={form.lastName} onChange={updateForm} required autoComplete="family-name" /></label>
                    <label>Position<input name="position" value={form.position} onChange={updateForm} maxLength="30" /></label>
                    <label>Category<input name="category" value={form.category} onChange={updateForm} maxLength="10" /></label>
                    <label>Date of birth<input type="date" name="dateOfBirth" value={form.dateOfBirth} onChange={updateForm} /></label>
                    <label>Dominant foot<select name="dominantFoot" value={form.dominantFoot} onChange={updateForm}><option value="LEFT">Left</option><option value="RIGHT">Right</option><option value="BOTH">Both</option></select></label>
                </div>
                <label>Password<input type="password" name="password" value={form.password} onChange={updateForm} required minLength="6" autoComplete="new-password" /></label>
                <label>Confirm password<input type="password" name="confirmPassword" value={form.confirmPassword} onChange={updateForm} required minLength="6" autoComplete="new-password" /></label>
                {state.error && <p className="alert error-message">{state.error}</p>}
                <button className="button primary" disabled={state.saving}>{state.saving ? "Creating account..." : "Create account"}</button>
            </form>
        </div>
    );
}

export default PlayerRegistration;
