package com.parkmk.ui.history

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.parkmk.R
import com.parkmk.databinding.FragmentHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private var _b: FragmentHistoryBinding? = null
    private val b get() = _b!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentHistoryBinding.bind(view)
        loadHistory()
    }

    private fun loadHistory() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        Firebase.firestore
            .collection("users").document(uid)
            .collection("sessions")
            .orderBy("startTime", Query.Direction.DESCENDING)
            .limit(50)
            .get()
            .addOnSuccessListener { snapshot ->
                b.tvEmpty.isVisible = snapshot.isEmpty

                val container = b.sessionsContainer
                container.removeAllViews()

                snapshot.documents.forEach { doc ->
                    val spotName  = doc.getString("spotName")  ?: "—"
                    val zoneName  = doc.getString("zoneName")  ?: "—"
                    val zoneCode  = doc.getString("zoneCode")  ?: "—"
                    val plate     = doc.getString("plate")     ?: "—"
                    val cost      = doc.getDouble("totalCost") ?: 0.0
                    val durSec    = doc.getLong("durationSec") ?: 0L
                    val smsNum    = doc.getString("smsNumber") ?: "144414"

                    val fmt = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                    val startTs  = doc.getTimestamp("startTime")
                    val endTs    = doc.getTimestamp("endTime")
                    val startStr = if (startTs != null) fmt.format(startTs.toDate()) else "—"
                    val endStr   = if (endTs   != null) fmt.format(endTs.toDate())   else "—"

                    val mins = durSec / 60
                    val secs = durSec % 60
                    val durStr = "${mins} мин ${secs} сек"

                    // Создај card за секоја сесија
                    val card = layoutInflater.inflate(
                        R.layout.item_session, container, false
                    )
                    card.findViewById<TextView>(R.id.tvSessionSpot).text  = spotName
                    card.findViewById<TextView>(R.id.tvSessionZone).text  = "$zoneName · $zoneCode"
                    card.findViewById<TextView>(R.id.tvSessionPlate).text = plate
                    card.findViewById<TextView>(R.id.tvSessionStart).text = "Почеток: $startStr"
                    card.findViewById<TextView>(R.id.tvSessionEnd).text   = "Крај: $endStr"
                    card.findViewById<TextView>(R.id.tvSessionDur).text   = durStr
                    card.findViewById<TextView>(R.id.tvSessionCost).text  = String.format("%.2f ден", cost)
                    card.findViewById<TextView>(R.id.tvSessionSms).text   = "SMS → $smsNum"

                    container.addView(card)
                }
            }
    }

    override fun onResume() { super.onResume(); loadHistory() }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}