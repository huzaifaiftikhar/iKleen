package org.huzaifa.ikleen.pricesmenu;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Huzaifa on 20-Jul-17.
 */

public interface RequestInterface {

    @GET("prices.json")
    Call<JSONResponseModel> getJSON();
}
