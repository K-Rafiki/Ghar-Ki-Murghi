package com.eyris.desimurghi.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eyris.desimurghi.Models.OnBoardItem;
import com.eyris.desimurghi.R;

import java.util.List;

public class OnBoardingAdapter extends RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder> {

    private List<OnBoardItem> onBoardItems;

    public OnBoardingAdapter(List<OnBoardItem> onBoardItems) {
        this.onBoardItems = onBoardItems;
    }

    @NonNull
    @Override
    public OnBoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnBoardingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.onboard_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OnBoardingViewHolder holder, int position) {
        holder.tv_title.setText(onBoardItems.get(position).getTitle());
        holder.tv_subtitle.setText(onBoardItems.get(position).getDescription());
        holder.iv_onboard.setImageResource(onBoardItems.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return onBoardItems.size();
    }

    class OnBoardingViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_subtitle;
        private ImageView iv_onboard;

        public OnBoardingViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_subtitle = itemView.findViewById(R.id.tv_subtitle);
            iv_onboard = itemView.findViewById(R.id.iv_onboard);
        }
    }

}
