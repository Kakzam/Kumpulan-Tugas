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

    function view()
    {
        // ambil fungsi viewData(dari Mkaryawan)
        $data = $this->model->viewData();
        // tampilkan hasil dari "tb_karyawan"
        return response([
            "karyawan" => $data
        ],http_response_code());
    }

    function detail($parameter)
    {
        // ambil fungsi viewData(dari Mkaryawan)
        $data = $this->model->detailData($parameter);
        // tampilkan hasil dari "tb_karyawan"
        return response([
            "karyawan" => $data
        ],http_response_code());
    }

    // buat fungsi untuk hapus data
    function delete($parameter)
    {
        // cek apakah data (nik) tersedia/tidak
        $cek_data = $this->model->detailData($parameter);
        // jika data ditemukan
        if(count($cek_data) == 1)
        {
            // hapus data sesuai nik
            $this->model->deleteData($parameter);
            // buat pesan
            $status = 1;
            $pesan = "Data Berhasil Dihapus";
        }
        // jika data tidak ditemukan
        else
        {
            // buat pesan
            $status = 0;
            $pesan = "Data Gagal Dihapus ! (NIK Tidak Ditemukan !)";
        }
        // tampilkan hasil respon
        return response([
            "status" => $status,
            "pesan" => $pesan,
        ],http_response_code());
    }

    // fungsi untuk simpan data
    function insert(Request $req)
    {
        // ambil data dari hasil input
        $data = [
            "nik" => $req->nik_karyawan,
            "nama" => $req->nama_karyawan,
            "alamat" => $req->alamat_karyawan,
            "telepon" => $req->telepon_karyawan,
            "jabatan" => $req->jabatan_karyawan,
        ];
        // cek apakah nik sudah pernah tersimpan/belum
        $parameter = base64_encode($data["nik"]);

        $cek_data = $this->model->detailData($parameter);
        // jika data tidak ditemukan
        if(count($cek_data) == 0)
        {
            // simpan data
            $this->model->saveData($data["nik"], $data["nama"], $data["alamat"], $data["telepon"], $data["jabatan"]);
            // buat pesan
            $status = 1;
            $pesan = "Data Berhasil Disimpan";
        }
        // jika data ditemukan
        else
        {
            // buat pesan
            $status = 0;
            $pesan = "Data Gagal Disimpan ! (NIK Sudah Pernah Tersimpan !)";
        }
        // tampilkan hasil respon
        return response([
            "status" => $status,
            "pesan" => $pesan,
        ],http_response_code());
    }
    
    // fungsi untuk ubah data
    function update($parameter, Request $req)
    {
        // ambil data dari hasil input
        $data = [
            "nik" => $req->nik_karyawan,
            "nama" => $req->nama_karyawan,
            "alamat" => $req->alamat_karyawan,
            "telepon" => $req->telepon_karyawan,
            "jabatan" => $req->jabatan_karyawan,
        ];
        // cek apakah nik data karyawan sudah pernah tersimpan / belum tersimpan
        $cek_data = $this->model->checkUpdate($parameter, $data["nik"]);
        // jika data tidak ditemukan
        if(count($cek_data) == 0)
        {
            // update data
            $this->model->updateData($data["nik"], $data["nama"], $data["alamat"], $data["telepon"], $data["jabatan"], $parameter);
            // buat pesan
            $status = 1;
            $pesan = "Data Berhasil Diubah";
        }
        // jika data ditemukan
        else
        {
            // buat pesan
            $status = 0;
            $pesan = "Data Gagal Diubah ! (NIK Sudah Pernah Tersimpan !)";
        }
        // tampilkan hasil respon
        return response([
            "status" => $status,
            "pesan" => $pesan,
        ],http_response_code());
    }
}