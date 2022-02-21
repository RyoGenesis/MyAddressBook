package com.latihan.myaddressbook_2301853962.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.latihan.myaddressbook_2301853962.R;

public class SearchEmployeeFragment extends Fragment {

    SearchFragment searchFragment;
    EmployeesFragment employeesFragment;

    public SearchEmployeeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            searchFragment = new SearchFragment();
            employeesFragment = new EmployeesFragment();
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction
                    .add(R.id.search_bar_fragment, searchFragment)
                    .add(R.id.employee_list_fragment, employeesFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_employee, container, false);

        return view;
    }
}