<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Login</title>
    <link href="../admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https:
        //fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">
    <link href="../admin/css/sb-admin-2.min.css" rel="stylesheet">
</head>

<body class="bg-gradient-primary">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-xl-4">
                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0">
                        <div class="row">
                            <div class="col-lg">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-4">Selamat Datang</h1>
                                    </div>
                                    <form>
                                        <div class="form-group">
                                            <input type="email" class="form-control form-control-user" id="username"
                                                placeholder="Enter Email Address...">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" class="form-control form-control-user" id="password"
                                                placeholder="Password">
                                        </div>
                                        <button type="button" id="tombol" onclick="simpan()"
                                            class="btn btn-primary btn-user btn-block">
                                            Login
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
    function simpan() {
        var username = document.getElementById('username').value;
        var password = document.getElementById('password').value;

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
                    if (data.action) window.location.href = "/dashboard";
                    else alert("Silahkan periksa kembali usernam dan password anda")
                })
                .catch((error) => {
                    console.error("Terjadi kesalahan:", error);
                });
        }
    }
    </script>
    <script src="../admin/vendor/jquery/jquery.min.js"></script>
    <script src="../admin/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="../admin/vendor/jquery-easing/jquery.easing.min.js"></script>
    <script src="../admin/js/sb-admin-2.min.js"></script>

</body>

</html>