package su.shev4enkostr.easycode;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by stas on 27.08.15.
 */
public class AboutFragment extends Fragment //implements LoaderManager.LoaderCallbacks<Boolean>
{
    private SupportMapFragment supportMapFragment;
    private GoogleMap googleMap;
    private final static LatLng POSITION = new LatLng(50.01655314, 36.22770569);
    private final static int CAMERA_ZOOM = 17;

    private ProgressBar pb;
    private LinearLayout ll;
    private Handler handler;

    private final static int LOADER_ID = 1;
    private final static String TAG = "Load_map_______________";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView()");
        //return inflater.inflate(R.layout.fragment_about, container, false);

        View view = inflater.inflate(R.layout.fragment_about, container, false);
        pb = (ProgressBar) view.findViewById(R.id.pb);
        ll = (LinearLayout) view.findViewById(R.id.ll);
        pb.setVisibility(View.VISIBLE);
        //pb.setIndeterminate(true);
        ll.setVisibility(View.GONE);
        Log.d(TAG, "return View");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        Log.d(TAG, "super.onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume()
    {
        Log.d(TAG, "super.onResume()");
        super.onResume();

        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run()   START");
                initializeMapFragment();
                initializeMap();
                Log.d(TAG, "run()   END");
            }
        });
    }

    @Override
    public void onStart()
    {
        Log.d(TAG, "super.onStart()");
        super.onStart();
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
        supportMapFragment.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(POSITION, CAMERA_ZOOM));
        supportMapFragment.getMap().addMarker(new MarkerOptions().position(POSITION));
        supportMapFragment.getMap().getUiSettings().setCompassEnabled(true);
        supportMapFragment.getMap().getUiSettings().setZoomControlsEnabled(true);
        Log.d(TAG, "initializeMap()  STOP");
        pb.setVisibility(View.GONE);
        ll.setVisibility(View.VISIBLE);
        Log.d(TAG, "pb.setVisibility(View.GONE)");
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        supportMapFragment = new SupportMapFragment()
        {
            @Override
            public void onActivityCreated(Bundle savedInstanceState)
            {
                super.onActivityCreated(savedInstanceState);
                googleMap = supportMapFragment.getMap();
                if (googleMap != null)
                    ;
            }
        };
        getChildFragmentManager().beginTransaction().add(R.id.map, supportMapFragment).commit();

        return view;
    }*/

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        initializeMap();
        return view;
    }

    private void initializeMap()
    {
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (supportMapFragment == null)
        {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            supportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, supportMapFragment).commit();
        }

        if (supportMapFragment != null)
        {
            googleMap = supportMapFragment.getMap();

            if (googleMap != null)
            {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
                {
                    @Override
                    public void onMapClick(LatLng latLng)
                    {

                    }
                });
            }
        }
    }*/
}
