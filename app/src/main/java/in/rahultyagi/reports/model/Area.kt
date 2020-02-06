package `in`.rahultyagi.reports.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity
class Area {

    /* @PrimaryKey(autoGenerate = true)
   public int id;*/

    @PrimaryKey
    @SerializedName("area")
    @Expose
    var area = ""
    @SerializedName("territory")
    @Expose
    var territory = ""

    override fun toString(): String {
        return "Area{" +
                "area='" + area + '\''.toString() +
                ", territory='" + territory + '\''.toString() +
                '}'.toString()
    }
}
