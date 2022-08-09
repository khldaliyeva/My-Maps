package com.khldaliyeva.mymaps

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.khldaliyeva.mymaps.models.Place
import com.khldaliyeva.mymaps.models.UserMap

const val EXTRA_USER_MAP = "EXTRA_USER_MAP"
const val EXTRA_MAP_TITLE = "EXTRA_MAP_TITLE"

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_CODE = 564
    }

    private lateinit var rvMaps: RecyclerView
    private lateinit var fabCreateMap: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMaps = findViewById(R.id.rvMaps)
        fabCreateMap = findViewById(R.id.fabCreateMap)

        // Set the layoutManager on the RecyclerView
        rvMaps.layoutManager = LinearLayoutManager(this)
        // Generate dummy data
        val userMaps = generateSampleData()
        // Set the adapter on the RecyclerView
        rvMaps.adapter = MapsAdapter(this, userMaps, object : MapsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                Log.i(TAG, "onItemClick $position")
                // When user taps on any item in the RecyclerView, navigate to a new activity
                val intent = Intent(this@MainActivity, DisplayMapActivity::class.java)
                intent.putExtra(EXTRA_USER_MAP, userMaps[position])
                startActivity(intent)
            }
        })

        fabCreateMap.setOnClickListener {
            Log.i(TAG, "setOnClickListener: Tap on FAB")
            val intent = Intent(this@MainActivity, CreateMapActivity::class.java)
            intent.putExtra(EXTRA_MAP_TITLE, "new map name")
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun generateSampleData(): List<UserMap> {
        return listOf(
            UserMap(
                "Memories from University",
                listOf(
                    Place("Branner Hall", "Best dorm at Stanford", 37.426, -122.163),
                    Place(
                        "Gates CS building",
                        "Many long nights in this basement",
                        37.430,
                        -122.173
                    ),
                    Place("Pinkberry", "First date with my wife", 37.444, -122.170)
                )
            ),
            UserMap(
                "January vacation planning!",
                listOf(
                    Place("Tokyo", "Overnight layover", 35.67, 139.65),
                    Place("Ranchi", "Family visit + wedding!", 23.34, 85.31),
                    Place("Singapore", "Inspired by \"Crazy Rich Asians\"", 1.35, 103.82)
                )
            ),
            UserMap(
                "Singapore travel itinerary",
                listOf(
                    Place("Gardens by the Bay", "Amazing urban nature park", 1.282, 103.864),
                    Place(
                        "Jurong Bird Park",
                        "Family-friendly park with many varieties of birds",
                        1.319,
                        103.706
                    ),
                    Place("Sentosa", "Island resort with panoramic views", 1.249, 103.830),
                    Place(
                        "Botanic Gardens",
                        "One of the world's greatest tropical gardens",
                        1.3138,
                        103.8159
                    )
                )
            ),
            UserMap(
                "My favorite places in the Midwest",
                listOf(
                    Place(
                        "Chicago",
                        "Urban center of the midwest, the \"Windy City\"",
                        41.878,
                        -87.630
                    ),
                    Place("Rochester, Michigan", "The best of Detroit suburbia", 42.681, -83.134),
                    Place(
                        "Mackinaw City",
                        "The entrance into the Upper Peninsula",
                        45.777,
                        -84.727
                    ),
                    Place("Michigan State University", "Home to the Spartans", 42.701, -84.482),
                    Place("University of Michigan", "Home to the Wolverines", 42.278, -83.738)
                )
            ),
            UserMap(
                "Restaurants to try",
                listOf(
                    Place("Champ's Diner", "Retro diner in Brooklyn", 40.709, -73.941),
                    Place("Althea", "Chicago upscale dining with an amazing view", 41.895, -87.625),
                    Place("Shizen", "Elegant sushi in San Francisco", 37.768, -122.422),
                    Place(
                        "Citizen Eatery",
                        "Bright cafe in Austin with a pink rabbit",
                        30.322,
                        -97.739
                    ),
                    Place(
                        "Kati Thai",
                        "Authentic Portland Thai food, served with love",
                        45.505,
                        -122.635
                    )
                )
            )
        )
    }
}