package com.sankalp.newsapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sankalp.newsapp.R;
import com.sankalp.newsapp.utils.Constants;
import com.squareup.picasso.Picasso;


public class NewsDescriptionFragment extends Fragment {

    private TextView textView;
    private ImageView imageView;
    private Button readMoreButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static NewsDescriptionFragment NewInstance(Bundle bundle) {
        NewsDescriptionFragment fragment = new NewsDescriptionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_description, container, false);
        InitViews(view);
        return view;
    }

    private void InitViews(View view) {
        textView = view.findViewById(R.id.description);
        imageView = view.findViewById(R.id.coverImage);
        readMoreButton = view.findViewById(R.id.btn_read_more);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();

        ((MainActivity)getActivity()).getToolbar().setTitle(getTitle(bundle));
        textView.setText(setNewsDescription(bundle));
        Picasso.get().load(bundle.getString(Constants.URL_TO_IMAGE)).into(imageView);
         String url = getUrlForMore(bundle);
        readMoreButton.setOnClickListener(view -> {
            if (!url.equals("")) {
                Intent intent = new Intent(getActivity().getApplicationContext(),  NewsPageWebViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }

    private String setNewsDescription(Bundle bundle) {

        String bodyText = bundle.getString(Constants.CONTENT);
        if (TextUtils.isEmpty(bodyText)) {
            bodyText = bundle.getString(Constants.DESCRIPTION);
            if (TextUtils.isEmpty(bodyText)) {
                bodyText = bundle.getString(Constants.TITLE);
            }
            if (TextUtils.isEmpty(bodyText)) {
                bodyText = "";
            }
        }
        return bodyText;
    }
    private String getTitle(Bundle bundle){
        String text = bundle.getString(Constants.TITLE);
        if(!TextUtils.isEmpty(text)){
            return text;
        }
        return "";
    }

    private String getUrlForMore(Bundle bundle){
        String text = bundle.getString(Constants.URL);
        if(!TextUtils.isEmpty(text)){
            return text;
        }
        return "";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
