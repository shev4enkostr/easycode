package su.shev4enkostr.easycode;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by stas on 27.08.15.
 */
public class AboutFragment extends Fragment
{
    private SupportMapFragment supportMapFragment;
    private GoogleMap googleMap;
    private final static LatLng POSITION = new LatLng(50.01655314, 36.22770569);
    private final static int CAMERA_ZOOM = 17;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (supportMapFragment == null)
        {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, supportMapFragment).commit();
            fm.executePendingTransactions();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (googleMap == null)
        {
            googleMap = supportMapFragment.getMap();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(POSITION, CAMERA_ZOOM));
            googleMap.addMarker(new MarkerOptions().position(POSITION));
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
        }
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
