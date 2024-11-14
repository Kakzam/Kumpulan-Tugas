<?php

use App\Http\Controllers\Karyawan;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

// Create Route Baru
// Route untuk tampil data karyawan
Route::get('/view', [Karyawan::class, 'view']);
//route untuk detail data
Route::get('/detail/{parameter}', [Karyawan::class, 'detail']);
//route untuk hapus data
Route::delete('/delete/{parameter}', [Karyawan::class, 'delete']);
// Route untuk insert data
Route::post('/insert', [Karyawan::class, 'insert']);
// Route untuk update data
Route::put('/update/{parameter}', [Karyawan::class, 'update']);
