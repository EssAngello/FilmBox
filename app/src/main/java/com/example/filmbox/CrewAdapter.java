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

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder>{

    private final List<CrewResult> crewResults;
    private final Context context;

    public CrewAdapter(List<CrewResult> crewResults, Context context) {
        this.crewResults = crewResults;
        this.context = context;
    }

    @NonNull
    @Override
    public CrewAdapter.CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CrewAdapter.CrewViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contenedorcast, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CrewAdapter.CrewViewHolder holder, int position) {
        holder.bindItem(crewResults.get(position), context);
    }

    @Override
    public int getItemCount() {
        return crewResults.size();
    }

    static class CrewViewHolder extends RecyclerView.ViewHolder {

        private final RoundedImageView imageItemPoster;
        private final TextView textItemName;
        private final TextView textItemNametwo;

        CrewViewHolder(@NonNull View itemView) {
            super(itemView);

            imageItemPoster = itemView.findViewById(R.id.imageItemPosterCast);
            textItemName = itemView.findViewById(R.id.textItemNameCast);
            textItemNametwo = itemView.findViewById(R.id.textItemNameCast2);
        }

        void bindItem(CrewResult crewResult, Context context) {
            if (!TextUtils.isEmpty(crewResult.getProfilePath())) {
                ImageAdapter.setPosterURL(imageItemPoster, crewResult.getProfilePath());
            } else {
                imageItemPoster.setImageResource(R.drawable.ic_android);
            }
            textItemName.setText(crewResult.getName());
            textItemNametwo.setText(crewResult.getDepartment());
        }
    }

}
