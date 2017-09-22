package org.huzaifa.ikleen.pricesmenu;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.huzaifa.ikleen.R;

import java.util.ArrayList;

/**
 * Created by Huzaifa on 20-Jul-17.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<PricesJSONModel> price;
    private Context context;

    public DataAdapter(Context context, ArrayList<PricesJSONModel> price) {
        this.price = price;
        this.context = context;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {
        viewHolder.txt_price.setText(R.string.Rs);
        viewHolder.txt_name.setText(price.get(i).getName());
        viewHolder.txt_price.setText(viewHolder.txt_price.getText().toString() + " " + price.get(i).getPrice());
        Glide.with(context)
                .load(price.get(i).getImg())
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return price.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name, txt_price;
        private ImageView image;

        public ViewHolder(View view) {
            super(view);

            txt_name = view.findViewById(R.id.item_name);
            txt_price = view.findViewById(R.id.item_price);
            image = view.findViewById(R.id.item_image);
        }
    }
}
