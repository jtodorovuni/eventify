package uni.fmi.eventify.ui.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uni.fmi.eventify.R;
import uni.fmi.eventify.databinding.FragmentSlideshowBinding;
import uni.fmi.eventify.entity.Event;
import uni.fmi.eventify.helper.RequestHelper;
import uni.fmi.eventify.helper.ResponseListener;
import uni.fmi.eventify.ui.MainActivity;
import uni.fmi.eventify.ui.adapter.EventAdapter;

public class HomeFragment extends Fragment {

    TextView selectedDateTV;
    CalendarView eventsCalendar;
    RecyclerView eventsRV;
    EventAdapter adapter;

    List<Event> allEvents = new ArrayList<>();

    MainActivity mainActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mainActivity = (MainActivity) getActivity();

        selectedDateTV = view.findViewById(R.id.selectedDateTV);
        eventsCalendar = view.findViewById(R.id.eventsCV);
        eventsRV = view.findViewById(R.id.eventsRV);
        adapter = new EventAdapter(getContext(), new ArrayList<Event>());

        eventsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        eventsRV.setAdapter(adapter);

        eventsCalendar.setOnDateChangeListener(onDateChange);

        loadAllEvents();

        return view;
    }

    public void loadAllEvents(){
        String urlString = String.format("%s:%s/%s", RequestHelper.ADDRESS, RequestHelper.PORT,
                String.format(RequestHelper.GET_EVENTS, RequestHelper.token));

        RequestHelper.requestService(urlString, new ResponseListener() {
            @Override
            public void onResponse(String response) throws JSONException {

                JSONObject res = new JSONObject(response);

                if(res.getBoolean("LoginRequired")){
                    mainActivity.finish();
                }else if(!res.getBoolean("Success")){
                    Toast.makeText(mainActivity, res.getString("Message"), Toast.LENGTH_SHORT).show();
                }else{

                    JSONArray array = res.getJSONArray("Data");

                    for(int i = 0; i < array.length(); i++){
                        JSONObject currentEv = array.getJSONObject(i);

                        Event event = new Event();

                        event.setAddress(currentEv.getString("Address"));
                        event.setDescription(currentEv.getString("Description"));
                        event.setId(currentEv.getInt("Id"));
                        event.setTitle(currentEv.getString("Title"));
                        event.setType(currentEv.getString("Type"));
                        event.setPrice(currentEv.getDouble("Price"));
                        event.setCreatedAt(currentEv.getLong("CreatedAt"));
                        allEvents.add(event);
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    CalendarView.OnDateChangeListener onDateChange = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
            selectedDateTV.setText("Selected Date: " + day + "." + (month + 1) + "." + year);


            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                updateRecyclerView(LocalDate.of(year, month + 1, day));
            //}
        }
    };

    private void updateRecyclerView(LocalDate date) {
        List<Event> currentEvents = new ArrayList<>();

        for (Event ev: allEvents) {
           // if(ev.getDate().toInstant().compareTo().compareTo(date)){
              //  currentEvents.add(ev);
          //  }
        }
       // adapter.changeEvents(currentEvents);
    }


}