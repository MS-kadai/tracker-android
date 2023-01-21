package one.nem.tracker_kadai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PointListAdapter extends RecyclerView.Adapter<PointListAdapter.PointListViewHolder>  {

    private List<String> rowDataPointNameList;
    private List<String> rowDataCoordinateList;
    private List<String> rowDataPointIdList;
    final selectPointInterface selectPointInterface;

    public PointListAdapter(List<String> rowDataPointNameList, List<String> rowDataCoordinateList, List<String> rowDataPointIdList, one.nem.tracker_kadai.selectPointInterface selectPointInterface) {
        this.rowDataPointNameList = rowDataPointNameList;
        this.rowDataCoordinateList = rowDataCoordinateList;
        this.rowDataPointIdList = rowDataPointIdList;
        this.selectPointInterface = selectPointInterface;
    }
    
    public static class PointListViewHolder extends RecyclerView.ViewHolder {
        
        TextView pointNameText;
        TextView pointCoordinateText;
        TextView pointIdText;
        selectPointInterface listener;
        
        
        PointListViewHolder(@NonNull View itemView, selectPointInterface listener) {
            super(itemView);
            pointNameText = itemView.findViewById(R.id.route_point_name);
            pointCoordinateText = itemView.findViewById(R.id.route_point_coordinates);
            pointIdText = itemView.findViewById(R.id.route_point_id);
            this.listener = listener;
        }

        public void onBind(String rowData) {
            pointNameText.setOnClickListener(v -> {
                listener.onSelect(rowData);
            });
        }
    }
    
    @NonNull
    @Override
    public PointListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_point, parent, false);
        return new PointListViewHolder(view, selectPointInterface);
    }
    
    @Override
    public void onBindViewHolder(@NonNull PointListViewHolder holder, int position) {
        holder.pointNameText.setText(rowDataPointNameList.get(position));
        holder.pointCoordinateText.setText(rowDataCoordinateList.get(position));
        holder.pointIdText.setText(rowDataPointIdList.get(position));
        if (holder instanceof PointListViewHolder) {
            ((PointListViewHolder) holder).onBind(rowDataPointIdList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return rowDataPointNameList.size();
    }
}
