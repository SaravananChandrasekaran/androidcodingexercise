package in.bcs.codingexercise.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.bcs.codingexercise.R;
import in.bcs.codingexercise.model.Facts;
import in.bcs.codingexercise.rest.ApiInterface;
import in.bcs.codingexercise.rest.RetrofitClient;
import in.bcs.codingexercise.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FactsRecyclerActivity extends AppCompatActivity {


    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.emptyTextView)
    TextView mTextView;
    @BindView(R.id.factsRecyclerView)
    RecyclerView recyclerView;


    private ApiInterface mApiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts_recycler);
        setUpViews();
    }


    /**
     * Checks if Internet connection is available and Initiates the views
     */
    public void setUpViews() {
        ButterKnife.bind(FactsRecyclerActivity.this);
        //Initialize the Retrofit Client
        mApiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        if (Utils.isInternetConnectionAvailable(this)) {
            loadData(false);
        }
        //Setting listener for swipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(true);
            }
        });

    }


    /**
     * Loads the data using API calls and displays in the Recycler view
     *
     * @param isRefresh will be true on pull to refresh
     */
    private void loadData(boolean isRefresh) {

        //Checking the Internet connection
        if (Utils.isInternetConnectionAvailable(this)) {
            if (isRefresh) {
                //shows the refresh icon on pull to refresh
                mSwipeRefreshLayout.setRefreshing(true);
            }


            Call<Facts> call = mApiInterface.fetchFactsFeeds();
            call.enqueue(new Callback<Facts>() {

                @Override
                public void onResponse(Call<Facts> call, Response<Facts> response) {
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    Facts body = response.body();
                    Log.d("Test",body.toString());

                }

                @Override
                public void onFailure(Call<Facts> call, Throwable t) {
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }


                    Log.d("Test",t.getMessage());
                }
            });
        }
    }
}
