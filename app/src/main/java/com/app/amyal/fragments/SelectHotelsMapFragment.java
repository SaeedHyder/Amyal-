package com.app.amyal.fragments;

import android.Manifest;
import android.content.Intent;
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
import com.app.amyal.entities.HotelGalleryEnt;
import com.app.amyal.entities.HotelListEnt;
import com.app.amyal.entities.HotelListModel;
import com.app.amyal.entities.HotelMapEnt;
import com.app.amyal.entities.HotelSearchModel;
import com.app.amyal.fragments.abstracts.BaseFragment;
import com.app.amyal.global.AppConstants;
import com.app.amyal.global.WebServiceConstants;
import com.app.amyal.helpers.UIHelper;
import com.app.amyal.interfaces.ActivityResultInterface;
import com.app.amyal.map.abstracts.GoogleMapOptions;
import com.app.amyal.map.abstracts.HotelMapMarkertemBinder;
import com.app.amyal.retrofit.GsonFactory;
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
import com.google.gson.Gson;

import static com.app.amyal.R.id.map;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class SelectHotelsMapFragment extends BaseFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,ActivityResultInterface {

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
    private GoogleMapOptions<HotelListModel> googleMapOptions;

    HotelListEnt hotelListEnt;

    HotelListModel hotelListModel;

    public static SelectHotelsMapFragment newInstance() {
        return new SelectHotelsMapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            hotelListEnt = GsonFactory.getConfiguredGson().fromJson(getArguments().getString(AppConstants.HotelListEnt), HotelListEnt.class);
        }

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
        getMainActivity().setOnActivityResultInterface(this);

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

                    //movemap(new LatLng(latitude, longitude));
                }

            }

        });

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

        /*entiity = new ArrayList<>();

        entiity.add(new HotelMapEnt(1, "$ 180", returnLatLng(latLng, 50).latitude + "", returnLatLng(latLng, 50).longitude + "", "drawable://" + R.drawable.hotelpin));
        entiity.add(new HotelMapEnt(1, "$ 150", returnLatLng(latLng, 50).latitude + "", returnLatLng(latLng, 70).longitude + "", "drawable://" + R.drawable.hotelpin));
        entiity.add(new HotelMapEnt(1, "$ 200", returnLatLng(latLng, 80).latitude + "", returnLatLng(latLng, 90).longitude + "", "drawable://" + R.drawable.hotelpin));
        entiity.add(new HotelMapEnt(1, "$ 190", returnLatLng(latLng, 80).latitude + "", returnLatLng(latLng, 100).longitude + "", "drawable://" + R.drawable.hotelpin));

        userCollection = new ArrayList<>();

        userCollection.addAll(entiity);*/

        //addMarker(hotelListEnt.getResponseModel());

    }


    public LatLng returnLatLng(LatLng lng, double meters) {

        double coef = meters * 0.0000089;
        double new_lat = lng.latitude + coef;
        double new_long = lng.longitude + coef / Math.cos(lng.latitude * 0.018);

        return new LatLng(new_lat, new_long);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        /*titleBar.showRightButton(R.drawable.search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSearchBox.setVisibility(View.VISIBLE);
            }
        });*/
        titleBar.setSubHeading(getString(R.string.nearby));

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

    void addMarker(List<HotelListModel> userCollection) {

        HotelMapMarkertemBinder mapMarkerItemBinder = new HotelMapMarkertemBinder(getDockActivity());
        googleMapOptions = new GoogleMapOptions<>(getDockActivity(),
                mMap,
                userCollection,
                null,
                mapMarkerItemBinder);

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
                if (hotelListEnt != null && hotelListEnt.getResponseModel() != null && hotelListEnt.getResponseModel().size() > 0) {

                    if (marker.getTag() != null) {

                        String LangCode = "en";
                        String CurCode = "AED";

                        if(prefHelper.isLogin()){

                            LangCode = prefHelper.getUser().getUser().getLanguageCode();
                            CurCode = prefHelper.getUser().getUser().getCurrencyCode();

                        }

                        hotelListModel = (HotelListModel) marker.getTag();
                        HotelSearchModel hotelSearchModel = HotelSearchModel.getInstance();
                        hotelSearchModel.setSequenceNumber(hotelListEnt.getSequenceNumber());
                        hotelSearchModel.setHotelCode(hotelListModel.getHotelCode());

                        serviceHelper.enqueueCall(webService.GetHotelDetails(LangCode,CurCode,hotelListModel.getHotelCode()/*,hotelListModel.getRoomCombinations().get(0).getRatePlanCode(),hotelListModel.getRoomCombinations().get(0).getRoomType()*/), WebServiceConstants.getHotelDetails);
                    }
                }
                return true;
            }
        });

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getHotelDetails:

                HotelGalleryEnt hotelGalleryEnt = (HotelGalleryEnt) result;

                if (hotelGalleryEnt != null) {
                    hotelListModel.setHotelGalleryEnt(hotelGalleryEnt);
                    HotelDetailFragment hotelDetailFragment = new HotelDetailFragment();
                    Bundle orderBundle = new Bundle();
                    orderBundle.putString(AppConstants.HotelListModel, new Gson().toJson(hotelListModel));
                    hotelDetailFragment.setArguments(orderBundle);
                    getDockActivity().replaceDockableFragment(hotelDetailFragment, HotelDetailFragment.class.getSimpleName());
                }

                break;
        }
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


                        //movemap(new LatLng(latitude, longitude));
                        if (hotelListEnt != null && hotelListEnt.getResponseModel()!= null && hotelListEnt.getResponseModel().size() > 0){
                            movemap(new LatLng(Double.valueOf( hotelListEnt.getResponseModel().get(0).getLatitude()), Double.valueOf(hotelListEnt.getResponseModel().get(0).getLongitude())));
                        }
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
                .zoom(10)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (hotelListEnt != null)
            addMarker(hotelListEnt.getResponseModel());

        //.bearing(0).tilt(50)
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getMainActivity().locationPermission();
        //movemap(new LatLng(latitude, longitude));
        if (hotelListEnt != null && hotelListEnt.getResponseModel()!= null && hotelListEnt.getResponseModel().size() > 0){
            movemap(new LatLng(Double.valueOf( hotelListEnt.getResponseModel().get(0).getLatitude()), Double.valueOf(hotelListEnt.getResponseModel().get(0).getLongitude())));
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (getMainActivity().statusCheck() && googleApiClient.isConnected()) {
            getLocation();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityResultI(int cameraPermission, int i, Intent data) {
        mMap.setMyLocationEnabled(true);
    }
}
