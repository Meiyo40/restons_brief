$(document).ready( function() {
    let createBtn = document.getElementById("createButton");
    let createSubmit = document.getElementById("createSubmit");
    let listBtn = document.getElementById("listButton");
    let formData = document.getElementById("DataForm");
    let deleteForm = document.getElementById("deleteForm");
    let countriesList = document.getElementById("countriesList");

    setDataTable();

    createBtn.addEventListener("click", (e) => {
        if(formData.style.display === "block") {
            formData.style.display = "none";
        } else {
            deleteForm.style.display = "none";
            formData.style.display = "block";
        }
    });

    createSubmit.addEventListener("click", (e) =>{
        e.preventDefault();

        let data = {
            name: document.getElementById("name").value,
            code: document.getElementById("code").value.toUpperCase()
        };

        if(data.code.length !== 2) {
            alert("Le code pays ne correspond pas au format attendu (2 caractères).")
        } else {
            $.ajax({
                url: "/country",
                method: "POST",
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                success: (data) => {
                    if(data.name !== "ERROR") {
                        setDataTable();
                    } else {
                        alert("Erreur: " + data.code);
                    }
                },
                error: () => {
                    console.log("Error while created new country");
                }
            })
        }
    });

    listBtn.addEventListener("click", (e) => {
        if(countriesList.style.display === "block") {
            countriesList.style.display = "none";
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
                if(data.name !== "ERROR") {
                    console.log(data);

                    let bodyList = document.getElementById("listBody");
                    bodyList.innerHTML = "";

                    data.forEach( el => {
                        let row = document.createElement("tr");
                        let id = document.createElement("td");
                        let name = document.createElement("td");
                        let code = document.createElement("td");
                        let action = document.createElement("td");

                        let img = document.createElement("img");
                        img.src = "https://flagcdn.com/16x12/" + el.code.toLowerCase() + ".png";
                        img.alt = "Flag from " + el.name;
                        let countryName = document.createElement("span");
                        countryName.innerText = el.name;

                        id.innerText = el.id;
                        id.className = "displayNone";
                        name.appendChild(img);
                        name.appendChild(countryName);
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
                } else {
                    alert("Erreur: " + data.code);
                }
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

            let data = {
              name: nName.value,
              code: nCode.value.toUpperCase()
            };

            if(data.code.length !== 2) {
                alert("Le code pays ne correspond pas au format attendu (2 caractères).")
            } else {
                $.ajax({
                    url: "/country/" + countryId,
                    method: "PUT",
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(data),
                    dataType: "json",
                    success: (data) => {
                        if(data.name !== "ERROR") {
                            document.getElementById("confirm-" + countryId).remove();
                            setDataTable();
                        } else {
                            alert("Erreur: " + data.code);
                        }
                    },
                    error: () => {
                        console.log("Error while updating: " + countryId);
                    }
                });
            }
        });
        document.getElementById("action-"+countryId).appendChild(confirm);
    }

    function deleteCountry(countryId) {
        console.log(countryId);
        $.ajax({
           url: "/country/" + countryId,
            method: "DELETE",
            success: (data) => {
                if(data.name !== "ERROR") {
                    let el = document.getElementById("row-" + countryId);
                    el.remove();
                    console.log("Country successfully deleted: " + countryId);
                } else {
                    alert("Erreur: " + data.code);
                }
            },
            error: () => {
               console.log("Delete Error(" + countryId + ")");
            }
        });
    }
});