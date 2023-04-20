let currentPage = 0;

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

            const table = document.getElementById("table");
            const tbody = document.createElement("tbody");
            for (let i = 0; i < data.registeredValues.content.length; i++) {
                const row = document.createElement("tr");
                row.innerHTML =
                    `<td>${data.registeredValues.content[i].registeredAt}</td>
                    <td>${data.registeredValues.content[i].saleValue}</td>`;

                tbody.appendChild(row);
            };

            table.innerHTML =
                `<thead>
                    <tr>
                        <th scope="col">Registered At</th>
                        <th scope="col">Sale value</th>
                    </tr>
                </thead>`;

            table.appendChild(tbody);

            const pagination = document.getElementById("pagination");
            pagination.innerHTML = `<li class="page-item"><a class="page-link" href="#" onclick="variation(${currentPage - 1})">Previous</a></li>`;
            for (let i = 0; i < data.registeredValues.totalPages; i++) {
                pagination.innerHTML += `<li class="page-item" id="${'page' + (i+1)}"><a class="page-link" href="#" onclick="variation(${i})">${i+1}</a></li>`;
            }
            pagination.innerHTML += `<li class="page-item"><a class="page-link" href="#" onclick="variation(${currentPage + 1})">Next</a></li>`;
        })
        .catch(error => console.error(error));
}