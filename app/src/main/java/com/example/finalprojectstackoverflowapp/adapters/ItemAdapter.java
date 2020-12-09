package com.example.finalprojectstackoverflowapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.finalprojectstackoverflowapp.R;
import com.example.finalprojectstackoverflowapp.model.Item;
import com.example.finalprojectstackoverflowapp.model.Owner;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>
{
    private List<Item> itemResults;

    public ItemAdapter(List<Item> itemResults){
        this.itemResults=itemResults;

    }
    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);

        View itemView=inflater.inflate(R.layout.item_cart_view, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {

        Item item=itemResults.get(position);
        holder.itemTitle.setText(item.getTitle());
        holder.itemLink.setText(item.getLink());
        holder.ownerName.setText("Owner name: "+item.getOwner().getDisplayName());

        Glide.with(holder.itemView.getContext()).load(item.getOwner().getProfileImage()).
                diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(holder.imageOwner);

    }

    @Override
    public int getItemCount() {
        return this.itemResults.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTitle;
        private TextView itemLink;
        private ImageView imageOwner;
        private TextView ownerName;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle=itemView.findViewById(R.id.item_title);
            itemLink=itemView.findViewById(R.id.item_link);
            imageOwner=itemView.findViewById(R.id.image_owner);
            ownerName=itemView.findViewById(R.id.owner_name);
        }
    }
}
