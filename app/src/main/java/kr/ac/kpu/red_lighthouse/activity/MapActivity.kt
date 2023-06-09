package kr.ac.kpu.red_lighthouse.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kr.ac.kpu.red_lighthouse.R


class MapActivity : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private lateinit var mView: MapView
    var mLocationManager: LocationManager? = null
    var mLocationListener: LocationListener? = null
    lateinit var button: Button
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
    private val REQUEST_PERMISSION_LOCATION = 10
    lateinit var mMap : GoogleMap
    var mapList: ArrayList<ArrayList<String>?> = arrayListOf()// map에 전달할 값 저장

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.activity_map, container, false)


        mView = rootView.findViewById(R.id.mapView)
        button = rootView.findViewById(R.id.mapBtn)
        mView.onCreate(savedInstanceState)
        mView.getMapAsync(this)
        button.setOnClickListener{
            if(checkPermissionForLocation(requireContext())){
                startLocationUpdates()
            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

//        mFusedLocationProviderClient?.requestLocationUpdates(
//            mLocationRequest,
//            Looper.getMainLooper()
//        )
    }

    private fun checkPermissionForLocation(context: android.content.Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION
                )
                false
            }
        } else {
            true
        }
    }

    //내위치 추적 권한 허용코드
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            } else {
                Log.d("ttt", "onRequestPermissionsResult() _ 권한 허용 거부")
                Toast.makeText(
                    requireContext(),
                    "권한이 없어 해당 기능을 실행할 수 없습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //최초로는 오이도 빨간등대가 나오도록
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val count = arguments?.getInt("count")
        if(count != null){
            for(i in 0..count!!-1){
                mapList.add(arguments?.getStringArrayList("resultKey${i}"))
                val marker = LatLng(mapList[i]?.get(2)!!.toDouble(),mapList[i]?.get(3)!!.toDouble())
                mMap.addMarker(MarkerOptions().position(marker).title(mapList[i]?.get(1)))
            }
        }
        val marker1 = LatLng(37.3452397,126.6879337)
        mMap.addMarker(MarkerOptions().position(marker1).title("빨간등대"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker1))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f))

        mMap.setOnInfoWindowClickListener(this)


    }

    override fun onInfoWindowClick(marker: Marker) {
        val marker1 = LatLng(37.3452397,126.6879337)
        mMap.addMarker(MarkerOptions().position(marker1).title("빨간등대"))
        var slat = marker1.latitude
        var slng = marker1.longitude
        var sname = "빨간등대"
        var dlat = marker.position.latitude
        var dlng = marker.position.longitude
        var dname = marker.title
        val url = "nmap://route/public?slat="+slat+"&slng="+slng+"&sname="+sname+"&dlat="+dlat+"&dlng="+dlng+"&dname="+dname+"&appname=kr.ac.kpu.red_lighthouse"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }



    override fun onStart() {
        super.onStart()
        mView.onStart()
    }
    override fun onStop() {
        super.onStop()
        mView.onStop()
    }
    override fun onResume() {
        super.onResume()
        mView.onResume()
    }
    override fun onPause() {
        super.onPause()
        mView.onPause()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mView.onLowMemory()
    }
    override fun onDestroy() {
        mView.onDestroy()
        super.onDestroy()
    }
}


