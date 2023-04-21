function variation(page) {
    const currency = document.getElementById("currency").value;
    const market = document.getElementById("market").value;
    const date = document.getElementById("date").value;

    const url = `http://localhost:8080/api/variation?currency=${currency}&market=${market}&date=${date}&page=${page}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const result = document.getElementById("result");
            result.innerHTML = data.variation;
        })
        .catch(error => console.error(error));
}