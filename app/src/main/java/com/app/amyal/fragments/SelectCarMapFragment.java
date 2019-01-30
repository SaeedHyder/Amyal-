package com.app.amyal.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.amyal.R;
import com.app.amyal.entities.HotelMapEnt;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.map.abstracts.CarMapMarkerItemBinder;
import com.app.amyal.map.abstracts.GoogleMapOptions;
import com.app.amyal.map.abstracts.HotelMapMarkertemBinder;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AutoCompleteLocation;
import com.app.amyal.ui.views.TitleBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.app.amyal.R.id.map;

public class SelectCarMapFragment extends BaseFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.txt_noresult)
    AnyTextView txtNoresult;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.edit_search)
    AutoCompleteLocation editSearch;
    @BindView(R.id.ll_searchBox)
    LinearLayout llSearchBox;
    @BindView(R.id.btn_location)
    CircleImageView btnLocation;
    @BindView(R.id.ll_maps)
    RelativeLayout llMaps;
    Unbinder unbinder;
    private Location Mylocation;
    private LocationListener listener;
    private GoogleMap mMap;
    GoogleApiClient googleApiClient;

    private double longitude;
    private double latitude;
    private View viewParent;
    private SupportMapFragment mapFragment;
    private GoogleMapOptions<HotelMapEnt> googleMapOptions;
    private ArrayList<HotelMapEnt> entiity;

    private ArrayList<HotelMapEnt> userCollection = new ArrayList<>();

    public static SelectCarMapFragment newInstance() {
        return new SelectCarMapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (viewParent != null) {
            ViewGroup parent = (ViewGroup) viewParent.getParent();
            if (parent != null)
                parent.removeView(viewParent);
        }
        try {
            viewParent = inflater.inflate(R.layout.fragment_select_hotels_map, container, false);

        } catch (InflateException e) {
            e.printStackTrace();
        }
        if (viewParent != null)
            ButterKnife.bind(this, viewParent);

        // ButterKnife.bind(this, viewParent);
        unbinder = ButterKnife.bind(this, viewParent);

        return viewParent;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMainActivity().setBackground(R.drawable.bg_hotel);

        if (mapFragment == null)
            initMap();

        editSearch.setAutoCompleteTextListener(new AutoCompleteLocation.AutoCompleteLocationListener() {
            @Override
            public void onTextClear() {

            }

            @Override
            public void onItemSelected(Place selectedPlace) {

                if (selectedPlace.getLatLng() != null) {
                    latitude = selectedPlace.getLatLng().latitude;
                    longitude = selectedPlace.getLatLng().longitude;

                    movemap(new LatLng(latitude, longitude));
                }

            }

        });

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.showRightButton(R.drawable.search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSearchBox.setVisibility(View.VISIBLE);
            }
        });
        titleBar.setSubHeading(getString(R.string.nearby));

    }


    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    void HomeMarkersData(LatLng latLng) {
        //serviceHelper.enqueueCall(webService.homeCompanyList(prefHelper.getUserId()), userHomeList);

        entiity = new ArrayList<>();

        entiity.add(new HotelMapEnt(1, "$ 180", returnLatLng(latLng, 50).latitude + "", returnLatLng(latLng, 50).longitude + "", "drawable://" + R.drawable.carpin));
        entiity.add(new HotelMapEnt(1, "$ 150", returnLatLng(latLng, 50).latitude + "", returnLatLng(latLng, 70).longitude + "", "drawable://" + R.drawable.carpin));
        entiity.add(new HotelMapEnt(1, "$ 200", returnLatLng(latLng, 80).latitude + "", returnLatLng(latLng, 90).longitude + "", "drawable://" + R.drawable.carpin));
        entiity.add(new HotelMapEnt(1, "$ 190", returnLatLng(latLng, 80).latitude + "", returnLatLng(latLng, 100).longitude + "", "drawable://" + R.drawable.carpin));

        userCollection = new ArrayList<>();

        userCollection.addAll(entiity);

        addMarker(userCollection);

    }


    public LatLng returnLatLng(LatLng lng, double meters) {

        double coef = meters * 0.0000089;
        double new_lat = lng.latitude + coef;
        double new_long = lng.longitude + coef / Math.cos(lng.latitude * 0.018);

        return new LatLng(new_lat, new_long);
    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(getMainActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    void addMarker(ArrayList<HotelMapEnt> userCollection) {

        CarMapMarkerItemBinder carMapMarkerItemBinder = new CarMapMarkerItemBinder(getDockActivity());
        googleMapOptions = new GoogleMapOptions<>(getDockActivity(),
                mMap,
                userCollection,
                null,
                carMapMarkerItemBinder);

        googleMapOptions.addMarkers();
        //  fixZoom();

        markerDialog();
        //mMap.setInfoWindowAdapter(googleMapOptions);
        //new BalloonViewBinder(getDockActivity())
    }

    private void markerDialog() {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (userCollection != null) {
                    getDockActivity().replaceDockableFragment(new HotelDetailFragment(), HotelDetailFragment.class.getSimpleName());
                }
                return true;
            }
        });

    }

    private void getLocation() {
        if (mMap != null)
            mMap.clear();
        if (ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (Mylocation == null) {
            locationRequest.setInterval(1000);
        }
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location mlocation) {
                if (mlocation != null) {
                    Mylocation = mlocation;

                    LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, listener);
                    //listener = null;
                    if (Mylocation != null) {
                        //Getting longitude and latitude
                        longitude = Mylocation.getLongitude();
                        latitude = Mylocation.getLatitude();
                        // origin = new LatLng(latitude, longitude);
                        //  String Address = getCurrentAddress(latitude, longitude);


                        movemap(new LatLng(latitude, longitude));
                        //serviceHelper.enqueueCall(webService.updateWasherLocation(Integer.parseInt(prefHelper.getUserId()), String.valueOf(latitude), String.valueOf(longitude)), userUpdateLocation);
                        locationRequest.setNumUpdates(1);
                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.location_error));
                    }
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, listener);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            {
                //        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, listener);
            }
        }


    }

    private void movemap(LatLng latLng) {

        // bindview();
        HomeMarkersData(latLng);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latLng.latitude, latLng.longitude))
                .zoom(15)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //.bearing(0).tilt(50)
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMyLocationEnabled(true);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (getMainActivity().statusCheck() && googleApiClient.isConnected())
            getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
