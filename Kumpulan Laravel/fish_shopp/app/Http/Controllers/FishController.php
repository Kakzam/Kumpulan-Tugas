<?php

namespace App\Http\Controllers;

use App\Models\FishModel;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;

class FishController extends Controller
{
    function __construct()
    {
        $this->model = new FishModel();
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
        $gambar = $request->file('gambar');
        $gambarPath = null;

        if ($gambar) {
            $gambarExtension = $gambar->getClientOriginalExtension();
            $gambarName = $gambar->getClientOriginalName();
            $gambarNameWithoutExtension = pathinfo($gambarName, PATHINFO_FILENAME);
            $newGambarName = $gambarNameWithoutExtension . '_' . time() . '.' . $gambarExtension;

            $gambarPath = $gambar->storeAs('galery', $newGambarName, 'public');
        }

        $this->model->create([
            'nama_ikan' => $request->name,
            'harga' => $request->price,
            'gambar_ikan' => $gambarPath
        ]);

        return response([
            "action" => true,
            "message" => "Data Ikan Berhasil Disimpan"
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
                "message" => "Data Galery Gagal Diupdate! (Belum Tersimpan)"
            ], 201);
        }

        $gambar = $req->file('gambar');
        $gambarPath = null;

        if ($gambar) {
            if ($fish->gambar_galery) {
                Storage::disk('public')->delete($fish->gambar_ikan);
            }

            $gambarExtension = $gambar->getClientOriginalExtension();
            $gambarName = $gambar->getClientOriginalName();
            $gambarNameWithoutExtension = pathinfo($gambarName, PATHINFO_FILENAME);
            $newGambarName = $gambarNameWithoutExtension . '_' . time() . '.' . $gambarExtension;

            $gambarPath = $gambar->storeAs('galery', $newGambarName, 'public');
        }

        if ($gambar) {
            $fish->update([
                'nama_ikan' => $req->name,
                'harga' => $req->price,
                'gambar_ikan' => $gambarPath
            ]);
        } else {
            $fish->update([
                'nama_ikan' => $req->name,
                'harga' => $req->price
            ]);
        }

        return response([
            "action" => true,
            "message" => "Data Ikan Berhasil Diupdate"
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
            "message" => "Data Ikan Tidak Ditemukan"
        ], 404);


        if ($fish->gambar_galery) Storage::disk('public')->delete($fish->gambar_galery);

        $fish->delete();

        return response([
            "action" => true,
            "message" => "Data Ikan Berhasil Dihapus"
        ], 200);
    }
}