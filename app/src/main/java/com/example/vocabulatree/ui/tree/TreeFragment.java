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

public class TreeFragment extends Fragment {

    AlphaMovieView videoView;
    TreeViewModel viewModel;
    FrameLayout placeholder;

    public TreeFragment() {
        // Required empty public constructor
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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