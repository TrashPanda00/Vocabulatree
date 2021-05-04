package com.example.vocabulatree.ui.tree;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.alphamovie.lib.AlphaMovieView;
import com.example.vocabulatree.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TreeFragment extends Fragment {

    AlphaMovieView videoView;
    TreeViewModel viewModel;
    FrameLayout placeholder;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TreeFragment newInstance(String param1, String param2) {
        TreeFragment fragment = new TreeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tree, container, false);
        viewModel = new ViewModelProvider(this).get(TreeViewModel.class);
        videoView = root.findViewById(R.id.videoView);
        String uriPath = "android.resource://" + getContext().getPackageName() + "/" + R.raw.tree1;
        Uri uri = Uri.parse(uriPath);
        placeholder = root.findViewById(R.id.placeholder);
        videoView.setVideoFromUri(getContext(),uri);
        videoView.requestFocus();
        videoView.setLooping(true);
    
        Runnable mRunnable;
        Handler mHandler=new Handler();
    
        mRunnable=new Runnable() {
        
            @Override
            public void run() {
                // TODO Auto-generated method stub
                placeholder.setVisibility(View.INVISIBLE); //If you want just hide the View. But it will retain space occupied by the View.
                placeholder.setVisibility(View.GONE); //This will remove the View. and free s the space occupied by the View
            }
        };
        placeholder.setZ(100);
        mHandler.postDelayed(mRunnable,400);
        videoView.start();
        return root;
    }

@Override
public void onResume() {
    super.onResume();
    videoView.onResume();
}

@Override
public void onPause() {
    super.onPause();
    videoView.onResume();
}

@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
    }
}