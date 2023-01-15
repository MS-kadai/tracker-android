package one.nem.tracker_kadai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PointListAdapter extends RecyclerView.Adapter<PointListAdapter.PointListViewHolder>  {

    private List<String> rowDataPointNameList;
    private List<String> rowDataCoordinateList;
    
    public PointListAdapter(List<String> rowDataPointNameList, List<String> rowDataCoordinateList) {
        this.rowDataPointNameList = rowDataPointNameList;
        this.rowDataCoordinateList = rowDataCoordinateList;
    }
    
    public static class PointListViewHolder extends RecyclerView.ViewHolder {
        
        TextView pointNameText;
        TextView pointCoordinateText;
        
        
        PointListViewHolder(@NonNull View itemView) {
            super(itemView);
            pointNameText = itemView.findViewById(R.id.route_point_name);
            pointCoordinateText = itemView.findViewById(R.id.route_point_coordinates);
        }
    }
    
    @NonNull
    @Override
    public PointListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_point, parent, false);
        return new PointListViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull PointListViewHolder holder, int position) {
        holder.pointNameText.setText(rowDataPointNameList.get(position));
        holder.pointCoordinateText.setText(rowDataCoordinateList.get(position));
    }

    @Override
    public int getItemCount() {
        return rowDataPointNameList.size();
    }
}
