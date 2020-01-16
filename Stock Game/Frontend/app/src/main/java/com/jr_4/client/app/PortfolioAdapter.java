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
import java.util.List;

public class PortfolioAdapter extends ArrayAdapter<Portfolio> {
    private static String TAG ="PortfolioAdapter";

    private Context mContext;
    int mResource;

    /**
     *Default constructor for the PortfolioAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public PortfolioAdapter(Context context, int resource, ArrayList<Portfolio> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource= resource;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        String stockName = getItem(position).getStockName();
        int quantity = getItem(position).getQuantity();
        double price = getItem(position).getCurrentValue();
        int id = getItem(position).getStockId();

        Portfolio portfolio = new Portfolio(stockName, quantity, price, id);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.port_StockName);
        TextView tvquantity = (TextView) convertView.findViewById(R.id.port_quantity);
        TextView tvprice = (TextView) convertView.findViewById(R.id.port_price);

        tvName.setText(stockName);
        tvquantity.setText("quantity: "+String.valueOf(quantity) );
        tvprice.setText("current price: "+String.valueOf(price));

        return convertView;
    }


}
