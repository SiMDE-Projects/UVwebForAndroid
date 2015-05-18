package fr.utc.assos.uvweb.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.model.Comment;
import fr.utc.assos.uvweb.ui.fragment.CommentFragment;

public class CommentActivity extends ToolbarActivity {

    public static final String ARG_COMMENT = "arg_comment";
    public static final String ARG_UVNAME = "arg_uvname";
    private static final String TAG_PASSED = "obtenue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Comment comment = getIntent().getParcelableExtra(ARG_COMMENT);
        String uvName = getIntent().getStringExtra(ARG_UVNAME);

        setTitle(uvName);

        TextView rateView = (TextView) findViewById(R.id.globalrate);
        TextView authorView = (TextView) findViewById(R.id.author);

        rateView.setText(getString(R.string.global_rate, comment.getGlobalRate()));
        if (comment.getPassed().equals(TAG_PASSED)) {
            authorView.setText(getString(R.string.author_passed, comment.getAuthor(), comment.getSemester()));
        } else {
            authorView.setText(getString(R.string.author_failed, comment.getAuthor(), comment.getSemester()));
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_comment_container, CommentFragment.newInstance(comment))
                .commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
