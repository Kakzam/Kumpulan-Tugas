<?php

namespace Database\Seeders;

use App\Models\JenisIkanModel;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class JenisSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        JenisIkanModel::create([
            'jenis_ikan' => 'Air Tawar'
        ]);

        JenisIkanModel::create([
            'jenis_ikan' => 'Air Asin'
        ]);

        JenisIkanModel::create([
            'jenis_ikan' => 'Air Hias'
        ]);

        JenisIkanModel::create([
            'jenis_ikan' => 'Air Konsumsi'
        ]);
    }
}