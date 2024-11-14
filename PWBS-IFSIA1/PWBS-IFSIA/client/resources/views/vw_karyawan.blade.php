<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tampil Data Karyawan</title>
</head>
<body>
    <!--buat menu -->
    <nav>
        <button id="btn_tambah">Tambah</button>
        <button id="btn_refresh">Refresh</button>
    </nav>
    <!--buat tabel -->
    <table class="w-full">
        <!-- buat judul -->
        <thead>
            <tr class="bg-green-600 h-5">
                <th class="border-2 border-slate-500">Aksi</th>
                <th class="border-2 border-slate-500">Nik</th>
                <th class="border-2 border-slate-500">Nama Karyawan</th>
                <th class="border-2 border-slate-500">Alamat</th>
                <th class="border-2 border-slate-500">Telepon</th>
                <th class="border-2 border-slate-500">Jabatan</th>
            </tr>
        </thead>
        <!-- buat judul -->
        <tbody>
        @foreach ($result as $output)
            <tr>
                <td>-</td>
                <td><p>{{$output->kode_karyawan}}</p></td>
                <td><p>{{$output->nama_karyawan}}</p></td>
                <td><p>{{$output->alamat_karyawan}}</p></td>
                <td><p>{{$output->telepon_karyawan}}</p></td>
                <td><p>{{$output->jabatan_karyawan}}</p></td>
            </tr>
        @endforeach
        </tbody>
    </table>
   
    <!--CDN tailwind -->
    <script src="https://cdn.tailwindcss.com"></script>
</body>
</html>