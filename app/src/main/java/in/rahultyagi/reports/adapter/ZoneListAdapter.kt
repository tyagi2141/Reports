package `in`.rahultyagi.reports.adapter

import `in`.rahultyagi.reports.R
import `in`.rahultyagi.reports.model.Zone
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import gr.escsoft.michaelprimez.searchablespinner.interfaces.ISpinnerSelectedView
import gr.escsoft.michaelprimez.searchablespinner.tools.UITools
import java.util.*


class ZoneListAdapter(
    private val mContext: Context,
    strings: List<Zone>
) :
    ArrayAdapter<Zone?>(mContext, R.layout.view_list_item), Filterable,
    ISpinnerSelectedView {
    private val mBackupArray: List<Zone>
    private var mZoneList: List<Zone>?
    private val mStringFilter = StringFilter()
    override fun getCount(): Int {
        return if (mZoneList == null) 0 else mZoneList!!.size + 1
    }

    override fun getItem(position: Int): Zone? {
        return if (mZoneList != null && position > 0) {
            mZoneList!![position - 1]
        } else {
            null
        }
    }

    override fun getItemId(position: Int): Long {
        return if (mZoneList == null && position > 0) {
            mZoneList!![position].hashCode().toLong()
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
            val displayName =
                view.findViewById<View>(R.id.TxtVw_DisplayName) as TextView
            val displayTerritory =
                view.findViewById<View>(R.id.TxtVw_Terror) as TextView
            letters.setImageDrawable(getTextDrawable(mZoneList!![position - 1].zone))
            displayName.text = mZoneList!![position - 1].zone
            displayTerritory.text = mZoneList!![position - 1].territory
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
            val displayName =
                view.findViewById<View>(R.id.TxtVw_DisplayName) as TextView
            val displayTerritory =
                view.findViewById<View>(R.id.TxtVw_Terror) as TextView
            letters.setImageDrawable(getTextDrawable(mZoneList!![position - 1].zone))
            displayName.text = mZoneList!![position - 1].zone
            displayTerritory.text = mZoneList!![position - 1].territory
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
            val filterStrings: MutableList<Zone> = ArrayList()
            for (text in mBackupArray) {
                if (text.zone.toLowerCase().contains(constraint)) {
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
            mZoneList = results.values as ArrayList<Zone>
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }
    }


    init {
        mZoneList = strings
        mBackupArray = strings
    }
}
