import {useState} from "react";
import {useNavigate} from "react-router-dom";
import api from "../services/api";
function Login(){
    const [email,setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error,setError] = useState("");

    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        try{
            const response = await api.post("/auth/login",{
                email,password,
            });

            localStorage.setItem("token", response.data);

            navigate("/dashboard");

        }catch (error){
            setError("Invalid email or password");
            console.error(error);
        }
    };

    return (
        <div>
            <h1>Club Management</h1>

            <h2>Login</h2>

            <form onSubmit={handleSubmit}>

                <div>
                    <label>Email</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>

                <div>
                    <label>Password</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>

                {error && <p>{error}</p>}

                <button type="submit">
                    Login
                </button>

            </form>
        </div>
    );
}

export default Login;
