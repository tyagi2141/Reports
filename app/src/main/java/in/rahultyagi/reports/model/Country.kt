package `in`.rahultyagi.reports.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity
class Country {

    @PrimaryKey
    @SerializedName("country")
    @Expose
    var country = ""
    @SerializedName("territory")
    @Expose
    var territory = ""


}
