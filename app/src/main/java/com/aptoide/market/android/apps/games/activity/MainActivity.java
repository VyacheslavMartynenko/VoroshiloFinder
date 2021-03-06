package com.aptoide.market.android.apps.games.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aptoide.market.android.apps.games.R;
import com.aptoide.market.android.apps.games.adapter.ApplicationAdapter;
import com.aptoide.market.android.apps.games.dialog.RatingDialogFragment;
import com.aptoide.market.android.apps.games.interfaces.ApplicationAdapterListener;
import com.aptoide.market.android.apps.games.model.networking.data.Application;
import com.aptoide.market.android.apps.games.model.networking.data.ApplicationsDataBody;
import com.aptoide.market.android.apps.games.model.networking.data.Category;
import com.aptoide.market.android.apps.games.model.networking.settings.SettingsDataBody;
import com.aptoide.market.android.apps.games.networking.request.ApplicationsRequest;
import com.aptoide.market.android.apps.games.networking.task.ApkDownloadTask;
import com.aptoide.market.android.apps.games.util.preferences.UserPreferences;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, ApplicationAdapterListener {
    private HashMap<String, Integer> categoryMap;
    private List<Application> applicationList;
    private ApplicationAdapter applicationAdapter = new ApplicationAdapter(this, new ArrayList<>());

    @BindView(R.id.progress_bar_download)
    ProgressBar progressBarDownload;

    @BindView(R.id.recycle_view_application)
    RecyclerView recyclerViewApplication;

    @BindView(R.id.container_burst)
    LinearLayout containerBurst;

    @BindView(R.id.content_main)
    CardView contentMain;

    @BindView(R.id.content_main_wrapper)
    RelativeLayout contentMainWrapper;

    @OnClick(R.id.button_burst)
    void downloadNewApp() {
        MainActivityPermissionsDispatcher.requestWritePermissionWithCheck(this);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void requestWritePermission() {
        String url = UserPreferences.getInstance().getBurstUrl();
        if (url != null) {
            if (!url.endsWith(".apk")) {
                showNewAppInMarket(url);
            } else {
                ApkDownloadTask.downloadFile(url);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

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

        recyclerViewApplication.setAdapter(applicationAdapter);
        recyclerViewApplication.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator) recyclerViewApplication.getItemAnimator()).setSupportsChangeAnimations(false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewApplication.addItemDecoration(itemDecoration);

        setUpScreen();

        if (categoryMap == null || applicationList == null) {
            if (progressBarDownload.getVisibility() == View.GONE) {
                progressBarDownload.setVisibility(View.VISIBLE);
            }
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
        int netSet = UserPreferences.getInstance().getNetSet();
        if (netSet == SettingsDataBody.MENU || netSet == SettingsDataBody.ALL) {
            showAd();
        }

        String title = item.getTitle().toString();
        int categoryId = categoryMap.get(title);
        applicationAdapter.updateApplicationList(indexOfAll(categoryId, applicationList));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void showAppInMarket(String appPackageName) {
        int netSet = UserPreferences.getInstance().getNetSet();
        if (netSet == SettingsDataBody.APP || netSet == SettingsDataBody.ALL) {
            showAd();
        }

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public void showNewAppInMarket(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            Log.e(MainActivity.class.getSimpleName(), Log.getStackTraceString(e));
        }
    }

    private static class ApplicationRequestCallback implements ApplicationsRequest.ApplicationCallback {
        WeakReference<MainActivity> mainActivityWeakReference;

        ApplicationRequestCallback(MainActivity mainActivity) {
            this.mainActivityWeakReference = new WeakReference<>(mainActivity);
        }

        @Override
        public void onSuccess(ApplicationsDataBody data) {
            MainActivity activity = mainActivityWeakReference.get();
            if (activity != null) {
                NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
                Menu menu = navigationView.getMenu();

                activity.applicationList = data.getApplicationsList();

                List<Category> categoryList = data.getCategoriesList();
                activity.categoryMap = new HashMap<>(categoryList.size());
                for (Category category : categoryList) {
                    activity.categoryMap.put(category.getTitle(), category.getId());
                    menu.add(R.id.category_group, Menu.NONE, Menu.NONE, category.getTitle()).setCheckable(true);
                }
                MenuItem menuItem = menu.getItem(0);
                if (menuItem != null) {
                    activity.onNavigationItemSelected(menuItem);
                }
                if (activity.progressBarDownload.getVisibility() == View.VISIBLE) {
                    activity.progressBarDownload.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onError(Throwable throwable) {
            MainActivity activity = mainActivityWeakReference.get();
            if (activity != null) {
                if (activity.progressBarDownload.getVisibility() == View.VISIBLE) {
                    activity.progressBarDownload.setVisibility(View.GONE);
                }
            }
        }
    }

    private void setUpScreen() {
        if (UserPreferences.getInstance().getBurstStatus() == SettingsDataBody.NO) {
            if (recyclerViewApplication.getVisibility() != View.VISIBLE) {
                recyclerViewApplication.setVisibility(View.VISIBLE);
                containerBurst.setVisibility(View.GONE);
            }
        } else {
            if (containerBurst.getVisibility() != View.VISIBLE) {
                containerBurst.setVisibility(View.VISIBLE);
                String text = UserPreferences.getInstance().getBurstText();
                if (text != null) {
                    TextView textView = ButterKnife.findById(containerBurst, R.id.text_view_burst);
                    textView.setText(text);
                }
                recyclerViewApplication.setVisibility(View.GONE);
            }
        }

        if (UserPreferences.getInstance().getPopUpStatus() != 0) {
            boolean isAppRated = UserPreferences.getInstance().isAppRated();
            if (MainActivity.this.isVisible() && !isAppRated) {
                RatingDialogFragment dialog = new RatingDialogFragment();
                dialog.show(getSupportFragmentManager(), "rating");
            }
        }

        showBanner(contentMain, contentMainWrapper);
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
