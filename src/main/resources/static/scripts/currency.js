function saveCurrency() {
    const name = document.getElementById("name").value;
    const code = document.getElementById("code").value;
    const symbol = document.getElementById("symbol").value;

    const currency = {
        'name': name,
        'code': code,
        'symbol': symbol
    };

    const options = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(currency)
    };

    fetch('/api/currency/admin/save', options)
        .then(response => response.json())
        .then(data => {window.location.href = "/admin/currency";})
        .catch(error => console.log(error));
}

function editCurrency() {
    const name = document.getElementById("name").value;
    const code = document.getElementById("code").value;
    const symbol = document.getElementById("symbol").value;

    const urlBase = `http://localhost:8080/api/currency/admin/${id}/edit`;
    let url = urlBase;

    if (name) {
        url += `?name=${name}`;
    }

    if (code) {
        url += `${url === urlBase ? '?' : '&'}code=${code}`;
    }

    if (symbol) {
        url += `${url === urlBase ? '?' : '&'}symbol=${symbol}`;
    }

    fetch(url, { method: 'PUT' })
        .then(response => response.json())
        .then(data => {
            window.location.href = "/admin/currency";
        })
        .catch(error => {
            console.error(`Failed to edit currency with id ${id}.`, error);
        });
}

function deleteCurrency(id) {
    const url = `http://localhost:8080/api/currency/admin/${id}/delete`;
    fetch(url, {method:'DELETE'})
        .then(() => {
            location.reload();
        })
        .catch(error => {
            console.error(`Failed to delete currency with id ${id}.`, error);
        });
    showValues(0);
}