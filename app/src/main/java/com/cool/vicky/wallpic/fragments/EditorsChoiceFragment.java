package com.cool.vicky.wallpic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cool.vicky.wallpic.R;
import com.cool.vicky.wallpic.adapter.RecyclerGridAdapter;
import com.cool.vicky.wallpic.pojo.Hit;
import com.cool.vicky.wallpic.pojo.Wallpaper;
import com.cool.vicky.wallpic.util.ProgressHUD;
import com.cool.vicky.wallpic.util.RetrofitApi;
import com.cool.vicky.wallpic.util.SpacesItemDecoration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditorsChoiceFragment extends Fragment {


    public EditorsChoiceFragment() {
        // Required empty public constructor
    }

    private String TAG = EditorsChoiceFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private int pageNumber = 1;
    private String category = "";
    private List<Hit> hitList;
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private ProgressHUD mProgressHUD;
    private Boolean reachedEnd = false;
    private Button retryButton;
    private LinearLayout errorLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editors_choice, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initErrorLayout();
        initRetryButton();
        initRecyclerView();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            if (!reachedEnd)
                                makeNetworkRequest();
                        }
                    }
                }
            }
        });

        makeNetworkRequest();
        mProgressHUD = ProgressHUD.show(getActivity(), "Loading", true, false, null);
    }

    private void initErrorLayout() {
        errorLayout = (LinearLayout) getView().findViewById(R.id.error_layout);
    }

    private void initRetryButton() {
        retryButton = (Button) getView().findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressHUD = ProgressHUD.show(getActivity(), "Loading", true, false, null);
                errorLayout.setVisibility(View.GONE);
                makeNetworkRequest();
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.getItemAnimator().setChangeDuration(0);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_insets);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }

    private void makeNetworkRequest() {

        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<Wallpaper> wallpaperCall = apiInterface.editorChoiceWallpapers(
                "2209479-4cbc19d132c80918a5167e2d9",
                String.valueOf(pageNumber),
                "high_resolution",
                "true",
                "true"
        );
        wallpaperCall.enqueue(new Callback<Wallpaper>() {
                                  @Override
                                  public void onResponse(Call<Wallpaper> call, Response<Wallpaper> response) {
                                      if (response.isSuccessful()) {
                                          mRecyclerView.setVisibility(View.VISIBLE);
                                          Wallpaper wallpaper = response.body();
                                          Log.d(TAG, "Page = " + pageNumber);
                                          if (pageNumber == 1) {
                                              hitList = wallpaper.getHits();
                                              mAdapter = new RecyclerGridAdapter(getActivity(), hitList);
                                              mRecyclerView.setAdapter(mAdapter);
                                              mRecyclerView.scrollToPosition(0);// scroll to top after selecting a category
                                          } else {
                                              hitList.addAll(wallpaper.getHits());
                                              mAdapter.notifyDataSetChanged();
                                          }
                                          ++pageNumber;
                                          loading = true;
                                      } else {
                                          if (pageNumber == 27)
                                              reachedEnd = true;
                                          else {
                                              mRecyclerView.setVisibility(View.GONE);
                                              errorLayout.setVisibility(View.VISIBLE);
                                          }
                                          loading = true;
                                      }
                                      mProgressHUD.dismiss();
                                  }

                                  @Override
                                  public void onFailure(Call<Wallpaper> call, Throwable t) {
                                      mRecyclerView.setVisibility(View.GONE);
                                      errorLayout.setVisibility(View.VISIBLE);
                                      loading = true;
                                      mProgressHUD.dismiss();
                                  }
                              }

        );
    }

}
