<?php

namespace App\Http\Controllers;

use App\Models\JabatanModel;
use Illuminate\Http\Request;

class JabatanController extends Controller
{

    function __construct()
    {
        $this->model = new JabatanModel();
    }

    public function index()
    {
        return response([
            "jabatan" => $this->model->all()
        ], http_response_code());
    }

    public function create(Request $request)
    {
        $this->model->create([
            'nama_jabatan' => $request->name
        ]);

        return response([
            "action" => true,
            "message" => "Data Jabatan Berhasil Disimpan"
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
                "message" => "Data Jabatan Gagal Diupdate! (Belum Tersimpan)"
            ], 201);
        }

        $fish->update([
            'nama_jabatan' => $req->name
        ]);

        return response([
            "action" => true,
            "message" => "Data Jabatan Berhasil Diupdate"
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
            "message" => "Data Jabatan Tidak Ditemukan"
        ], 404);

        $fish->delete();

        return response([
            "action" => true,
            "message" => "Data Jabatan Berhasil Dihapus"
        ], 200);
    }
}