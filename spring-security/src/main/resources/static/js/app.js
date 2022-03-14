const url1 = "http://localhost:8080/users"
const url2 = "/userAuth"
let count = 0


async function loadTableUsers() {
    let temp = ""
    temp += "<a class=\"nav-link active\" href=\"#tableUsers\" data-toggle=\"tab\">Users table</a>"
    document.getElementById("tableUsersButt").innerHTML = temp
}

async function loadNewUser() {
    let temp = ""
    temp += "<a class=\"nav-link btn-light\" href=\"#newUser\"  data-toggle=\"tab\">New User</a>"
    document.getElementById("newUserButt").innerHTML = temp
}

async function loadUserAndAdminProfile() {
    let temp = ""
    temp += "<a class=\"nav-link active\" id = \"adminProfile\"  data-toggle=\"tab\" href = \"#adminInfo\">Admin</a>\n" +
        "<a class=\"nav-link\" id = \"userProfile\"  data-toggle=\"tab\" href = \"#userInfo\">User</a>"
    document.getElementById("adminAndUserProfiles").innerHTML = temp
}

async function getResponceJS(url) {
    const response = await fetch(url)
    const data = await response.json()
    return data
}

function getRoles(user) {
    let strRoles = ""
    user.roles.forEach(role => {
        strRoles += role.username.replace("ROLE_", "") + " "
    })
    return strRoles
}

async function fetchMainTable() {
    const data = await getResponceJS(url1)
    let temp = ""
    data.forEach(user => {
        temp += "<tr>"
        temp += "<td>" + user.id + "</td>"
        temp += "<td>" + user.username + "</td>"
        temp += "<td>" + user.surname + "</td>"
        temp += "<td>" + user.age + "</td>"
        temp += "<td>" + user.email + "</td>"
        temp +="<td>"  + getRoles(user) + "</td>"
        temp += "<td><button class=\"btn btn-info editButt\"  data-userId = \"" + user.id +  "\" onclick = \"openModalEdit(" + user.id + ")\" data-toggle = \"modal\" data-target = \"#editModal\">Edit</button></td>"
        temp += "<td><button class=\"btn btn-danger deleteButt\" data-userId = \"" + user.id +  "\" onclick = \"openModalDelete(" + user.id + ")\" data-toggle = \"modal\" data-target=\"#deleteModal\">Delete</button></td>"
    })
    document.getElementById("alluser").innerHTML = temp
}

async function fetchUserProfile() {
    const data = await getResponceJS(url2)
    let temp = ""
    temp += "<tr>"
    temp += "<td>" + data.id + "</td>"
    temp += "<td>" + data.username + "</td>"
    temp += "<td>" + data.surname + "</td>"
    temp += "<td>" + data.age + "</td>"
    temp += "<td>" + data.email + "</td>"
    temp += "<td>" + getRoles(data) + "</td></tr>"
    document.getElementById("userAuthorize").innerHTML = temp
}

async function getEmailDOM() {
    const data = await getResponceJS(url2)
    let temp = "<p class=\"nav-link active text-white\" aria-current=\"page\" >" + data.email + "</p>"
    document.getElementById("emailAutho").innerHTML = temp
}

async function getRoleDOM() {
    const data = await getResponceJS(url2)
    let temp = "<p class=\"nav-link active text-white\" aria-current=\"page\">"+getRoles(data)+"</p>"
    document.getElementById("roleAutho").innerHTML = temp
}

async function openModalEdit(idUser) {
    let urlUser = "user/" + idUser;
    const data = await getResponceJS(urlUser);
    $("#idEdit").attr('value', idUser);
    $("#usernameEdit").attr('value', data.username);
    $("#surnameEdit").attr('value', data.surname);
    $("#ageEdit").attr('value', data.age);
    $("#emailEdit").attr('value', data.email);
    $("#passwordEdit").attr('value', data.password);
    $("closeEdit").attr("onclick", closeModalEdit());
    data.roles.forEach(role => {
        let strRole = role.username.replace("ROLE_", "")
        $("#testOrderEdit").prepend('<option value= "">' + strRole + '</option>');
    });
}

async function openModalDelete(idUser) {
    let urlUser = "user/" + idUser;
    const data = await getResponceJS(urlUser);
    $("#idDelete").attr('value', idUser);
    $("#usernameDelete").attr('value', data.username);
    $("#surnameDelete").attr('value', data.surname);
    $("#ageDelete").attr('value', data.age);
    $("#emailDelete").attr('value', data.email);
    $("#passwordDelete").attr('value', data.password);
    $("closeDelete").attr("onclick", closeModalDelete());
    data.roles.forEach(role => {
        let strRole = role.username.replace("ROLE_", "")
        $("#testOrderDelete").prepend('<option value= "">' + strRole + '</option>');
    });
}

async function closeModalEdit() {
    $("#idEdit").empty();
    $("#usernameEdit").empty();
    $("#surnameEdit").empty();
    $("#ageEdit").empty();
    $("#emailEdit").empty();
    $("#passwordEdit").empty();
    $('#testOrderEdit').empty();
}

async function closeModalDelete() {
    $("#idDelete").empty();
    $("#usernameDelete").empty();
    $("#surnameDelete").empty();
    $("#ageDelete").empty();
    $("#emailDelete").empty();
    $("#passwordDelete").empty();
    $('#testOrderDelete').empty();
}

loadTableUsers()
loadNewUser()
loadUserAndAdminProfile()
getRoleDOM()
getEmailDOM()
fetchUserProfile()
fetchMainTable()
