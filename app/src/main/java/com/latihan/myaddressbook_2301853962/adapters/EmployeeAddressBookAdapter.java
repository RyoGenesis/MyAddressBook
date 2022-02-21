package com.latihan.myaddressbook_2301853962.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.latihan.myaddressbook_2301853962.R;
import com.latihan.myaddressbook_2301853962.models.AddressBookEmployee;
import com.latihan.myaddressbook_2301853962.services.LoadImageTask;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAddressBookAdapter extends RecyclerView.Adapter<EmployeeAddressBookAdapter.ListViewHolder> {

    private final List<AddressBookEmployee> listAddressBookEmployee;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public EmployeeAddressBookAdapter(List<AddressBookEmployee> list){
        listAddressBookEmployee = list;
    }

    public List<AddressBookEmployee> getListAddressBookEmployee() {
        return listAddressBookEmployee;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_book_item,viewGroup,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        AddressBookEmployee employee = listAddressBookEmployee.get(position);

        new LoadImageTask(holder.imgEmployee).execute(employee.getUrlImg());

        holder.tvEmpName.setText(employee.getName());

        String cityText = "City: " + employee.getCity() + ", " + employee.getCountry();

        holder.tvCity.setText(cityText);

        holder.itemView.setOnClickListener(view -> {
            onItemClickCallback.onItemClicked(employee.getEmployeeID());
        });

        holder.btnCall.setOnClickListener(view -> {
            Intent callPhoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + employee.getPhone()));
             view.getContext().startActivity(callPhoneIntent);
        });

        holder.btnEmail.setOnClickListener(view -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("*/*");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {employee.getEmail()});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Send email to " + employee.getName());
            view.getContext().startActivity(emailIntent);
        });
    }

    @Override
    public int getItemCount() {
        return listAddressBookEmployee.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder{

        ImageView imgEmployee;
        TextView tvEmpName, tvCity;
        Button btnCall, btnEmail;

        public ListViewHolder(View itemView) {
            super(itemView);
            imgEmployee = itemView.findViewById(R.id.img_employee);
            tvEmpName = itemView.findViewById(R.id.tv_emp_name);
            tvCity = itemView.findViewById(R.id.tv_city);
            btnCall = itemView.findViewById(R.id.btn_call);
            btnEmail = itemView.findViewById(R.id.btn_email);
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(int employeeId);
    }
}
