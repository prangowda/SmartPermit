package info.accolade.ocr_nexus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import info.accolade.ocr_nexus.R;
import info.accolade.ocr_nexus.modal.ComplaintsModal;
import info.accolade.ocr_nexus.utils.ApiInterface;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder> {

    private ArrayList<ComplaintsModal> modal = new ArrayList<>();
    private Context context;
    private ComplaintAdapter.RecyclerViewClickListener listener;

    public ComplaintAdapter(Context context, ArrayList<ComplaintsModal> modal, ComplaintAdapter.RecyclerViewClickListener listener) {
        this.modal = modal;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintAdapter.ViewHolder holder, int position) {
        holder.cnumber.setText("No: "+modal.get(position).getNumber());
        holder.type.setText("Complaint Type: "+modal.get(position).getType());
        holder.com.setText("Complaint: "+modal.get(position).getComplaint());
        holder.desc.setText("Description: "+modal.get(position).getDescription());
        holder.name.setText("Name: "+modal.get(position).getName());
        holder.add.setText("Address: "+modal.get(position).getAddress());
        holder.phone.setText("Phone: "+modal.get(position).getPhone());
        holder.city.setText("City: "+modal.get(position).getCity());
        holder.pin.setText("PIN Code: "+modal.get(position).getPincode());
        holder.status.setText("Status: "+modal.get(position).getStatus());
        holder.date.setText(modal.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return modal.size();
    }
    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView cnumber, type, com, desc, name, add, phone, city, pin, status, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cnumber = itemView.findViewById(R.id.complaint_num);
            type = itemView.findViewById(R.id.complaint_typ);
            com = itemView.findViewById(R.id.complaint_com);
            desc = itemView.findViewById(R.id.complaint_des);
            name = itemView.findViewById(R.id.complaint_namer);
            add = itemView.findViewById(R.id.complaint_add);
            phone = itemView.findViewById(R.id.complaint_ph);
            city = itemView.findViewById(R.id.complaint_cit);
            pin = itemView.findViewById(R.id.complaint_pi);
            status = itemView.findViewById(R.id.complaint_st);
            date = itemView.findViewById(R.id.complaint_dt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}
