package `in`.rahultyagi.reports.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity
class ResponseData {
    /*  @PrimaryKey(autoGenerate = true)
    public int id;*/
    @PrimaryKey
    @SerializedName("country")
    @Expose
    var country: List<Country>? = null
    @SerializedName("zone")
    @Expose
    var zone: List<Zone>? = null
    @SerializedName("region")
    @Expose
    var region: List<Region>? = null
    @SerializedName("area")
    @Expose
    var area: List<Area>? = null
    @SerializedName("employee")
    @Expose
    var employee: List<Employee>? = null

    override fun toString(): String {
        return "ResponseData{" +
                "country=" + country +
                ", zone=" + zone +
                ", region=" + region +
                ", area=" + area +
                ", employee=" + employee +
                '}'.toString()
    }
}
