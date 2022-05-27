package com.example.filmbox;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ProviderViewHolder>{
    private final List<ProviderResult> providerResults;
    private final Context context;

    public ProviderAdapter(List<ProviderResult> providerResults, Context context) {
        this.providerResults = providerResults;
        this.context = context;
    }

    @NonNull
    @Override
    public ProviderAdapter.ProviderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProviderAdapter.ProviderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contenedorprovider, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderAdapter.ProviderViewHolder holder, int position) {
        holder.bindItem(providerResults.get(position), context);
    }

    @Override
    public int getItemCount() {
        return providerResults.size();
    }

    static class ProviderViewHolder extends RecyclerView.ViewHolder {

        private final RoundedImageView imageItemPoster;
        private final TextView textItemName;

        ProviderViewHolder(@NonNull View itemView) {
            super(itemView);

            imageItemPoster = itemView.findViewById(R.id.imageItemProvider);
            textItemName = itemView.findViewById(R.id.textItemNameProvider);
        }

        void bindItem(ProviderResult providerResult, Context context) {
            if (!TextUtils.isEmpty(providerResult.getLogoPath())) {
                ImageAdapter.setProviderImageURL(imageItemPoster, providerResult.getLogoPath());
            } else {
                imageItemPoster.setImageResource(R.drawable.ic_android);
            }
            textItemName.setText(providerResult.getName());
        }
    }
}
