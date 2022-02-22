package com.example.signin_firebase

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


val db = Firebase.firestore
class Landing_page : AppCompatActivity() {
    private lateinit var firebase_data: MutableList<DocumentSnapshot>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        findViewById<TextView>(R.id.user_desc).setText("Welcome \n"+user?.displayName)

        findViewById<Button>(R.id.Login_page).setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        findViewById<Button>(R.id.Sign_Out).setOnClickListener{
            signout()
        }

        findViewById<Button>(R.id.bus_MH43A).setOnClickListener{
            startActivity(Intent(this,BusBook::class.java))
        }
        findViewById<ImageView>(R.id.back_img).setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }


        val bus_data=db.collection("Buses").get()
        bus_data.addOnSuccessListener { document->
            if (document != null) {

                 firebase_data = document.documents
                findViewById<EditText>(R.id.editTextTextMultiLine2).setText(""+firebase_data.get(0).get("Bus_Name") )
                setRecycler()

            } else {
                Log.d(ContentValues.TAG, "No such document")
            }
        }


    }

    private fun setRecycler() {
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<Recycler_data>()
        for (i in 0..firebase_data.size-1) {

            data.add(Recycler_data(R.drawable.logo,
                firebase_data.get(i).get("Bus_Name").toString(),firebase_data.get(i).get("Timing").toString(),"10/10",firebase_data.get(i).get("Bus_No").toString()))

        }
        val adapter = Adapter_RecyclerData(data)
        recyclerview.adapter = adapter
    }

    public fun signout() {

        AuthUI.getInstance().signOut(this).addOnCompleteListener {
            Toast.makeText(applicationContext,"you have signed out", Toast.LENGTH_LONG).show()
            findViewById<TextView>(R.id.user_desc).setText("User Signed out")
        }
    }
}