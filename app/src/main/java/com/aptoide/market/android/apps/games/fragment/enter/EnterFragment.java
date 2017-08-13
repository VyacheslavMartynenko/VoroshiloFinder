package com.aptoide.market.android.apps.games.fragment.enter;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aptoide.market.android.apps.games.R;
import com.aptoide.market.android.apps.games.application.FinderApplication;
import com.aptoide.market.android.apps.games.fragment.BaseFragment;

import butterknife.BindView;

public class EnterFragment extends BaseFragment {
    public static final String POSITION = "position";

    @BindView(R.id.enter_text)
    TextView enterTextView;

    @BindView(R.id.container)
    RelativeLayout containerLayout;

    public static EnterFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(POSITION, position);

        EnterFragment fragment = new EnterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        int position = getArguments().getInt(POSITION);

        switch (position) {
            case 0:
                setUpFragment(getString(R.string.enter_text_first), getBackgroundDrawable(R.drawable.bg_first));
                break;
            case 1:
                setUpFragment(getString(R.string.enter_text_second), getBackgroundDrawable(R.drawable.bg_second));
                break;
            case 2:
                setUpFragment(getString(R.string.enter_text_third), getBackgroundDrawable(R.drawable.bg_third));
                break;
            default:
                break;
        }

        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_enter;
    }

    @SuppressWarnings("deprecation")
    public void setUpFragment(String text, Drawable drawable) {
        enterTextView.setText(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            containerLayout.setBackground(drawable);
        } else {
            containerLayout.setBackgroundDrawable(drawable);
        }
    }

    private Drawable getBackgroundDrawable(int id) {
        return ContextCompat.getDrawable(FinderApplication.getInstance().getApplicationContext(), id);
    }
}
