<?php

namespace App\Http\Controllers;

use App\Models\Mkaryawan;
use Illuminate\Http\Request;

class Karyawan extends Controller
{

    function __construct()
    {
        $this->model = new Mkaryawan();
    }

    // Function Untuk Tampil Data
    function view()
    {   // ambil fungsi dari viewData(dari Mkaryawan)
        $data = $this->model->viewData();

        // tampikan hasil dari "tbkaryawan"
        return response([
            "karyawan" => $data
        ], http_response_code());
    }

    // Function Untuk Detail Data
    function detail($parameter)
    {   // ambil fungsi detailData(dari Mkaryawan)

        $data = $this->model->detailData($parameter);

        // tampikan hasil dari "tbkaryawan"
        return response([
            "karyawan" => $data
        ], http_response_code());
    }

    // buat fungsi untuk delete data
    function delete($parameter)
    {
        // cek data dari tbl_karyawan
        //(berdasarkan nik)
        $data = $this->model->detailData($parameter);

        // jika data ditemukan
        if (count($data) != 0) {
            // lakukan penghapusan data
            $data = $this->model->deleteData($parameter);
            // buat pesan dan status hasil penghapusan data
            $status = 1;
            $pesan = "Data Berhasil di Hapus";
        }
        // jika data tidak ditemukan
        else {
            // tampilkan pesan data gagal dihapus
            $status = 1;
            $pesan = "Data Gagal di Hapus ! (NIK tidak ditemukan !)";
        }

        // tampilkan hasil respon

        return response([
            "status" => $status,
            "pesan" => $pesan
        ], http_response_code());
    }

    // buat function untuk insert data
    function insert(Request $req)
    {
        // ambil data hasil input
        $data = array(
            "nik" => $req->nik_karyawan,
            "nama" => $req->nama_karyawan,
            "alamat" => $req->alamat_karyawan,
            "telepon" => $req->telepon_karyawan,
            "jabatan" => $req->jabatan_karyawan,

        );
        // baruu
        $parameter = base64_encode($data["nik"]);
        // cek apakah data karyawan (nik) sudah pernah tersimpan/belum
        $check = $this->model->detailData($parameter);


        // jika data tidak ditemukan
        if (count($check) == 0) {
            // lakukan proses penyimpanan
            $this->model->saveData($data["nik"], $data["nama"], $data["alamat"], $data["telepon"], $data["jabatan"]);
            // buat pesan dan status hasil penyimpanan data
            $status = 1;
            $pesan = "Data Berhasil disimpan";
        }
        // jika data tidak ditemukan
        else {

            // tampilkan pesan data gagal disimpan
            $status = 0;
            $pesan = "Data Gagal disimpan";
        }
        // tampilkan hasil respon

        return response([
            "status" => $status,
            "pesan" => $pesan
        ], http_response_code());
    }

    // Buat Fungsi untuk ubah data
    function update($parameter, Request $req)
    {
        // ambil data hasil input
        $data = array(
            "nik" => $req->nik_karyawan,
            "nama" => $req->nama_karyawan,
            "alamat" => $req->alamat_karyawan,
            "telepon" => $req->telepon_karyawan,
            "jabatan" => $req->jabatan_karyawan,
        );

        // Cek apakah data karyawan tersedia/tidak
        $cek = $this->model->checkUpdate($parameter, $data["nik"]);
        // Jika data tidak ditemukan
        if (count($cek) == 0) {
            //  ubah data
            $this->model->updateData(
                $data["nik"],
                $data["nama"],
                $data["alamat"],
                $data["telepon"],
                $data["jabatan"],
                $parameter
            );
            // tampilkan pesan
            $status = "1";
            $pesan = "Data Berhasil Diubah";
        }
        // jika data ditemukan
        else {
            // tampilkan pesan
            $status = 0;
            $pesan = "Data Gagal Diubah ! (NIK Sudah Pernah Tersimpan)";
        }
        // tampilkan hasil respon
        return response([
            "status" => $status,
            "pesan" => $pesan
        ], http_response_code());
    }
}
