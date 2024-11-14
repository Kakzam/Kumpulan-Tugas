<?php

namespace App\Http\Controllers;

use App\Models\UserModel;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;

class DataPegawai extends Controller
{
    function __construct()
    {
        $this->model = new UserModel();
    }

    public function index()
    {
        return response([
            "jabatan" => $this->model->all()
        ], http_response_code());
    }

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
            'nama_lengkap' => $request->name,
            'username' => $request->username,
            'password' => $request->password,
            'gambar' => $gambarPath,
            'alamat' => $request->alamat,
            'phone' => $request->phone_number,
            'jenis_kelamin' => $request->jenis_kelamin
        ]);

        return response([
            "action" => true,
            "request" => $request,
            "message" => "Data Pegawai Berhasil Disimpan"
        ], http_response_code());
    }

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

    public function show(string $id)
    {
        //
    }

    public function edit(Request $req)
    {
        $fish = $this->model->where('id', $req->id)->first();

        if (!$fish) {
            return response([
                "action" => false,
                "message" => "Data Ikan Gagal Diupdate! (Belum Tersimpan)"
            ], 201);
        }

        $gambar = $req->file('gambar');
        $gambarPath = null;

        if ($gambar) {
            if ($fish->gambar_ikan) {
                Storage::disk('public')->delete($fish->gambar);
            }

            $gambarExtension = $gambar->getClientOriginalExtension();
            $gambarName = $gambar->getClientOriginalName();
            $gambarNameWithoutExtension = pathinfo($gambarName, PATHINFO_FILENAME);
            $newGambarName = $gambarNameWithoutExtension . '_' . time() . '.' . $gambarExtension;
            $gambarPath = $gambar->storeAs('galery', $newGambarName, 'public');
            
            $fish->update([
                'nama_lengkap' => $req->name,
                'username' => $req->username,
                'password' => $req->password,
                'alamat' => $req->alamat,
                'phone' => $req->phone_number,
                'jenis_kelamin' => $req->jenis_kelamin,
                'gambar_ikan' => $gambarPath
            ]);
        } else {
            $fish->update([
                'nama_lengkap' => $req->name,
                'username' => $req->username,
                'password' => $req->password,
                'alamat' => $req->alamat,
                'phone' => $req->phone_number,
                'jenis_kelamin' => $req->jenis_kelamin
            ]);
        }

        return response([
            "action" => true,
            "message" => "Data Pegawai Berhasil Diupdate"
        ], http_response_code());
    }

    public function update(Request $request, string $id)
    {
        //
    }

    public function destroy($parameter)
    {
        $fish = $this->model->find($parameter);

        if (!$fish) return response([
            "action" => false,
            "message" => "Data Pegawai Tidak Ditemukan"
        ], 404);

        if ($fish->gambar) Storage::disk('public')->delete($fish->gambar);
        $fish->delete();

        return response([
            "action" => true,
            "message" => "Data Pegawai Berhasil Dihapus"
        ], 200);
    }
}