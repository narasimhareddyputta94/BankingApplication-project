function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('error-message');

    if (username === 'admin' && password === '123') {
        // Successful login
        document.getElementById('login-container').style.display = 'none';
        document.getElementById('dashboard-container').style.display = 'block';
        errorMessage.textContent = '';
    } else {
        // Incorrect login
        errorMessage.textContent = 'Invalid username or password';
    }
}

function viewBalance() {
    const messageDiv = document.getElementById('message');
    messageDiv.innerHTML = '<p>Your account balance is: $10,000</p>';
}

function transferDeposit() {
    const messageDiv = document.getElementById('message');
    messageDiv.innerHTML = '<p>Transfer/Deposit functionality coming soon!</p>';
}

function loanApplication() {
    const messageDiv = document.getElementById('message');
    messageDiv.innerHTML = '<p>Loan Application functionality coming soon!</p>';
}

function viewStatement() {
    const messageDiv = document.getElementById('message');
    messageDiv.innerHTML = '<p>View Statement functionality coming soon!</p>';
}

function createAccount() {
    alert('Create Account functionality coming soon!');
}
