package `in`.rahultyagi.reports.adapter

import `in`.rahultyagi.reports.R
import `in`.rahultyagi.reports.model.Country
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
import java.util.ArrayList


class CountryListAdapter(private val mContext: Context, private var mCountryList: List<Country>) :
    ArrayAdapter<Country>(mContext, R.layout.view_list_item), Filterable, ISpinnerSelectedView {
    private val mBackupList: List<Country> = mCountryList
    private val mStringFilter = StringFilter()

    override fun getCount(): Int {

        return mCountryList.size + 1
    }

    override fun getItem(position: Int): Country? {
        return if (position > 0) {

            mCountryList[position - 1]
        } else {
            null
        }
    }

    override fun getItemId(position: Int): Long {

        return -1
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        if (position == 0) {
            view = noSelectionView
        } else {
            view = View.inflate(mContext, R.layout.view_list_item, null)
            val letters = view.findViewById(R.id.ImgVw_Letters)  as ImageView
            val dispalyName = view.findViewById(R.id.TxtVw_DisplayName) as TextView
            val displayTerritory = view.findViewById(R.id.TxtVw_Terror)as TextView


            letters.setImageDrawable(getTextDrawable(mCountryList!![position - 1].country))
            dispalyName.text = mCountryList[position - 1].country
            displayTerritory.text = mCountryList[position - 1].territory
        }
        return view!!
    }

    override fun getSelectedView(position: Int): View? {
        val view: View?

        if (position == 0) {
            view = noSelectionView
        } else {
            view = View.inflate(mContext, R.layout.view_list_item, null)
            val letters = view.findViewById(R.id.ImgVw_Letters)  as ImageView
            val dispalyName = view.findViewById(R.id.TxtVw_DisplayName) as TextView
            val displayTerritory = view.findViewById(R.id.TxtVw_Terror)as TextView

            letters.setImageDrawable(getTextDrawable(mCountryList[position - 1].country))
            dispalyName.text = mCountryList[position - 1].country
            displayTerritory.text = mCountryList[position - 1].territory
        }
        return view!!
    }

    override fun getNoSelectionView(): View {
        return View.inflate(mContext, R.layout.view_list_no_selection_item, null)
    }

    private fun getTextDrawable(displayName: String): TextDrawable? {
        val drawable: TextDrawable?
        if (!TextUtils.isEmpty(displayName)) {
            val color2 = ColorGenerator.MATERIAL.getColor(displayName)
            drawable = TextDrawable.builder()
                .beginConfig()
                .width(UITools.dpToPx(mContext, 32f))
                .height(UITools.dpToPx(mContext, 32f))
                .textColor(Color.WHITE)
                .toUpperCase()
                .endConfig()
                .round()
                .build(displayName.substring(0, 1), color2)
        } else {
            drawable = TextDrawable.builder()
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
                filterResults.count = mBackupList.size
                filterResults.values = mBackupList
                return filterResults
            }
            val filterStrings = ArrayList<Country>()
            for (text in mBackupList) {
                if (text.country.toLowerCase().contains(constraint)) {
                    filterStrings.add(text)
                }
            }
            filterResults.count = filterStrings.size
            filterResults.values = filterStrings
            return filterResults
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            mCountryList = results.values as ArrayList<Country>
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }

        }
    }


}
