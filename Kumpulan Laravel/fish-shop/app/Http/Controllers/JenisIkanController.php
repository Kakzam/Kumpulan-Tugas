<?php

namespace App\Http\Controllers;

use App\Models\JenisIkanModel;
use Illuminate\Http\Request;

class JenisIkanController extends Controller
{
    function __construct()
    {
        $this->model = new JenisIkanModel();
    }

    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        return response([
            "fish" => $this->model->all()
        ], http_response_code());
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create(Request $request)
    {
        $this->model->create([
            'jenis_ikan' => $request->name
        ]);

        return response([
            "action" => true,
            "message" => "Data Jenis Ikan Berhasil Disimpan"
        ], http_response_code());
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(Request $req)
    {
        $fish = $this->model->where('id', $req->id)->first();

        if (!$fish) {
            return response([
                "action" => false,
                "message" => "Data Jenis Ikan Gagal Diupdate! (Belum Tersimpan)"
            ], 201);
        }

        $fish->update([
            'jenis_ikan' => $req->name
        ]);

        return response([
            "action" => true,
            "message" => "Data Jenis Ikan Berhasil Diupdate"
        ], http_response_code());
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy($parameter)
    {
        $fish = $this->model->find($parameter);

        if (!$fish) return response([
            "action" => false,
            "message" => "Data Jenis Ikan Tidak Ditemukan"
        ], 404);

        $fish->delete();

        return response([
            "action" => true,
            "message" => "Data Jenis Ikan Berhasil Dihapus"
        ], 200);
    }
}