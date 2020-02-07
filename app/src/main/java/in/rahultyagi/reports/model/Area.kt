package `in`.rahultyagi.reports.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity
class Area {

    @PrimaryKey
    @SerializedName("area")
    @Expose
    var area = ""
    @SerializedName("territory")
    @Expose
    var territory = ""

}
