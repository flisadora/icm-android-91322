package deti.icm.trotinet.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import deti.icm.trotinet.R
import deti.icm.trotinet.model.Ride
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ListRidesAdapter(
    private val context: Context,
    rides: List<Ride> = emptyList()
) :  RecyclerView.Adapter<ListRidesAdapter.ViewHolder>() {

    private val rides = rides.toMutableList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(ride: Ride) {
            val route = itemView.findViewById<TextView>(R.id.ride_route)
            route.text = ride.endRoute
            val date = itemView.findViewById<TextView>(R.id.ride_date)
            date.text = ride.date?.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)).toString()
            val cost = itemView.findViewById<TextView>(R.id.ride_cost)
            cost.text = ride.cost.toString() + " €"
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.ride_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ride = rides[position]
        holder.bind(ride)
    }

    override fun getItemCount(): Int = rides.size

    fun refresh(rides: List<Ride>) {
        this.rides.clear()
        this.rides.addAll(rides)
        notifyDataSetChanged()
    }
}
