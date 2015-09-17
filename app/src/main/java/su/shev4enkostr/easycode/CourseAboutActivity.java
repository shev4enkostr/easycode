package su.shev4enkostr.easycode;

import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class CourseAboutActivity extends CustomAppCompatActivity {

    private WebView webView;
    private String url = null;
    private String title = null;
    private int pictureId = 0;

    public final static String ARG_URL = "url";
    public final static String ARG_TITLE = "title";
    public final static String ARG_PICTURE_ID = "picture_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_about);

        url = getIntent().getStringExtra(ARG_URL);
        title = getIntent().getStringExtra(ARG_TITLE);
        pictureId = getIntent().getIntExtra(ARG_PICTURE_ID, 0);

        initializeCoordinatorLayout();
        initializeToolBar();

        webView = (WebView) findViewById(R.id.web_view);

        if (url != null)
        {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl(url);
                }
            });
        }
    }

    private void initializeToolBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(title);

        loadToolbarImage();
    }

    private void loadToolbarImage()
    {
        ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(pictureId).centerCrop().into(imageView);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }*/
}
