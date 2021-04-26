package com.example.vocabulatree.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vocabulatree.R;
import com.example.vocabulatree.ui.models.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DashboardFragment extends Fragment implements EntryAdapter.OnListItemClickListener {

    private DashboardViewModel dashboardViewModel;
    FloatingActionButton button;
    RecyclerView mEntries;
    EntryAdapter mEntryAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mEntries = root.findViewById(R.id.rv);
        mEntries.hasFixedSize();
        mEntries.setLayoutManager(new LinearLayoutManager(this.getContext()));
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);


        dashboardViewModel.getAllEntries().observe(getViewLifecycleOwner(), entries -> {
            mEntryAdapter = new EntryAdapter(new ArrayList<Entry>(entries),this);
            mEntries.setAdapter(mEntryAdapter);
        });
        //mEntryAdapter = new EntryAdapter(new ArrayList<Entry>(dashboardViewModel.getAllEntries().getValue()), this);
        //mEntries.setAdapter(mEntryAdapter);

        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController nav = Navigation.findNavController(view);
        button = view.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav.navigate(R.id.action_navigation_dashboard_to_editEntry);
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        int entryNumber = clickedItemIndex + 1;
        Bundle bundle = new Bundle();
        Entry toEdit = dashboardViewModel.getAllEntries().getValue().get(clickedItemIndex);
        bundle.putSerializable("toEdit", toEdit);
        NavController nav = Navigation.findNavController(this.getView());
        nav.navigate(R.id.action_navigation_dashboard_to_editEntry,bundle);
        Toast.makeText(this.getContext(), "Entry number: " + entryNumber, Toast.LENGTH_LONG).show();
    }
}