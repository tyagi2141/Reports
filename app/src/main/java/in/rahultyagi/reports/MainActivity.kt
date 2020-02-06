package `in`.rahultyagi.reports

import `in`.rahultyagi.reports.adapter.AreaListAdapter
import `in`.rahultyagi.reports.model.*
import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.os.AsyncTask
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner
import gr.escsoft.michaelprimez.searchablespinner.interfaces.IStatusListener
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener
import rahultyag.`in`.javanestedexample.adapter.AreaListAdapter
import rahultyag.`in`.javanestedexample.adapter.CountryListAdapter
import rahultyag.`in`.javanestedexample.adapter.EmployeeListAdapter
import rahultyag.`in`.javanestedexample.adapter.RegionListAdapter
import rahultyag.`in`.javanestedexample.adapter.ZoneListAdapter
import rahultyag.`in`.javanestedexample.model.Area
import rahultyag.`in`.javanestedexample.model.Country
import rahultyag.`in`.javanestedexample.model.Employee
import rahultyag.`in`.javanestedexample.model.Example
import rahultyag.`in`.javanestedexample.model.Region
import rahultyag.`in`.javanestedexample.model.ResponseData
import rahultyag.`in`.javanestedexample.model.Zone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    internal var apiInterface: APIInterface

    private var CountrySpinner: SearchableSpinner? = null
    private var AreaSpinner: SearchableSpinner? = null
    private var ZoneSpinner: SearchableSpinner? = null
    private var EmployeeSpinner: SearchableSpinner? = null
    private var RegionSpinner: SearchableSpinner? = null

    private var employeeAdapter: EmployeeListAdapter? = null
    private var countryAdapter: CountryListAdapter? = null
    private var AreaListAdapter: AreaListAdapter? = null
    private var ZoneListAdapter: ZoneListAdapter? = null
    private var RegionListAdapters: RegionListAdapter? = null

    private val mOnItemSelectedListenerCountry = object : OnItemSelectedListener {
        override fun onItemSelected(view: View, position: Int, id: Long) {
            if (position == 0) {
                ZoneSpinner!!.visibility = View.GONE
                AreaSpinner!!.visibility = View.GONE
                RegionSpinner!!.visibility = View.GONE
                EmployeeSpinner!!.visibility = View.GONE
            } else {
                ZoneSpinner!!.visibility = View.VISIBLE
                getZone()
            }

        }

        override fun onNothingSelected() {
            Toast.makeText(this@MainActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
        }
    }


    private val mOnItemSelectedListenerArea = object : OnItemSelectedListener {
        override fun onItemSelected(view: View, position: Int, id: Long) {
            val area = AreaListAdapter!!.getItem(position)

            if (position == 0) {
                EmployeeSpinner!!.visibility = View.GONE

            } else {
                EmployeeSpinner!!.visibility = View.VISIBLE
                getEmployee(area.getArea())
            }
        }

        override fun onNothingSelected() {
            Toast.makeText(this@MainActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
        }
    }
    private val mOnItemSelectedListenerRegion = object : OnItemSelectedListener {
        override fun onItemSelected(view: View, position: Int, id: Long) {
            if (position == 0) {
                AreaSpinner!!.visibility = View.GONE
                EmployeeSpinner!!.visibility = View.GONE
            } else {
                AreaSpinner!!.visibility = View.VISIBLE
                getArea()
            }

        }

        override fun onNothingSelected() {
            Toast.makeText(this@MainActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
        }
    }
    private val mOnItemSelectedListenerZone = object : OnItemSelectedListener {
        override fun onItemSelected(view: View, position: Int, id: Long) {
            if (position == 0) {
                AreaSpinner!!.visibility = View.GONE
                RegionSpinner!!.visibility = View.GONE
                EmployeeSpinner!!.visibility = View.GONE

            } else {
                RegionSpinner!!.visibility = View.VISIBLE
                getRegion()
            }

        }

        override fun onNothingSelected() {
            Toast.makeText(this@MainActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
        }
    }
    private val mOnItemSelectedListenerEmployee = object : OnItemSelectedListener {
        override fun onItemSelected(view: View, position: Int, id: Long) {

        }

        override fun onNothingSelected() {
            Toast.makeText(this@MainActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiInterface = APIClient.getClient().create(APIInterface::class.java)
        EmployeeSpinner = findViewById(R.id.EmployeeSpinnerId)
        RegionSpinner = findViewById(R.id.RegionAapterId)
        ZoneSpinner = findViewById(R.id.ZoneSpinnerId)
        AreaSpinner = findViewById(R.id.AreaSpinnerId)
        CountrySpinner = findViewById(R.id.CountrySpinnerId)

        CountrySpinner!!.setOnItemSelectedListener(mOnItemSelectedListenerCountry)
        EmployeeSpinner!!.setOnItemSelectedListener(mOnItemSelectedListenerEmployee)
        AreaSpinner!!.setOnItemSelectedListener(mOnItemSelectedListenerArea)
        ZoneSpinner!!.setOnItemSelectedListener(mOnItemSelectedListenerZone)
        RegionSpinner!!.setOnItemSelectedListener(mOnItemSelectedListenerRegion)

        val toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val call = apiInterface.doGetListResources()
        call.enqueue(object : Callback<Example> {
            override fun onResponse(call: Call<Example>, response: Response<Example>) {
                clearDatabase()

                val st = SaveData()
                st.execute(response.body().getResponseData())

            }

            override fun onFailure(call: Call<Example>, t: Throwable) {

            }
        })

        getCountry()
    }

    internal inner class SaveData : AsyncTask<ResponseData, Void, Void>() {

        override fun doInBackground(vararg voids: ResponseData): Void? {

            val responseData = voids[0]

            for (area in responseData.getArea()) {
                DatabaseClient.getInstance(applicationContext).getAppDatabase()
                    .dataDao()
                    .insertAllArea(area)
            }
            for (country in responseData.getCountry()) {
                DatabaseClient.getInstance(applicationContext).getAppDatabase()
                    .dataDao()
                    .insertAllCountry(country)
            }
            for (employee in responseData.getEmployee()) {
                DatabaseClient.getInstance(applicationContext).getAppDatabase()
                    .dataDao()
                    .insertAllEmployee(employee)
            }
            for (region in responseData.getRegion()) {
                DatabaseClient.getInstance(applicationContext).getAppDatabase()
                    .dataDao()
                    .insertAllRegion(region)
            }
            for (zone in responseData.getZone()) {
                DatabaseClient.getInstance(applicationContext).getAppDatabase()
                    .dataDao()
                    .insertAllZone(zone)
            }

            return null
        }
    }

    private fun getCountry() {
        class GetCountry : AsyncTask<Void, Void, List<Country>>() {

            override fun doInBackground(vararg voids: Void): List<Country> {

                return DatabaseClient
                    .getInstance(applicationContext)
                    .getAppDatabase()
                    .dataDao()
                    .getAllCountry()
            }

            override fun onPostExecute(tasks: List<Country>) {
                super.onPostExecute(tasks)
                countryAdapter = CountryListAdapter(applicationContext, tasks)
                CountrySpinner!!.setAdapter(countryAdapter!!)

                CountrySpinner!!.setStatusListener(object : IStatusListener {
                    override fun spinnerIsOpening() {
                        hideSpinnerView()
                    }

                    override fun spinnerIsClosing() {

                    }
                })

            }
        }

        val gt = GetCountry()
        gt.execute()
    }


    private fun getArea() {
        class GetTasks : AsyncTask<Void, Void, List<Camera.Area>>() {

            override fun doInBackground(vararg voids: Void): List<Camera.Area> {

                return DatabaseClient
                    .getInstance(applicationContext)
                    .getAppDatabase()
                    .dataDao()
                    .getAllArea()
            }

            override fun onPostExecute(areas: List<Camera.Area>) {
                super.onPostExecute(areas)
                AreaListAdapter = AreaListAdapter(applicationContext, areas)

                AreaSpinner!!.setAdapter(AreaListAdapter!!)
                AreaSpinner!!.setStatusListener(object : IStatusListener {
                    override fun spinnerIsOpening() {
                        hideSpinnerView()
                    }

                    override fun spinnerIsClosing() {

                    }
                })

            }
        }

        val gt = GetTasks()
        gt.execute()
    }

    private fun getEmployee(area: String) {
        class GetEmployee : AsyncTask<Void, Void, List<Employee>>() {

            override fun doInBackground(vararg voids: Void): List<Employee> {

                return DatabaseClient
                    .getInstance(applicationContext)
                    .getAppDatabase()
                    .dataDao()
                    .findEmployeeById(area)
            }

            override fun onPostExecute(employees: List<Employee>) {
                super.onPostExecute(employees)
                employeeAdapter = EmployeeListAdapter(applicationContext, employees)

                EmployeeSpinner!!.setAdapter(employeeAdapter!!)
                EmployeeSpinner!!.setStatusListener(object : IStatusListener {
                    override fun spinnerIsOpening() {
                        hideSpinnerView()
                    }

                    override fun spinnerIsClosing() {

                    }
                })
            }
        }

        val gt = GetEmployee()
        gt.execute()
    }

    private fun getRegion() {
        class GetEmployee : AsyncTask<Void, Void, List<Region>>() {

            override fun doInBackground(vararg voids: Void): List<Region> {

                return DatabaseClient
                    .getInstance(applicationContext)
                    .getAppDatabase()
                    .dataDao()
                    .getAllRegion()
            }

            override fun onPostExecute(regions: List<Region>) {
                super.onPostExecute(regions)
                RegionListAdapters = RegionListAdapter(applicationContext, regions)

                RegionSpinner!!.setAdapter(RegionListAdapters!!)
                RegionSpinner!!.setStatusListener(object : IStatusListener {
                    override fun spinnerIsOpening() {
                        hideSpinnerView()
                    }

                    override fun spinnerIsClosing() {

                    }
                })
            }
        }

        val gt = GetEmployee()
        gt.execute()
    }

    private fun getZone() {
        class GetEmployee : AsyncTask<Void, Void, List<Zone>>() {

            override fun doInBackground(vararg voids: Void): List<Zone> {
                return DatabaseClient
                    .getInstance(applicationContext)
                    .getAppDatabase()
                    .dataDao()
                    .getAllZone()
            }

            override fun onPostExecute(zones: List<Zone>) {
                super.onPostExecute(zones)
                ZoneListAdapter = ZoneListAdapter(applicationContext, zones)

                ZoneSpinner!!.setAdapter(ZoneListAdapter!!)
                ZoneSpinner!!.setStatusListener(object : IStatusListener {
                    override fun spinnerIsOpening() {
                        hideSpinnerView()
                    }

                    override fun spinnerIsClosing() {

                    }
                })

            }
        }

        val gt = GetEmployee()
        gt.execute()
    }

    fun clearDatabase() {
        DatabaseClient.getInstance(applicationContext).getAppDatabase().dataDao().deleteArea()
        DatabaseClient.getInstance(applicationContext).getAppDatabase().dataDao().deleteCountry()
        DatabaseClient.getInstance(applicationContext).getAppDatabase().dataDao().deleteEmployee()
        DatabaseClient.getInstance(applicationContext).getAppDatabase().dataDao().deleteRegion()
        DatabaseClient.getInstance(applicationContext).getAppDatabase().dataDao().deleteZone()

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!CountrySpinner!!.isInsideSearchEditText(event)) {
            CountrySpinner!!.hideEdit()
        }
        if (!ZoneSpinner!!.isInsideSearchEditText(event)) {
            ZoneSpinner!!.hideEdit()
        }
        if (!EmployeeSpinner!!.isInsideSearchEditText(event)) {
            EmployeeSpinner!!.hideEdit()
        }
        if (!AreaSpinner!!.isInsideSearchEditText(event)) {
            AreaSpinner!!.hideEdit()
        }
        if (!RegionSpinner!!.isInsideSearchEditText(event)) {
            RegionSpinner!!.hideEdit()
        }
        return super.onTouchEvent(event)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId


        if (id == R.id.action_reset) {
            CountrySpinner!!.setSelectedItem(0)
            ZoneSpinner!!.setSelectedItem(0)
            EmployeeSpinner!!.setSelectedItem(0)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun hideSpinnerView() {
        CountrySpinner!!.hideEdit()
        EmployeeSpinner!!.hideEdit()
        AreaSpinner!!.hideEdit()
        RegionSpinner!!.hideEdit()
        ZoneSpinner!!.hideEdit()
    }

}