package `in`.rahultyagi.reports.adapter

import `in`.rahultyagi.reports.R
import `in`.rahultyagi.reports.model.Employee
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import gr.escsoft.michaelprimez.searchablespinner.interfaces.ISpinnerSelectedView
import gr.escsoft.michaelprimez.searchablespinner.tools.UITools
import java.util.*


class EmployeeListAdapter(
    private val mContext: Context,
    strings: List<Employee>
) :
    ArrayAdapter<Employee?>(mContext, R.layout.view_list_item), Filterable,
    ISpinnerSelectedView {
    private val mBackupArray: List<Employee>
    private var mEmployeeList: List<Employee>?
    private val mStringFilter = StringFilter()
    override fun getCount(): Int {
        return if (mEmployeeList == null) 0 else mEmployeeList!!.size + 1
    }

    override fun getItem(position: Int): Employee? {
        return if (mEmployeeList != null && position > 0) {
            mEmployeeList!![position - 1]
        } else {
            null
        }
    }

    override fun getItemId(position: Int): Long {
        return if (mEmployeeList == null && position > 0) {
            mEmployeeList!![position].hashCode().toLong()
        } else -1
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view: View?
        if (position == 0) {
            view = noSelectionView
        } else {
            view = View.inflate(mContext, R.layout.view_list_item, null)
            val letters =
                view.findViewById<View>(R.id.ImgVw_Letters) as ImageView
            val dispalyName =
                view.findViewById<View>(R.id.TxtVw_DisplayName) as TextView
            val displayTerritory =
                view.findViewById<View>(R.id.TxtVw_Terror) as TextView
            val displayEmpName =
                view.findViewById<View>(R.id.employeeNameId) as TextView
            displayEmpName.visibility = View.VISIBLE
            letters.setImageDrawable(getTextDrawable(mEmployeeList!![position - 1].name))
            dispalyName.text = mEmployeeList!![position - 1].area
            displayTerritory.text = mEmployeeList!![position - 1].territory
            displayEmpName.text = mEmployeeList!![position - 1].name
        }
        return view!!
    }

    override fun getSelectedView(position: Int): View {
        val view: View?

        if (position == 0) {
            view = noSelectionView
        } else {
            view = View.inflate(mContext, R.layout.view_list_item, null)
            val letters =
                view.findViewById<View>(R.id.ImgVw_Letters) as ImageView
            val dispalyName =
                view.findViewById<View>(R.id.TxtVw_DisplayName) as TextView
            val displayTerritory =
                view.findViewById<View>(R.id.TxtVw_Terror) as TextView
            val displayEmpName =
                view.findViewById<View>(R.id.employeeNameId) as TextView
            displayEmpName.visibility = View.VISIBLE
            letters.setImageDrawable(getTextDrawable(mEmployeeList!![position - 1].name))
            dispalyName.text = mEmployeeList!![position - 1].area
            displayTerritory.text = mEmployeeList!![position - 1].territory
            displayEmpName.text = mEmployeeList!![position - 1].name
        }
        return view!!
    }

    override fun getNoSelectionView(): View {
        return View.inflate(mContext, R.layout.view_list_no_selection_item, null)
    }

    private fun getTextDrawable(displayName: String): TextDrawable? {
        val drawable: TextDrawable?
        drawable = if (!TextUtils.isEmpty(displayName)) {
            val color2 = ColorGenerator.MATERIAL.getColor(displayName)
            TextDrawable.builder()
                .beginConfig()
                .width(UITools.dpToPx(mContext, 32f))
                .height(UITools.dpToPx(mContext, 32f))
                .textColor(Color.WHITE)
                .toUpperCase()
                .endConfig()
                .round()
                .build(displayName.substring(0, 1), color2)
        } else {
            TextDrawable.builder()
                .beginConfig()
                .width(UITools.dpToPx(mContext, 32f))
                .height(UITools.dpToPx(mContext, 32f))
                .endConfig()
                .round()
                .build("?", Color.GRAY)
        }
        return drawable
    }

    override fun getFilter(): Filter {
        return mStringFilter
    }

    inner class StringFilter : Filter() {
        @SuppressLint("DefaultLocale")
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filterResults = FilterResults()
            if (TextUtils.isEmpty(constraint)) {
                filterResults.count = mBackupArray.size
                filterResults.values = mBackupArray
                return filterResults
            }
            val filterStrings: MutableList<Employee> =
                ArrayList()
            for (text in mBackupArray) {
                if (text.name.toLowerCase().contains(constraint)) {
                    filterStrings.add(text)
                }
            }
            filterResults.count = filterStrings.size
            filterResults.values = filterStrings
            return filterResults
        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {
            mEmployeeList = results.values as ArrayList<Employee>
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }
    }



    init {
        mEmployeeList = strings
        mBackupArray = strings
    }
}
