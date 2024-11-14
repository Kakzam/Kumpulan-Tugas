<?php

namespace App\Http\Controllers;

use App\Models\UserModel;
use Illuminate\Http\Request;

class UserController extends Controller
{
    function __construct()
    {
        $this->model = new UserModel();
    }

    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        return response([
            "user" => $this->model->all()
        ], http_response_code());
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create(Request $request)
    {
        $this->model->create([
            'username' => $request->username,
            'password' => $request->password,
            'nama_lengkap' => $request->name
        ]);

        return response([
            "action" => true,
            "message" => "Data User Berhasil Disimpan"
        ], http_response_code());
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $user = UserModel::where('username', $request->username)->where('password', $request->password)->first();

        if ($user) {
            return
                response([
                    "action" => true,
                    "message" => "Anda Berhasil Login"
                ], 201);
        } else {
            return
                response([
                    "action" => false,
                    "message" => "Silahkan periksa username dan password anda"
                ], 201);
        }
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
    public function edit(Request $request)
    {
        $user = $this->model->where('id', $request->id)->first();

        if (!$user) {
            return response([
                "action" => false,
                "message" => "Data User Gagal Diupdate! (Belum Tersimpan)"
            ], 201);
        }

        $user->update([
            'username' => $request->username,
            'password' => $request->password,
            'nama_lengkap' => $request->name
        ]);

        return response([
            "action" => true,
            "message" => "Data User Berhasil Diupdate"
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
    public function destroy($id)
    {
        $user = $this->model->find($id);

        if (!$user) return response([
            "action" => false,
            "message" => "Data User Tidak Ditemukan"
        ], 404);

        $user->delete();

        return response([
            "action" => true,
            "message" => "Data User Berhasil Dihapus"
        ], 200);
    }
}