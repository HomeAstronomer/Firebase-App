package com.example.signin_firebase

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


public var user: FirebaseUser? = null
public val auth = getInstance()

class MainActivity : AppCompatActivity() {

    public val auth = getInstance()


    private var RC_SIGN_IN: Int = 123
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        createRequest()
        user=auth.currentUser
        if (user?.displayName!=null) signIn()
        findViewById<TextView>(R.id.textView).setText(user?.displayName)


        findViewById<Button>(R.id.sign_in).setOnClickListener {
            signIn()

        }
        findViewById<Button>(R.id.sign_out).setOnClickListener{
            user=auth.currentUser
            signout()
        }
        findViewById<Button>(R.id.cur_user).setOnClickListener{
            user=auth.currentUser
            findViewById<TextView>(R.id.textView).setText(user?.displayName)
        }




    }

    public fun signout() {

        AuthUI.getInstance().signOut(this).addOnCompleteListener {
            Toast.makeText(applicationContext,"you have signed out", LENGTH_LONG).show()
            findViewById<TextView>(R.id.textView).setText("User Signed "+user?.displayName+"out")
        }
    }

    private fun signIn() {
        user = auth.currentUser
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun createRequest() {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(applicationContext,"Failed in catch block", LENGTH_LONG).show()
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }

    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    user = auth.currentUser
                    Toast.makeText(applicationContext,"Logging IN as "+user?.displayName, LENGTH_LONG).show()
                    findViewById<TextView>(R.id.textView).setText("Logged IN"+user?.displayName)
                    val intent= Intent(this,Landing_page::class.java)
                    startActivity(intent)


                } else {
                    Toast.makeText(applicationContext,"Failed in else blok", LENGTH_LONG).show()
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)

                }
            }
    }
}


