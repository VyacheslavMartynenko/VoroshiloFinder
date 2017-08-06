package com.finder.voroshilo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.finder.voroshilo.R;
import com.finder.voroshilo.adapter.ApplicationAdapter;
import com.finder.voroshilo.interfaces.ApplicationAdapterListener;
import com.finder.voroshilo.model.networking.data.Application;
import com.finder.voroshilo.model.networking.data.Category;
import com.finder.voroshilo.model.networking.data.DataBody;
import com.finder.voroshilo.networking.request.ApplicationsRequest;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ApplicationAdapterListener {
    private HashMap<String, Integer> categoryMap;
    private List<Application> applicationList;
    private ApplicationAdapter applicationAdapter = new ApplicationAdapter(this, new ArrayList<>());

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

        RecyclerView recyclerViewApplication = (RecyclerView) findViewById(R.id.recycle_view_application);
        recyclerViewApplication.setAdapter(applicationAdapter);
        recyclerViewApplication.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator) recyclerViewApplication.getItemAnimator()).setSupportsChangeAnimations(false);
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
        applicationAdapter.updateApplicationList(indexOfAll(categoryId, applicationList));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showAppInMarket(String appPackageName) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
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

    private List<Application> indexOfAll(int categoryId, List<Application> applicationList) {
        ArrayList<Application> indexList = new ArrayList<>();
        for (Application application : applicationList) {
            if (application.getCategoryId() == categoryId) {
                indexList.add(application);
            }
        }
        return indexList;
    }
}
