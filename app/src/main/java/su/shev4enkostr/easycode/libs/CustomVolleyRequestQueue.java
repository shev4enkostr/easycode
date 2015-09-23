package su.shev4enkostr.easycode.libs;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by stas on 23.08.15.
 */
public class CustomVolleyRequestQueue //extends Application
{
    private static CustomVolleyRequestQueue mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;


    private CustomVolleyRequestQueue(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache()
        {
            private final LruCache<String, Bitmap>
                    cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
        });
    }

    public static synchronized CustomVolleyRequestQueue getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new CustomVolleyRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
        {
            Cache cache = new DiskBasedCache(mCtx.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            // Don't forget to start the volley request queue
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader()
    {
        return mImageLoader;
    }
}
    /*public static final String TAG = CustomVolleyRequestQueue.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static CustomVolleyRequestQueue mInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized CustomVolleyRequestQueue getInstance()
    {
        return mInstance;
    }

    public RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader()
    {
        getRequestQueue();
        if (mImageLoader == null)
        {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag)
    {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag)
    {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}*/


    /*private static CustomVolleyRequestQueue instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context cnxt;

    private CustomVolleyRequestQueue(Context context)
    {
        cnxt = context;
        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache()
        {
            private final LruCache<String, Bitmap> cache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url)
            {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap)
            {
                cache.put(url, bitmap);
            }
        });
    }

    public RequestQueue getRequestQueue()
    {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(cnxt.getApplicationContext());

        return requestQueue;
    }

    public static synchronized CustomVolleyRequestQueue getInstance(Context context)
    {
        if (instance == null)
            instance = new CustomVolleyRequestQueue(context);

        return instance;
    }

    public <T> void addToRequestQueue(Request<T> request)
    {
        getRequestQueue().add(request);
    }

    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }
}*/
