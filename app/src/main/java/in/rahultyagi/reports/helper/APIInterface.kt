package `in`.rahultyagi.reports.helper

import `in`.rahultyagi.reports.model.Result
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("assignment")
    fun doGetListResources(): Call<Result>
}
