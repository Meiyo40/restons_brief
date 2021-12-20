let createBtn = document.getElementById("createButton");
let updateBtn = document.getElementById("updateButton");
let deleteBtn = document.getElementById("deleteButton");
let formData = document.getElementById("DataForm");
let deleteForm = document.getElementById("deleteForm");

createBtn.addEventListener("click", (e) => {
    if(formData.style.display === "block") {
        formData.style.display = "";
    } else {
        deleteForm.style.display = "";
        formData.style.display = "block";
    }
});

deleteBtn.addEventListener("click", (e) => {

    console.log(ajax("country/3", "GET"));

    if(deleteForm.style.display === "block") {
        deleteForm.style.display = "";
    } else {
        deleteForm.style.display = "block";
        formData.style.display = "";
    }
});

function ajax(url, pMethod, data = null) {
    let init = {
        method: pMethod,
        mode: "cors",
        cache: "default"
    };

    fetch(url, init).then( function (response) {
        if(response.ok) {
            return response.json();
        }
    })
}