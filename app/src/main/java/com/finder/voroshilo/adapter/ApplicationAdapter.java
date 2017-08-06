package com.finder.voroshilo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.finder.voroshilo.R;
import com.finder.voroshilo.application.FinderApplication;
import com.finder.voroshilo.interfaces.ApplicationAdapterListener;
import com.finder.voroshilo.model.networking.data.Application;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {
    private ApplicationAdapterListener listener;
    private List<Application> applicationList;

    public ApplicationAdapter(ApplicationAdapterListener listener, List<Application> applicationList) {
        this.listener = listener;
        this.applicationList = applicationList;
    }

    public void updateApplicationList(List<Application> newApplicationList) {
        applicationList.clear();
        applicationList.addAll(newApplicationList);
        notifyDataSetChanged();
    }

    class ApplicationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewApplicationIcon;
        TextView textViewApplicationTitle;

        ApplicationViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageViewApplicationIcon = itemView.findViewById(R.id.image_view_application_icon);
            textViewApplicationTitle = itemView.findViewById(R.id.text_view_application_title);
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
