package su.shev4enkostr.easycode;

import android.os.Handler;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private CoordinatorLayout coordinatorLayout;
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
    private static int checkedDrawerItem;

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

        //disableCollapsingToolBar();
        initializeCoordinatorLayout();
        initializeToolBar();
        initializeNavigationView();

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_CHECKED_DRAWER_ITEM))
        {
            int checkedItem = (savedInstanceState.getInt(ARG_CHECKED_DRAWER_ITEM));
            prepareFragment(checkedItem);
            addFragment();
        }

        else
            selectHomeItem();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(ARG_CHECKED_DRAWER_ITEM, checkedDrawerItem);
    }

    private void initializeCoordinatorLayout()
    {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        coordinatorLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            return super.onTouchEvent(event);
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem)
    {
        // get checked item id for save in onSaveInstanceState()
        checkedDrawerItem = menuItem.getItemId();

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

                getSupportActionBar().setTitle(title);

                if (runnable != null) {
                    handler.post(runnable);
                    runnable = null;
                }
                //addFragment();
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

    /*private void disableCollapsingToolBar()
    {
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setVisibility(View.GONE);
    }*/

    private void selectHomeItem()
    {
        fragment = homeFragment;
        title = getString(R.string.app_name);
        navigationView.setCheckedItem(R.id.drawer_home);
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
    }

    private void resetToolbarScrollState()
    {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) layoutParams.getBehavior();
        behavior.onNestedFling(coordinatorLayout, appBarLayout, null, 0, -1000, true);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
