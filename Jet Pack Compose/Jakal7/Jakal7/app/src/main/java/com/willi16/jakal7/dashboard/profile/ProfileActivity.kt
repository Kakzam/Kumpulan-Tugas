package com.willi16.jakal7.dashboard.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.willi16.jakal7.R
import com.willi16.jakal7.databinding.ActivityProfileBinding
import com.willi16.jakal7.login.SignInActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileBinding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding.root)

        auth = FirebaseAuth.getInstance()
        Glide.with(this)
            .load(auth.currentUser?.photoUrl)
            .placeholder(R.drawable.progress_animation)
            .into(profileBinding.ivProfile)

        profileBinding.ivNama.text = auth.currentUser?.displayName
        profileBinding.tvEmail.text = auth.currentUser?.email

         profileBinding.btnLogout.setOnClickListener{
             auth.signOut()
             val intent = Intent(this, SignInActivity::class.java)
             startActivity(intent)
             finishAffinity()
         }
    }
}