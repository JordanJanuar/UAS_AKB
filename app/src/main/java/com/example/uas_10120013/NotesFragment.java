package com.example.uas_10120013;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// NIM : 10120013
// NAMA : Jordan Januar Ilhami G
// KELAS : IF 1
public class NotesFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RVAdapter adapter;
    DAONotes dao;
    boolean isLoading = false;
    String key = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadData();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void init(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swip);
        recyclerView = view.findViewById(R.id.rv_notes);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adapter = new RVAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        dao = new DAONotes();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItem = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (totalItem < lastVisible + 3) {
                    if (!isLoading) {
                        isLoading = true;
                        loadData();
                    }
                }
            }
        });

        view.findViewById(R.id.fab).setOnClickListener(view1 -> startActivity(new Intent(getActivity(), InputActivity.class)));
    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        dao.get(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Notes> emps = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Notes emp = data.getValue(Notes.class);
                    emp.setKey(data.getKey());
                    emps.add(emp);
                    key = data.getKey();
                }
                adapter.setItems(emps);
                adapter.notifyDataSetChanged();

                isLoading = false;

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}