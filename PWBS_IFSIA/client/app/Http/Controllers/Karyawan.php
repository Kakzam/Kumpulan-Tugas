<?php



namespace App\Http\Controllers;

use Illuminate\Http\Request;

class Karyawan extends Controller
{
    function __construct()
    {
        $this->client = new \GuzzleHttp\Client();

    }

    // Buat Fungsi index (tampil data kayrawan)
    function index()
    {
        // $this->client = new \GuzzleHttp\Client();
        // echo env("API_URL");

        $url = env("API_URL")."view";
        // echo $url;


        // ambil service "get" dari server
        $request = $this->client->get($url);

     
        // menampilkan hasil
        $response = $request->getBody();

        // tampilkan data
        // foreach (json_decode($response)->karyawan as $hasil) {
        //     echo $hasil->nama_karyawan."<br>";
        // }

        $data["result"] = json_decode($response);

        // panggil view "vw_karyawan"

        return view("vw_karyawan",$data);
    }
}
