package com.example.signin_firebase

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BusBook : AppCompatActivity() {


    var book_stat= intArrayOf(0,0,0,0,0,0,0,0,0,0)
    //var changed_book_stat=intArrayOf(0,0,0,0,0,0,0,0,0,0)
    var book_stat_len=book_stat.size
    var available=book_stat_len
    var booked=0
    var seatID= intArrayOf(R.id.Seat_icon1,R.id.Seat_icon2,R.id.Seat_icon3,R.id.Seat_icon4,R.id.Seat_icon5,R.id.Seat_icon6,R.id.Seat_icon7,R.id.Seat_icon8,R.id.Seat_icon9,R.id.Seat_icon10)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_book)

        setBackgroundGrey()
        get_seat_status()

        findViewById<ImageView>(R.id.Seat_icon1).setOnClickListener{ clicked(R.id.Seat_icon1,1) }
        findViewById<ImageView>(R.id.Seat_icon2).setOnClickListener{ clicked(R.id.Seat_icon2,2) }
        findViewById<ImageView>(R.id.Seat_icon3).setOnClickListener{ clicked(R.id.Seat_icon3,3) }
        findViewById<ImageView>(R.id.Seat_icon4).setOnClickListener{ clicked(R.id.Seat_icon4,4) }
        findViewById<ImageView>(R.id.Seat_icon5).setOnClickListener{ clicked(R.id.Seat_icon5,5) }
        findViewById<ImageView>(R.id.Seat_icon6).setOnClickListener{ clicked(R.id.Seat_icon6,6) }
        findViewById<ImageView>(R.id.Seat_icon7).setOnClickListener{ clicked(R.id.Seat_icon7,7) }
        findViewById<ImageView>(R.id.Seat_icon8).setOnClickListener{ clicked(R.id.Seat_icon8,8) }
        findViewById<ImageView>(R.id.Seat_icon9).setOnClickListener{ clicked(R.id.Seat_icon9,9) }
        findViewById<ImageView>(R.id.Seat_icon10).setOnClickListener{ clicked(R.id.Seat_icon10,10) }
        findViewById<Button>(R.id.reset_seat).setOnClickListener{book_stat= intArrayOf(0,0,0,0,0,0,0,0,0,0)
        set_seat_status(book_stat)
        get_seat_status()}
        findViewById<Button>(R.id.book).setOnClickListener{set_seat_status(book_stat)}
        findViewById<TextView>(R.id.Booked_viewer).setText(""+booked)
        findViewById<TextView>(R.id.Available_viewer).setText(""+available)

//        get_seat_status(book_stat)


    }

    private fun setBackgroundGrey() {
        for (i in 0..9){
            findViewById<ImageView>(seatID[i]).setBackgroundColor(Color.GRAY)
        }

    }

    private fun get_seat_status() {
        val docRef = db.collection("Buses").document("MH43A9045")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    var result= document.data?.get("Seat_Status") as ArrayList<Int>
                    book_stat= result?.toIntArray()!!
                    updateSeatColor()
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }

    private fun updateSeatColor() {
        for(i in 0..9) {
            if (book_stat[i] == 0) {
                findViewById<ImageView>(seatID[i]).setBackgroundColor(Color.BLUE)
            } else {
                findViewById<ImageView>(seatID[i]).isClickable=false
                findViewById<ImageView>(seatID[i]).setBackgroundColor(Color.RED)
            }
        }
    }

    private fun set_seat_status(bookStat: IntArray) {
        var user= hashMapOf("Seat_Status" to bookStat.toCollection(ArrayList()))
        db.collection("Buses").document("MH43A9045").set(user).addOnSuccessListener {
            Log.d(TAG, "DocumentSnapshot successfully written!")
            Toast.makeText(this,"Updated scuccess fully",Toast.LENGTH_LONG).show()
            updateSeatColor()}
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e)
                Toast.makeText(this,"Unsuccessful entry",Toast.LENGTH_LONG).show()}
    }



    private fun clicked(seatIcon: Int, i: Int) {
        if (book_stat[i - 1] == 1) {
            findViewById<ImageView>(seatIcon).setBackgroundColor(Color.BLUE)
            book_stat[i - 1] = 0
        }else{
                findViewById<ImageView>(seatIcon).setBackgroundColor(Color.RED)
                book_stat[i - 1] = 1}

        refresh_booking_stat()

    }

    private fun refresh_booking_stat() {
        available=10
        booked=0
        for (i in book_stat){
            if (i==1){
                available -= 1
                booked += 1}
        }
        findViewById<TextView>(R.id.Available_viewer).setText(""+available)
        findViewById<TextView>(R.id.Booked_viewer).setText(""+booked)

    }


}