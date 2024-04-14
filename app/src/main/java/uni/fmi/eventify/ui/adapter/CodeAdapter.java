package uni.fmi.eventify.ui.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uni.fmi.eventify.R;
import uni.fmi.eventify.entity.Score;

public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.CodeViewHolder>{

    Context context;
    private List<Score> scoreList;
    private List<Score> fullScoreList;

    public CodeAdapter(Context context, List<Score> scoreList) {
        this.scoreList = scoreList;
        fullScoreList = new ArrayList<>();
        this.context = context;
    }


    @NonNull
    @Override
    public CodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.codes_list_row, parent, false);

        return new CodeViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull CodeViewHolder holder, int position) {
        Score score = scoreList.get(position);

        holder.bind(score);
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    public void addAndUpdate(ArrayList<Score> allCodes, boolean updateList) {
        if(updateList){
            scoreList.addAll(allCodes);
            fullScoreList.addAll(allCodes);
        }else{
            scoreList.addAll(allCodes);
        }
        notifyDataSetChanged();
    }

    public void resetList(){
        scoreList.addAll(fullScoreList);
        notifyDataSetChanged();
    }

    public void sortItems(int item) {

        if(item == 0){
            Collections.sort(scoreList, Comparator.comparing(Score::getExpireDate));
        }else if(item == 1){
            Collections.sort(scoreList, Comparator.comparing(Score::getExpireDate).reversed());
        }else if(item == 2){
            Collections.sort(scoreList, Comparator.comparing(Score::getCodePercent));
        }else if(item == 3){
            Collections.sort(scoreList, Comparator.comparing(Score::getCodePercent).reversed());
        }

        notifyDataSetChanged();
    }

    public void showUnusedOnly() {

        for(int i = 0; i < scoreList.size(); i++){
            if(scoreList.get(i).isUsed()){
                scoreList.remove(i);
            }
        }

        notifyDataSetChanged();
    }

    public static class CodeViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout rootLayout;
        private TextView percentTV;
        private TextView codeNameTV;
        private TextView expireDateTV;
        private ImageButton copyB;

        Context context;

        public CodeViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            rootLayout = itemView.findViewById(R.id.codeRootLL);
            percentTV = itemView.findViewById(R.id.percentTV);
            codeNameTV = itemView.findViewById(R.id.codeNameTV);
            expireDateTV = itemView.findViewById(R.id.codeExpireTV);
            copyB = itemView.findViewById(R.id.copyB);
        }

        public void bind(Score score){
            percentTV.setText(score.getCodePercent() + "");
            expireDateTV.setText(score.getExpireDate());
            codeNameTV.setText(score.getCode());

            if(score.isUsed()){
                rootLayout.setBackgroundColor(Color.RED);
            }else{
                rootLayout.setBackgroundColor(Color.GREEN);
            }

            copyB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("", codeNameTV.getText());
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(context, "Code has been copied", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
