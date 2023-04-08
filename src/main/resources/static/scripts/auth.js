function register() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (password === confirmPassword) {
        const credential = {
            'username': username,
            'password': password
        };

        const options = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(credential)
        };

        fetch('/api/register', options)
            .then(response => response.json())
            .then(data => {window.location.href = "/login";})
            .catch(error => console.log(error));
    } else {
        const mismatch = document.getElementById('mismatch');
        mismatch.classList.remove('sr-only');
    }
}