import { useState } from "react";
import { Navigate, useLocation, useNavigate } from "react-router-dom";
import api from "../services/api";
import { isAuthenticated, setToken } from "../utils/auth";

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();
    const location = useLocation();

    if (isAuthenticated()) {
        return <Navigate to="/dashboard" replace />;
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setLoading(true);

        try {
            const response = await api.post("/auth/login", {
                email,
                password,
            });

            setToken(response.data.token);
            navigate("/dashboard");
        } catch (err) {
            setError("Invalid email or password");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <main className="login-page">
            <section className="login-showcase" aria-label="Club Management">
                <div className="showcase-topline"><span className="ball-mark">+</span><span>ELITE FC / 2026</span></div>
                <div className="showcase-copy">
                    <p className="eyebrow">Your club. Your standard.</p>
                    <h1>Build the team<br /><em>behind the team.</em></h1>
                    <p>One calm workspace for your roster, club operations, and the next matchday.</p>
                </div>
                <div className="showcase-footer"><span>01</span><span className="footer-line" /><span>Club operations platform</span></div>
            </section>

            <section className="login-form-area">
                <div className="login-form-wrap">
                    <div className="mobile-brand"><span className="ball-mark">+</span><span>ELITE FC</span></div>
                    <p className="eyebrow">Welcome back</p>
                    <h2>Sign in to your club</h2>
                    <p className="login-intro">Keep the squad moving forward.</p>

                    {location.state?.message && <p className="login-success">{location.state.message}</p>}
                    {error && <p className="login-error" role="alert">{error}</p>}

                    <form onSubmit={handleSubmit} className="login-form">
                        <label htmlFor="login-email">Email address</label>
                        <input id="login-email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="you@club.com" required disabled={loading} autoComplete="email" />
                        <div className="password-label"><label htmlFor="login-password">Password</label><button type="button" className="forgot-button" onClick={() => setError("Password recovery is not available yet.")}>Forgot password?</button></div>
                        <input id="login-password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Enter your password" required disabled={loading} autoComplete="current-password" />
                        <button className="login-submit" type="submit" disabled={loading}>{loading ? "Signing in..." : "Sign in"}<span aria-hidden="true">↗</span></button>
                    </form>
                    <p className="register-prompt">New to Elite FC? <button type="button" onClick={() => navigate("/register")}>Create a manager account</button></p>
                </div>
                <p className="login-legal">By continuing, you agree to the club workspace terms.</p>
            </section>
        </main>
    );
}

export default Login;
