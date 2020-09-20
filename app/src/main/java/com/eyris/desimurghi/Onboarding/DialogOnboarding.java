package com.eyris.desimurghi.Onboarding;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.eyris.desimurghi.Adapters.OnBoardingAdapter;
import com.eyris.desimurghi.Models.OnBoardItem;
import com.eyris.desimurghi.R;

import java.util.ArrayList;
import java.util.List;

public class DialogOnboarding extends DialogFragment {
    private Context mContext;
    private OnBoardingAdapter adapter;

    private LinearLayout ll_indicators;
    private Button bt_next;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = new Dialog(mContext);
//        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        d.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        d.setContentView(R.layout.activity_onboarding_screen);

        setupOnboarding();

        ViewPager2 viewPager2 = d.findViewById(R.id.viewpager);
        ll_indicators = d.findViewById(R.id.ll_indicators);
        bt_next = d.findViewById(R.id.bt_next);
        viewPager2.setAdapter(adapter);
        setupIndicators();
        setCurrentIndicator(0);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager2.getCurrentItem() + 1 < adapter.getItemCount()) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                } else {
                    dismiss();
                }
            }
        });
        return d;
    }

    private void setupIndicators() {
        ImageView[] indicators = new ImageView[adapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(mContext);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.onboard_inactive));
            indicators[i].setLayoutParams(layoutParams);
            ll_indicators.addView(indicators[i]);
        }
    }

    private void setCurrentIndicator(int index) {
        int childCount = ll_indicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) ll_indicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.onboard_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.onboard_inactive));
            }
        }

        if (index == adapter.getItemCount() - 1) {
            bt_next.setText("Start");
        } else {
            bt_next.setText("Next");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void setupOnboarding() {
        List<OnBoardItem> list = new ArrayList<>();

        OnBoardItem item = new OnBoardItem(R.drawable.ic_first, "Healthy Chickens", "Our chickens are raised in a healthy open farm. We make sure they are in their natural habitat");
        list.add(item);

        OnBoardItem item2 = new OnBoardItem(R.drawable.ic_second, "Healthy Meat", "Desi Chickens have protein rich healthy meat");
        list.add(item2);

        OnBoardItem item3 = new OnBoardItem(R.drawable.ic_third, "Healthy Eggs", "Desi Chicken eggs are proven to have vital nutrients and health benefits");
        list.add(item3);

        adapter = new OnBoardingAdapter(list);
    }

}
