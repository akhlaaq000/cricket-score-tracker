// auth-utils.js
const getTheme = () => localStorage.getItem('theme') || 'dark';

const applyTheme = (theme) => {
    document.documentElement.setAttribute('data-theme', theme);
    localStorage.setItem('theme', theme);
    document.querySelectorAll('.theme-icon').forEach(icon => {
        icon.className = theme === 'dark' ? 'fas fa-sun theme-icon' : 'fas fa-moon theme-icon';
    });
};

applyTheme(getTheme());

function toggleTheme() {
    applyTheme(getTheme() === 'dark' ? 'light' : 'dark');
}

function logout() {
    localStorage.removeItem('authToken');
    window.location.replace('/login.html');
}

const getAuthToken = () => localStorage.getItem('authToken');

async function fetchWithAuth(url, options = {}) {
    const headers = {
        'Authorization': `Bearer ${getAuthToken()}`,
        'Content-Type': 'application/json',
        ...options.headers
    };
    const response = await fetch(url, { ...options, headers });
    if (response.status === 401) logout();
    return response;
}

function requireAuth() {
    if (!getAuthToken()) {
        window.location.replace('/login.html');
        return false;
    }
    return true;
}


// Add this to the bottom of auth-utils.js
function setAuthToken(token) {
    localStorage.setItem('authToken', token);
}

function isAuthenticated() {
    return !!localStorage.getItem('authToken');
}