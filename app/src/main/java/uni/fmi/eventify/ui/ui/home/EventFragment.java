package uni.fmi.eventify.ui.ui.home;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uni.fmi.eventify.R;
import uni.fmi.eventify.entity.Event;
import uni.fmi.eventify.helper.RequestHelper;
import uni.fmi.eventify.helper.ResponseListener;

public class EventFragment extends Fragment {

    ImageView image;
    TextView titleTV;
    TextView descriptionTV;
    Button buyButton;
    int eventId;
    Activity activity;

    public EventFragment(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event, container, false);

        image = view.findViewById(R.id.eventIV);
        titleTV = view.findViewById(R.id.titleTV);
        descriptionTV = view.findViewById(R.id.descriptionTV);
        buyButton = view.findViewById(R.id.buyButton);

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "I bought it", Toast.LENGTH_SHORT).show();
            }
        });

        activity = getActivity();

        String urlString = String.format("%s:%s/%s", RequestHelper.ADDRESS, RequestHelper.PORT,
                String.format(RequestHelper.GET_EVENT_BY_ID, RequestHelper.token, eventId));

        RequestHelper.requestService(urlString, listener);

        return view;
    }


    ResponseListener listener = new ResponseListener() {
        @Override
        public void onResponse(String response) throws JSONException {
            JSONObject jsonObject = new JSONObject(response);

            if(jsonObject.getBoolean("LoginRequired")){
                activity.finish();
            }
            else if (jsonObject.getBoolean("Success")) {

                JSONObject resultObject = jsonObject.getJSONObject("Data");

                String title = resultObject.getString("Title");
                String description = resultObject.getString("Description");

                titleTV.setText(title);
                descriptionTV.setText(description);

                JSONArray imgArray = resultObject.getJSONObject("Image").getJSONArray("Data");

                Bitmap bitmap=null;
                byte[] tmp=new byte[imgArray.length()];

                for(int i=0;i<imgArray.length();i++){

                    tmp[i]=(byte)(((int)imgArray.get(i)) & 0xFF);
                }

                bitmap= BitmapFactory.decodeByteArray(tmp, 0, tmp.length);


                image.setImageBitmap(bitmap);

                image.invalidate();

            }

        }

        @Override
        public void onError(String error) {

        }
    };
}