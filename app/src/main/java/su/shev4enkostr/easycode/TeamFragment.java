package su.shev4enkostr.easycode;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by stas on 23.08.15.
 */
public class TeamFragment extends Fragment
{
    private ImageView imageView;
    private NetworkImageView nwImageView;
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
        /*String url = "http://easycode.com.ua/img/teacher/denis1.jpg";

        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, 0, 0, null,
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        imageView.setImageResource(android.R.drawable.stat_notify_error);
                    }
                });

        MySingleton.getInstance(getActivity()).addToRequestQueue(request);*/

        /*String url = "http://easycode.com.ua/img/teacher/denis1.jpg";
        ImageLoader imageLoader = MySingleton.getInstance().getImageLoader();

        // If you are using normal ImageView
        imageLoader.get(url, new ImageLoader.ImageListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e(TAG, "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1)
            {
                if (response.getBitmap() != null)
                {
                    // load image into imageview
                    imageView.setImageBitmap(response.getBitmap());
                }
            }
        });*/

        super.onAttach(context);

        String url = "http://easycode.com.ua/img/teacher/denis1.jpg";

        ImageLoader imageLoader = MySingleton.getInstance().getImageLoader();
        nwImageView.setImageUrl(url, imageLoader);


    }
}
