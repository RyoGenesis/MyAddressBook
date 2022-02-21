package com.latihan.myaddressbook_2301853962.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.latihan.myaddressbook_2301853962.MyAddressBookApi;
import com.latihan.myaddressbook_2301853962.R;
import com.latihan.myaddressbook_2301853962.adapters.EmployeeSearchAdapter;
import com.latihan.myaddressbook_2301853962.models.APIResponse;
import com.latihan.myaddressbook_2301853962.models.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeesFragment extends Fragment {

    RecyclerView rvEmployees;
    TextView tvNoData;
    private String querySearch = "";

    public EmployeesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_employees, container, false);

        if(getArguments() != null)
            querySearch = getArguments().getString("query").trim();

        tvNoData = view.findViewById(R.id.tv_no_data);

        rvEmployees = view.findViewById(R.id.rv_employees);
        rvEmployees.setLayoutManager(new LinearLayoutManager(getContext()));
        //set adapter kosong agar tidak error "no adapter attached, skipping layout"
        rvEmployees.setAdapter(new EmployeeSearchAdapter(new ArrayList<Employee>()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/stage2/people/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyAddressBookApi addressBookApi = retrofit.create(MyAddressBookApi.class);

        Call<APIResponse> apiCall = addressBookApi.getResponseAndEmployees();

        apiCall.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {

                if (!response.isSuccessful()){
                    Log.d("Error API Call","Call unsuccessful");
                    return;
                }

                APIResponse apiResponse = response.body();

                String headerTitle = "UserId: " + apiResponse.getNim() + ", Username: " + apiResponse.getNama();
                requireActivity().setTitle(headerTitle);

                List<Employee> employees = apiResponse.getEmployees();

                if(!querySearch.isEmpty()) {
                    List<Employee> noMatchEmployee = new ArrayList<>();

                    for (Employee emp: employees) {
                        String empName = emp.getName().getFirst() + " " + emp.getName().getLast();
                        if(!empName.toLowerCase().contains(querySearch.toLowerCase()))
                            noMatchEmployee.add(emp);
                    }
                    employees.removeAll(noMatchEmployee);
                }

                if(employees.size()<1) {
                    tvNoData.setVisibility(View.VISIBLE);
                } else {
                    tvNoData.setVisibility(View.GONE);
                }

                EmployeeSearchAdapter adapter = new EmployeeSearchAdapter(employees);
                rvEmployees.setAdapter(adapter);
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
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Log.d("Failure API Call",t.getMessage());
            }
        });

        return view;
    }
}