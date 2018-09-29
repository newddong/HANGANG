package leekiyoung.newmap;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class MapsActivity extends Fragment implements OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener,ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    Location location;
    LocationManager locationManager;
    String locationProvider;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 100;
    static final LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
    private Marker currentMarker = null;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_maps,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        getPermission(Manifest.permission.ACCESS_FINE_LOCATION,MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        getPermission(Manifest.permission.ACCESS_COARSE_LOCATION,MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);








        return rootView;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12));


        //우측 상단에 내위치 버튼
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }else{

        }

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationProvider = locationManager.getBestProvider(new Criteria(),true);
        //Toast.makeText(getActivity(),"위치정보 공급자 : "+locationProvider,0).show();
        location = locationManager.getLastKnownLocation(locationProvider);

        setCurrentLocation(location,"시작","marker",12);


        //mMap.getUiSettings().setCompassEnabled(true);//나침반

        mMap.getUiSettings().setZoomControlsEnabled(true);//줌 컨트롤


        //LatLng seoul= new LatLng(37.49,127);
        //mMap.addMarker(new MarkerOptions().position(seoul).title("서울"));


    }

    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet,float zoom) {
        if ( currentMarker != null ) currentMarker.remove();

        if ( location != null) {
            //현재위치의 위도 경도 가져옴
            LatLng currentLocation = new LatLng( location.getLatitude(), location.getLongitude());

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLocation);
            markerOptions.title(markerTitle);
            markerOptions.snippet(markerSnippet);
            markerOptions.draggable(true);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            currentMarker = this.mMap.addMarker(markerOptions);

            this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,zoom));
            return;
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = this.mMap.addMarker(markerOptions);

        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION,zoom));
    }






    public GoogleMap getmMap(){
        return mMap;
    }

    public void addMarker(){
        LatLng sydney = new LatLng(37.49, 127);
        mMap.addMarker(new MarkerOptions().position(sydney).title("addMarker"));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "현재 위치를 표시합니다",Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                switch (requestCode) {
                    case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                        // If request is cancelled, the result arrays are empty.
                        if (grantResults.length > 0
                                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                            // permission was granted, yay! Do the
                            // contacts-related task you need to do.

                        } else {
                            Toast.makeText(getActivity(),"권한 요청이 거부되었습니다.",Toast.LENGTH_LONG).show();
                            // permission denied, boo! Disable the
                            // functionality that depends on this permission.
                        }
                        return;
                    }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void getPermission(String permission,int request){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    permission)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{permission},
                        request);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


}
