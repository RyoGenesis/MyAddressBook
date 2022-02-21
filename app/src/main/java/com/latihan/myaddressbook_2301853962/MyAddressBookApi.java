package com.latihan.myaddressbook_2301853962;

import com.latihan.myaddressbook_2301853962.models.APIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyAddressBookApi {
    @GET("?nim=2301853962&nama=Rio")
    Call<APIResponse> getResponseAndEmployees();

    @GET("{employeeID}/?nim=2301853962&nama=Rio")
    Call<APIResponse> getResponseAndDetailEmployee(@Path("employeeID") int employeeId);
}
