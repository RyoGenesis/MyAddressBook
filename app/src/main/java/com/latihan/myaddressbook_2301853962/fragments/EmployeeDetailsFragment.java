package com.latihan.myaddressbook_2301853962.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.latihan.myaddressbook_2301853962.MyAddressBookApi;
import com.latihan.myaddressbook_2301853962.R;
import com.latihan.myaddressbook_2301853962.helpers.DBHelper;
import com.latihan.myaddressbook_2301853962.models.APIResponse;
import com.latihan.myaddressbook_2301853962.models.AddressBookEmployee;
import com.latihan.myaddressbook_2301853962.models.Coordinate;
import com.latihan.myaddressbook_2301853962.models.Employee;
import com.latihan.myaddressbook_2301853962.models.EmployeeLocation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeDetailsFragment extends Fragment {

    TextView tvEmpName, tvCity, tvPhone, tvMemberSince, tvEmail, tvAdd;
    int employeeId = 0;
    DBHelper dbHelper = null;

    public EmployeeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_employee_details, container, false);

        tvEmpName = view.findViewById(R.id.tv_emp_name);
        tvCity = view.findViewById(R.id.tv_city);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvMemberSince = view.findViewById(R.id.tv_member_since);
        tvEmail = view.findViewById(R.id.tv_email);
        tvAdd = view.findViewById(R.id.tv_add_to_address_book);

        setupEmployeeDetails();
        return view;
    }

    private void setupEmployeeDetails() {
        employeeId = getArguments().getInt("employeeId",0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/stage2/people/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyAddressBookApi addressBookApi = retrofit.create(MyAddressBookApi.class);

        Call<APIResponse> apiCall = addressBookApi.getResponseAndDetailEmployee(employeeId);

        apiCall.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {

                if (!response.isSuccessful()){
                    Log.d("Error API Call","Call unsuccessful");
                    return;
                }

                APIResponse apiResponse = response.body();

                String headerTitle = "UserId: " + apiResponse.getNim() + ", Username: " + apiResponse.getNama();
                getActivity().setTitle(headerTitle);

                Employee employee = apiResponse.getEmployees().get(0);


                String name = employee.getName().getFirst() + " " + employee.getName().getLast();
                tvEmpName.setText(name);

                setupMap(employee.getLocation().getCoordinates(), name);

                String city = "City: " + employee.getLocation().getCity()
                        + ", " + employee.getLocation().getCountry();
                tvCity.setText(city);

                String phone = "Phone: " + employee.getPhone() + " / " + employee.getCell();
                tvPhone.setText(phone);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);

                try {
                    Date date = format.parse(employee.getRegistered().getDate());
                    String memberSince = "Member since: " + monthYearFormat.format(date);
                    tvMemberSince.setText(memberSince);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String email = "Email: " + employee.getEmail();
                tvEmail.setText(email);

                tvAdd.setOnClickListener(view -> {
                    setupAddToAddressBook(employee);
                });
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Log.d("Failure API Call",t.getMessage());
            }
        });
    }

    private void setupMap(Coordinate coordinate, String empName) {
        MapsFragment mapFragment = new MapsFragment(coordinate.getLatitude(),coordinate.getLongitude(), empName);
        getChildFragmentManager().beginTransaction()
                .add(R.id.map_fragment, mapFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void setupAddToAddressBook(Employee employee) {

        AddressBookEmployee addressBookEmployee
                = new AddressBookEmployee(employeeId,
                                        employee.getPicture().getMedium(),
                                        employee.getName().getFirst() + " " + employee.getName().getLast(),
                                        employee.getLocation().getCity(),
                                        employee.getLocation().getCountry(),
                                        employee.getPhone(),
                                        employee.getEmail());

        boolean inserted = dbHelper.insertData(addressBookEmployee);
        if(inserted) {
            Toast.makeText(getContext(), "Successfully Added To Address Book!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Already Added To Address Book!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dbHelper = new DBHelper(context);
    }
}