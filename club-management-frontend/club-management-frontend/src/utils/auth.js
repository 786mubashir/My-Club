const TOKEN_KEY = "token";

export function getToken() {
    return localStorage.getItem(TOKEN_KEY);
}

export function setToken(token) {
    localStorage.setItem(TOKEN_KEY, token);
}

export function removeToken() {
    localStorage.removeItem(TOKEN_KEY);
}

export function isAuthenticated() {
    return Boolean(getToken());
}

export function getUserRole() {
    const token = getToken();

    if (!token) {
        return null;
    }

    try {
        const payload = JSON.parse(atob(token.split(".")[1]));
        const role = payload.role || payload.roles?.[0] || payload.authorities?.[0];
        return typeof role === "string" ? role.replace("ROLE_", "").toUpperCase() : null;
    } catch {
        return null;
    }
}
