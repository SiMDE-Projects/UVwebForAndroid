package fr.utc.assos.uvweb.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.model.UvListItem;
import fr.utc.assos.uvweb.ui.fragment.UvFragment;

public class UvActivity extends ToolbarActivity {
    public static final String ARG_UV = "arg_uv";

    private TextView nameView;
    private TextView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        UvListItem uv = getIntent().getParcelableExtra(ARG_UV);

        nameView = (TextView) findViewById(R.id.name);
        titleView = (TextView) findViewById(R.id.title);

        nameView.setText(uv.getName());
        titleView.setText(uv.getTitle());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_uv_container, UvFragment.newInstance(uv))
                .commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_uv;
    }
}
