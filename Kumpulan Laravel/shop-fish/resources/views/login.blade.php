<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Modernize Free</title>
    <link rel="shortcut icon" type="image/png" href="../admin/src/assets/images/logos/favicon.png" />
    <link rel="stylesheet" href="../admin/src/assets/css/styles.min.css" />
</head>

<body>
    <!--  Body Wrapper -->
    <div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
        data-sidebar-position="fixed" data-header-position="fixed">
        <div
            class="position-relative overflow-hidden radial-gradient min-vh-100 d-flex align-items-center justify-content-center">
            <div class="d-flex align-items-center justify-content-center w-100">
                <div class="row justify-content-center w-100">
                    <div class="col-md-8 col-lg-6 col-xxl-3">
                        <div class="card mb-0">
                            <div class="card-body">
                                <a href="/" class="text-nowrap logo-img text-center d-block py-3 w-100">
                                    <img src="../logo.png" width="180" alt="">
                                </a>
                                <p class="text-center">Fish Shop</p>
                                <form>
                                    <div class="mb-3">
                                        <label for="exampleInputEmail1" class="form-label">Username</label>
                                        <input type="email" class="form-control" id="email">
                                    </div>
                                    <div class="mb-4">
                                        <label for="exampleInputPassword1" class="form-label">Password</label>
                                        <input type="password" class="form-control" id="password">
                                    </div>
                                    <button type="button" onclick="insert()"
                                        class="btn btn-primary w-100 py-8 fs-4 mb-4 rounded-2">MASUK</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="../admin/src/assets/libs/jquery/dist/jquery.min.js"></script>
    <script src="../admin/src/assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

    <script>
    function insert() {
        var username = document.getElementById("email").value;
        var password = document.getElementById("password").value;

        if (username === "") {
            alert("Username Tidak Boleh Kosong");
        } else if (password === "") {
            alert("Password Tidak Boleh Kosong");
        } else {
            var newData = new FormData();
            newData.append("username", username);
            newData.append("password", password);

            fetch("api/validation", {
                    method: "POST",
                    body: newData,
                })
                .then((response) => response.json())
                .then((data) => {
                    if (data.action) {
                        localStorage.setItem("login", true);
                        alert(data.message);
                        window.location.href = '/dashboard';
                    } else alert(data.message);
                })
                .catch((error) => {
                    console.error("Terjadi kesalahan:", error);
                });
        }
    }
    </script>
</body>

</html>