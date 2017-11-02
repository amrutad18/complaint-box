package com.example.amruta.homescreen2.adapters;

/**
 * Created by amruta on 27/10/17.
 */
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amruta.homescreen2.R;
import com.example.amruta.homescreen2.Model.Complaint;

import java.util.List;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<Complaint> listUsers;

    public UsersRecyclerAdapter(List<Complaint> listUsers) {
        this.listUsers = listUsers;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_recycler, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewEmail.setText(listUsers.get(position).getUser());
        holder.textViewProduct.setText(listUsers.get(position).getProductType());
        holder.textViewModel.setText(listUsers.get(position).getModelNo());
        holder.textViewDetails.setText(listUsers.get(position).getDetails());
        holder.textViewFdate.setText(listUsers.get(position).getFileDate());
        if(listUsers.get(position).getPriority()==0)
        {
            if(listUsers.get(position).getStatus_code()==1)
            {
                System.out.println("hey");
                holder.textViewPriority.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_priority,0,R.drawable.ic_resolved,0);
            }
            else
            {
                System.out.println("hi");
                holder.textViewPriority.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_priority,0,0,0);
            }

        }
        else
        {
            if(listUsers.get(position).getStatus_code()==1)
            {
                holder.textViewPriority.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_resolved,0);
            }
            else
            {
                holder.textViewPriority.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }
    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }


    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewProduct;
        public AppCompatTextView textViewModel;
        public AppCompatTextView textViewDetails;
        public AppCompatTextView textViewFdate;
        public AppCompatTextView textViewPriority;

        public UserViewHolder(View view) {
            super(view);

            textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewProduct = (AppCompatTextView) view.findViewById(R.id.textViewProduct);
            textViewModel = (AppCompatTextView) view.findViewById(R.id.textViewModel);
            textViewDetails = (AppCompatTextView) view.findViewById(R.id.textViewDetails);
            textViewFdate = (AppCompatTextView) view.findViewById(R.id.textViewFdate);
            textViewPriority = (AppCompatTextView) view.findViewById(R.id.textViewPriority);
        }
    }


}