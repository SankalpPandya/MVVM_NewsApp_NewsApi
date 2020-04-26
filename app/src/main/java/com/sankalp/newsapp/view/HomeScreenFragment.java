package com.sankalp.newsapp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.sankalp.newsapp.R;
import com.sankalp.newsapp.adapter.CountriesAdapter;
import com.sankalp.newsapp.adapter.NewsFeedAdapter;
import com.sankalp.newsapp.model.Article;
import com.sankalp.newsapp.model.Country;
import com.sankalp.newsapp.utils.ApiResponse;
import com.sankalp.newsapp.utils.BundleHelper;
import com.sankalp.newsapp.utils.Constants;
import com.sankalp.newsapp.view.listener.NewsRecyclerTouchListener;
import com.sankalp.newsapp.viewmodel.FeedsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeScreenFragment extends Fragment implements CountriesAdapter.ICountryClickedListner {

    private RecyclerView recyclerViewTopHeadlines;
    private RecyclerView recyclerViewCountries;
    private NewsFeedAdapter trendingNewsAdapter;
    ProgressDialog progressDialog;
    private FeedsViewModel mViewModel;
    CountriesAdapter countriesAdapter;

    public static HomeScreenFragment NewInstance() {
        return new HomeScreenFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_screen_layout, container, false);
        initViews(view);
        return view;
    }

    private void initViewModel() {
        mViewModel = ((MainActivity) getActivity()).getMainViewModel();
        mViewModel.init();
        mViewModel.getEverythingApiResponse().observe(this, this::processResponse);
    }

    private void initViews(View view) {
        recyclerViewTopHeadlines = view.findViewById(R.id.recycle_view_trending);
        recyclerViewCountries = view.findViewById(R.id.recycle_view_countries);
    }


    private void initTopHeadLinesView() {
        trendingNewsAdapter = new NewsFeedAdapter(Constants.ListType.HomeScreen);
        RecyclerView.LayoutManager layoutManagerTrending = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTopHeadlines.setLayoutManager(layoutManagerTrending);
        recyclerViewTopHeadlines.setAdapter(trendingNewsAdapter);

        recyclerViewTopHeadlines.addOnItemTouchListener(new NewsRecyclerTouchListener
                (getActivity().getApplicationContext(),
                        recyclerViewTopHeadlines, (view, position) -> {
                    Article article = Objects.requireNonNull(mViewModel.getEverythingArticles().getValue()).get(position);
                    this.showNewsDesciption(BundleHelper.getShowNewsDescriptionIntent(article));
                }));
    }


    private void initCountriesView() {
        countriesAdapter = new CountriesAdapter(getAvailableCountries());
        RecyclerView.LayoutManager layoutManagerCountries = new GridLayoutManager(getActivity(), 3);
        recyclerViewCountries.setLayoutManager(layoutManagerCountries);
        recyclerViewCountries.setAdapter(countriesAdapter);
        countriesAdapter.setCountryClickedListner(this);
        countriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
        initProgressBar();
        initTopHeadLinesView();
    }

    private List<Country> getAvailableCountries() {
        List<Country> countries = new ArrayList<>();
        String[] countryArray = getResources().getStringArray(R.array.countries_array);
        String[] countryArrayName = getResources().getStringArray(R.array.countries_array_name);
        int i = 0;
        while (i < countryArray.length) {
            countries.add(new Country().setName(countryArrayName[i]).setFlagIcon(this.getResources()
                    .getIdentifier(countryArray[i], "drawable",
                            getActivity().getPackageName())).setId(i));
            i++;
        }
        return countries;
    }

    private void initProgressBar() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("loading news...");
        progressDialog.setCancelable(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        String title = "NewsApp";
        ((MainActivity) getActivity()).getToolbar().setTitle(title);

        mViewModel.hitGetEverythingApi("latest news", null
                , null, null, null, null, Constants.PREFERRED_LANGUAGE, null,
                15, 1);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countriesAdapter != null) {
            countriesAdapter.setCountryClickedListner(null);
        }
    }

    private void showNewsDesciption(Bundle bundle) {

        ((MainActivity) Objects.requireNonNull(getActivity())).showCountryNewsDescriptionFragment(bundle);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void processResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                progressDialog.show();
                break;
            case SUCCESS:
                progressDialog.dismiss();
                renderSuccessResponse(apiResponse.data);
                break;
            case ERROR:
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Failed to load feeds.. please check internet connection ",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(JsonElement response) {
        if (!response.isJsonNull()) {
            JsonElement articlesJson = response.getAsJsonObject().getAsJsonArray("articles");
            Gson gson = new Gson();
            ArrayList<Article> articles = gson.fromJson(articlesJson, new TypeToken<ArrayList<Article>>() {
            }.getType());
            mViewModel.getEverythingArticles().setValue(articles);
            if (articles != null) {
                trendingNewsAdapter.setData(articles);
                trendingNewsAdapter.notifyDataSetChanged();
            }
            initCountriesView();
        } else {
            Log.e(this.getClass().getName(), "Failed to parse response " + response);
        }
    }

    @Override
    public void onCountryClicked(String countryCode, String countryName) {
        ((MainActivity) getActivity()).showCountryNewsFragment(countryCode, countryName);
    }
}


