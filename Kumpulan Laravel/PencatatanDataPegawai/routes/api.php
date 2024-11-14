<?php

use App\Http\Controllers\DataPegawai;
use App\Http\Controllers\JabatanController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/

/* ============================ User ============================ */

Route::get('user', [DataPegawai::class, 'index']);
Route::post('user', [DataPegawai::class, 'create']);
Route::post('validation', [DataPegawai::class, 'store']);
Route::post('user_update', [DataPegawai::class, 'edit']);
Route::post('user/{parameter}', [DataPegawai::class, 'destroy']);

/* ============================ Jabatan ========================== */
Route::get('jabatan', [JabatanController::class, 'index']);
Route::post('jabatan', [JabatanController::class, 'create']);
Route::post('jabatan_update', [JabatanController::class, 'edit']);
Route::post('jabatan/{parameter}', [JabatanController::class, 'destroy']);