package in.bcs.codingexercise.rest;

import in.bcs.codingexercise.model.Facts;
import in.bcs.codingexercise.utils.Constants;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by saran on 2/19/2018.
 */

public interface  ApiInterface {
    @GET(Constants.FACTS_API)
    Call<Facts> fetchFactsFeeds();
}
