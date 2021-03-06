package `in`.rahultyagi.reports.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity
class Employee {

    @SerializedName("area")
    @Expose
    var area: String? = null

    @PrimaryKey
    @SerializedName("name")
    @Expose
    lateinit var name: String
    @SerializedName("territory")
    @Expose
    var territory: String? = null


}
