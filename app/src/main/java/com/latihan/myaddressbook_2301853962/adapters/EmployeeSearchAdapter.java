package com.latihan.myaddressbook_2301853962.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.latihan.myaddressbook_2301853962.R;
import com.latihan.myaddressbook_2301853962.models.Employee;
import com.latihan.myaddressbook_2301853962.services.LoadImageTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EmployeeSearchAdapter extends RecyclerView.Adapter<EmployeeSearchAdapter.ListViewHolder> {

    private final List<Employee> listEmployee;
    private OnItemClickCallback onItemClickCallback;

    public EmployeeSearchAdapter(List<Employee> listItem){
        this.listEmployee = listItem;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.employee_item,viewGroup,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Employee employee = listEmployee.get(position);

        new LoadImageTask(holder.imgEmployee).execute(employee.getPicture().getMedium());

        String name = employee.getName().getFirst() + " " + employee.getName().getLast();
        holder.tvEmpName.setText(name);

        String city = "City: " + employee.getLocation().getCity()
                + ", " + employee.getLocation().getCountry();
        holder.tvCity.setText(city);

        String phone = "Phone: " + employee.getPhone() + " / " + employee.getCell();
        holder.tvPhone.setText(phone);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);

        try {
            Date date = format.parse(employee.getRegistered().getDate());
            String memberSince = "Member since: " + monthYearFormat.format(date);
            holder.tvMemberSince.setText(memberSince);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(v -> {
            onItemClickCallback.onItemClicked(employee.getEmployeeId());
        });
    }

    @Override
    public int getItemCount() {
        return listEmployee.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder{

        ImageView imgEmployee;
        TextView tvEmpName, tvCity, tvPhone, tvMemberSince;

        public ListViewHolder(View itemView) {
            super(itemView);
            imgEmployee = itemView.findViewById(R.id.img_employee);
            tvEmpName = itemView.findViewById(R.id.tv_emp_name);
            tvCity = itemView.findViewById(R.id.tv_city);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvMemberSince = itemView.findViewById(R.id.tv_member_since);
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(int employeeId);
    }
}
