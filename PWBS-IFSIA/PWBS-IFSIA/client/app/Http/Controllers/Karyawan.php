<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class Karyawan extends Controller
{
    protected $client;

    // buat konstruktor
    function __construct()
    {
        $this->client = new \GuzzleHttp\Client();
    }
    function index()
    {
        // echo "Ini Halaman Home";
        // echo env("API_URL");

        // buat url (service "GET" dari server)
        $url = env("API_URL"). "view";
        // ambil service "GET" dari server
        $request = $this->client->get($url);
        // tampilkan hasil
        $response = $request->getBody();
        
        // foreach(json_decode($response)->karyawan as $data)
        // {
        //     echo $data->nama_karyawan."<br>";
        // }

        $data["result"] = json_decode($response)->karyawan;

        // panggil view "vw_karyawan"
        return view("vw_karyawan", $data);
    }
}
