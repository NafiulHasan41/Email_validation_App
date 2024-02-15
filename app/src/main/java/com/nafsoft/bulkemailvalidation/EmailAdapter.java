package com.nafsoft.bulkemailvalidation;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ViewHolder> {

    private ArrayList<EmailItem> emailList;

    public EmailAdapter(ArrayList<EmailItem> emailList) {
        this.emailList = emailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_email, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmailItem emailItem = emailList.get(position);
        holder.emailTextView.setText(emailItem.getEmail());
        holder.statusTextView.setText(emailItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return emailList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView emailTextView;
        TextView statusTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }
}