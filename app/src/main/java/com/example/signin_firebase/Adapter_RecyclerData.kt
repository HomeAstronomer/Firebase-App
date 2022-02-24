package com.example.signin_firebase



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter_RecyclerData(private val mList: List<Recycler_data>,onNoteListener: onNoteListener) : RecyclerView.Adapter<Adapter_RecyclerData.ViewHolder>() {
    var mOnNoteListener=onNoteListener

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bus_layout, parent, false)


        return ViewHolder(view,mOnNoteListener)

    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val Recycler_data = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(Recycler_data.imageId)

        // sets the text to the textview from our itemHolder class
        holder.Bus_name.text = Recycler_data.Name
        holder.Available_Seats.text=Recycler_data.avail_seats
        holder.Timing.text=Recycler_data.Timing
        holder.Bus_no.text=Recycler_data.Bus_No

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View, var onNoteListener: onNoteListener) : RecyclerView.ViewHolder(ItemView) , View.OnClickListener{

        val imageView: ImageView = itemView.findViewById(R.id.imageView2)
        val Bus_name: TextView = itemView.findViewById(R.id.BusName)
        val Available_Seats:TextView=itemView.findViewById(R.id.SeatAvailability)
        val Timing:TextView=itemView.findViewById(R.id.Time)
        val Bus_no:TextView=itemView.findViewById(R.id.BusNo)


        init {
            itemView.setOnClickListener(this)
            this.onNoteListener=onNoteListener

        }
        override fun onClick(p0: View?) {
            onNoteListener.onNote(adapterPosition)
        }


    }
    public interface onNoteListener{
        fun  onNote(position: Int)
    }
}


