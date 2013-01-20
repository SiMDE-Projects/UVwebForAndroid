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

    private List<UVwebContent.UV> mUVs = Collections.emptyList();
    private Context mContext;
    private HashMap<String, Integer> mSectionToPosition = new HashMap<String, Integer>();
    private HashMap<Integer, String> mSectionHeaderPosition = new HashMap<Integer, String>(); // TODO: int[] ?
    private String[] mSections; // TODO: Character[] ?

    public UVAdapter(Context context) {
        mContext = context;
        mSectionToPosition = new HashMap<String, Integer>();
        mSectionHeaderPosition = new HashMap<Integer, String>();
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

        int i = 0;
        for (UVwebContent.UV UV : UVs) {
            final String s = UV.getLetterCode().substring(0, 1).toUpperCase();
            if (!mSectionToPosition.containsKey(s)) {
                mSectionToPosition.put(s, i);
            }
            mSectionHeaderPosition.put(i, s);
            i++;
        }

        // create a list from the set to sort
        ArrayList<String> sectionList = new ArrayList<String>(mSectionToPosition.keySet());
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
    public int getPositionForSection(int section) {
        return mSectionToPosition.get(mSections[Math.min(section, mSections.length - 1)]);
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
        UVHeaderHolder.get(convertView).setText(String.valueOf(getSectionName(position)));

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return getSectionName(position);
    }

    /**
     * @param position the position of a given item in the ListView
     * @return the name of the corresponding section
     */
    private char getSectionName(int position) {
        return mSectionHeaderPosition.get(position).charAt(0);
    }
}
