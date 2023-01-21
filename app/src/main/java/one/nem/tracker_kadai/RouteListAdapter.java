package one.nem.tracker_kadai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.RouteListViewHolder> {

    private List<String> rowRouteNameList;
    private List<String> rowRouteIdList;
    final SelectRouteInterface selectRouteInterface;

    public RouteListAdapter(List<String> rowRouteNameList, List<String> rowRouteIdList, SelectRouteInterface selectRouteInterface) {
        this.rowRouteNameList = rowRouteNameList;
        this.rowRouteIdList = rowRouteIdList;
        this.selectRouteInterface = selectRouteInterface;
    }

    static class RouteListViewHolder extends RecyclerView.ViewHolder {

        SelectRouteInterface listener;
        TextView titleText;
        TextView routeIdText;

        RouteListViewHolder(@NonNull View itemView, SelectRouteInterface listener) {
            super(itemView);
            titleText = itemView.findViewById(R.id.select_route_route_name);
            routeIdText = itemView.findViewById(R.id.select_route_route_id);
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
        holder.titleText.setText(rowRouteNameList.get(position));
        holder.routeIdText.setText(rowRouteIdList.get(position));

        if (holder instanceof RouteListViewHolder) {
            ((RouteListViewHolder) holder).onBind(rowRouteIdList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return rowRouteNameList.size();
    }

}
