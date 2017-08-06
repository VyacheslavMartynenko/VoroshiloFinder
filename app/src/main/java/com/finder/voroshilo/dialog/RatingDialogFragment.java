package com.finder.voroshilo.dialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.finder.voroshilo.R;
import com.finder.voroshilo.util.preferences.UserPreferences;

import butterknife.BindView;
import butterknife.OnClick;

public class RatingDialogFragment extends BaseDialogFragment {
    private static final int MIN_RATING_MARKET = 4;

    @BindView(R.id.rating_bar)
    RatingBar ratingBar;

    @BindView(R.id.text_view_rating)
    TextView textView;

    @OnClick(R.id.rating_button)
    void rate() {
        int rating = (int) ratingBar.getRating();
        if (rating >= MIN_RATING_MARKET) {
            openAppInMarket();
        }
        UserPreferences.getInstance().setIsAppRated();
        dismissAllowingStateLoss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        String text = UserPreferences.getInstance().getPopupText();
        if (text != null) {
            textView.setText(text);
        }
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fragment_rating;
    }

    private void openAppInMarket() {
        String marketUrl = UserPreferences.getInstance().getPopUpUrl();
        if (marketUrl != null) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(marketUrl)));
        }
    }
}