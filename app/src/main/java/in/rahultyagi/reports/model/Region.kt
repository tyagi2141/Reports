package `in`.rahultyagi.reports.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity
class Region {

    @PrimaryKey
    @SerializedName("region")
    @Expose
    lateinit var region: String

    @SerializedName("territory")
    @Expose
    var territory: String? = null


}
