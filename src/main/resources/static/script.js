// Helper function for making authenticated fetch requests
async function fetchWithAuth(url, options = {}) {
    const jwtToken = localStorage.getItem("jwtToken");
    if (!jwtToken) {
        alert("You must be logged in.");
        window.location.href = "/login.html";
        throw new Error("Not authenticated");
    }

    const headers = { Authorization: `Bearer ${jwtToken}`, ...options.headers };
    const response = await fetch(url, { ...options, headers });

    if (!response.ok) {
        if (response.status === 401) {
            alert("Session expired. Please log in again.");
            localStorage.removeItem("jwtToken");
            window.location.href = "/login.html";
        }
        const error = await response.text();
        throw new Error(error || "Request failed");
    }
    return response.json();
}

// Login Form Submission Handler
document.getElementById("loginForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password }),
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Login failed!");
        }

        const data = await response.json(); // Expecting JSON from the backend
        alert(data.message);

        if (data.token) {
            localStorage.setItem("jwtToken", data.token);
            window.location.href = "/dashboard.html"; // Redirect to the dashboard
        }
    } catch (err) {
        alert(err.message);
    }
});

// Signup Form Submission Handler
document.getElementById("signupForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (password !== confirmPassword) {
        alert("Passwords do not match!");
        return;
    }

    fetch("/api/signup", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, password }),
    })
        .then((response) => {
            if (!response.ok) throw new Error("Signup failed");
            return response.json();
        })
        .then(() => alert("Signup successful!"))
        .catch((err) => alert(err.message));
});

// Account Fetch and Display Handler
document.addEventListener("DOMContentLoaded", async function () {
    try {
        const accounts = await fetchWithAuth("/api/accounts/user");
        const tableBody = document.getElementById("accountsTable");

        if (!accounts || accounts.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="4">No accounts available</td></tr>`;
        } else {
            accounts.forEach(account => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${account.accountType || 'N/A'}</td>
                    <td>${account.accountNumber || 'N/A'}</td>
                    <td>${account.balance.toLocaleString('en-US', { style: 'currency', currency: 'USD' }) || '$0.00'}</td>
                    <td>${new Date(account.createOn).toLocaleDateString() || 'N/A'}</td>
                `;
                tableBody.appendChild(row);
            });
        }
    } catch (error) {
        console.error("Error fetching account data:", error);
        alert("An error occurred while fetching account data. Please log in again.");
        localStorage.removeItem("jwtToken");
        window.location.href = "/login.html";
    }
});
