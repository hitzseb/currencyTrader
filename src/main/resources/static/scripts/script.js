function exchange() {
    const operation = document.getElementById("operation").value;
    const market = document.getElementById("market").value;
    const from = document.getElementById("from").value;
    const to = document.getElementById("to").value;
    const amount = document.getElementById("amount").value;

    const url = `http://localhost:8080/api/v1/exchange?operation=${operation}&market=${market}&from=${from}&to=${to}&amount=${amount}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const result = document.getElementById("result");
            result.innerHTML = JSON.stringify(data);
        })
        .catch(error => console.error(error));
}

function variation() {
    const currency = document.getElementById("currency").value;
    const market = document.getElementById("market").value;
    const date = document.getElementById("date").value;

    const url = `http://localhost:8080/api/v1/variation?currency=${currency}&market=${market}&date=${date}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const result = document.getElementById("result");
            result.innerHTML = JSON.stringify(data);
        })
        .catch(error => console.error(error));
}

function getValue() {
    const currency = document.getElementById("currency").value;
    const market = document.getElementById("market").value;

    const url = `http://localhost:8080/api/v1/current?currency=${currency}&market=${market}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const result = document.getElementById("result");
            result.innerHTML = JSON.stringify(data);
        })
        .catch(error => console.error(error));
}