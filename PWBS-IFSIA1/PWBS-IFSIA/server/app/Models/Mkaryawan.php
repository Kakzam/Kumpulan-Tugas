<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB; 

class Mkaryawan extends Model
{
    function viewData()
    {
        $query = DB::table('tb_karyawan')
        ->select("nik AS kode_karyawan","nama AS nama_karyawan","alamat AS alamat_karyawan","telepon AS telepon_karyawan", "jabatan AS jabatan_karyawan")
        ->orderBy("nik")        
        ->get();

        return $query;
    }

    function detailData($parameter)
    {
        $query = DB::table('tb_karyawan')
        ->select("nik AS kode_karyawan","nama AS nama_karyawan","alamat AS alamat_karyawan","telepon AS telepon_karyawan", "jabatan AS jabatan_karyawan")
       // ->where("nik","=",$parameter)
       //ambil nik dengan hash (md5)
       // ->whereRaw("MD5(nik) = '$parameter'")
       //->where(DB::raw("MD5(nik)"), '=', $parameter)
       //ambil nik dengan base64_encode
        ->where(DB::raw("TO_BASE64(nik)"), '=', $parameter)
        ->orderBy("nik")   
        ->orderBy("nik")        
        ->get();

        return $query;
    }

    //fungsi untuk delete data
    function deleteData($parameter)
    {
        DB::table("tb_karyawan")
           ->where(DB::raw("TO_BASE64(nik)"), '=', $parameter)
           ->delete();
    }

    // buat fungsi untuk simpan data
    function saveData($nik,$nama,$alamat,$telepon,$jabatan)
    {
        DB::table("tb_karyawan")
        ->insert([
            "nik" => $nik,
            "nama" => $nama,
            "alamat" => $alamat,
            "telepon" => $telepon,
            "jabatan" => $jabatan,
        ]);
    }
    //buat fungsi untuk cek ubah data
    function checkUpdate($nik_lama, $nik_baru)
    {
        $query = DB::table("tb_karyawan")
            ->select("nik")
            ->where("nik","=", $nik_baru)
            ->where(DB::raw("TO_BASE64(nik)"), "!=", $nik_lama)
            ->get();

        return $query;
    }

    // buat fungsi untuk ubah data
    function updateData($nik,$nama,$alamat,$telepon,$jabatan, $nik_lama)
    {
        DB::table("tb_karyawan")
        ->where(DB::raw("TO_BASE64(nik)"), '=', $nik_lama)
        ->update([
            "nik" => $nik,
            "nama" => $nama,
            "alamat" => $alamat,
            "telepon" => $telepon,
            "jabatan" => $jabatan,
        ]);
    }

}
