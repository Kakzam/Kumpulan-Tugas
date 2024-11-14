<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class FishModel extends Model
{
    use HasFactory;
    protected $fillable = ['nama_ikan', 'jenis_ikan', 'harga', 'gambar_ikan'];
}