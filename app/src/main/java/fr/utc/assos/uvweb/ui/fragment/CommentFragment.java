package fr.utc.assos.uvweb.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.utc.assos.uvweb.R;
import fr.utc.assos.uvweb.model.Comment;

public class CommentFragment extends Fragment {
    private static final String ARG_COMMENT = "arg_comment";

    private Comment comment;

    public static CommentFragment newInstance(Comment comment) {
        CommentFragment fragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_COMMENT, comment);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comment = getArguments().getParcelable(ARG_COMMENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_comment, container, false);
        return root;
    }
}
