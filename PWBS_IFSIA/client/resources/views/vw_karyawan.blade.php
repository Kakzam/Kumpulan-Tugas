<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Tampil Data Karyawan</title>
</head>

<body class="m-5">
{{-- buat navigasi --}}
    <nav class="text-center mb-5">
        <button id="btn_tambah" class="bg-orange-600 h-12 w-28 rounded-full text-white uppercase font-bold">Tambah</button>
        <button id="btn_refresh" class="bg-stone-200 h-12 w-28 rounded-full uppercase font-bold">Refresh</button>
    </nav>
    {{-- buat table --}}
    <table class="w-full">
        {{-- judul table --}}
        <thead>
            <tr class="h-12">
                <th class="border-solid border-2 border-green-500 bg-green-200 w-1/12">Aksi</th>
                <th class="border-solid border-2 border-green-500 bg-green-200 w-1/12">NIK</th>
                <th class="border-solid border-2 border-green-500 bg-green-200 w-2/12">Nama Karyawan</th>
                <th class="border-solid border-2 border-green-500 bg-green-200 w-3/12">Alamat </th>
                <th class="border-solid border-2 border-green-500 bg-green-200 w-1/12">Telepon</th>
                <th class="border-solid border-2 border-green-500 bg-green-200 w-2/12">Jabatan</th>
            </tr>
        </thead>

        {{-- isi table --}}
        <tbody>
            @foreach ($result->karyawan as $output)
                <tr>
                    <td class="border-solid border-2 border-green-500 bg-transparent text-center px-2.5">-</td>
                    <td class="border-solid border-2 border-green-500 bg-transparent text-center px-2.5">{{ $output->kode_karyawan }}</td>
                    <td class="border-solid border-2 border-green-500 bg-transparent px-2.5">{{ $output->nama_karyawan }}</td>
                    <td class="border-solid border-2 border-green-500 bg-transparent px-2.5">{{ $output->alamat_karyawan }}</td>
                    <td class="border-solid border-2 border-green-500 bg-transparent text-center px-2.5">{{ $output->telepon_karyawan }}</td>
                    <td class="border-solid border-2 border-green-500 bg-transparent px-2.5">{{ $output->jabatan_karyawan }}</td>
                </tr>
            @endforeach
        </tbody>
    </table>

    {{-- @foreach ($result->karyawan as $output)
        <p>{{ $output->nama_karyawan }}</p>
    @endforeach --}}

    <script src="https://cdn.tailwindcss.com"></script>
</body>

</html>
