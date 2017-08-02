package com.finder.voroshilo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.finder.voroshilo.R;
import com.finder.voroshilo.model.networking.data.Application;
import com.finder.voroshilo.model.networking.data.Category;
import com.finder.voroshilo.model.networking.data.DataBody;
import com.finder.voroshilo.networking.request.ApplicationsRequest;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private HashMap<String, Integer> categoryMap;
    private List<Application> applicationList;

    @BindView(R.id.recycle_view_application)
    RecyclerView recyclerViewApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (categoryMap == null || applicationList == null) {
            ApplicationsRequest.requestApplications(new ApplicationRequestCallback(this));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String title = item.getTitle().toString();
        int categoryId = categoryMap.get(title);
        Log.e("Id", String.valueOf(categoryId));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static class ApplicationRequestCallback implements ApplicationsRequest.ApplicationCallback {
        WeakReference<MainActivity> mainActivityWeakReference;

        ApplicationRequestCallback(MainActivity mainActivity) {
            this.mainActivityWeakReference = new WeakReference<>(mainActivity);
        }

        @Override
        public void onSuccess(DataBody data) {
            MainActivity activity = mainActivityWeakReference.get();
            if (activity != null) {
                NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
                Menu menu = navigationView.getMenu();

                List<Category> categoryList = data.getCategoriesList();
                activity.categoryMap = new HashMap<>(categoryList.size());
                for (Category category : categoryList) {
                    activity.categoryMap.put(category.getTitle(), category.getId());
                    menu.add(R.id.category_group, Menu.NONE, Menu.NONE, category.getTitle()).setCheckable(true);
                }
                activity.applicationList = data.getApplicationsList();
            }
        }

        @Override
        public void onError(Throwable throwable) {

        }
    }
}
