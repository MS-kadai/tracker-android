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

    private List<String> rowDataList;
    
    public PointListAdapter(List<String> rowDataList) {
        this.rowDataList = rowDataList;
    }
    
    public class PointListViewHolder extends RecyclerView.ViewHolder {
        
        TextView pointNameText;
        
        
        PointListViewHolder(@NonNull View itemView) {
            super(itemView);
            pointNameText = itemView.findViewById(R.id.route_point_name);
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
        holder.pointNameText.setText(rowDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return rowDataList.size();
    }
}
