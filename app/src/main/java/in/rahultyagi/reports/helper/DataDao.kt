package `in`.rahultyagi.reports.helper

import `in`.rahultyagi.reports.model.*
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DataDao {
    //Area
    @get:Query("SELECT * FROM Area")
    val allArea: List<Area>

    @Query("SELECT * FROM Area WHERE area LIKE :title")
    fun getAllArea(title: String?): List<Area>?

    @Insert
    fun insertAllArea(task: Area)

    @Query("DELETE FROM Area")
    fun deleteArea()

    @Update
    fun updateArea(area: Area)

    //Country

    @Query("SELECT COUNT(*) FROM Country ORDER BY country ASC LIMIT 1")
    fun getCount(): LiveData<Int>

    @get:Query("SELECT * FROM Country")
    val allCountry: List<Country>

    @Query("SELECT * FROM Country WHERE country LIKE :country")
    fun findCountryById(country: String?): List<Country>

    @Insert
    fun insertAllCountry(country: Country)

    @Query("DELETE FROM Country")
    fun deleteCountry()

    @Update
    fun updateCountry(area: Country)

    //Employee
    @get:Query("SELECT * FROM Employee")
    val allEmployee: List<Employee>

    @Query("SELECT * FROM Employee WHERE area LIKE :area")
    fun findEmployeeById(area: String?): List<Employee>

    @Insert
    fun insertAllEmployee(employee: Employee)

    @Query("DELETE FROM Employee")
    fun deleteEmployee()

    @Update
    fun updateEmployee(employee: Employee)

    //Region
//Employee
    @get:Query("SELECT * FROM Region")
    val allRegion: List<Region>

    @Query("SELECT * FROM Region WHERE region LIKE :region")
    fun findRegionById(region: String?): List<Region>

    @Insert
    fun insertAllRegion(region: Region)

    @Query("DELETE FROM Region")
    fun deleteRegion()

    @Update
    fun updateRegion(region: Region)

    //Zone
    @get:Query("SELECT * FROM Zone")
    val allZone: List<Zone>

    @Query("SELECT * FROM Zone WHERE zone LIKE :zone")
    fun findZoneId(zone: String?): List<Zone>

    @Insert
    fun insertAllZone(zone: Zone)

    @Query("DELETE FROM Zone")
    fun deleteZone()

    @Update
    fun updateZone(zone: Zone)
}
