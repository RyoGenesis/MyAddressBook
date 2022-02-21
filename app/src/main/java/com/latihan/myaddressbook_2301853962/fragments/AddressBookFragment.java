package com.latihan.myaddressbook_2301853962.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.latihan.myaddressbook_2301853962.R;
import com.latihan.myaddressbook_2301853962.adapters.EmployeeAddressBookAdapter;
import com.latihan.myaddressbook_2301853962.helpers.DBHelper;
import com.latihan.myaddressbook_2301853962.models.AddressBookEmployee;

import java.util.ArrayList;
import java.util.List;

public class AddressBookFragment extends Fragment {

    RecyclerView rvAddressBook;
    TextView tvNoData;
    DBHelper dbHelper = null;

    public AddressBookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_book, container, false);

        tvNoData = view.findViewById(R.id.tv_no_data);
        rvAddressBook = view.findViewById(R.id.rv_address_book);

        showAddressBook();

        return view;
    }

    private List<AddressBookEmployee> getData(){
        Cursor cursor = dbHelper.getAddressBookEmployees();
        List<AddressBookEmployee> listAddressBookEmployee = new ArrayList<>();
        if (cursor.getCount() <= 0){
            return listAddressBookEmployee;
        }

        while (cursor.moveToNext()){
            int employeeId = cursor.getInt(0);
            String name = cursor.getString(1);
            String city = cursor.getString(2);
            String country = cursor.getString(3);
            String phone = cursor.getString(4);
            String email = cursor.getString(5);
            String imageUrl = cursor.getString(6);

            AddressBookEmployee employee = new AddressBookEmployee(employeeId, imageUrl, name, city, country, phone, email);
            listAddressBookEmployee.add(employee);
        }

        return listAddressBookEmployee;
    }

    private void showAddressBook() {
        List<AddressBookEmployee> list = getData();
        if(list.size() < 1){
            tvNoData.setVisibility(View.VISIBLE);
        }
        else{
            tvNoData.setVisibility(View.GONE);
        }
        EmployeeAddressBookAdapter adapter = new EmployeeAddressBookAdapter(list);
        rvAddressBook.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAddressBook.setAdapter(adapter);

        adapter.setOnItemClickCallback(employeeId -> {
            EmployeeDetailsFragment detailFragment = new EmployeeDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("employeeId", employeeId);
            detailFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dbHelper = new DBHelper(context);
    }
}