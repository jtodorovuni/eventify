package uni.fmi.eventify.ui.ui.codes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uni.fmi.eventify.R;
import uni.fmi.eventify.databinding.FragmentGalleryBinding;
import uni.fmi.eventify.entity.Score;
import uni.fmi.eventify.helper.RequestHelper;
import uni.fmi.eventify.helper.ResponseListener;
import uni.fmi.eventify.ui.adapter.CodeAdapter;

public class CodesFragment extends Fragment {
    RecyclerView codeRV;
    CodeAdapter adapter;
    CheckBox showAllCB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_codes, container, false);

        codeRV = view.findViewById(R.id.codesRV);
        showAllCB = view.findViewById(R.id.showAllCB);

        ArrayList<Score> score = new ArrayList<>();
        Score newCode = new Score();

        newCode.setCode("asd");
        newCode.setCodePercent(33);
        newCode.setCreatedAt(1312313213);
        newCode.setUsed(false);
        score.add(newCode);

        Score newCode2 = new Score();

        newCode2.setCode("asdffffff");
        newCode2.setCodePercent(33);
        newCode2.setCreatedAt(1312313213);
        newCode2.setUsed(true);
        score.add(newCode2);

        adapter = new CodeAdapter(score);
        codeRV.setLayoutManager(new LinearLayoutManager(getContext()));
        codeRV.setAdapter(adapter);

        String urlString = String.format("%s:%s/%s", RequestHelper.ADDRESS, RequestHelper.PORT,
                    String.format(RequestHelper.GET_CODES, RequestHelper.token));

        /*
        RequestHelper.requestService(urlString, new ResponseListener() {
            @Override
            public void onResponse(String response) throws JSONException {
                JSONObject jsonResponse = new JSONObject(response);

                JSONArray codesArray = jsonResponse.getJSONArray("Data");

                ArrayList<Score> allCodes = new ArrayList<>();

                for (int i = 0; i < codesArray.length(); i++)
                {
                    JSONObject codeJson = codesArray.getJSONObject(i);
                    //{"Code":"61e2980b780c4cf4","CodePercent":18,"CreatedAt":1710599009,"HasBeenUsed":true,"Id":14,"Points":15500,"UserId":4}
                    Score newCode = new Score();

                    newCode.setCode(codeJson.getString("Code"));
                    newCode.setCodePercent(codeJson.getInt("CodePercent"));
                    newCode.setCreatedAt(codeJson.getLong("CreatedAt"));
                    newCode.setUsed(codeJson.getBoolean("HasBeenUsed"));
                    allCodes.add(newCode);
                }

                adapter.addAndUpdate(allCodes);

                Log.wtf("response", response);
            }

            @Override
            public void onError(String error) {

            }
        });
        */

        return view;

    }


}