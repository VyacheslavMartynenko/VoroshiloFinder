package com.aptoide.market.android.apps.games.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aptoide.market.android.apps.games.R;
import com.aptoide.market.android.apps.games.application.FinderApplication;
import com.aptoide.market.android.apps.games.interfaces.ApplicationAdapterListener;
import com.aptoide.market.android.apps.games.model.networking.data.Application;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {
    private ApplicationAdapterListener listener;
    private List<Application> applicationList;
    private String installStatus = FinderApplication.getInstance().getApplicationContext().getString(R.string.install_status);
    private String sellStatus = FinderApplication.getInstance().getApplicationContext().getString(R.string.sell_status);

    public ApplicationAdapter(ApplicationAdapterListener listener, List<Application> applicationList) {
        this.listener = listener;
        this.applicationList = applicationList;
    }

    public void updateApplicationList(List<Application> newApplicationList) {
        applicationList.clear();
        applicationList.addAll(newApplicationList);
        notifyDataSetChanged();
    }

    private boolean isPackageInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return false;
        }
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    class ApplicationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewApplicationIcon;
        TextView textViewApplicationTitle;
        TextView textViewDeveloperName;
        TextView textViewRating;
        TextView textViewInstalled;

        ApplicationViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageViewApplicationIcon = itemView.findViewById(R.id.image_view_application_icon);
            textViewApplicationTitle = itemView.findViewById(R.id.text_view_application_title);
            textViewDeveloperName = itemView.findViewById(R.id.text_view_developer_name);
            textViewRating = itemView.findViewById(R.id.text_view_rating);
            textViewInstalled = itemView.findViewById(R.id.text_view_installed);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                Application application = applicationList.get(getAdapterPosition());
                String appPackageName = application.getPackageName();
                listener.showAppInMarket(appPackageName);
            }
        }
    }

    @Override
    public ApplicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_application_item, parent, false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ApplicationViewHolder holder, int position) {
        Application application = applicationList.get(position);
        holder.textViewApplicationTitle.setText(application.getTitle());
        holder.textViewDeveloperName.setText(application.getDeveloperName());
        holder.textViewRating.setText(String.valueOf(application.getRating()));
        Context context = holder.itemView.getContext();
        String text = isPackageInstalled(context, application.getPackageName()) ? installStatus : sellStatus;
        holder.textViewInstalled.setText(text);
        Picasso.with(FinderApplication.getInstance().getApplicationContext())
                .load(application.getIconUrl()).fit().centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageViewApplicationIcon);
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }
}
