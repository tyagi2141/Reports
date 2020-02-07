package `in`.rahultyagi.reports


import `in`.rahultyagi.reports.adapter.*
import `in`.rahultyagi.reports.helper.APIClient
import `in`.rahultyagi.reports.helper.APIInterface
import `in`.rahultyagi.reports.helper.DatabaseClient
import `in`.rahultyagi.reports.model.*
import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner
import gr.escsoft.michaelprimez.searchablespinner.interfaces.IStatusListener
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    var apiInterface: APIInterface? = null
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiInterface = APIClient.client?.create(APIInterface::class.java)
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
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val call = apiInterface!!.doGetListResources()
        call.enqueue(object : Callback<Result> {
            override fun onResponse(
                call: Call<Result>,
                response: Response<Result>
            ) {
                clearDatabase()
                val st = SaveTask()
                st.execute(response.body().responseData)
                country
            }

            override fun onFailure(
                call: Call<Result>,
                t: Throwable
            ) {
            }
        })

        getCount().observe(this,
            Observer { integer ->
                if (integer > 0) {
                    country
                    CountrySpinner!!.visibility = View.VISIBLE
                }
            })


    }


    private fun getCount(): LiveData<Int> {
        return DatabaseClient.getInstance(applicationContext)?.appDatabase
            ?.dataDao()!!.getCount()
    }


    @SuppressLint("StaticFieldLeak")
    internal inner class SaveTask :
        AsyncTask<ResponseData?, Void?, Void?>() {
        override fun doInBackground(vararg params: ResponseData?): Void? {
            val responseData = params[0]
            for (area in responseData?.area!!) {
                DatabaseClient.getInstance(applicationContext)?.appDatabase
                    ?.dataDao()
                    ?.insertAllArea(area)
            }
            for (country in responseData.country!!) {
                DatabaseClient.getInstance(applicationContext)?.appDatabase
                    ?.dataDao()
                    ?.insertAllCountry(country)
            }
            for (employee in responseData.employee!!) {
                DatabaseClient.getInstance(applicationContext)?.appDatabase
                    ?.dataDao()
                    ?.insertAllEmployee(employee)
            }
            for (region in responseData.region!!) {
                DatabaseClient.getInstance(applicationContext)?.appDatabase
                    ?.dataDao()
                    ?.insertAllRegion(region)
            }
            for (zone in responseData.zone!!) {
                DatabaseClient.getInstance(applicationContext)?.appDatabase
                    ?.dataDao()
                    ?.insertAllZone(zone)
            }
            return null
        }
    }

    private val country: Unit
        get() {
            class GetCountry :
                AsyncTask<Void?, Void?, List<Country>>() {
                override fun doInBackground(vararg params: Void?): List<Country>? {
                    return DatabaseClient
                        .getInstance(applicationContext)
                        ?.appDatabase
                        ?.dataDao()
                        ?.allCountry
                }

                override fun onPostExecute(tasks: List<Country>) {
                    super.onPostExecute(tasks)
                    countryAdapter = CountryListAdapter(applicationContext, tasks)
                    CountrySpinner!!.setAdapter(countryAdapter)
                    CountrySpinner!!.setStatusListener(object : IStatusListener {
                        override fun spinnerIsOpening() {
                            hideSpinnerView()
                        }

                        override fun spinnerIsClosing() {}
                    })
                }
            }

            val gt = GetCountry()
            gt.execute()
        }

    private val area: Unit
        get() {
            class GetTasks :
                AsyncTask<Void?, Void?, List<Area>>() {
                override fun doInBackground(vararg params: Void?): List<Area>? {
                    return DatabaseClient
                        .getInstance(applicationContext)
                        ?.appDatabase
                        ?.dataDao()
                        ?.allArea
                }

                override fun onPostExecute(areas: List<Area>) {
                    super.onPostExecute(areas)
                    AreaListAdapter = AreaListAdapter(applicationContext, areas)
                    AreaSpinner!!.setAdapter(AreaListAdapter)
                    AreaSpinner!!.setStatusListener(object : IStatusListener {
                        override fun spinnerIsOpening() {
                            hideSpinnerView()
                        }

                        override fun spinnerIsClosing() {}
                    })
                }
            }

            val gt = GetTasks()
            gt.execute()
        }

    private fun getEmployee(area: String) {
        class GetEmployee :
            AsyncTask<Void?, Void?, List<Employee>>() {
            override fun doInBackground(vararg params: Void?): List<Employee>? {
                return DatabaseClient
                    .getInstance(applicationContext)
                    ?.appDatabase
                    ?.dataDao()
                    ?.findEmployeeById(area)
            }

            override fun onPostExecute(tasks: List<Employee>) {
                super.onPostExecute(tasks)
                employeeAdapter = EmployeeListAdapter(applicationContext, tasks)
                EmployeeSpinner!!.setAdapter(employeeAdapter)
                EmployeeSpinner!!.setStatusListener(object : IStatusListener {
                    override fun spinnerIsOpening() {
                        hideSpinnerView()
                    }

                    override fun spinnerIsClosing() {}
                })
            }
        }

        val gt = GetEmployee()
        gt.execute()
    }

    private val region: Unit
        get() {
            class GetEmployee :
                AsyncTask<Void?, Void?, List<Region>>() {
                override fun doInBackground(vararg params: Void?): List<Region> {
                    return DatabaseClient
                        .getInstance(applicationContext)
                        ?.appDatabase
                        ?.dataDao()!!.allRegion
                }

                override fun onPostExecute(tasks: List<Region>) {
                    super.onPostExecute(tasks)
                    RegionListAdapters = RegionListAdapter(applicationContext, tasks)
                    RegionSpinner!!.setAdapter(RegionListAdapters)
                    RegionSpinner!!.setStatusListener(object : IStatusListener {
                        override fun spinnerIsOpening() {
                            hideSpinnerView()
                        }

                        override fun spinnerIsClosing() {}
                    })
                }
            }

            val ge = GetEmployee()
            ge.execute()
        }

    private val zone: Unit
        get() {
            class GetEmployee :
                AsyncTask<Void, Void, List<Zone>>() {
                override fun doInBackground(vararg params: Void?): List<Zone> {
                    return DatabaseClient
                        .getInstance(applicationContext)
                        ?.appDatabase
                        ?.dataDao()
                        ?.allZone!!
                }

                override fun onPostExecute(tasks: List<Zone>) {
                    super.onPostExecute(tasks)
                    ZoneListAdapter = ZoneListAdapter(applicationContext, tasks)
                    ZoneSpinner!!.setAdapter(ZoneListAdapter)
                    ZoneSpinner!!.setStatusListener(object : IStatusListener {
                        override fun spinnerIsOpening() {
                            hideSpinnerView()
                        }

                        override fun spinnerIsClosing() {}
                    })
                }
            }

            val gt = GetEmployee()
            gt.execute()
        }

    fun clearDatabase() {
        DatabaseClient.getInstance(applicationContext)?.appDatabase?.dataDao()?.deleteArea()
        DatabaseClient.getInstance(applicationContext)?.appDatabase?.dataDao()
            ?.deleteCountry()
        DatabaseClient.getInstance(applicationContext)?.appDatabase?.dataDao()
            ?.deleteEmployee()
        DatabaseClient.getInstance(applicationContext)?.appDatabase?.dataDao()
            ?.deleteRegion()
        DatabaseClient.getInstance(applicationContext)?.appDatabase?.dataDao()?.deleteZone()
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

    private val mOnItemSelectedListenerCountry: OnItemSelectedListener =
        object : OnItemSelectedListener {
            override fun onItemSelected(
                view: View,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    ZoneSpinner!!.visibility = View.GONE
                    AreaSpinner!!.visibility = View.GONE
                    RegionSpinner!!.visibility = View.GONE
                    EmployeeSpinner!!.visibility = View.GONE

                } else {
                    ZoneSpinner!!.visibility = View.VISIBLE
                    zone
                }
            }

            override fun onNothingSelected() {
                Toast.makeText(this@MainActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
            }
        }
    private val mOnItemSelectedListenerArea: OnItemSelectedListener =
        object : OnItemSelectedListener {
            override fun onItemSelected(
                view: View,
                position: Int,
                id: Long
            ) {
                val area = AreaListAdapter!!.getItem(position)
                if (position == 0) {
                    EmployeeSpinner!!.visibility = View.GONE

                } else {
                    EmployeeSpinner!!.visibility = View.VISIBLE
                    getEmployee(area!!.area)
                }
            }

            override fun onNothingSelected() {
                Toast.makeText(this@MainActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
            }
        }
    private val mOnItemSelectedListenerRegion: OnItemSelectedListener =
        object : OnItemSelectedListener {
            override fun onItemSelected(
                view: View,
                position: Int,
                id: Long
            ) {
                val region = RegionListAdapters!!.getItem(position)
                if (position == 0) {
                    AreaSpinner!!.visibility = View.GONE
                    EmployeeSpinner!!.visibility = View.GONE
                } else {
                    AreaSpinner!!.visibility = View.VISIBLE
                    area
                }
            }

            override fun onNothingSelected() {
                Toast.makeText(this@MainActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
            }
        }
    private val mOnItemSelectedListenerZone: OnItemSelectedListener =
        object : OnItemSelectedListener {
            override fun onItemSelected(
                view: View,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    AreaSpinner!!.visibility = View.GONE
                    RegionSpinner!!.visibility = View.GONE
                    EmployeeSpinner!!.visibility = View.GONE
                } else {
                    RegionSpinner!!.visibility = View.VISIBLE
                    region
                }
            }

            override fun onNothingSelected() {
                Toast.makeText(this@MainActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
            }
        }
    private val mOnItemSelectedListenerEmployee: OnItemSelectedListener =
        object : OnItemSelectedListener {
            override fun onItemSelected(
                view: View,
                position: Int,
                id: Long
            ) {
                val area = employeeAdapter!!.getItem(position)
            }

            override fun onNothingSelected() {
                Toast.makeText(this@MainActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
            }
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
