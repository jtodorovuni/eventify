package uni.fmi.eventify.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uni.fmi.eventify.R;
import uni.fmi.eventify.entity.Event;
import uni.fmi.eventify.helper.Helper;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{

    private List<Event> events;
    private Context context;

    public EventAdapter(Context context, List<Event> events){
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row, parent, false);

        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        Event currentEvent = events.get(position);
        holder.title.setText(currentEvent.getTitle());

        if(currentEvent.getDescription().length() > 20){
            String desc = currentEvent.getDescription().substring(0,20);
            //Малки зелени човечет а обикалят наоколо търсейки нещо!
            //Малки зелени човечет
            String temp = currentEvent.getDescription().substring(20);

            if(temp.indexOf(" ") > 0){
                desc += temp.substring(0, temp.indexOf(" "));
            }
            holder.description.setText(desc + "...");
        }else{
            holder.description.setText(currentEvent.getDescription());
        }

        holder.time.setText(Helper.convertLongAsTimeString(currentEvent.getCreatedAt()));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void changeEvents(List<Event> currentEvents) {
        events.clear();
        events.addAll(currentEvents);
        notifyDataSetChanged();
    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        TextView time;
        ImageButton buy;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.eventTitleTV);
            description = itemView.findViewById(R.id.eventDescriptionTV);
            time = itemView.findViewById(R.id.eventTimeTV);
            buy = itemView.findViewById(R.id.BuyB);
        }
    }
}
