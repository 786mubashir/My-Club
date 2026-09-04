import { useEffect, useState } from "react";
import api from "../services/api";

function Club() {
    const [club, setClub] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const [name, setName] = useState("");
    const [city, setCity] = useState("");
    const [address, setAddress] = useState("");
    const [logoUrl, setLogoUrl] = useState("");

    useEffect(() => {
        const loadClub = async () => {
            try {
                const response = await api.get("/clubs");

                setClub(response.data);
                setName(response.data.name || "");
                setCity(response.data.city || "");
                setAddress(response.data.address || "");
                setLogoUrl(response.data.logoUrl || "");
            } catch (error) {
                if (error.response?.status !== 404) {
                    setError("Failed to load club");
                    console.error(error);
                }
            } finally {
                setLoading(false);
            }
        };

        loadClub();
    }, []);

    const handleCreate = async (e) => {
        e.preventDefault();
        setError("");

        try {
            const response = await api.post("/clubs", null, {
                params: {
                    name,
                    city,
                    address,
                    logoUrl,
                },
            });

            setClub(response.data);

        } catch (error) {
            setError("Failed to create club");
            console.error(error);
        }
    };

    const handleUpdate = async (e) => {
        e.preventDefault();
        setError("");

        try {
            const response = await api.put("/clubs", null, {
                params: {
                    name,
                    city,
                    address,
                    logoUrl,
                },
            });

            setClub(response.data);

        } catch (error) {
            setError("Failed to update club");
            console.error(error);
        }
    };

    const handleDelete = async () => {
        const confirmed = window.confirm(
            "Are you sure you want to delete your club?"
        );

        if (!confirmed) {
            return;
        }

        try {
            await api.delete("/clubs");

            setClub(null);
            setName("");
            setCity("");
            setAddress("");
            setLogoUrl("");

        } catch (error) {
            setError("Failed to delete club");
            console.error(error);
        }
    };

    if (loading) {
        return <section className="page-shell"><p className="loading-state">Loading club...</p></section>;
    }

    return (
        <section className="page-shell">
            <div className="page-heading"><div><p className="eyebrow">Club identity</p><h1>My Club</h1><p className="muted">Set the details your players see and recognize.</p></div></div>

            {error && <p>{error}</p>}

            {!club ? (
                <>
                    <div className="form-panel club-form"><h2>Create your club</h2><p className="muted">Start with the basics. You can update them anytime.</p>

                    <form onSubmit={handleCreate}>

                        <div>
                            <label>Club Name</label>
                            <input
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <label>City</label>
                            <input
                                value={city}
                                onChange={(e) => setCity(e.target.value)}
                            />
                        </div>

                        <div>
                            <label>Address</label>
                            <input
                                value={address}
                                onChange={(e) => setAddress(e.target.value)}
                            />
                        </div>

                        <div>
                            <label>Logo URL</label>
                            <input
                                value={logoUrl}
                                onChange={(e) => setLogoUrl(e.target.value)}
                            />
                        </div>

                        <button type="submit">
                            Create Club
                        </button>

                    </form></div>
                </>
            ) : (
                <>
                    <div className="club-hero"><div><p className="eyebrow">Your home ground</p><h2>{club.name}</h2><p className="muted">{club.city || "Club workspace"}</p></div><span className="club-badge">FC</span></div>

                    <div className="form-panel club-form"><h2>Club details</h2><form onSubmit={handleUpdate}>

                        <div>
                            <label>Club Name</label>
                            <input
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <label>City</label>
                            <input
                                value={city}
                                onChange={(e) => setCity(e.target.value)}
                            />
                        </div>

                        <div>
                            <label>Address</label>
                            <input
                                value={address}
                                onChange={(e) => setAddress(e.target.value)}
                            />
                        </div>

                        <div>
                            <label>Logo URL</label>
                            <input
                                value={logoUrl}
                                onChange={(e) => setLogoUrl(e.target.value)}
                            />
                        </div>

                        <button type="submit">
                            Update Club
                        </button>

                    </form>

                    <br />

                    <button className="button danger-button" onClick={handleDelete}>
                        Delete Club
                    </button>
                    </div>
                </>
            )}
        </section>
    );
}

export default Club;