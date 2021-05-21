package com.example.myapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.myapp.MainActivity.OnMapReadyCallback
import com.tomtom.online.sdk.common.location.BoundingBox
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.*
import android.content.Context
import android.util.Log


public class MainActivity constructor (private val context: Context) : AppCompatActivity()
{



    var mapFragment = MapFragment.newInstance()
    val mapView = MapView(context)
     //val mapProperties : MapProperties
    val LOG_FILE_PATH = null
    val MAP_TILES_SOURCE_ID = null
    val TRAFFIC_INCIDENTS_SOURCE_ID = null
    val TRAFFIC_FLOW_SOURCE_ID = null


    val cameraPosition = CameraPosition.builder()
        .focusPosition(LatLng(12.34, 23.45))
        .zoom(10.0)
        .bearing(24.0)
        .pitch(45.2)
        .build()

    val keysMap = mapOf(
        ApiKeyType.MAPS_API_KEY to "maps-key",
        ApiKeyType.TRAFFIC_API_KEY to "traffic-key"
    )

    val cameraFocusArea = CameraFocusArea.Builder(
        BoundingBox(LatLng(52.407943, 4.808601), LatLng(52.323363, 4.969053))
    )
        .bearing(24.0)
        .pitch(45.2)
        .build()

    val layerSetConfiguration = LayerSetConfiguration.Builder()
        .mapTilesConfiguration(MAP_TILES_SOURCE_ID)
        .trafficIncidentsTilesConfiguration(TRAFFIC_INCIDENTS_SOURCE_ID)
        .trafficFlowTilesConfiguration(TRAFFIC_FLOW_SOURCE_ID)
        .build()

    class MainActivity(var firstName: String?, var lastName: String?) {
        constructor() : this(null, null) {
        }
    }

 //   val fragment = MapFragment.newInstance(mapProperties)
  //  val view = MapView(context, mapProperties)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        //mapFragment.getAsyncMap(onMapReadyCallback)
        MainActivity()

        MapProperties.Builder()
            .backgroundColor(Color.BLUE)
            .build()

        MapProperties.Builder()
            .customStyleUri("asset://styles/style.json")
            .build()


        MapProperties.Builder()
            .cameraPosition(cameraPosition)
            .build()


        MapProperties.Builder()
            .cameraFocusArea(cameraFocusArea)
            .build()


        MapProperties.Builder()
            .padding(MapPadding(50.0, 40.0, 100.0, 80.0))
            .build()


        MapProperties.Builder()
            .keys(keysMap)
            .build()

        MapProperties.Builder()
            .mapStyleSource(MapStyleSource.STYLE_MERGER)
            .build()


        MapProperties.Builder()
            .mapStyleSource(MapStyleSource.STYLE_MERGER)
            .layerSetConfiguration(layerSetConfiguration)
            .build()


    }



    /**
     * Callback interface executed when the map is ready to be used.
     * The instance of this interface is set to [MapFragment],
     * and the [OnMapReadyCallback.onMapReady] is triggered
     * when the map is fully initialized and not-null.
     */
    interface OnMapReadyCallback {
        /**
         * Called when the map is ready to be used.
         */
        fun onMapReady(@NonNull tomtomMap: TomtomMap)
    }

   /*  private val onMapReadyCallback = OnMapReadyCallback { tomtomMap ->
        val mapPaddingVertical = resources.getDimension(R.dimen.map_padding_vertical).toDouble()
        val mapPaddingHorizontal = resources.getDimension(R.dimen.map_padding_horizontal).toDouble()

        tomtomMap.uiSettings.currentLocationView.hide()
        tomtomMap.setPadding(
            mapPaddingVertical, mapPaddingHorizontal,
            mapPaddingVertical, mapPaddingHorizontal
        )
        tomtomMap.collectLogsToFile(SampleApp.LOG_FILE_PATH)
    } */

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
       // tomtomMap.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    public override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    public override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    public override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    public override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    public override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onHostSaveInstanceState()
    }

    //override fun onMapReady(tomtomMap: TomtomMap) {
    //    tomtomMap.collectLogsToFile(MainActivity.LOG_FILE_PATH)
   // }






}
