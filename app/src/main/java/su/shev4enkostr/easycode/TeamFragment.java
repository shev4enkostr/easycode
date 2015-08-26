package su.shev4enkostr.easycode;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by stas on 23.08.15.
 */
public class TeamFragment extends Fragment
{
    private ImageView imageView;
    private NetworkImageView nwImageView;
    private ImageLoader imageLoader;
    private final static String TAG = "Log_volley";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_team, null);
        //imageView = (ImageView) view.findViewById(R.id.team_image);
        nwImageView = (NetworkImageView) view.findViewById(R.id.team_image);

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        // Instantiate the RequestQueue.
        imageLoader = CustomVolleyRequestQueue.getInstance(getActivity().getApplicationContext()).getImageLoader();
        //Image URL - This can point to any image file supported by Android
        final String url = "http://easycode.com.ua/img/teacher/denis1.jpg";
        imageLoader.get(url, ImageLoader.getImageListener(nwImageView,R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        nwImageView.setImageUrl(url, imageLoader);
    }
}
