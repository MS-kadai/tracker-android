package one.nem.tracker_kadai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.RouteListViewHolder> {

    private List<String> rowDataList;
    final SelectRouteInterface selectRouteInterface;

    public RouteListAdapter(List<String> rowDataList, SelectRouteInterface selectRouteInterface) {
        this.rowDataList = rowDataList;
        this.selectRouteInterface = selectRouteInterface;
    }

    static class RouteListViewHolder extends RecyclerView.ViewHolder {

        SelectRouteInterface listener;
        TextView titleText;

        RouteListViewHolder(@NonNull View itemView, SelectRouteInterface listener) {
            super(itemView);
            titleText = itemView.findViewById(R.id.select_route_route_name);
            this.listener = listener;
        }

        public void onBind(String rowData) {
//            titleText.setText(rowData);
            titleText.setOnClickListener(v -> {
                listener.onSelect(rowData);
            });
        }
    }

    @NonNull
    @Override
    public RouteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_select_route, parent, false);
        return new RouteListViewHolder(view, selectRouteInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteListViewHolder holder, int position) {
        holder.titleText.setText(rowDataList.get(position));

        if (holder instanceof RouteListViewHolder) {
            ((RouteListViewHolder) holder).onBind(rowDataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return rowDataList.size();
    }

}
