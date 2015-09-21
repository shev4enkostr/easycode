package su.shev4enkostr.easycode;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends CustomAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toast backPressed;

    private String title;
    private Fragment fragment;
    private HomeFragment homeFragment;
    private CoursesFragment coursesFragment;
    private TeamFragment teamFragment;
    private AboutFragment aboutFragment;

    private Handler handler;
    private Runnable runnable;

    private final static String ARG_CHECKED_DRAWER_ITEM = "checked_drawer_item";
    private int checkedDrawerItemId;
    private static final String ARG_TOOLBAR_TITLE = "toolbar_title";

    private static final String TAG = "EasyCode MainActivity";

    // Social networks constants
    private static final String VK_APP = "com.vkontakte.android";
    private static final String FB_APP = "com.facebook.katana";
    private static final String GPLUS_APP = "com.google.android.apps.plus";
    private static final String VK_COMMUNITY_ID = "-94218931";
    private static final String VK_COMMUNITY_URL = "http://vk.com/easy_code_kharkov";
    private static final String FB_COMMUNITY_ID = "830245307056749";
    private static final String FB_COMMUNITY_URL = "https://m.facebook.com/easycodekharkov";
    private static final String GPLUS_COMMUNITY_ID = "114035626550085554848";
    private static final String GPLUS_COMMUNITY_URL = "https://plus.google.com/+EasyCodeKharkov";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = null;
        homeFragment = new HomeFragment();
        coursesFragment = new CoursesFragment();
        teamFragment = new TeamFragment();
        aboutFragment = new AboutFragment();
        handler = new Handler();

        initializeToolBar();
        initializeNavigationView();
        initializeCoordinatorLayout();

        //appBarLayout.setExpanded(true);

        if (savedInstanceState == null || ! savedInstanceState.containsKey(ARG_CHECKED_DRAWER_ITEM))
            selectHomeItem();

        else {
            //checkedDrawerItemId = (savedInstanceState.getInt(ARG_CHECKED_DRAWER_ITEM));
            //prepareFragment(checkedDrawerItemId);

            title = savedInstanceState.getString(ARG_TOOLBAR_TITLE);
            addFragment();
        }

        Log.d(TAG, "onCreate__________________________");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //getSupportActionBar().setTitle(title);
        Log.d(TAG, "onStart()______________________");
        appBarLayout.setExpanded(true);
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()______________________");
        Log.d(TAG, "appBarLayout..getTargetElevation()__________" + appBarLayout.getTargetElevation());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()______________________");
        Log.d(TAG, "appBarLayout..getTargetElevation()__________" + appBarLayout.getTargetElevation());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()______________________");

        appBarLayout.setExpanded(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()______________________");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()______________________");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_CHECKED_DRAWER_ITEM, checkedDrawerItemId);
        outState.putString(ARG_TOOLBAR_TITLE, title);

        /*AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.setExpanded(true);*/

        Log.d(TAG, "onSaveInstanceState()__________________");
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem)
    {
        // get checked item id for save in onSaveInstanceState()
        checkedDrawerItemId = menuItem.getItemId();

        //Checking if the item is in checked state or not, if not make it in checked state
        if (menuItem.isChecked())
            menuItem.setChecked(true);
        else
            menuItem.setEnabled(false);

        prepareFragment(menuItem.getItemId());

        runnable = new Runnable() {
            @Override
            public void run() {
                addFragment();
            }
        };
        resetToolbarScrollState();
        // closing drawer on item click
        drawerLayout.closeDrawers();

        return true;
    }

    private void prepareFragment(int checkedItemId)
    {
        switch (checkedItemId)
        {
            case R.id.drawer_home:
                fragment = homeFragment;
                title = getString(R.string.app_name);
                break;

            case R.id.drawer_courses:
                fragment = coursesFragment;
                title = getString(R.string.item_courses);
                break;

            case R.id.drawer_team:
                fragment = teamFragment;
                title = getString(R.string.item_team);
                break;

            case R.id.drawer_about:
                fragment = aboutFragment;
                title = getString(R.string.item_about_us);
                break;

            default:
                break;
        }
        //getSupportActionBar().setTitle(title);
        Log.d(TAG, "prepareFragment()__________________");
        Log.d(TAG, fragment.toString());
    }

    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(navigationView) && drawerLayout != null)
            drawerLayout.closeDrawers();

        /*else if (navigationView.getId() != R.id.drawer_home)
            selectHomeItem();*/

        else if (backPressed != null && backPressed.getView().getWindowToken() != null)
            super.onBackPressed();

        else
        {
            backPressed = Toast.makeText(this, R.string.toast_exit, Toast.LENGTH_SHORT);
            backPressed.show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_MENU)
        {
            if (! drawerLayout.isDrawerOpen(navigationView))
                drawerLayout.openDrawer(navigationView);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initializeToolBar()
    {
        // initializing appBarLayout
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        //appBarLayout.setExpanded(true);
        // initializing toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initializeNavigationView()
    {
        // initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        {
            @Override
            public void onDrawerClosed(View drawerView)
            {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
                //getSupportActionBar().setTitle(title);
                /*if (runnable != null) {
                    handler.post(runnable);
                    runnable = null;
                }*/
                addFragment();
            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void selectHomeItem()
    {
        fragment = homeFragment;
        title = getString(R.string.app_name);
        navigationView.setCheckedItem(checkedDrawerItemId = R.id.drawer_home);
        addFragment();
    }

    private void addFragment()
    {
        if (fragment != null)
        {
            FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragment_container, fragment);
            fTransaction.commit();
            //getSupportActionBar().setTitle(title);
            fragment = null;
        }
        else
            Log.d(TAG, "addFragment() fragment = null !!!___________");
    }

    private void resetToolbarScrollState()
    {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) layoutParams.getBehavior();
        behavior.onNestedFling(coordinatorLayout, appBarLayout, null, 0, -1000, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        Intent intent = null;
        switch (id)
        {
            case R.id.action_social_vk:
                if (isAppInstalled(VK_APP))
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vkontakte://profile/" + VK_COMMUNITY_ID));
                else
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(VK_COMMUNITY_URL));
                break;

            case R.id.action_social_fb:
                if (isAppInstalled(FB_APP))
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + FB_COMMUNITY_ID));
                else
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FB_COMMUNITY_URL));
                break;

            case R.id.action_social_gplus:
                if (isAppInstalled(GPLUS_APP))
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + GPLUS_COMMUNITY_ID + "/posts")).setPackage(GPLUS_APP);
                else
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(GPLUS_COMMUNITY_URL));
                break;

            default:
                break;
        }
        if (intent != null) {
            startActivity(intent);
            intent = null;
        }
        /*//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    private boolean isAppInstalled(String app)
    {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(app, PackageManager.GET_ACTIVITIES);
            //Toast.makeText(this, "Installed", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {
            //Toast.makeText(this, "NOT Installed!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
