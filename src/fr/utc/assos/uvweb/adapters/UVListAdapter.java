package fr.utc.assos.uvweb.adapters;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;
import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.ThreadPreconditions;
import fr.utc.assos.uvweb.data.UVwebContent;
import fr.utc.assos.uvweb.holders.UVwebHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * An adapter used all together with the {@link fr.utc.assos.uvweb.UVListFragment}'s ListView.
 * It relies on a standard ViewHolder pattern implemented in the {@link fr.utc.assos.uvweb.holders.UVwebHolder} class and thus allows UVs recycling.
 * It implements both SectionIndexer and StickyListHeadersAdapter interfaces
 */
public class UVListAdapter extends UVAdapter implements SectionIndexer, StickyListHeadersAdapter {
	private static final String TAG = "UVListAdapter";

	private List<UVwebContent.UV> mUVs = Collections.emptyList();
	/**
	 * Used to dynamically build the sections array
	 */
	private HashMap<Character, Integer> mSectionToPosition = new HashMap<Character, Integer>();
	/**
	 * Used to keep track the
	 */
	private SparseArray<Character> mSectionPosition;
	private Character[] mSections;

	public UVListAdapter(Context context) {
		super(context);
	}

	@Override
	public int getCount() {
		return mUVs.size();
	}

	@Override
	public UVwebContent.UV getItem(int position) {
		return mUVs.get(position);
	}

	public void updateUVs(List<UVwebContent.UV> UVs) {
		ThreadPreconditions.checkOnMainThread();

		mSectionPosition = new SparseArray<Character>(UVs.size());

		int i = 0;
		for (UVwebContent.UV UV : UVs) {
			final char section = UV.getLetterCode().charAt(0);
			if (!mSectionToPosition.containsKey(section)) {
				mSectionToPosition.put(section, i);
			}

			mSectionPosition.append(i, section);
			i++;
		}

		// create a list from the set to sort
		ArrayList<Character> sectionList = new ArrayList<Character>(mSectionToPosition.keySet());
		Collections.sort(sectionList);
		mSections = new Character[sectionList.size()];
		sectionList.toArray(mSections);
		mUVs = UVs;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.uv, null);
		}

		final TextView code1View = UVwebHolder.get(convertView, R.id.uv_code_letter);
		final TextView code2View = UVwebHolder.get(convertView, R.id.uv_code_number);
		final TextView rateView = UVwebHolder.get(convertView, R.id.rate);
		final TextView descView = UVwebHolder.get(convertView, R.id.desc);

		final UVwebContent.UV UV = getItem(position);

		code1View.setText(UV.getLetterCode());
		code2View.setText(UV.getNumberCode());
		descView.setText(UV.getDescription());
		rateView.setText(UV.getFormattedRate());

		final View separatorView = UVwebHolder.get(convertView, R.id.list_divider);
		if (separatorView != null) {
			// In tablet mode, we need to remove the last horizontal divider from the section, because these
			// are manually drawn
			final int currentSection = computeSectionFromPosition(position),
					nextSection = computeSectionFromPosition(position + 1);
			if (currentSection != nextSection) {
				// TODO: fix issue with the last separator from the penultimate section which won't be removed
				Log.d(TAG, "mSections[currentSection] == " + mSections[currentSection]);
				Log.d(TAG, "mSections[nextSection] == " + mSections[nextSection]);
				separatorView.setVisibility(View.GONE);
			} else {
				separatorView.setVisibility(View.VISIBLE);
			}
		}

		return convertView;
	}

	/**
	 * This private method is used to compute the @return section from a
	 *
	 * @param position
	 */
	private int computeSectionFromPosition(int position) {
		int prevIndex = 0;
		for (int i = 0; i < mSections.length; i++) {
			final int positionForSection = computePositionFromSectionIndex(i);
			if (positionForSection > position && prevIndex <= position) {
				prevIndex = i;
				break;
			}
			prevIndex = i;
		}
		return prevIndex;
	}

	/**
	 * This private method is used to compute the @return position from a
	 *
	 * @param sectionIndex in the mSections array
	 *                     It doesn't need the workaround from getPositionForSection() (which is automatically called by the system),
	 *                     meaning we don't need the Math.min calculation and thus have to rewrite this method that don't use it.
	 */
	private int computePositionFromSectionIndex(int sectionIndex) {
		return mSectionPosition.indexOfValue(mSections[sectionIndex]);
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
		final int actualSection = Math.min(section, mSections.length - 1); // Workaround for the fastScroll issue
		// See https://code.google.com/p/android/issues/detail?id=33293 for more information
		return mSectionPosition.indexOfValue(mSections[actualSection]);
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
			convertView = mLayoutInflater.inflate(R.layout.header_uv_list, null);
		}

		// Set header text as first char in name
		final TextView headerView = UVwebHolder.get(convertView, R.id.header_text);
		headerView.setEnabled(false);
		headerView.setText((String.valueOf(getSectionName(position))));

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
		return mSectionPosition.get(position);
	}
}
