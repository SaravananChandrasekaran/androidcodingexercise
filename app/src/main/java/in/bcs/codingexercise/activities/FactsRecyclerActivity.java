package in.bcs.codingexercise.activities;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.bcs.codingexercise.R;
import in.bcs.codingexercise.adapter.FactsAdapter;
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
    private ProgressDialog progressDialog;
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
        } else {
            toggleError(true, getString(R.string.no_internet));
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
            } else {
                showProgress(getString(R.string.loading_message));
            }


            Call<Facts> call = mApiInterface.fetchFactsFeeds();
            call.enqueue(new Callback<Facts>() {

                @Override
                public void onResponse(Call<Facts> call, Response<Facts> response) {
                    hideProgress();
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    Facts body = response.body();
                    FactsAdapter factsAdapter = new FactsAdapter(FactsRecyclerActivity.this, body.getTitle(), body.removeEmpty(body.getRows()));
                    recyclerView.setAdapter(factsAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(FactsRecyclerActivity.this));

                    toggleError(false, "");
                }

                @Override
                public void onFailure(Call<Facts> call, Throwable t) {
                    hideProgress();
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    toggleError(true, getString(R.string.error));
                }
            });
        } else {
            toggleError(true, getString(R.string.no_internet));
        }
    }

    /**
     * Shows the progress dialog with the message passed as parameter
     *
     * @param msg message to be displayed
     */
    private void showProgress(String msg) {
        progressDialog = new ProgressDialog(FactsRecyclerActivity.this);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    /**
     * Dismiss progress dialog
     */
    private void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSwipeRefreshLayout.setOnRefreshListener(null);
        hideProgress();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Toggles the views based on the successful load or error
     * Hides the recyclerview on error and displays the empty view
     * with custom message
     *
     * @param isError will be true if error occurs
     * @param message Error message on empty view
     */
    private void toggleError(boolean isError, String message) {
        mTextView.setText(message);
        mTextView.setVisibility(isError ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isError ? View.GONE : View.VISIBLE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // on menu refresh is clicked
        if (id == R.id.menu_refresh) {
            loadData(false);
        }
        return super.onOptionsItemSelected(item);
    }


}
