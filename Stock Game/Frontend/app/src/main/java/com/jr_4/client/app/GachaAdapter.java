package com.jr_4.client.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jr_4.client.Pojos.Portfolio;
import com.jr_4.client.R;

import java.util.ArrayList;

public class GachaAdapter extends ArrayAdapter<Portfolio> {
    private static String TAG ="GachaAdapter";

    private Context mContext;
    int mResource;

    /**
     *Default constructor for the GachaAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public GachaAdapter(Context context, int resource, ArrayList<Portfolio> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource= resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String symbol = getItem(position).getStockName();
        int quantity = getItem(position).getQuantity();
        double price = getItem(position).getCurrentValue();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvSymbol = (TextView) convertView.findViewById(R.id.gacha_ada_symbol);
        TextView tvquantity = (TextView) convertView.findViewById(R.id.gacha_ada_quantity);
        TextView tvprice = (TextView) convertView.findViewById(R.id.gacha_ada_price);

        tvSymbol.setText(symbol);
        tvquantity.setText(String.valueOf(quantity) );
        tvprice.setText(String.valueOf(price));

        return convertView;
    }
}
