package `in`.rahultyagi.reports.adapter

import `in`.rahultyagi.reports.R
import `in`.rahultyagi.reports.model.Country
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import gr.escsoft.michaelprimez.searchablespinner.interfaces.ISpinnerSelectedView
import gr.escsoft.michaelprimez.searchablespinner.tools.UITools
import java.util.ArrayList


class CountryListAdapter(private val mContext: Context, private var mCountryList: List<Country>) :
    ArrayAdapter<Country>(mContext, R.layout.view_list_item), Filterable, ISpinnerSelectedView {
    private val mBackupList: List<Country>
    private val mStringFilter = StringFilter()

    init {
        mBackupList = mCountryList
    }

    override fun getCount(): Int {

        return if (mCountryList == null) 0 else mCountryList!!.size + 1
    }

    override fun getItem(position: Int): Country? {
        return if (mCountryList != null && position > 0) {

            mCountryList!![position - 1]
        } else {
            null
        }
    }

    override fun getItemId(position: Int): Long {

        return if (mCountryList == null && position > 0) {
            mCountryList!![position].hashCode().toLong()
        } else
            -1
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = null
        if (position == 0) {
            view = noSelectionView
        } else {
            view = View.inflate(mContext, R.layout.view_list_item, null)
            val letters = view.findViewById(R.id.ImgVw_Letters)
            val dispalyName = view.findViewById(R.id.TxtVw_DisplayName)
            val TxtVw_Terror = view.findViewById(R.id.TxtVw_Terror)

            letters.setImageDrawable(getTextDrawable(mCountryList!![position - 1].country))
            dispalyName.setText(mCountryList!![position - 1].country)
            TxtVw_Terror.setText(mCountryList!![position - 1].territory)
        }
        return view!!
    }

    override fun getSelectedView(position: Int): View? {
        var view: View? = null

        if (position == 0) {
            view = noSelectionView
        } else {
            view = View.inflate(mContext, R.layout.view_list_item, null)
            val letters = view.findViewById(R.id.ImgVw_Letters)
            val dispalyName = view.findViewById(R.id.TxtVw_DisplayName)
            val TxtVw_Terror = view.findViewById(R.id.TxtVw_Terror)
            letters.setImageDrawable(getTextDrawable(mCountryList!![position - 1].country))
            dispalyName.setText(mCountryList!![position - 1].country)
            TxtVw_Terror.setText(mCountryList!![position - 1].territory)
        }
        return view!!
    }

    override fun getNoSelectionView(): View {
        return View.inflate(mContext, R.layout.view_list_no_selection_item, null)
    }

    private fun getTextDrawable(displayName: String): TextDrawable? {
        var drawable: TextDrawable? = null
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

        override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
            val filterResults = Filter.FilterResults()
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

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            mCountryList = results.values as ArrayList<Country>
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }

        }
    }


}
