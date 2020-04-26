package com.sankalp.newsapp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.sankalp.newsapp.R;
import com.sankalp.newsapp.adapter.NewsFeedAdapter;
import com.sankalp.newsapp.model.Article;
import com.sankalp.newsapp.utils.ApiResponse;
import com.sankalp.newsapp.utils.BundleHelper;
import com.sankalp.newsapp.utils.Constants;
import com.sankalp.newsapp.view.listener.NewsRecyclerTouchListener;
import com.sankalp.newsapp.viewmodel.FeedsViewModel;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;


public class CountryNewsFragment extends Fragment {

    private NewsFeedAdapter adapter;
    private RecyclerView recyclerView;
    private FeedsViewModel mViewModel;
    ProgressDialog progressDialog;
    private String countrCode;

    public static CountryNewsFragment NewInstance(String countryCode ,String cooutryName) {
        CountryNewsFragment fragment = new CountryNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.COUNTRY_CODE, countryCode);
        bundle.putString(Constants.COUNTRY_NAME, cooutryName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        countrCode = getArguments().getString(Constants.COUNTRY_CODE);
        String title = getActivity().getResources().getString(R.string.title_country_headlines) +
                " " + getArguments().getString(Constants.COUNTRY_NAME);

        ((MainActivity)getActivity()).getToolbar().setTitle(title);
        InitProgressBar();
        InitViewModel();
        setupRecyclerView(getView());
        Hashtable h = new Hashtable();
    }

    private void InitViewModel() {
        mViewModel = ((MainActivity) getActivity()).getMainViewModel();
        mViewModel.getHeadlineApiResponse().observe(this, this::consumeResponse);
    }

    private void InitProgressBar() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("loading news...");
        progressDialog.setCancelable(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.hitHeadlineApi(countrCode);
    }

    private void consumeResponse(ApiResponse apiResponse) {

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
                Toast.makeText(getActivity(), "Failed to Refresh feed..", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(JsonElement response) {
        if (!response.isJsonNull()) {
            JsonElement articlesJson = response.getAsJsonObject().getAsJsonArray(Constants.ARTICLES);
            Gson gson = new Gson();
            ArrayList<Article> articles = gson.fromJson(articlesJson, new TypeToken<ArrayList<Article>>() {
            }.getType());
            mViewModel.getHeadlineArticles().setValue(articles);
            if (articles != null) {
                adapter.setData(articles);
                adapter.notifyDataSetChanged();
            }
        } else {
            Log.d(getClass().getName() , "Json response null");
        }
    }

    public static CountryNewsFragment newInstance() {
        return new CountryNewsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.country_news, container, false);
    }

    private void setupRecyclerView(View parent) {
        adapter = new NewsFeedAdapter(Constants.ListType.DetailView);
        recyclerView = parent.findViewById(R.id.headline_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new NewsRecyclerTouchListener(getActivity().getApplicationContext(),
                recyclerView, (view, position) -> {

            Article article = Objects.requireNonNull(mViewModel.getHeadlineArticles().getValue()).get(position);
            this.showNewsDesciption(BundleHelper.getShowNewsDescriptionIntent(article));
        }));
        registerForContextMenu(recyclerView);
    }
    private void showNewsDesciption(Bundle bundle) {

        ((MainActivity) Objects.requireNonNull(getActivity())).showCountryNewsDescriptionFragment(bundle);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
