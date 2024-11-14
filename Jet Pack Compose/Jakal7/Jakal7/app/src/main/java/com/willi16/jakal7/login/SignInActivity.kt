package com.willi16.jakal7.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.willi16.jakal7.R
import com.willi16.jakal7.databinding.ActivitySignInBinding
import com.willi16.jakal7.home.HomeScreenActivity
import com.willi16.jakal7.home.ProggresDialog

class SignInActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 9001
        const val TAG = "SignInActivity"
    }

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    private lateinit var sigInBinding : ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sigInBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(sigInBinding.root)

        mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        sigInBinding.btnLogin.setOnClickListener() {
//            private val signIn = registerForActivityResult() { result ->
//                // Mengolah hasil dari proses sign in di sini
//            }
            signIn()
        }

        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/proyek1-1137c.appspot.com/o/background.jpg?alt=media&token=a917eec0-08c6-44f6-82a8-c9e06ff3d496")
            .placeholder(R.drawable.progress_animation)
            .into(sigInBinding.imageLogin)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val show = ProggresDialog(this)
        show.showLoading()
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exepection = task.exception
            show.dismissLoading()
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)
                    Log.d(TAG, "FirebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken)
                } catch (e: ApiException) {
                    Log.w(TAG, "Google sign in Failed", e)
                    show.showLoading()
                }
            } else {
                Log.w(TAG, exepection?.message.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val show = ProggresDialog(this)
        show.showLoading()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) {
                show.dismissLoading()
                if (it.isSuccessful) {
                    Log.d(TAG, "SignBerhasil :: success")
                    val intent = Intent(this, HomeScreenActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Tahap 3 Gagal", Toast.LENGTH_SHORT).show()
                    Log.w(TAG, "SignWithCredential : Failure", it.exception)
                    show.showLoading()
                }
            }
    }
}