package su.shev4enkostr.easycode;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import org.w3c.dom.Document;
import java.util.ArrayList;

import su.shev4enkostr.easycode.libs.GoogleDirection;

/**
 * Created by stas on 27.08.15.
 */
public class AboutFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        OnMapReadyCallback
{
    private GoogleApiClient googleApiClient;
    protected static boolean locationEnable = false;
    protected static final int REQUEST_CHECK_SETTINGS = 1;

    private SupportMapFragment supportMapFragment;
    private GoogleMap googleMap;
    private LatLng myLocation;
    private GoogleDirection googleDirection;
    private Document document;
    private final static LatLng POSITION = new LatLng(50.01655314, 36.22770569);
    private final static int CAMERA_ZOOM = 17;

    private MenuItem buttonRoute;
    private MenuItem buttonAnimate;
    //private ProgressBar pb;
    //private LinearLayout ll;
    private Handler handler;

    private final static String TAG = "Load_map_______________";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();

        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView()");
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        Log.d(TAG, "super.onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
        checkLocationEnable();

        FragmentManager fm = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, supportMapFragment).commit();
            supportMapFragment.getMapAsync(this);
            googleMap = supportMapFragment.getMap();
        }
    }

    @Override
    public void onStart()
    {
        Log.d(TAG, "super.onStart()");
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        supportMapFragment.getMap().setMyLocationEnabled(locationEnable);
        Log.d(TAG, "onResume()___locationEnable is...." + locationEnable);
        Log.d(TAG, "onResume()___isMyLocationEnable()...." + supportMapFragment.getMap().isMyLocationEnabled());
    }

    @Override
    public void onPause() {
        super.onPause();
        supportMapFragment.getMap().setMyLocationEnabled(false);
        googleDirection.cancelAnimated();
        Log.d(TAG, "onPause()___locationEnable is...." + locationEnable);
        Log.d(TAG, "onPause()___isMyLocationEnable()...." + supportMapFragment.getMap().isMyLocationEnabled());
    }

    @Override
    public void onStop() {
        Log.d(TAG, "super.onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        locationEnable = false;
        Log.d(TAG, "onDestroyView()___locationEnable is...." + locationEnable);
        Log.d(TAG, "onDestroyView()___isMyLocationEnable()...." + supportMapFragment.getMap().isMyLocationEnabled());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap = supportMapFragment.getMap();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(POSITION, CAMERA_ZOOM));
        googleMap.addMarker(new MarkerOptions().position(POSITION).title(getString(R.string.app_name)));
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setOnMyLocationChangeListener(myLocationChangeListener());
        googleMap.setOnMyLocationButtonClickListener(myLocationButtonClickListener());
        googleMap.setOnMapLongClickListener(mapLongClickListener());

        googleMap.setMyLocationEnabled(locationEnable);
        Log.d(TAG, "onMapReady()__locationEnable is...." + locationEnable);
        Log.d(TAG, "onMapReady()___isMyLocationEnable()...." + supportMapFragment.getMap().isMyLocationEnabled());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_about_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
        buttonRoute = menu.findItem(R.id.action_request_route);
        buttonAnimate = menu.findItem(R.id.action_animate_route);
    }

    /*@Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (supportMapFragment.getMap().isMyLocationEnabled())
            menu.getItem(R.id.action_request_route).setVisible(true);
        else
            menu.getItem(R.id.action_request_route).setVisible(false);

        super.onPrepareOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_request_route:
                if (myLocation != null)
                    createRoute();
                else
                    Toast.makeText(getContext(), R.string.toast_location_not_available, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_animate_route:
                animateRoute();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeMapFragment()
    {
        Log.d(TAG, "initializeMapFragment");
        FragmentManager fm = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        Log.d(TAG, "supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);");

        if (supportMapFragment == null)
        {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, supportMapFragment).commit();
            fm.executePendingTransactions();
        }
        Log.d(TAG, "if (supportMapFragment == null)...");
    }

    private void initializeMap()
    {
        Log.d(TAG, "initializeMap()  START");
        googleMap = supportMapFragment.getMap();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(POSITION, CAMERA_ZOOM));
        googleMap.addMarker(new MarkerOptions().position(POSITION).title(getString(R.string.app_name)));
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        //googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        //googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationChangeListener(myLocationChangeListener());
        googleMap.setOnMyLocationButtonClickListener(myLocationButtonClickListener());

        Log.d(TAG, "initializeMap()  STOP");
        Log.d(TAG, "pb.setVisibility(View.GONE)");
    }

    private void checkLocationEnable() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY));

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        supportMapFragment.getMap().setMyLocationEnabled(locationEnable = true);
                        Log.d(TAG, "Request_onResult_SUCCESS_()___locationEnable is...." + locationEnable);
                        Log.d(TAG, "Request_onResult_SUCCESS_()___isMyLocationEnable()...." + supportMapFragment.getMap().isMyLocationEnabled());
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        supportMapFragment.getMap().setMyLocationEnabled(locationEnable = false);
                        Log.d(TAG, "Request_onResult_SETTINGS_CHANGE_UNAVAILABLE_()___locationEnable is...." + locationEnable);
                        Log.d(TAG, "Request_onResult_SETTINGS_CHANGE_UNAVAILABLE_()___isMyLocationEnable()...." + supportMapFragment.getMap().isMyLocationEnabled());
                        break;
                }
            }
        });
    }

    private void createRoute() {
        buttonRoute.setVisible(false);
        googleDirection = new GoogleDirection(getContext());
        googleDirection.setOnDirectionResponseListener(onDirectionResponseListener());
        googleDirection.setOnAnimateListener(onAnimateListener());
        googleDirection.setLogging(true);
        googleDirection.request(myLocation, POSITION, GoogleDirection.MODE_DRIVING);
    }

    private void animateRoute() {
        googleDirection.animateDirection(supportMapFragment.getMap(), googleDirection.getDirection(document),
                GoogleDirection.SPEED_VERY_FAST, true, false, true, false, null, false, true, null);
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener() {
        return new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                supportMapFragment.getMap().getUiSettings().setMyLocationButtonEnabled(true);

                if (googleDirection == null)
                    buttonRoute.setVisible(true);

                /*googleMap.addMarker(new MarkerOptions().position(myLocation)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));*/
            }
        };
    }

    private GoogleMap.OnMyLocationButtonClickListener myLocationButtonClickListener() {
        return new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                try {
                    supportMapFragment.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, CAMERA_ZOOM));
                }
                catch (NullPointerException e) {
                    Toast.makeText(getContext(), R.string.toast_location_not_available, Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        };
    }

    private GoogleMap.OnMapLongClickListener mapLongClickListener() {
        return new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                supportMapFragment.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(POSITION, CAMERA_ZOOM));
            }
        };
    }

    private GoogleDirection.OnDirectionResponseListener onDirectionResponseListener() {
        return new GoogleDirection.OnDirectionResponseListener() {
            @Override
            public void onResponse(String status, Document doc, GoogleDirection gd) {
                document = doc;
                supportMapFragment.getMap().addPolyline(gd.getPolyline(doc, 3, Color.RED));
                buttonAnimate.setVisible(true);
            }
        };
    }

    private GoogleDirection.OnAnimateListener onAnimateListener() {
        return new GoogleDirection.OnAnimateListener() {
            @Override
            public void onStart() {
                buttonAnimate.setVisible(false);
            }

            @Override
            public void onProgress(int progress, int total) {}

            @Override
            public void onFinish() {
                buttonAnimate.setVisible(true);
            }
        };
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(intent);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        supportMapFragment.getMap().setMyLocationEnabled(locationEnable = true);
                        Log.d(TAG, "onActivityResult_OK_()___locationEnable is...." + locationEnable);
                        Log.d(TAG, "onActivityResult_OK_()___isMyLocationEnable()...." + supportMapFragment.getMap().isMyLocationEnabled());
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        supportMapFragment.getMap().setMyLocationEnabled(locationEnable = false);
                        Log.d(TAG, "onActivityResult_CANCELED_()___locationEnable is...." + locationEnable);
                        Log.d(TAG, "onActivityResult_CANCELED_()___isMyLocationEnable()...." + supportMapFragment.getMap().isMyLocationEnabled());
                        break;
                    default:
                        Log.d(TAG, "onActivityResult_DEFAULT!!!_()");
                        break;
                }
                break;
        }
    }*/

    @Override
    public void onConnected(Bundle bundle) {
        /*Log.d(TAG, "onConnected(Bundle bundle)");
        supportMapFragment.getMap().setMyLocationEnabled(locationEnable = true);
        Log.d(TAG, "onConnected()___locationEnable is...." + locationEnable);
        Log.d(TAG, "onConnected()___isMyLocationEnable()...." + supportMapFragment.getMap().isMyLocationEnabled());*/
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onConnectionSuspended(int i) {}
}
