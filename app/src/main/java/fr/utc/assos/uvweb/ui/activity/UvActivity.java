package fr.utc.assos.uvweb.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.model.UvListItem;
import fr.utc.assos.uvweb.ui.fragment.UvFragment;

public class UvActivity extends AppCompatActivity {
    public static final String ARG_UV = "arg_uv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_uv);

        UvListItem uv = getIntent().getParcelableExtra(ARG_UV);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_uv_container, UvFragment.newInstance(uv))
                .commit();
    }
}
