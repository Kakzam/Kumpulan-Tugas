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
                                            <label class="form-label">Nama Ikan</label>
                                            <input type="text" class="form-control" id="name">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Harga</label>
                                            <input type="number" class="form-control" id="price">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Jenis Ikan</label>
                                            <select class="form-control" id="jenis"></select>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Gambar</label>
                                            <input type="file" class="form-control" id="gambar">
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
                                            <label class="form-label">Nama Ikan</label>
                                            <input type="text" class="form-control" id="ubah_name">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Harga</label>
                                            <input type="number" class="form-control" id="ubah_price">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Jenis Ikan</label>
                                            <select class="form-control" id="jenis_update"></select>
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Gambar</label>
                                            <input type="file" class="form-control" id="ubah_gambar">
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
                                <h5 class="card-title fw-semibold mb-4">Data Ikan</h5>
                                <div class="table-responsive">
                                    <table class="table text-nowrap mb-0 align-middle">
                                        <thead class="text-dark fs-4">
                                            <tr>
                                                <th class="border-bottom-0">
                                                    <h6 class="fw-semibold mb-0">No</h6>
                                                </th>
                                                <th class="border-bottom-0">
                                                    <h6 class="fw-semibold mb-0">Nama Ikan</h6>
                                                </th>
                                                <th>
                                                    <h6 class="fw-semibold mb-0">Jenis</h6>
                                                </th>
                                                <th class="border-bottom-0">
                                                    <h6 class="fw-semibold mb-0">Harga</h6>
                                                </th>
                                                <th class="border-bottom-0">
                                                    <h6 class="fw-semibold mb-0">Gambar</h6>
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
var formUpdate = document.getElementById("form_ubah");
formUpdate.style.display = "none";

var formInput = document.getElementById("form_input");
formInput.style.display = "none";

window.addEventListener("DOMContentLoaded", (event) => {
    getDataFromAPI();
});

function getDataFromAPI() {
    fetch("api/fish")
        .then((response) => response.json())
        .then((data) => {
            displayDataListFish(data);
        })
        .catch((error) => {
            console.error("Terjadi kesalahan:", error);
        });
}

function displayDataListFish(data) {
    const tbody = document.getElementById("dataBody");
    let position = 1;

    if (data.fish.length > 0)
        while (tbody.firstChild) {
            tbody.removeChild(tbody.firstChild);
        }

    data.fish.forEach((item) => {
        const tr = document.createElement("tr");
        const tdNo = document.createElement("td");
        const tdNama = document.createElement("td");
        const tdJenis = document.createElement("td");
        const tdHarga = document.createElement("td");
        const tdGambar = document.createElement("td");
        const tdAksi = document.createElement("td");

        const buttonDelete = document.createElement("button");
        const buttonUbah = document.createElement("button");
        const img = document.createElement("img");
        const br = document.createElement("br");

        buttonDelete.textContent = "Hapus";
        buttonDelete.classList.add("btn", "btn-danger");
        buttonDelete.addEventListener("click", function() {
            deleteData(item.id);
        });

        buttonUbah.textContent = "Ubah";
        buttonUbah.classList.add("btn", "btn-info", "mt-1");
        buttonUbah.addEventListener("click", function() {
            change(item.id, item.nama_ikan, item.harga, data);
        });

        tdNo.textContent = position;
        tdNama.textContent = item.nama_ikan;
        tdHarga.textContent = item.harga;
        data.jenis.forEach((jurusan) => {

            if (jurusan.id == item.jenis_ikan) {
                tdJenis.textContent = jurusan.jenis_ikan;
            }
        });;

        img.src = "storage/" + item.gambar_ikan;
        img.alt = "Gambar " + item.nama_ikan;
        img.style
            .maxWidth = "50px";
        img.style.maxHeight = "50px";

        tdGambar.appendChild(img);

        tdAksi.appendChild(buttonDelete);
        tdAksi.appendChild(br);
        tdAksi.appendChild(buttonUbah);

        tr.appendChild(tdNo);
        tr.appendChild(tdNama);
        tr.appendChild(tdJenis);
        tr.appendChild(
            tdHarga);
        tr.appendChild(tdGambar);
        tr.appendChild(tdAksi);

        tbody.appendChild(tr);
        position++;
    });
}

function showInput() {
    formInput.style.display = "block";
    formUpdate.style.display = "none";
    const selectElement = document.getElementById("jenis");

    const option = document.createElement("option");
    option.value = "EMPTY";
    option.textContent = "Pilih Jenis Ikan";
    selectElement.appendChild(option);

    fetch("api/fish")
        .then((response) => response.json())
        .then((data) => {
            data.jenis.forEach((jurusan) => {
                const option = document.createElement("option");
                option.value = jurusan.id;
                option.textContent = jurusan.jenis_ikan;
                selectElement.appendChild(option);
            });
        })
        .catch((error) => {
            console.error("Terjadi kesalahan:", error);
        });
}

function change(id, name, price, data) {
    document.getElementById("ubah_name").value = name;
    document.getElementById("ubah_price").value = price;
    document.getElementById("id").value = id;
    formUpdate.style.display = "block";
    formInput.style.display = "none";
    const selectElement = document.getElementById("jenis_update");

    const option = document.createElement("option");
    option.value = "EMPTY";
    option.textContent = "Pilih Jenis Ikan";
    selectElement.appendChild(option);

    data.jenis.forEach((jurusan) => {
        const option = document.createElement("option");
        option.value = jurusan.id;
        option.textContent = jurusan.jenis_ikan;
        selectElement.appendChild(option);
    });
}


function insert() {
    var name = document.getElementById("name").value;
    var username = document.getElementById("price").value;
    var password = document.getElementById("gambar");
    var jenis = document.getElementById("jenis").value;
    var file = password.files[0];

    if (name === "") {
        alert("Nama Ikan Tidak Boleh Kosong");
    } else if (username === "") {
        alert("Harga Ikan Tidak Boleh Kosong");
    } else if (jenis === "EMPTY") {
        alert("Jenis Ikan Tidak Boleh Kosong");
    } else if (!file) {
        alert("Gambar Ikan Tidak Boleh Kosong");
    } else {
        var newData = new FormData();
        newData.append("name", name);
        newData.append("price", username);
        newData.append("jenis", jenis);
        newData.append("gambar", file);

        fetch("api/fish", {
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
    fetch("api/fish/" + id, {
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
    var price = document.getElementById("ubah_price").value;
    var gambar = document.getElementById("ubah_gambar");
    var id = document.getElementById("id").value;
    var jenis = document.getElementById("jenis_update").value;
    var file = gambar.files[0];

    if (name === "") {
        alert("Nama Ikan Tidak Boleh Kosong");
    } else if (price === "") {
        alert("Harga Ikan Tidak Boleh Kosong");
    } else if (!file) {

        var newData = new FormData();
        newData.append("id", id);
        newData.append("name", name);
        newData.append("price", price);
        newData.append("jenis", jenis);

        fetch("api/fish_update", {
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
    } else {
        var newData = new FormData();
        newData.append("id", id);
        newData.append("name", name);
        newData.append("price", price);
        newData.append("gambar", file);
        newData.append("jenis", jenis);
        fetch("api/fish_update", {
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

function logout() {
    localStorage.removeItem("login");
    window.location.href = '/login';
}
</script>


</html>