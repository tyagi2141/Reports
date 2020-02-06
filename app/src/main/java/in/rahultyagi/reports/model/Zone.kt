package `in`.rahultyagi.reports.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity
class Zone {
    /* @PrimaryKey(autoGenerate = true)
    public int id;*/
    @PrimaryKey
    @SerializedName("zone")
    @Expose
    var zone = ""
    @SerializedName("territory")
    @Expose
    var territory = ""

    override fun toString(): String {
        return "Zone{" +
                "zone='" + zone + '\''.toString() +
                ", territory='" + territory + '\''.toString() +
                '}'.toString()
    }
}
