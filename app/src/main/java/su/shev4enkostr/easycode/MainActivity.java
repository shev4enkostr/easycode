package su.shev4enkostr.easycode;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.ColorStateList;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toast backPressed;

    private String title;
    private Fragment fragment;
    private HomeFragment homeFragment;
    private CoursesFragment coursesFragment;
    private TeamFragment teamFragment;

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

        initializeToolBar();
        initializeNavigationView();

        if (savedInstanceState != null)
            navigationView.setCheckedItem(savedInstanceState.getInt(ARG_CHECKED_DRAWER_ITEM));

        else
            selectHomeItem();

        /*FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        fTransaction.add(R.id.fragment_container, homeFragment);
        fTransaction.commit();*/
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(ARG_CHECKED_DRAWER_ITEM, navigationView.getId());
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem)
    {
        //Checking if the item is in checked state or not, if not make it in checked state
        if (menuItem.isChecked())
            menuItem.setChecked(true);
        else
            menuItem.setEnabled(false);

        //Fragment fragment = null;
        //String title = getString(R.string.app_name);

        switch (menuItem.getItemId())
        {
            case R.id.drawer_home:
                fragment = homeFragment;
                title = getString(R.string.app_name);
                //navigationView.setCheckedItem(R.id.drawer_home);
                //navigationView.setItemTextColor(ColorStateList.valueOf(getColor(R.color.navigation_view_item_color)));
                break;

            case R.id.drawer_courses:
                fragment = coursesFragment;
                title = getString(R.string.item_courses);
                break;

            case R.id.drawer_team:
                fragment = teamFragment;
                title = getString(R.string.item_team);

            case R.id.drawer_about:
                break;

            default:
                break;
        }
        addFragment();
        /*if (fragment != null)
        {
            //fTransaction.replace(R.id.fragment_container, fragment);
            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragment_container, fragment);
            fTransaction.commit();
            getSupportActionBar().setTitle(title);
        }*/

        // closing drawer on item click
        drawerLayout.closeDrawers();

        return true;
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
        // initialazing toolbar
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
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        {
            @Override
            public void onDrawerClosed(View drawerView)
            {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
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
        navigationView.setCheckedItem(R.id.drawer_home);
        addFragment();
    }

    private void addFragment()
    {
        if (fragment != null)
        {
            //fTransaction.replace(R.id.fragment_container, fragment);
            FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
            fTransaction.replace(R.id.fragment_container, fragment);
            fTransaction.commit();
            getSupportActionBar().setTitle(title);
        }
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
