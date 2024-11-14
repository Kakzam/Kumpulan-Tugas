<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Fish Shop</title>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="../logo.png" />
    <!-- Bootstrap icons-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet" />
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="../landing/css/styles.css" rel="stylesheet" />
</head>

<body>
    <header class="py-5">
        <div class="container px-lg-5">
            <div class="p-4 p-lg-5 bg-light rounded-3 text-center">
                <div class="m-4 m-lg-5">
                    <h1 class="display-5 fw-bold">Selamat Datang!</h1>
                    <p class="fs-4">Kami menyajikan pilihan ikan terbaik, langsung dari laut ke meja Anda. Nikmati
                        kelezatan alami laut yang tiada tara dalam setiap gigitan. Dari ikan laut yang lezat hingga ikan
                        air tawar yang menyegarkan, kami memiliki beragam jenis ikan yang siap memperkaya hidangan Anda
                        setiap hari. Dapatkan kepuasan dan kesehatan untuk seluruh keluarga dengan ikan kami yang
                        dipilih dengan teliti.</p>
                    <a class="btn btn-primary btn-lg" href="#!">Hubungi Kami</a>
                </div>
            </div>
        </div>
    </header>
    <!-- Page Content-->
    <section class="pt-4">
        <div class="container px-lg-5">
            <!-- Page Features-->
            <div class="row gx-lg-5">
            </div>
        </div>
    </section>
    <!-- Footer-->
    <footer class="py-5 bg-dark">
        <div class="container">
            <p class="m-0 text-center text-white">Copyright &copy; Fish Shop 2024</p>
        </div>
    </footer>
    <!-- Bootstrap core JS-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Core theme JS-->
    <script src="../landing/js/scripts.js"></script>
    <script>
    window.addEventListener("DOMContentLoaded", (event) => {
        getDataFromAPI();
    });

    function getDataFromAPI() {
        fetch("api/fish")
            .then((response) => response.json())
            .then((data) => {

                var row = document.querySelector(".row.gx-lg-5");

                data.fish.forEach((item) => {
                    // Buat string HTML untuk card
                    var cardHTML =
                        `
    <div class="col-lg-6 col-xxl-4 mb-5">
        <div class="card bg-light border-0 h-100">
            <div class="card-body text-center p-4 p-lg-5 pt-0 pt-lg-0">
                <div class="feature bg-primary bg-gradient text-white rounded-3 mb-4 mt-n4"><img style="max-width: 500%; height: auto;" src="storage/` +
                        item
                        .gambar_ikan +
                        `" /></div>
                <h2 class="fs-4 fw-bold">` + item.nama_ikan +
                        `</h2>
                        <h4 class="fs-4 fw-bold">` + item.harga +
                        `</h4>
            </div>
        </div>
    </div>
`;

                    // Buat elemen HTML dari string HTML
                    var tempContainer = document.createElement('div');
                    tempContainer.innerHTML = cardHTML;
                    var cardElement = tempContainer.firstChild;
                    row.insertAdjacentHTML('beforeend', cardHTML);
                });
            })
            .catch((error) => {
                console.error("Terjadi kesalahan:", error);
            });
    }
    </script>
</body>

</html>