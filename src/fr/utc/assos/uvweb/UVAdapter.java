package fr.utc.assos.uvweb;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;
import fr.utc.assos.uvweb.data.UVwebContent;
import fr.utc.assos.uvweb.holders.UVHeaderHolder;
import fr.utc.assos.uvweb.holders.UVHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * An adapter used all together with the {@link UVListFragment}'s ListView.
 * It relies on a standard ViewHolder pattern implemented in the {@link fr.utc.assos.uvweb.holders.UVHolder} class and thus allows UVs recycling.
 * It implements both SectionIndexer and StickyListHeadersAdapter interfaces
 */
public class UVAdapter extends BaseAdapter implements SectionIndexer, StickyListHeadersAdapter {

    private static List<UVwebContent.UV> mUVs = Collections.emptyList();
    private Context mContext;
    private HashMap<String, Integer> mAlphaIndexer;
    private String[] mSections;

    public UVAdapter(Context context) {
        this.mContext = context;
        this.mAlphaIndexer = new HashMap<String, Integer>();
    }

    @Override
    public int getCount() {
        return mUVs.size();
    }

    @Override
    public UVwebContent.UV getItem(int position) {
        return mUVs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateUVs(List<UVwebContent.UV> UVs) {
        ThreadPreconditions.checkOnMainThread();

        for (int i = 0, l = UVs.size(); i < l; i++) {
            final UVwebContent.UV UV = UVs.get(i);
            final String s = UV.getLetterCode().substring(0, 1).toUpperCase();

            // HashMap will prevent duplicates
            mAlphaIndexer.put(s, i);
        }

        // create a list from the set to sort
        ArrayList<String> sectionList = new ArrayList<String>(mAlphaIndexer.keySet());
        Collections.sort(sectionList);
        mSections = new String[sectionList.size()];
        sectionList.toArray(mSections);

        mUVs = UVs;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.uv, null);
        }

        TextView codeView = UVHolder.get(convertView, R.id.uvcode);
        TextView rateView = UVHolder.get(convertView, R.id.rate);
        TextView descView = UVHolder.get(convertView, R.id.desc);

        UVwebContent.UV UV = getItem(position);
        codeView.setText(Html.fromHtml(String.format(UVwebContent.UV_TITLE_FORMAT, UV.getLetterCode(), UV.getNumberCode())));
        descView.setText(UV.getDescription());
        rateView.setText(UV.getFormattedRate());

        return convertView;
    }

    /**
     * SectionIndexer interface's methods
     */
    @Override
    public int getSectionForPosition(int position) {
         return 1;
    }

    @Override
    public int getPositionForSection(int position) {
        return mAlphaIndexer.get(mSections[position]);
    }

    @Override
    public Object[] getSections() {
        return mSections;
    }

    /**
     * StickyListHeadersAdapter interface's methods
     */
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.header_uv_list, null);
        }

        // Set header text as first char in name
        char headerChar = mUVs.get(position).getLetterCode().subSequence(0, 1).charAt(0);
        UVHeaderHolder.get(convertView).setText(String.valueOf(headerChar));

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return mUVs.get(position).getLetterCode().subSequence(0, 1).charAt(0);
    }
}
