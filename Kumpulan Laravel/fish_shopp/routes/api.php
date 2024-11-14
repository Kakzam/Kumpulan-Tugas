<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/* ============================ User ============================ */

Route::get('user', [UserController::class, 'index']);
Route::post('user', [UserController::class, 'create']);
Route::post('user_update', [UserController::class, 'edit']);
Route::post('user/{parameter}', [UserController::class, 'destroy']);

/* ============================ Fish ============================ */

Route::get('fish', [FishController::class, 'index']);
Route::post('fish', [FishController::class, 'create']);
Route::post('fish_update', [FishController::class, 'edit']);
Route::post('fish/{parameter}', [FishController::class, 'destroy']);