package `in`.rahultyagi.reports.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity
class Result {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @SerializedName("ResponseStatus")
    @Expose
    var responseStatus: Int? = null

    @PrimaryKey
    @SerializedName("ResponseData")
    @Expose
    lateinit var responseData: ResponseData
    @SerializedName("Success")
    @Expose
    var success: Boolean? = null

}
