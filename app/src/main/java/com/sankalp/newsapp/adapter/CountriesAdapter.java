package com.sankalp.newsapp.adapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sankalp.newsapp.R;
import com.sankalp.newsapp.model.Country;
import com.squareup.picasso.Picasso;

import java.util.Hashtable;
import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> {

    private List<Country> countries;
   public ICountryClickedListner countryClickedListner;

    public CountriesAdapter(List<Country> countries) {
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountriesAdapter.CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view;
        view = layoutInflater.inflate(R.layout.card_country, viewGroup, false);
        return new CountriesViewHolder(view ,this);
    }

    public void setCountryClickedListner(ICountryClickedListner countryClickedListner){
        this.countryClickedListner =countryClickedListner;
    }
    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder countriesViewHolder, int i) {
        if (i == RecyclerView.NO_POSITION) return;
        countriesViewHolder.updateFields(i);
    }

    class CountriesViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        private ImageView coverImage;
        CountriesAdapter countriesAdapter;

        CountriesViewHolder(View itemView , CountriesAdapter adapter) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.title);
            coverImage = itemView.findViewById(R.id.coverImage);
            countriesAdapter = adapter;
        }

        void updateFields(int position){
            itemView.setTag(position);
            txtTitle.setText(countries.get(position).getName());
            Picasso.get().load(countries.get(position).getFlagIcon()).into(coverImage);
            itemView.setOnClickListener(view -> {
                countriesAdapter.countryClickedListner.onCountryClicked(itemView.getContext().getResources()
                        .getStringArray(R.array.countries_array)[(int)itemView.getTag()] , txtTitle.getText().toString());
            });
        }
    }

    public interface ICountryClickedListner{

        void onCountryClicked(String countryCode ,String countryName);
    }


    @Override
    public int getItemCount() {
        return countries.size();
    }
}
