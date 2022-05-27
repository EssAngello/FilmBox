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

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder>{

    private final List<CastResult> castResults;
    private final Context context;

    public CastAdapter(List<CastResult> castResults, Context context) {
        this.castResults = castResults;
        this.context = context;
    }

    @NonNull
    @Override
    public CastAdapter.CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CastAdapter.CastViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contenedorcast, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.CastViewHolder holder, int position) {
        holder.bindItem(castResults.get(position), context);
    }

    @Override
    public int getItemCount() {
        return castResults.size();
    }

    static class CastViewHolder extends RecyclerView.ViewHolder {

        private final RoundedImageView imageItemPoster;
        private final TextView textItemName;
        private final TextView textItemNametwo;

        CastViewHolder(@NonNull View itemView) {
            super(itemView);

            imageItemPoster = itemView.findViewById(R.id.imageItemPosterCast);
            textItemName = itemView.findViewById(R.id.textItemNameCast);
            textItemNametwo = itemView.findViewById(R.id.textItemNameCast2);
        }

        void bindItem(CastResult castResult, Context context) {
            if (!TextUtils.isEmpty(castResult.getProfilePath())) {
                ImageAdapter.setPosterURL(imageItemPoster, castResult.getProfilePath());
            } else {
                imageItemPoster.setImageResource(R.drawable.ic_android);
            }
            textItemName.setText(castResult.getName());
            textItemNametwo.setText(castResult.getCharacter());
        }
    }

}
