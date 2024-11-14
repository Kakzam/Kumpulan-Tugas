<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Data User</title>
    <link href="../admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">
    <link href="../admin/css/sb-admin-2.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <style>
    .loader {
        border: 4px solid #f3f3f3;
        border-radius: 50%;
        border-top: 4px solid #3498db;
        width: 30px;
        height: 30px;
        animation: spin 1s linear infinite;
        margin: 20px auto;
    }

    @keyframes spin {
        0% {
            transform: rotate(0deg);
        }

        100% {
            transform: rotate(360deg);
        }
    }
    </style>
</head>

<body id="page-top">

    <div id="wrapper">

        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
                <div class="sidebar-brand-icon rotate-n-15">
                    <i class="fas fa-laugh-wink"></i>
                </div>
                <div class="sidebar-brand-text mx-3">SB Admin</div>
            </a>

            <hr class="sidebar-divider my-0">

            <li class="nav-item">
                <a class="nav-link" href="dashboard">
                    <i class="fas fa-fw fa-tachometer-alt"></i>
                    <span>Dashboard</span></a>
            </li>

            <hr class="sidebar-divider">

            <div class="sidebar-heading">
                Interface
            </div>

            <li class="nav-item active">
                <a class="nav-link" href="data-user">
                    <i class="fas fa-fw fa-cog"></i>
                    <span>Data User</span></a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="jabatan">
                    <i class="fas fa-fw fa-cog"></i>
                    <span>Jabatan</span></a>
            </li>
        </ul>
        <!-- End of Sidebar -->

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar -->
                <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                    <!-- Sidebar Toggle (Topbar) -->
                    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                        <i class="fa fa-bars"></i>
                    </button>

                    <!-- Topbar Navbar -->
                    <ul class="navbar-nav ml-auto">

                        <!-- Nav Item - User Information -->
                        <li class="nav-item dropdown no-arrow">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="mr-2 d-none d-lg-inline text-gray-600 small">Douglas McGee</span>
                            </a>
                            <!-- Dropdown - User Information -->
                            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                aria-labelledby="userDropdown">
                                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Logout
                                </a>
                            </div>
                        </li>
                    </ul>

                </nav>
                <!-- End of Topbar -->

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <div class="d-sm-flex align-items-center justify-content-between mb-4">
                        <h1 class="h3 mb-0 text-gray-800">Data User</h1>
                        <div class="loader" id="loader"></div>
                        <button type="button" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"
                            onclick="showInput()"><i class="fas fa-plus fa-sm text-white-50"></i> Tambah
                            Data</button>
                    </div>

                    <div class="row">
                        <div class="col-xl-6 col-lg-12" id="form_input">
                            <div class="card shadow mb-4">
                                <div class="card-body">
                                    <form>
                                        <div class="form-group">
                                            <input type="email" class="form-control form-control-user" id="nama_lengkap"
                                                aria-describedby="emailHelp" placeholder="Nama Lengkap...">
                                        </div>

                                        <div class="form-group">
                                            <input type="email" class="form-control form-control-user" id="username"
                                                aria-describedby="emailHelp" placeholder="Username...">
                                        </div>

                                        <div class="form-group">
                                            <input type="password" class="form-control form-control-user" id="password"
                                                aria-describedby="emailHelp" placeholder="Password...">
                                        </div>

                                        <div class="form-group">
                                            <input type="number" class="form-control form-control-user"
                                                id="phone_number" aria-describedby="emailHelp"
                                                placeholder="Nomor Handphone...">
                                        </div>

                                        <div class="form-group">
                                            <select class="form-control form-control-user" id="jenis_kelamin"
                                                name="jenis_kelamin">
                                                <option value="">Pilih Jenis Kelamin</option>
                                                <option value="Pria">Pria</option>
                                                <option value="Wanita">Wanita</option>
                                            </select>
                                        </div>

                                        <div class="form-group">
                                            <textarea type="text" class="form-control form-control-user" id="alamat"
                                                aria-describedby="emailHelp" placeholder="Alamat..."></textarea>
                                        </div>

                                        <div class="form-group">
                                            <input type="file" id="file" aria-describedby="emailHelp">
                                        </div>

                                        <button type="button" onclick="insert()"
                                            class="btn btn-primary btn-user btn-block">
                                            SIMPAN
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-6 col-lg-7" id="form_update">
                            <div class="card shadow mb-4">
                                <div class="card-body">
                                    <form>
                                        <input type="hidden" id="id">
                                        <div class="form-group">
                                            <input type="email" class="form-control form-control-user"
                                                id="nama_lengkap_" aria-describedby="emailHelp"
                                                placeholder="Nama Lengkap...">
                                        </div>

                                        <div class="form-group">
                                            <input type="email" class="form-control form-control-user" id="username_"
                                                aria-describedby="emailHelp" placeholder="Username...">
                                        </div>

                                        <div class="form-group">
                                            <input type="password" class="form-control form-control-user" id="password_"
                                                aria-describedby="emailHelp" placeholder="Password...">
                                        </div>

                                        <div class="form-group">
                                            <input type="number" class="form-control form-control-user"
                                                id="phone_number_" aria-describedby="emailHelp"
                                                placeholder="Nomor Handphone...">
                                        </div>

                                        <div class="form-group">
                                            <select class="form-control form-control-user" id="jenis_kelamin_"
                                                name="jenis_kelamin">
                                                <option value="">Pilih Jenis Kelamin</option>
                                                <option value="Pria">Pria</option>
                                                <option value="Wanita">Wanita</option>
                                            </select>
                                        </div>

                                        <div class="form-group">
                                            <textarea type="text" class="form-control form-control-user" id="alamat_"
                                                aria-describedby="emailHelp" placeholder="Alamat..."></textarea>
                                        </div>

                                        <div class="form-group">
                                            <input type="file" id="file_" aria-describedby="emailHelp">
                                        </div>

                                        <button type="button" onclick="update()"
                                            class="btn btn-primary btn-user btn-block">
                                            UPDATE
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-12 col-lg-7">
                            <div class="card shadow mb-4">
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                            <thead>
                                                <tr>
                                                    <th>Urutan</th>
                                                    <th>Nama Lengkap</th>
                                                    <th>Username</th>
                                                    <th>Alamat</th>
                                                    <th>Jenis Kelamin</th>
                                                    <th>Phone</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tfoot>
                                                <tr>
                                                    <th>Urutan</th>
                                                    <th>Nama Lengkap</th>
                                                    <th>Username</th>
                                                    <th>Alamat</th>
                                                    <th>Jenis Kelamin</th>
                                                    <th>Phone</th>
                                                    <th>Action</th>
                                                </tr>
                                            </tfoot>
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
    </div>

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="login.html">Logout</a>
                </div>
            </div>
        </div>
    </div>

    <script>
    var formUpdate = document.getElementById("form_update");
    formUpdate.style.display = "none";

    var loading = document.getElementById("loader");
    loading.style.display = "none";

    var formInput = document.getElementById("form_input");
    formInput.style.display = "none";

    document.addEventListener('DOMContentLoaded', function() {
        axios.get('/api/user')
            .then((response) => displayDataListFish(response.data))
            .catch(function(error) {
                console.error('Error:', error);
            });
    });

    function displayDataListFish(data) {
        console.log(data)
        const tbody = document.getElementById("dataBody");
        let position = 1;

        if (data.jabatan.length > 0)
            while (tbody.firstChild) {
                tbody.removeChild(tbody.firstChild);
            }

        data.jabatan.forEach((item) => {
            const tr = document.createElement("tr");
            const tdUrutan = document.createElement("td");
            const tdNama = document.createElement("td");
            const tdUsername = document.createElement("td");
            const tdAlamat = document.createElement("td");
            const tdJenisKelamin = document.createElement("td");
            const tdPhone = document.createElement("td");
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
                change(item.id,
                    item.nama_lengkap,
                    item.username,
                    item.password,
                    item.gambar,
                    item.alamat,
                    item.phone,
                    item.jenis_kelamin);
            });

            tdUrutan.textContent = position;
            tdNama.textContent = item.nama_lengkap;
            tdUsername.textContent = item.username;
            tdAlamat.textContent = item.alamat;
            tdJenisKelamin.textContent = item.jenis_kelamin;
            tdPhone.textContent = item.phone;

            tdAksi.appendChild(buttonDelete);
            tdAksi.appendChild(br);
            tdAksi.appendChild(buttonUbah);

            tr.appendChild(tdUrutan);
            tr.appendChild(tdNama);
            tr.appendChild(tdUsername);
            tr.appendChild(tdAlamat);
            tr.appendChild(tdJenisKelamin);
            tr.appendChild(tdPhone);
            tr.appendChild(tdAksi);

            tbody.appendChild(tr);
            position++;
        });
    }

    function showInput() {
        formInput.style.display = "block";
        formUpdate.style.display = "none";
    }

    function change(id, name, username, password, gambar, alamat, phone, jnies_kelamin) {
        document.getElementById("id").value = id;
        document.getElementById("nama_lengkap").value = name;
        formUpdate.style.display = "block";
        formInput.style.display = "none";
    }


    function insert() {
        var namaLengkap = document.getElementById('nama_lengkap').value.trim();
        var username = document.getElementById('username').value.trim();
        var password = document.getElementById('password').value.trim();
        var phoneNumber = document.getElementById('phone_number').value.trim();
        var jenisKelamin = document.getElementById('jenis_kelamin').value;
        var alamat = document.getElementById('alamat').value.trim();
        var gambar = document.getElementById('file');
        var file = gambar.files[0];

        if (namaLengkap === "") {
            alert("Nama Lengkap Tidak Boleh Kosong");
        } else if (username === "") {
            alert("Username Tidak Boleh Kosong");
        } else if (password === "") {
            alert("Password Tidak Boleh Kosong");
        } else if (phoneNumber === "") {
            alert("Nomor Handphone Tidak Boleh Kosong");
        } else if (jenisKelamin === "") {
            alert("Silahkan Pilih Jenis Kelamin Terlabih Dahulu");
        } else if (alamat === "") {
            alert("Alamat Tidak Boleh Kosong");
        } else if (file === "") {
            alert("Silahkan Pilih File Photo Terlebih Dahulu");
        } else {
            loading.style.display = "block";
            var newData = new FormData();
            newData.append("name", namaLengkap);
            newData.append("username", username);
            newData.append("password", password);
            newData.append("phone_number", phoneNumber);
            newData.append("jenis_kelamin", jenisKelamin);
            newData.append("alamat", alamat);
            newData.append("gambar", file);

            fetch("api/user", {
                    method: "POST",
                    body: newData,
                })
                .then((response) => response.json())
                .then((data) => {
                    if (data.action) {
                        loading.style.display = "none";
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
        loading.style.display = "block";
        fetch("api/user/" + id, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            })
            .then((response) => response.json())
            .then((data) => {
                loading.style.display = "none";
                if (data.action) alert(data.message)
                location.reload(true)
            })
            .catch((error) => {
                console.error("Terjadi kesalahan:", error);
            });
    }

    function update() {
        var name = document.getElementById("nama_jabatan_edit").value;
        var id = document.getElementById("id").value;

        if (name === "") {
            alert("Nama Jabatan Boleh Kosong");
        } else {
            loading.style.display = "block";
            var newData = new FormData();
            newData.append("id", id);
            newData.append("name", name);
            fetch("api/user_update", {
                    method: "POST",
                    body: newData,
                })
                .then((response) => response.json())
                .then((data) => {
                    if (data.action) {
                        loading.style.display = "none";
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
    <script src="../admin/vendor/jquery/jquery.min.js"></script>
    <script src="../admin/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="../admin/vendor/jquery-easing/jquery.easing.min.js"></script>
    <script src="../admin/js/sb-admin-2.min.js"></script>
    <script src="../admin/vendor/chart.js/Chart.min.js"></script>
    <script src="../admin/js/demo/chart-area-demo.js"></script>
    <script src="../admin/js/demo/chart-pie-demo.js"></script>

</body>

</html>