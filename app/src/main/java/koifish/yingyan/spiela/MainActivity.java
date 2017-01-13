package koifish.yingyan.spiela;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import koifish.yingyan.spiela.fragments.ExploreFragment;
import koifish.yingyan.spiela.fragments.JabberFragment;
import koifish.yingyan.spiela.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity
{
    private  DrawerLayout mDrawerLayout;
    private  ActionBarDrawerToggle mDrawerToggle;
    private  Toolbar toolbar;
    private  FragmentManager fragmentManager;
    private  NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setUpHeaderView();
        onMenuItemSelected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);

        return true;
    }

    /*  Init all views  */
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.slider_menu);

        fragmentManager = getSupportFragmentManager();

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout, toolbar, // nav menu toggle icon
                R.string.drawer_open, // nav drawer open - description for
                // accessibility
                R.string.drawer_close // nav drawer close - description for
                // accessibility
        )
        {
            public void onDrawerClosed(View view) {
            }

            public void onDrawerOpened(View drawerView) {

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    /**
     * For using header view use this method
     **/
    private void setUpHeaderView() {
        View headerView = navigationView.inflateHeaderView(R.layout.header_view);
        TextView textOne = (TextView) headerView.findViewById(R.id.company_title);
        TextView textTwo = (TextView) headerView.findViewById(R.id.company_sub_title);
    }

    /*  Method for Navigation View item selection  */

    private void onMenuItemSelected() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //Check and un-check menu item if they are checkable behaviour
                if (item.isCheckable()) {
                    if (item.isChecked()) item.setChecked(false);
                    else item.setChecked(true);
                }
                //Closing drawer on item click
                mDrawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    case R.id.explore:
                        //Replace fragment
                        setFragment(item);
                        break;

                    case R.id.profile:
                        //Replace fragment
                        setFragment(item);
                        break;
                    case R.id.jabber:
                        //Replace fragment
                        setFragment(item);
                        break;

                    case R.id.rate_app:
                        //Start new Activity or do your stuff
                        Toast.makeText(MainActivity.this, "You Clicked on Rate", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.settings:
                        //Start new Activity or do your stuff
                        Toast.makeText(MainActivity.this, "You Clicked on Settings", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.help:
                        //Start new Activity or do your stuff
                        Toast.makeText(MainActivity.this, "You Clicked on Help", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }
    /*  Set Fragment, setting toolbar title and passing item title via bundle to fragments*/
    public void setFragment(MenuItem item)
    {
        //Set toolbar
        toolbar.setTitle(item.getTitle());
        //Find fragment by tag
        Fragment fr = fragmentManager.findFragmentByTag(item.getTitle().toString());
        Fragment exploreFragment = new ExploreFragment();
        Fragment profileFragment = new ProfileFragment();
        Fragment jabberFragment = new JabberFragment();
        Bundle b = new Bundle();
        //If fragment is null replace fragment
        if (fr == null)
        {
            exploreFragment.setArguments(b);
            //Set Arguments
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, exploreFragment, item.getTitle().toString())
                    .commit();
        }
    }
    //On back press check if drawer is open and closed
    @Override
    public void onBackPressed()
    {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawers();
        else
            super.onBackPressed();
    }
}
