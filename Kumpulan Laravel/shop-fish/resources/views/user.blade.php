<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fish Shop</title>
    <link rel="shortcut icon" type="image/png" href="../logo.png" />
    <link rel="stylesheet" href="../admin/src/assets/css/styles.min.css" />
    <script>
    if (!localStorage.getItem("login")) window.location.href = '/login';
    </script>
</head>

<body>
    <!--  Body Wrapper -->
    <div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
        data-sidebar-position="fixed" data-header-position="fixed">
        <!-- Sidebar Start -->
        <aside class="left-sidebar">
            <!-- Sidebar scroll-->
            <div>
                <div class="brand-logo d-flex align-items-center justify-content-between">
                    <a href="./index.html" class="text-nowrap logo-img">
                        <img src="../logo.png" width="180" alt="" />
                    </a>
                    <div class="close-btn d-xl-none d-block sidebartoggler cursor-pointer" id="sidebarCollapse">
                        <i class="ti ti-x fs-8"></i>
                    </div>
                </div>
                <!-- Sidebar navigation-->
                <nav class="sidebar-nav scroll-sidebar" data-simplebar="">
                    <ul id="sidebarnav">
                        <li class="nav-small-cap">
                            <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
                            <span class="hide-menu">Home</span>
                        </li>
                        <li class="sidebar-item">
                            <a class="sidebar-link" href="./dashboard" aria-expanded="false">
                                <span>
                                    <i class="ti ti-layout-dashboard"></i>
                                </span>
                                <span class="hide-menu">Dashboard</span>
                            </a>
                        </li>
                        <li class="nav-small-cap">
                            <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
                            <span class="hide-menu">COMPONENTS</span>
                        </li>
                        <li class="sidebar-item">
                            <a class="sidebar-link" href="./user" aria-expanded="false">
                                <span>
                                    <i class="ti ti-login"></i>
                                </span>
                                <span class="hide-menu">User</span>
                            </a>
                        </li>
                        <li class="sidebar-item">
                            <a class="sidebar-link" href="./jenis-fish" aria-expanded="false">
                                <span>
                                    <i class="ti ti-article"></i>
                                </span>
                                <span class="hide-menu">Jenis Ikan</span>
                            </a>
                        </li>
                        <li class="sidebar-item">
                            <a class="sidebar-link" href="./fish" aria-expanded="false">
                                <span>
                                    <i class="ti ti-article"></i>
                                </span>
                                <span class="hide-menu">Fish</span>
                            </a>
                        </li>
                        <li class="sidebar-item">
                            <a class="sidebar-link" onclick="logout()" aria-expanded="false">
                                <span>
                                    <i class="ti ti-alert-circle"></i>
                                </span>
                                <span class="hide-menu">Logout</span>
                            </a>
                        </li>
                    </ul>
                </nav>
                <!-- End Sidebar navigation -->
            </div>
            <!-- End Sidebar scroll-->
        </aside>
        <!--  Sidebar End -->
        <!--  Main wrapper -->
        <div class="body-wrapper">
            <header class="app-header">
                <nav class="navbar navbar-expand-lg navbar-light">
                    <div class="navbar-collapse justify-content-end px-0" id="navbarNav">
                        <ul class="navbar-nav flex-row ms-auto align-items-center justify-content-end">
                            <button type="button" onclick="showInput()" class="btn btn-primary">Tambah Data</button>
                        </ul>
                    </div>
                </nav>
            </header>

            <div class="container-fluid">
                <!--  Row 1 -->
                <div class="row">
                    <div class="card" id="form_input">
                        <div class="card-body">
                            <h5 class="card-title fw-semibold mb-4">Tambah Data</h5>
                            <div class="card">
                                <div class="card-body">
                                    <form>
                                        <div class="mb-3">
                                            <label class="form-label">Nama Lengkap</label>
                                            <input type="text" class="form-control" id="name">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Username</label>
                                            <input type="text" class="form-control" id="username">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Password</label>
                                            <input type="password" class="form-control" id="password">
                                        </div>
                                        <button type="button" onclick="insert()" class="btn btn-primary">SIMPAN</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="card" id="form_ubah">
                        <div class="card-body">
                            <h5 class="card-title fw-semibold mb-4">Ubah Data</h5>
                            <div class="card">
                                <div class="card-body">
                                    <form>
                                        <input type="hidden" id="id">
                                        <div class="mb-3">
                                            <label class="form-label">Nama Lengkap</label>
                                            <input type="text" class="form-control" id="ubah_name">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Username</label>
                                            <input type="email" class="form-control" id="ubah_username">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Password</label>
                                            <input type="password" class="form-control" id="ubah_password">
                                        </div>
                                        <button type="button" onclick="update()" class="btn btn-primary">SIMPAN</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-12 d-flex align-items-stretch">
                        <div class="card w-100">
                            <div class="card-body p-4">
                                <h5 class="card-title fw-semibold mb-4">Data User</h5>
                                <div class="table-responsive">
                                    <table class="table text-nowrap mb-0 align-middle">
                                        <thead class="text-dark fs-4">
                                            <tr>
                                                <th class="border-bottom-0">
                                                    <h6 class="fw-semibold mb-0">No</h6>
                                                </th>
                                                <th class="border-bottom-0">
                                                    <h6 class="fw-semibold mb-0">Nama Lengkap</h6>
                                                </th>
                                                <th class="border-bottom-0">
                                                    <h6 class="fw-semibold mb-0">Username</h6>
                                                </th>
                                                <th class="border-bottom-0">
                                                    <h6 class="fw-semibold mb-0">Password</h6>
                                                </th>
                                                <th class="border-bottom-0">
                                                    <h6 class="fw-semibold mb-0">Action</h6>
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody id="dataBody">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="../admin/src/assets/libs/jquery/dist/jquery.min.js"></script>
    <script src="../admin/src/assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    <script src="../admin/src/assets/js/sidebarmenu.js"></script>
    <script src="../admin/src/assets/js/app.min.js"></script>
    <script src="../admin/src/assets/libs/apexcharts/dist/apexcharts.min.js"></script>
    <script src="../admin/src/assets/libs/simplebar/dist/simplebar.js"></script>
    <script src="../admin/src/assets/js/dashboard.js"></script>
</body>

<script src="../admin/src/assets/libs/jquery/dist/jquery.min.js"></script>
<script src="../admin/src/assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
<script src="../admin/src/assets/js/sidebarmenu.js"></script>
<script src="../admin/src/assets/js/app.min.js"></script>
<script src="../admin/src/assets/libs/apexcharts/dist/apexcharts.min.js"></script>
<script src="../admin/src/assets/libs/simplebar/dist/simplebar.js"></script>
<script src="../admin/src/assets/js/dashboard.js"></script>

<script>
const path = window.location.pathname;
const segments = path.split("/");
const param = segments[segments.length - 1];

var formUpdate = document.getElementById("form_ubah");
formUpdate.style.display = "none";

var formInput = document.getElementById("form_input");
formInput.style.display = "none";

window.addEventListener("DOMContentLoaded", (event) => {
    getDataFromAPI();
});

function getDataFromAPI() {
    fetch("api/user")
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            displayDataListUser(data);
        })
        .catch((error) => {
            console.error("Terjadi kesalahan:", error);
        });
}

function displayDataListUser(data) {
    const tbody = document.getElementById("dataBody");
    let position = 1;

    if (data.user.length > 0)
        while (tbody.firstChild) {
            tbody.removeChild(tbody.firstChild);
        }

    data.user.forEach((item) => {
        const tr = document.createElement("tr");
        const tdNo = document.createElement("td");
        const tdNama = document.createElement("td");
        const tdUsername = document.createElement("td");
        const tdPassword = document.createElement("td");
        const tdAksi = document.createElement("td");

        const buttonDelete = document.createElement("button");
        const buttonUbah = document.createElement("button");
        const br = document.createElement("br");

        buttonDelete.textContent = "Hapus";
        buttonDelete.classList.add("btn", "btn-danger");
        buttonDelete.addEventListener("click", function() {
            deleteData(item.id);
        });

        buttonUbah.textContent = "Ubah";
        buttonUbah.classList.add("btn", "btn-info", "mt-1");
        buttonUbah.addEventListener("click", function() {
            change(item.id, item.nama_lengkap, item.username, item.password);
        });

        tdNo.textContent = position;
        tdNama.textContent = item.nama_lengkap;
        tdUsername.textContent = item.username;
        tdPassword.textContent = "******";

        tdAksi.appendChild(buttonDelete);
        tdAksi.appendChild(br);
        tdAksi.appendChild(buttonUbah);

        tr.appendChild(tdNo);
        tr.appendChild(tdNama);
        tr.appendChild(tdUsername);
        tr.appendChild(tdPassword);
        tr.appendChild(tdAksi);

        tbody.appendChild(tr);
        position++;
    });
}

function showInput() {
    formInput.style.display = "block";
    formUpdate.style.display = "none";
}

function change(id, name, username, password) {
    document.getElementById("ubah_name").value = name;
    document.getElementById("ubah_username").value = username;
    document.getElementById("ubah_password").value = password;
    document.getElementById("id").value = id;
    formUpdate.style.display = "block";
    formInput.style.display = "none";
}


function insert() {
    var name = document.getElementById("name").value;
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    if (name === "") {
        alert("Nama Lengkap Tidak Boleh Kosong");
    } else if (username === "") {
        alert("Username Tidak Boleh Kosong");
    } else if (password === "") {
        alert("Password Tidak Boleh Kosong");
    } else {
        var newData = new FormData();
        newData.append("name", name);
        newData.append("username", username);
        newData.append("password", password);

        fetch("api/user", {
                method: "POST",
                body: newData,
            })
            .then((response) => response.json())
            .then((data) => {
                if (data.action) {
                    alert(data.message);
                    location.reload(true)
                }
            })
            .catch((error) => {
                console.error("Terjadi kesalahan:", error);
            });
    }
}

function deleteData(id) {
    fetch("api/user/" + id, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
        })
        .then((response) => response.json())
        .then((data) => {
            if (data.action) alert(data.message);
            location.reload(true)
        })
        .catch((error) => {
            console.error("Terjadi kesalahan:", error);
        });
}

function update() {
    var name = document.getElementById("ubah_name").value;
    var username = document.getElementById("ubah_username").value;
    var password = document.getElementById("ubah_password").value;
    var id = document.getElementById("id").value;

    if (name === "") {
        alert("Nama Lengkap Tidak Boleh Kosong");
    } else if (username === "") {
        alert("Username Tidak Boleh Kosong");
    } else if (password === "") {
        alert("Password Tidak Boleh Kosong");
    } else {
        var newData = {
            id: id,
            name: name,
            username: username,
            password: password
        };

        fetch("api/user_update", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(newData),
            })
            .then((response) => response.json())
            .then((data) => {
                if (data.action) {
                    formUpdate.style.display = "none";
                    alert(data.message);
                    location.reload(true)
                }
            })
            .catch((error) => {
                console.error("Terjadi kesalahan:", error);
            });
    }
}

function logout() {
    localStorage.removeItem("login");
    window.location.href = '/login';
}
</script>

</html>