<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('fish_models', function (Blueprint $table) {
            $table->id();
            $table->string('nama_ikan');
            $table->string('jenis_ikan');
            $table->string('harga');
            $table->string('gambar_ikan')->unique();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('fish_models');
    }
};