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
import info.accolade.ocr_nexus.modal.RenewModal;

public class RenewAdapter extends RecyclerView.Adapter<RenewAdapter.ViewHolder> {

    private ArrayList<RenewModal> modal = new ArrayList<>();
    private Context context;
    private RenewAdapter.RecyclerViewClickListener listener;

    public RenewAdapter(Context context, ArrayList<RenewModal> modal, RenewAdapter.RecyclerViewClickListener listener) {
        this.modal = modal;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RenewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.renew_recycler, parent, false);
        return new RenewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RenewAdapter.ViewHolder holder, int position) {
        holder.cnumber.setText("No: "+modal.get(position).getLnumber());
        holder.expdate.setText("Expire Date: "+modal.get(position).getExpdate());
        holder.name.setText("Name: "+modal.get(position).getName());
        holder.phone.setText("Phone: "+modal.get(position).getPhone());
        holder.address.setText("Address: "+modal.get(position).getAddress());
        holder.type.setText("License Type: "+modal.get(position).getType());
        holder.trade.setText("Trade Address: "+modal.get(position).getTradeAddress());
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
        private TextView cnumber, type, name, address, phone, expdate, trade, status, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cnumber = itemView.findViewById(R.id.relicense_num);
            type = itemView.findViewById(R.id.relicense_typ);
            name = itemView.findViewById(R.id.relicense_nme);
            address = itemView.findViewById(R.id.relicense_add);
            phone = itemView.findViewById(R.id.relicense_pne);
            expdate = itemView.findViewById(R.id.relicense_exp);
            trade = itemView.findViewById(R.id.relicense_trd);
            status = itemView.findViewById(R.id.relicense_sts);
            date = itemView.findViewById(R.id.relicense_dte);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}
