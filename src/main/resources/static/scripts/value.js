let currenctPage = 0;

function showValues(page) {
    currenctPage = page;
    const currency = document.getElementById("currency").value;
    const market = document.getElementById("market").value;
    const size = 10;

    const url = `http://localhost:8080/api/value?currency=${currency}&market=${market}&page=${page}&size=${size}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const table = document.getElementById("table");
            const tbody = document.createElement("tbody");

            for (let i = 0; i < data.content.length; i++) {
                const row = document.createElement("tr");
                row.innerHTML =
                    `<td>${data.content[i].id}</td>
                    <td>${data.content[i].registeredAt}</td>
                    <td>${data.content[i].purchaseValue}</td>
                    <td>${data.content[i].saleValue}</td>
                    <td>${data.content[i].active}</td>
                    <td>
                        <a href="http://localhost:8080/admin/value/${data.content[i].id}/edit">Edit</a>
                        <a href="#" onclick="deleteValue(${data.content[i].id})">Delete</a>
                    </td>`;

                tbody.appendChild(row);
            }

            table.innerHTML =
                `<thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Registered At</th>
                        <th scope="col">Purchase value</th>
                        <th scope="col">Sale value</th>
                        <th scope="col">Is active</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>`;

            table.appendChild(tbody);

            const pagination = document.getElementById("pagination");
            pagination.innerHTML = `<li class="page-item"><a class="page-link" href="#" onclick="showValues(${currenctPage - 1})">Previous</a></li>`;
            for (let i = 0; i < data.totalPages; i++) {
                pagination.innerHTML += `<li class="page-item"><a class="page-link" href="#" id="${'page' + (i+1)}" onclick="showValues(${i})">${i+1}</a></li>`;
            }
            pagination.innerHTML += `<li class="page-item"><a class="page-link" href="#" onclick="showValues(${currenctPage + 1})">Next</a></li>`;
        })
        .catch(error => console.error(error));
}

function saveValue() {
    const registeredAt = document.getElementById("registeredAt").value;
    const purchaseValue = document.getElementById("purchaseValue").value;
    const saleValue = document.getElementById("saleValue").value;
    const isActive = document.getElementById("isActive").checked;
    const currencyId = document.getElementById('currency').value;
    const marketId = document.getElementById('market').value;

    const value = {
        'registeredAt': registeredAt,
        'purchaseValue': purchaseValue,
        'saleValue': saleValue,
        'isActive': isActive,
        'currency': {
            'id': currencyId
        },
        'market': {
            'id': marketId
        }
    };

    const options = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(value)
    };

    fetch('/api/value/admin/save', options)
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.log(error));
}

function editValue(button) {
    const registeredAt = document.getElementById("registeredAt").value;
    const purchaseValue = document.getElementById("purchaseValue").value;
    const saleValue = document.getElementById("saleValue").value;
    const isActive = document.getElementById("isActive").checked;
    const id = button.dataset.valueId;

    const urlBase = `http://localhost:8080/api/value/admin/${id}/edit`;
    let url = urlBase;

    if (registeredAt) {
        url += `?registeredAt=${registeredAt}`;
    }

    if (purchaseValue) {
        url += `${url === urlBase ? '?' : '&'}purchaseValue=${purchaseValue}`;
    }

    if (saleValue) {
        url += `${url === urlBase ? '?' : '&'}saleValue=${saleValue}`;
    }

    if (isActive !== undefined) {
        url += `${url === urlBase ? '?' : '&'}isActive=${isActive}`;
    }

    fetch(url, { method: 'PUT' })
        .then(response => response.json())
        .then(data => {
            window.location.href = "/admin/value";
        })
        .catch(error => {
            console.error(`Failed to edit currency value with id ${id}.`, error);
        });
}

function deleteValue(id) {
    const url = `http://localhost:8080/api/value/admin/${id}/delete`;
    fetch(url, {method:'DELETE'})
        .then(response => {
            console.log(`Successfully deleted currency value with id: ${id}`);
        })
        .catch(error => {
            console.error(`Failed to delete currency value with id ${id}.`, error);
        });
    showValues(0);
}