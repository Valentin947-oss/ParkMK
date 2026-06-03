package com.parkmk.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parkmk.R
import com.parkmk.model.Vehicle

class VehicleAdapter(
    private var vehicles: List<Vehicle>,
    private val onSetActive: (Vehicle) -> Unit,
    private val onDelete: (Vehicle) -> Unit
) : RecyclerView.Adapter<VehicleAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlate:  TextView    = view.findViewById(R.id.tvPlate)
        val tvName:   TextView    = view.findViewById(R.id.tvVehicleName)
        val tvActive: TextView    = view.findViewById(R.id.tvActiveBadge)
        val btnSet:   TextView    = view.findViewById(R.id.btnSetActive)
        val btnDel:   ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_vehicle, parent, false))

    override fun onBindViewHolder(h: VH, i: Int) {
        val v = vehicles[i]
        h.tvPlate.text  = v.plate
        h.tvName.text   = v.displayName
        if (v.isActive) {
            h.tvActive.visibility = View.VISIBLE
            h.btnSet.visibility   = View.GONE
        } else {
            h.tvActive.visibility = View.GONE
            h.btnSet.visibility   = View.VISIBLE
        }
        h.btnSet.setOnClickListener { onSetActive(v) }
        h.btnDel.setOnClickListener { onDelete(v) }
    }

    override fun getItemCount() = vehicles.size

    fun update(list: List<Vehicle>) {
        vehicles = list
        notifyDataSetChanged()
    }
}