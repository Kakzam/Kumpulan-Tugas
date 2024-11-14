<?php

namespace Database\Seeders;

use App\Models\UserModel;
use Illuminate\Database\Seeder;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        UserModel::create([
            'username' => 'a',
            'password' => 'a',
            'nama_lengkap' => 'AA'
        ]);
    }
}