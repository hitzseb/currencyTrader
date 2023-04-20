function exchange() {
    const operation = document.getElementById("operation").value;
    const market = document.getElementById("market").value;
    const from = document.getElementById("from").value;
    const to = document.getElementById("to").value;
    const amount = document.getElementById("amount").value;

    const url = `http://localhost:8080/api/exchange?operation=${operation}&market=${market}&from=${from}&to=${to}&amount=${amount}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const result = document.getElementById("result");
            result.innerHTML = data.outputAmount;

            const base = document.getElementById("base");
            const quote = document.getElementById("quote");

            base.innerHTML = data.currencyFrom.name + " value in " + data.market.name + " = "
                + data.market.currency.code + data.market.currency.symbol + data.currencyFromMarketValue;
            quote.innerHTML = data.currencyTo.name + " value in " + data.market.name + " = "
                + data.market.currency.code + data.market.currency.symbol + data.currencyToMarketValue;

        })
        .catch(error => console.error(error));
}