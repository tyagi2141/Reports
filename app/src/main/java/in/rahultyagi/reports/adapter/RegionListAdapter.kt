package `in`.rahultyagi.reports.adapter

import `in`.rahultyagi.reports.R
import `in`.rahultyagi.reports.model.Region
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


class RegionListAdapter(
    private val mContext: Context,
    strings: List<Region>
) :
    ArrayAdapter<Region?>(mContext, R.layout.view_list_item), Filterable,
    ISpinnerSelectedView {
    private val mBackupArray: List<Region>
    private var mRegionList: List<Region>?
    private val mStringFilter = StringFilter()
    override fun getCount(): Int {
        return if (mRegionList == null) 0 else mRegionList!!.size + 1
    }

    override fun getItem(position: Int): Region? { // Region area=mRegionList.get(position);
        return if (mRegionList != null && position > 0) {
            mRegionList!![position - 1]
        } else {
            null
        }
    }

    override fun getItemId(position: Int): Long {
        return if (mRegionList == null && position > 0) { // Region area=mRegionList.get(position);
            mRegionList!![position].hashCode().toLong()
        } else -1
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var view: View? = null
        //  Region area=mRegionList.get(position);
        if (position == 0) {
            view = noSelectionView
        } else {
            view = View.inflate(mContext, R.layout.view_list_item, null)
            val letters =
                view.findViewById<View>(R.id.ImgVw_Letters) as ImageView
            val dispalyName =
                view.findViewById<View>(R.id.TxtVw_DisplayName) as TextView
            val TxtVw_Terror =
                view.findViewById<View>(R.id.TxtVw_Terror) as TextView
            Log.e("jiijijijij", "" + mRegionList!!.size)
            letters.setImageDrawable(getTextDrawable(mRegionList!![position - 1].region))
            dispalyName.setText(mRegionList!![position - 1].region)
            TxtVw_Terror.setText(mRegionList!![position - 1].territory)
        }
        return view!!
    }

    override fun getSelectedView(position: Int): View {
        var view: View? = null
        // Region area=mRegionList.get(position);
        Log.e("yfyffyfyfyf", mRegionList.toString())
        if (position == 0) {
            view = noSelectionView
        } else {
            view = View.inflate(mContext, R.layout.view_list_item, null)
            val letters =
                view.findViewById<View>(R.id.ImgVw_Letters) as ImageView
            val dispalyName =
                view.findViewById<View>(R.id.TxtVw_DisplayName) as TextView
            val TxtVw_Terror =
                view.findViewById<View>(R.id.TxtVw_Terror) as TextView
            letters.setImageDrawable(getTextDrawable(mRegionList!![position - 1].region))
            dispalyName.setText(mRegionList!![position - 1].region)
            TxtVw_Terror.setText(mRegionList!![position - 1].territory)
        }
        return view!!
    }

    override fun getNoSelectionView(): View {
        return View.inflate(mContext, R.layout.view_list_no_selection_item, null)
    }

    private fun getTextDrawable(displayName: String): TextDrawable? {
        var drawable: TextDrawable? = null
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
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filterResults = FilterResults()
            if (TextUtils.isEmpty(constraint)) {
                filterResults.count = mBackupArray.size
                filterResults.values = mBackupArray
                return filterResults
            }
            val filterStrings: MutableList<Region> =
                ArrayList<Region>()
            for (text in mBackupArray) {
                if (text.region.toLowerCase().contains(constraint)) {
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
            mRegionList = results.values as ArrayList<Region>
            Log.e("jhbjhbjhbjhb", mRegionList.toString() + "\n" + results.values)
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }
    }

    private inner class ItemView {
        var mImageView: ImageView? = null
        var mTextView: TextView? = null
    }

    enum class ItemViewType {
        ITEM, NO_SELECTION_ITEM
    }

    init {
        mRegionList = strings
        mBackupArray = strings
    }
}
