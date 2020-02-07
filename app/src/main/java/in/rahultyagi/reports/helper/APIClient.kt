package `in`.rahultyagi.reports.helper

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


internal object APIClient {
    private var retrofit: Retrofit? = null
    val client: Retrofit?
        get() {
            val client = OkHttpClient.Builder().build()
            retrofit = Retrofit.Builder()
                .baseUrl("http://demo1929804.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit
        }
}
