package fr.utc.assos.uvweb;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import fr.utc.assos.uvweb.data.UVwebContent;

import java.util.*;

/**
 * An adapter used all together with the {@link UVListFragment}'s ListView.
 * It relies on a standard ViewHolder pattern implemented in the {@link UVHolder} class and thus allows UVs recycling.
 */
public class UVAdapter extends BaseAdapter implements SectionIndexer {

    private static List<UVwebContent.UV> mUVs = Collections.emptyList();
    private Context mContext;
    private String mFormattedUVPattern;

    private HashMap<String, Integer> mAlphaIndexer;
    private String[] mSections;

    public UVAdapter(Context context) {
        this.mContext = context;
        //mFormattedUVPattern = context.getResources().getString(R.string.title_uv);
        mAlphaIndexer = new HashMap<String, Integer>();
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
        mUVs = UVs;
        for (int i = 0, l = UVs.size(); i < l; i++) {
            final UVwebContent.UV UV = UVs.get(i);
            final String s = UV.getLetterCode().substring(0, 1).toUpperCase();
            // HashMap will prevent duplicates
            if (!mAlphaIndexer.containsKey(s)) {
                mAlphaIndexer.put(s, i);
            }
        }

        // create a list from the set to sort
        ArrayList<String> sectionList = new ArrayList<String>(mAlphaIndexer.keySet());
        Collections.sort(sectionList);
        mSections = new String[sectionList.size()];
        sectionList.toArray(mSections);

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
        //String uvName = String.format(mFormattedUVPattern, "MT", "23");
        codeView.setText(Html.fromHtml("<font color='#000000'>" + UV.getLetterCode()  + "</font>" + UV.getNumberCode()));
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
}
