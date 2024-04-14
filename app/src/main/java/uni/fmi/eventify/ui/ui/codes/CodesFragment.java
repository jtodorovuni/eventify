package uni.fmi.eventify.ui.ui.codes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uni.fmi.eventify.R;
import uni.fmi.eventify.entity.Score;
import uni.fmi.eventify.helper.RequestHelper;
import uni.fmi.eventify.helper.ResponseListener;
import uni.fmi.eventify.ui.adapter.CodeAdapter;

public class CodesFragment extends Fragment {
    RecyclerView codeRV;
    CodeAdapter adapter;
    CheckBox showUnusedCB;
    Spinner sortingS;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_codes, container, false);

        codeRV = view.findViewById(R.id.codesRV);
        showUnusedCB = view.findViewById(R.id.showAllCB);
        sortingS = view.findViewById(R.id.sortingS);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.coupons_sorting_options,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        sortingS.setAdapter(spinnerAdapter);

        sortingS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.sortItems(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        ArrayList<Score> score = new ArrayList<>();
        adapter = new CodeAdapter(getContext(), score);
        codeRV.setLayoutManager(new LinearLayoutManager(getContext()));
        codeRV.setAdapter(adapter);

        String urlString = String.format("%s:%s/%s", RequestHelper.ADDRESS, RequestHelper.PORT,
                    String.format(RequestHelper.GET_CODES, RequestHelper.token));


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

                adapter.addAndUpdate(allCodes, true);

                Log.wtf("response", response);
            }

            @Override
            public void onError(String error) {

            }
        });


        showUnusedCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    adapter.resetList();
                }else{
                    adapter.showUnusedOnly();
                }
            }
        });


        return view;

    }


}