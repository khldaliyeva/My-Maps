package com.khldaliyeva.mymaps

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.khldaliyeva.mymaps.databinding.ActivityCreateMapBinding

class CreateMapActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val TAG = "CreateMapActivity"
    }

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityCreateMapBinding

    private var markers: MutableList<Marker> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = intent.getStringExtra(EXTRA_MAP_TITLE)



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapFragment.view?.let {
            Snackbar.make(it, "Long press to add a marker!", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK") {}
                .setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
                .show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnInfoWindowClickListener { markerToDelete ->
            Log.i(TAG, "setOnInfoWindowClickListener - delete this marker")
            markers.remove(markerToDelete)
            markerToDelete.remove()
        }

        mMap.setOnMapLongClickListener { latLng ->
            Log.i(TAG, "setOnMapLongClickListener")
            showAlertDialog(latLng)
        }
    }

    private fun showAlertDialog(latLng: LatLng) {
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_place, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Create a marker")
            .setView(placeFormView)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val title = placeFormView.findViewById<EditText>(R.id.etTitle).text.toString()
            val description = placeFormView.findViewById<EditText>(R.id.etDescription).text.toString()

            if(title.trim().isEmpty() || description.trim().isEmpty()) {
                Toast.makeText(this, "Place must have non-empty title and description", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val marker = mMap.addMarker(
                MarkerOptions().position(latLng).title(title)
                    .snippet(description)
            )
            if (marker != null) {
                markers.add(marker)
            }
            dialog.dismiss()
        }
    }
}