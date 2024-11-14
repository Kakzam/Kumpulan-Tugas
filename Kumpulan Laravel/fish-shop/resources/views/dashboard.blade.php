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
            <div class="container-fluid">
                <!--  Row 1 -->
                <div class="row">
                    <div class="col-lg-4">
                        <div class="card w-100">
                            <div class="card-body">
                                <h2 class="card-title fw-semibold">User</h2>
                                <br>
                                <h4 class="card-title" id="user"></h4>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <div class="card w-100">
                            <div class="card-body">
                                <h5 class="card-title fw-semibold">Fish</h5>
                                <br>
                                <h4 class="card-title" id="fish"></h4>
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
window.addEventListener("DOMContentLoaded", (event) => {
    getDataUser();
    getDataFish();
});

function getDataUser() {
    fetch("api/user")
        .then((response) => response.json())
        .then((data) => {
            document.getElementById("user").textContent = data.user.length;
        })
        .catch((error) => {
            console.error("Terjadi kesalahan:", error);
        });
}

function getDataFish() {
    fetch("api/fish")
        .then((response) => response.json())
        .then((data) => {
            document.getElementById("fish").textContent = data.fish.length;
        })
        .catch((error) => {
            console.error("Terjadi kesalahan:", error);
        });
}

function logout() {
    localStorage.removeItem("login");
    window.location.href = '/login';
}
</script>

</html>