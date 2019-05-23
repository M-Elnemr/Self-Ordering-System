package com.example.hp.baleno;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerVAdapter extends RecyclerView.Adapter<RecyclerVAdapter.ReyclerVHolder> {
    ArrayList<Items> arrayList;
    OnClickL listener;

    public interface OnClickL{
        void onC(int position);
    }

    public void setOnClickL(OnClickL listener) {
        this.listener = listener;
    }

    public RecyclerVAdapter(ArrayList<Items> arrayList){
        this.arrayList = arrayList;
    }

    public class ReyclerVHolder extends RecyclerView.ViewHolder{
        TextView itemNameE, itemNameA;
        ImageView itemImage;

        public ReyclerVHolder(@NonNull View itemView, final OnClickL listener) {
            super(itemView);
            itemNameE = itemView.findViewById(R.id.itemNameE);
            itemNameA = itemView.findViewById(R.id.itemNameA);
            itemImage = itemView.findViewById(R.id.itemImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onC(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ReyclerVHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.iteam_list, viewGroup, false);
        ReyclerVHolder RVH = new ReyclerVHolder(v, listener);

        return RVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ReyclerVHolder reyclerVHolder, int i) {
        Items items = arrayList.get(i);

        reyclerVHolder.itemNameE.setText(items.getNamesE());
        reyclerVHolder.itemNameA.setText(items.getNamesA());
        reyclerVHolder.itemImage.setImageResource(items.getPics());

    }

    @Override
    public int getItemCount() {
        if(arrayList != null){
            return arrayList.size();
        }else{
            return 0;
        }
    }
}
