<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\FishController;
use App\Http\Controllers\JenisIkanController;
use App\Http\Controllers\UserController;

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

Route::get('user', [UserController::class, 'index']);
Route::post('user', [UserController::class, 'create']);
Route::post('validation', [UserController::class, 'store']);
Route::post('user_update', [UserController::class, 'edit']);
Route::post('user/{parameter}', [UserController::class, 'destroy']);

/* ============================ Fish ============================ */

Route::get('fish', [FishController::class, 'index']);
Route::post('fish', [FishController::class, 'create']);
Route::post('fish_update', [FishController::class, 'edit']);
Route::post('fish/{parameter}', [FishController::class, 'destroy']);

/* ============================ Jenis Ikan ============================ */

Route::get('jenis', [JenisIkanController::class, 'index']);
Route::post('jenis', [JenisIkanController::class, 'create']);
Route::post('jenis_update', [JenisIkanController::class, 'edit']);
Route::post('jenis/{parameter}', [JenisIkanController::class, 'destroy']);