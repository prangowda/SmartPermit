package info.accolade.ocr_nexus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import info.accolade.ocr_nexus.R;
import info.accolade.ocr_nexus.modal.ComplaintsModal;
import info.accolade.ocr_nexus.modal.PermissionModal;

public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.ViewHolder> {

    private ArrayList<PermissionModal> modal = new ArrayList<>();
    private Context context;
    private PermissionAdapter.RecyclerViewClickListener listener;

    public PermissionAdapter(Context context, ArrayList<PermissionModal> modal, PermissionAdapter.RecyclerViewClickListener listener) {
        this.modal = modal;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PermissionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.permissio_recycler, parent, false);
        return new PermissionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PermissionAdapter.ViewHolder holder, int position) {
        holder.cnumber.setText("No: "+modal.get(position).getPnumber());
        holder.type.setText("Permission Type: "+modal.get(position).getType());
        holder.desc.setText("Description: "+modal.get(position).getDescription());
        holder.name.setText("Name: "+modal.get(position).getName());
        holder.add.setText("Address: "+modal.get(position).getAddress());
        holder.phone.setText("Phone: "+modal.get(position).getPhone());
        holder.from.setText("Valid From: "+modal.get(position).getValidfrom());
        holder.to.setText("Valid To: "+modal.get(position).getValidto());
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
        private TextView cnumber, type, desc, name, add, phone, from, to, status, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cnumber = itemView.findViewById(R.id.permi_num);
            type = itemView.findViewById(R.id.permi_typ);
            desc = itemView.findViewById(R.id.permi_desc);
            name = itemView.findViewById(R.id.permi_nam);
            add = itemView.findViewById(R.id.permi_add);
            phone = itemView.findViewById(R.id.permi_ph);
            from = itemView.findViewById(R.id.permi_frm);
            to = itemView.findViewById(R.id.permi_to);
            status = itemView.findViewById(R.id.permi_st);
            date = itemView.findViewById(R.id.permi_dt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}
