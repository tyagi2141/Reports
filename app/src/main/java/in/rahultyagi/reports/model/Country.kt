package `in`.rahultyagi.reports.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity
class Country {
    /* @PrimaryKey(autoGenerate = true)
    public int id;*/

    @PrimaryKey
    @SerializedName("country")
    @Expose
    var country = ""
    @SerializedName("territory")
    @Expose
    var territory = ""

    override fun toString(): String {
        return "Country{" +
                "country='" + country + '\''.toString() +
                ", territory='" + territory + '\''.toString() +
                '}'.toString()
    }
}
