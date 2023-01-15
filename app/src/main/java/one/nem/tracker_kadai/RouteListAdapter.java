package one.nem.tracker_kadai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.RouteListViewHolder> {

    private List<String> rowDataList;

    public RouteListAdapter(List<String> rowDataList) {
        this.rowDataList = rowDataList;
    }

    static class RouteListViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;

        RouteListViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.select_route_route_name);
        }
    }

    @NonNull
    @Override
    public RouteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_select_route, parent, false);
        return new RouteListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteListViewHolder holder, int position) {
        holder.titleText.setText(rowDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return rowDataList.size();
    }

}
