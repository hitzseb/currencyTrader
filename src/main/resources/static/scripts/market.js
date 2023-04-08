function saveMarket() {
    const name = document.getElementById("name").value;
    const code = document.getElementById("code").value;
    const currencyId = document.getElementById('currency').value;

    const market = {
        'name': name,
        'code': code,
        'currency': {
            'id': currencyId
        }
    };

    const options = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(market)
    };

    fetch('/api/market/admin/save', options)
        .then(response => response.json())
        .then(data => {window.location.href = "/admin/market";})
        .catch(error => console.log(error));
}

function editMarket() {
    const name = document.getElementById("name").value;
    const code = document.getElementById("code").value;
    const currencyCode = document.getElementById('currency').value;

    const urlBase = `http://localhost:8080/api/market/admin/${id}/edit`;
    let url = urlBase;

    if (name) {
        url += `?name=${name}`;
    }

    if (code) {
        url += `${url === urlBase ? '?' : '&'}code=${code}`;
    }

    if (currencyCode) {
        url += `${url === urlBase ? '?' : '&'}currencyCode=${currencyCode}`;
    }

    fetch(url, { method: 'PUT' })
        .then(response => response.json())
        .then(data => {
            window.location.href = "/admin/market";
        })
        .catch(error => {
            console.error(`Failed to edit market with id ${id}.`, error);
        });
}

function deleteMarket(id) {
    const url = `http://localhost:8080/api/market/admin/${id}/delete`;
    fetch(url, {method:'DELETE'})
        .then(response => {
            console.log(`Successfully deleted market with id: ${id}`);
        })
        .catch(error => {
            console.error(`Failed to delete market with id ${id}.`, error);
        });
    showValues(0);
}