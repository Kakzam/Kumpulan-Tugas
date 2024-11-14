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

// buat route karyawan
// route untuk tampil data
Route::get('/view',[Karyawan::class,'view']);
// route untuk detail data
Route::get('/detail/{parameter}',[Karyawan::class,'detail']);
// route untuk delete data
Route::delete('/delete/{parameter}',[Karyawan::class,'delete']);
// route untuk insert data
Route::post('/insert',[Karyawan::class,'insert']);
// route untuk update data
Route::delete('/update/{parameter}',[Karyawan::class,'update']);
