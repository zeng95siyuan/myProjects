package com.jr_4.client.app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jr_4.client.Pojos.Portfolio;
import com.jr_4.client.Pojos.searchResult;
import com.jr_4.client.R;
import com.jr_4.client.stockInfoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends ArrayAdapter<searchResult> {
    private static String TAG ="SearchAdapter";

    private Context mContext;
    int mResource;

    /**
     *Default constructor for the SearchAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public SearchAdapter(Context context, int resource, ArrayList<searchResult> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource= resource;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        String symbol = getItem(position).getSymbol();
        double price = getItem(position).getPrice();
        String company = getItem(position).getCompany();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvSymbol = convertView.findViewById(R.id.search_ada_symbol);
        TextView tvPrice = convertView.findViewById(R.id.search_ada_price);
        TextView tvCompany = convertView.findViewById(R.id.search_ada_company);

        tvSymbol.setText(symbol);
        tvCompany.setText(company);
        tvPrice.setText(String.valueOf(price));

        return convertView;
    }


}