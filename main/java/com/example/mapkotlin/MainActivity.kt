package com.example.mapkotlin

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
//import android.widget.SearchView
//import android.support.annotation.NonNull
//import android.support.v4.app.ActivityCompat
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.common.location.LatLngBias
import com.tomtom.online.sdk.location.Locations
import com.tomtom.online.sdk.map.*
import com.tomtom.online.sdk.map.style.layers.Visibility
import com.tomtom.online.sdk.search.SearchApi
import com.tomtom.online.sdk.search.autocomplete.AutocompleteLocationDescriptor
import com.tomtom.online.sdk.search.autocomplete.AutocompleteSearchEngineDescriptor
import com.tomtom.online.sdk.search.autocomplete.AutocompleteSpecification
import com.tomtom.online.sdk.search.fuzzy.FuzzyLocationDescriptor
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchEngineDescriptor
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchSpecification
import com.tomtom.online.sdk.search.time.OpeningHoursMode
import com.tomtom.online.sdk.search.time.TimeDescriptor
import com.tomtom.online.sdk.search.OnlineSearchApi
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.nfc.Tag
import android.os.AsyncTask
import android.os.Build
import android.os.StrictMode
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.view.menu.ActionMenuItem
import androidx.appcompat.view.menu.ActionMenuItemView
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQuery
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.widget.SearchView;
import com.tomtom.online.sdk.search.autocomplete.AutocompleteSuggestion
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


open class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    //lateinit late initialization of non-null type variables
    private lateinit var map: TomtomMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
 //   private lateinit var preciseness : LatLng
    private lateinit var term : String
    private lateinit var locationDescriptor: Unit
    private var text : String = ""
    val northEast = LatLng(56.5, 34.6)
    val southWest = LatLng(23.5, 45.8)

    var displayList:MutableList<String> = ArrayList()


    val places = mutableListOf<SearchApi>()

    val url = "https://api.tomtom.com/search/2/autocomplete/?key=<DuujoKxauvhngFp2gzuoECD1ra58U8jK>&language=pl-PL"

    //val list: List<String> = List<String>(this, String())


//    val searchAdapter: Adapter

  //  val searchAutocompleteList: SearchView.SearchAutoComplete

    //val queryBuilder: FuzzySearchQueryBuilder =

    private lateinit var searchApi: SearchApi





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as MapFragment

        val searchBox = findViewById<TextView>(R.id.search_bar)
        mapFragment.getAsyncMap(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 101
    }

     fun setUpMap() {


        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )

        }
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                var currentLatLng = LatLng(location.latitude, location.longitude)
                val balloon = SimpleMarkerBalloon("Jesteś tutaj")
                map.addMarker(MarkerBuilder(currentLatLng).markerBalloon(balloon))
                //map.centerOn(CameraPosition.builder(currentLatLng).zoom(7.0).build())
            }





        }

        map.setLanguage("pl-PL")

        val searchEngineDescriptor = FuzzySearchEngineDescriptor.Builder()
            .language("pl-PL")
            .build()

    }

    override fun onMapReady(@NonNull tomtomMap: TomtomMap) {
        this.map = tomtomMap




        var clicked : Boolean = false

        val addPlaceToFavourite: TomtomMapCallback.OnMapLongClickListener =
            TomtomMapCallback.OnMapLongClickListener { latLng ->
                val chosenLatLng = LatLng(latLng.latitude, latLng.longitude)
                val balloon = SimpleMarkerBalloon(chosenLatLng.toString())
                map.addMarker(MarkerBuilder(chosenLatLng).markerBalloon(balloon))


               // var listFav: List<String> = List<String>(this,addPlaceToFavourite)


                //var listPlace: List<LatLng> = List<LatLng>(chosenLatLng)


                var arrayList: ArrayList<LatLng> = ArrayList()
                //var listFav: MutableList<String> = MutableList<String>(10, chosenLatLng)

                arrayList.add(chosenLatLng)
                var favList = findViewById<ActionMenuItemView>(R.id.menu_fav)
               Toast.makeText(this@MainActivity, arrayList.toString(), Toast.LENGTH_SHORT).show()

                favList.setOnClickListener(View.OnClickListener {

                        val intent = Intent(this,FavouritePlaces::class.java)
                    startActivity(intent)

                })

            }



        searchApi = OnlineSearchApi.create(this, "OLUjjte8UZ5GcK4q0HLoAtfH0pq5XHlX")
        var appContext: Context = applicationContext
        var searchApi = OnlineSearchApi.create(appContext)
        appContext = requireNotNull(this).application
        val searching : TomtomMapCallback

        map.addOnMapLongClickListener(addPlaceToFavourite)


         setUpMap()


    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {


        displayList.run { url }
        menuInflater.inflate(R.menu.main,menu)
        val searchItem = menu.findItem(R.id.menu_search)
        val myFav = menu.findItem(R.id.menu_fav)



      //  var charSearchApi: SearchApi
        var arrayAdapterPlaces: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)

        val favView = myFav.actionView as ExpandableListView

       // searchItem.setOnMenuItemClickListener {
          //  val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, SearchApi)
          //  displayList.setAdapter(adapter)
       // }
        if(searchItem != null) {
            val searchView = searchItem.actionView as TextView
            val searchText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            if(searchText != null) {
                searchText.hint = "Wyszukaj tutaj..."
                // searchView.setHint(arrayAdapterPlaces)
            }

           /* val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            val url: URL = URL("http://api.tomtom.com/search/2/autocomplete/?key=<DuujoKxauvhngFp2gzuoECD1ra58U8jK>&language=pl-PL")
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"

                inputStream.bufferedReader().use {
                    it.lines().forEach() {line ->
                        println(line)
                    }
                }
            }*/

        }



            return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun searchAddress(searchWord: String, autoCompleteTextView: AutoCompleteTextView) {
        var arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)





        searchApi.search(FuzzySearchQueryBuilder(searchWord)
            .withLanguage(Locale.getDefault().toLanguageTag())
            .withTypeAhead(true)
            .withMinFuzzyLevel(2).build())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<FuzzySearchResponse>() {
                override fun onSuccess(fuzzySearchResponse: FuzzySearchResponse) {
                    if (!fuzzySearchResponse.results.isEmpty()) {
                        arrayAdapter.clear()
                        //searchResultsMap.clear()
                       // if (this.currentLatLng != null) {
                           // val currentLocationTitle = getString(R.string.main_current_position)
                          //  arrayAdapter.add(currentLocationTitle)
                            //searchResultsMap[currentLocationTitle] = latLngCurrentPosition!!
                        }
                        for (result in fuzzySearchResponse.results) {
                            val addressString = result.address.freeformAddress
                            arrayAdapter.add(addressString)
                            //searchResultsMap[addressString] = result.position
                        }
                        arrayAdapter.apply {
                            this.clear()
                         //   this.addAll(arrayAdapter)
                            this.filter.filter("")
                       // }
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@MainActivity, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }



            })
    }
}


