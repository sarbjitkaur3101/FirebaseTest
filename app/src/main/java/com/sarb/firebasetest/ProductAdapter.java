package com.sarb.firebasetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

List<Product> productList;
Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Picasso.get().load(productList.get(position).getProductImage()).into(holder.img_product);
        holder.txt_name.setText(productList.get(position).getProductName());
        holder.txt_price.setText(productList.get(position).getProductPrice());
        holder.txt_memory.setText(productList.get(position).getProductMemory());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView img_product;
        TextView txt_name,txt_price,txt_memory;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            img_product=itemView.findViewById(R.id.img_product);
            txt_memory=itemView.findViewById(R.id.txt_memory);
            txt_name=itemView.findViewById(R.id.txt_name);
            txt_price=itemView.findViewById(R.id.txt_price);

        }
    }
}
