function getValue() {
    const currency = document.getElementById("currency").value;
    const market = document.getElementById("market").value;

    const url = `http://localhost:8080/api/current?currency=${currency}&market=${market}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const result = document.getElementById("result");
            result.innerHTML = data.value;
        })
        .catch(error => console.error(error));
}