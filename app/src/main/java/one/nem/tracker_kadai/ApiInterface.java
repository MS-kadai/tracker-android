package one.nem.tracker_kadai;

import retrofit2.*;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("meta/time")
    Call serverTime();

    @GET("meta/version")
    Call serverVersion();

}
