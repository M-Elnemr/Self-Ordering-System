package com.example.hp.baleno;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerVAdapter2 extends RecyclerView.Adapter<RecyclerVAdapter2.ReyclerVHolder2> {
    Context context;
    static ArrayList<ItemsDetails> itemsDetails;
    OnClickL2 listener2;

    public interface OnClickL2{
        void onC2(int position);
        void onCart(int position);
    }

    public void setOnClickL2(RecyclerVAdapter2.OnClickL2 listener2) {
        this.listener2 = listener2;
    }

    public RecyclerVAdapter2(Context context, ArrayList<ItemsDetails> itemsDetails){
        this.context = context;
        this.itemsDetails = itemsDetails;
    }

    public class ReyclerVHolder2 extends RecyclerView.ViewHolder{

        TextView itemNameE2, itemNameA2;
        ImageView itemImage2, imgCart;

        public ReyclerVHolder2(@NonNull View itemView, final OnClickL2 listener2) {
            super(itemView);
            itemNameE2 = itemView.findViewById(R.id.itemNameE2);
            itemNameA2 = itemView.findViewById(R.id.itemNameA2);
            itemImage2 = itemView.findViewById(R.id.itemImage2);
            imgCart = itemView.findViewById(R.id.imgCart);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener2 != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener2.onC2(position);
                        }
                    }
                }
            });

            imgCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener2 != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener2.onCart(position);
                        }
                    }
                }
            });
        }

    }

    @NonNull
    @Override
    public ReyclerVHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list2, viewGroup, false);
        ReyclerVHolder2 RVA = new ReyclerVHolder2(v,listener2);
        return RVA;
    }

    @Override
    public void onBindViewHolder(@NonNull ReyclerVHolder2 reyclerVHolder2, int i) {
        ItemsDetails currentItem = itemsDetails.get(i);

        reyclerVHolder2.itemImage2.setImageResource(currentItem.getItem_image());
        reyclerVHolder2.itemNameE2.setText(currentItem.getItem_nameE());
        reyclerVHolder2.itemNameA2.setText(currentItem.getItem_nameA());
    }

    @Override
    public int getItemCount() {
        if(itemsDetails!= null){
        return itemsDetails.size();
        }else {
            return 0;
        }
    }
}
