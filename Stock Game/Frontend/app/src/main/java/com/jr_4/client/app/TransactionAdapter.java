package com.jr_4.client.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jr_4.client.Pojos.Transaction;
import com.jr_4.client.R;

import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter <Transaction> {
    private static String TAG ="TransactionAdapter";

    private Context mContext;
    int mResource;

    /**
     *Default constructor for the TransanctionAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public TransactionAdapter(Context context, int resource, ArrayList<Transaction> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource= resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        boolean trans_buy = getItem(position).isBuy();
        int quantity = getItem(position).getQuantity();
        double price = getItem(position).getPrice();
        String symbol = getItem(position).getSymbol();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvSymbol = (TextView) convertView.findViewById(R.id.trans_ada_ticker);
        TextView tvquantity = (TextView) convertView.findViewById(R.id.trans_ada_quantity);
        TextView tvprice = (TextView) convertView.findViewById(R.id.trans_ada_price);
        TextView tvbuy = (TextView) convertView.findViewById(R.id.trans_ada_type);

        tvSymbol.setText(symbol);
        tvquantity.setText(String.valueOf(quantity) );
        tvprice.setText(String.valueOf(price));
        if(trans_buy)
            tvbuy.setText("buy");
        else
            tvbuy.setText("sell");

        return convertView;
    }
}
