package com.latihan.myaddressbook_2301853962.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.latihan.myaddressbook_2301853962.R;

public class SearchFragment extends Fragment {

    EditText etSearch;
    TextView tvSearch;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etSearch = view.findViewById(R.id.et_search);
        tvSearch = view.findViewById(R.id.tv_search);

        tvSearch.setOnClickListener(view1 -> {
            String querySearch = etSearch.getText().toString();
            try {
                Bundle bundle = new Bundle();
                bundle.putString("query", querySearch);
                EmployeesFragment empFragment = new EmployeesFragment();
                empFragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.employee_list_fragment, empFragment)
                        .commit();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

        return view;
    }
}