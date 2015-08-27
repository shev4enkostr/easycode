package su.shev4enkostr.easycode;

import android.os.Bundle;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by stas on 27.08.15.
 */
public class AboutFragment extends MapFragment
{
    private MapFragment supportMapFragment;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        initializeMap();
        return view;
    }

    private void initializeMap()
    {
        supportMapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (supportMapFragment == null)
        {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            supportMapFragment = MapFragment.newInstance();
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
    }
}
