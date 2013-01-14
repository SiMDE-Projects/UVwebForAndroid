package fr.utc.assos.uvweb;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fr.utc.assos.uvweb.data.UVwebContent;

import java.util.Collections;
import java.util.List;

/**
 * An adapter used all together with the {@link UVListFragment}'s ListView.
 * It relies on a standard ViewHolder pattern implemented in the {@link UVHolder} class and thus allows UVs recycling.
 */
public class UVAdapter extends BaseAdapter {

    private static List<UVwebContent.UV> mUVs = Collections.emptyList();
    private Context mContext;
    private String mFormattedUVPattern;

    public UVAdapter(Context context) {
        this.mContext = context;
        //mFormattedUVPattern = context.getResources().getString(R.string.title_uv);
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
}
