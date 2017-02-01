package com.cool.vicky.wallpic.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.cool.vicky.wallpic.R;
import com.cool.vicky.wallpic.fragments.WallpapersFragment;
import com.cool.vicky.wallpic.fragments.EditorsChoiceFragment;
import com.cool.vicky.wallpic.pojo.Category;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String category;
    private MenuItem lastMenuItemSelected;
    private String fontPath;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initFont();
        initToolbar();
        initViewPager();
        initTabLayout();
        initDrawrLayout();
        initNavView();
    }

    private void initFont() {
        fontPath = "fonts/Pacifico.ttf";
        typeface = Typeface.createFromAsset(getAssets(), fontPath);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(typeface);
        mTitle.setText(getResources().getString(R.string.app_name));
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
    }

    private void initTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initDrawrLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close
        );
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void initNavView() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        TextView headerTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_textView);
        headerTextView.setTypeface(typeface);
        lastMenuItemSelected = navigationView.getMenu().getItem(0);//getting first menu item which is selected by default
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                tabLayout.getTabAt(0).select();//select first tab to display category results
                if (lastMenuItemSelected.getItemId() != menuItem.getItemId())
                    selectDrawerItem(menuItem);
                else
                    drawerLayout.closeDrawers();
                lastMenuItemSelected = menuItem;
                return true;
            }
        });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.random:
                category = "";
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle("Random");
                break;
            case R.id.fashion:
                category = getResources().getString(R.string.fashion_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.fashion_label));
                break;
            case R.id.nature:
                category = getResources().getString(R.string.nature_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.nature_label));
                break;
            case R.id.backgrounds:
                category = getResources().getString(R.string.backgrounds_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.backgrounds_label));
                break;
            case R.id.science:
                category = getResources().getString(R.string.science_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.science_label));
                break;
            case R.id.education:
                category = getResources().getString(R.string.education_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.education_label));
                break;
            case R.id.people:
                category = getResources().getString(R.string.people_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.people_label));
                break;
            case R.id.feelings:
                category = getResources().getString(R.string.feelings_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.feelings_label));
                break;
            case R.id.religion:
                category = getResources().getString(R.string.religion_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.religion_label));
                break;
            case R.id.health:
                category = getResources().getString(R.string.health_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.health_label));
                break;
            case R.id.places:
                category = getResources().getString(R.string.places_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.places_label));
                break;
            case R.id.animals:
                category = getResources().getString(R.string.animals_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.animals_label));
                break;
            case R.id.industry:
                category = getResources().getString(R.string.industry_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.industry_label));
                break;
            case R.id.food:
                category = getResources().getString(R.string.food_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.food_label));
                break;
            case R.id.computer:
                category = getResources().getString(R.string.computer_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.computer_label));
                break;
            case R.id.sports:
                category = getResources().getString(R.string.sports_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.sports_label));
                break;
            case R.id.transportation:
                category = getResources().getString(R.string.transportation_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.transportation_label));
                break;
            case R.id.travel:
                category = getResources().getString(R.string.travel_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.travel_label));
                break;
            case R.id.buildings:
                category = getResources().getString(R.string.buildings_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.buildings_label));
                break;
            case R.id.business:
                category = getResources().getString(R.string.business_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.business_label));
                break;
            case R.id.music:
                category = getResources().getString(R.string.music_label).toLowerCase();
                Glide.get(MainActivity.this).clearMemory();//clear cache in memory
                EventBus.getDefault().post(new Category(category));//change the category
//                getSupportActionBar().setTitle(getResources().getString(R.string.music_label));
                break;
        }

        drawerLayout.closeDrawers();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WallpapersFragment(), "Wallpapers");
        adapter.addFragment(new EditorsChoiceFragment(), "Editor's Choice");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
