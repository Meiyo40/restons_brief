$(document).ready( function() {
    let createBtn = document.getElementById("createButton");
    let listBtn = document.getElementById("listButton");
    let formData = document.getElementById("DataForm");
    let deleteForm = document.getElementById("deleteForm");
    let countriesList = document.getElementById("countriesList");

    createBtn.addEventListener("click", (e) => {
        if(formData.style.display === "block") {
            formData.style.display = "";
        } else {
            deleteForm.style.display = "";
            formData.style.display = "block";
        }
    });

    listBtn.addEventListener("click", (e) => {
        if(countriesList.style.display === "block") {
            countriesList.style.display = "";
        } else {
            countriesList.style.display = "block";
           setDataTable();
        }
    });

    function setDataTable() {
        $.ajax({
            url: "/countries",
            type: "GET",
            success: (data) => {
                console.log(data);

                let bodyList = document.getElementById("listBody");
                bodyList.innerHTML = "";

                data.forEach( el => {
                    let row = document.createElement("tr");
                    let id = document.createElement("td");
                    let name = document.createElement("td");
                    let code = document.createElement("td");
                    let action = document.createElement("td");

                    id.innerText = el.id;
                    name.innerText = el.name;
                    name.id = "name-" + el.id;
                    code.innerText = el.code;
                    code.id = "code-" + el.id;

                    let del = document.createElement("button");
                    let update = document.createElement("button");

                    del.id = "del-" + el.id;
                    del.innerText = "Supprimer";
                    del.addEventListener("click", (e) => {
                        deleteCountry(el.id);
                    });
                    update.id = "upt-" + el.id;
                    update.innerText = "MàJ";
                    update.addEventListener("click", (e) => {
                        updateCountry(el.id);
                    });

                    action.id = "action-" + el.id;
                    action.appendChild(del);
                    action.appendChild(update);

                    row.id = "row-" + el.id;

                    row.appendChild(id);
                    row.appendChild(name);
                    row.appendChild(code);
                    row.appendChild(action);
                    bodyList.appendChild(row);
                });
            },
            error: () => {
                console.log("Fetch List Error");
            }
        });
    }

    function updateCountry(countryId) {
        console.log(countryId);
        let nameInput = document.createElement("input");
        let codeInput = document.createElement("input");
        let confirm = document.createElement("button");
        let name = document.getElementById("name-"+countryId);
        let code = document.getElementById("code-"+countryId);

        nameInput.id = "input-name-" + countryId;
        nameInput.value = name.innerText;
        codeInput.id = "input-code-" + countryId;
        codeInput.value = code.innerText;

        name.innerText = "";
        name.appendChild(nameInput);
        code.innerText = "";
        code.appendChild(codeInput);
        confirm.id = "confirm-" + countryId;
        confirm.innerText = "Confirmer";
        confirm.addEventListener("click", () => {
            let nName = document.getElementById("input-name-"+countryId);
            let nCode = document.getElementById("input-code-"+countryId);
            console.log("Update: " + nName.value + " : " + nCode.value);
        });
        document.getElementById("action-"+countryId).appendChild(confirm);
    }

    function deleteCountry(countryId) {
        console.log(countryId);
        $.ajax({
           url: "/country/" + countryId,
            method: "DELETE",
            success: (data) => {
                let el = document.getElementById("row-" + countryId);
                el.remove();
                console.log("Country successfully deleted: " + countryId);
            },
            error: () => {
               console.log("Delete Error(" + countryId + ")");
            }
        });
    }
});