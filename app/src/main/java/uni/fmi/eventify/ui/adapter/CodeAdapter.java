package uni.fmi.eventify.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uni.fmi.eventify.R;
import uni.fmi.eventify.entity.Score;

public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.CodeViewHolder>{

    private List<Score> scoreList;

    public CodeAdapter(List<Score> scoreList) {
        this.scoreList = scoreList;
    }


    @NonNull
    @Override
    public CodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.codes_list_row, parent, false);

        return new CodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CodeViewHolder holder, int position) {
        Score score = scoreList.get(position);

        holder.bind(score, position);
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    public static class CodeViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout rootLayout;
        private TextView positionTV;
        private TextView codeNameTV;
        private TextView codePercentTV;
        private CheckBox isUsedCB;


        public CodeViewHolder(@NonNull View itemView) {
            super(itemView);

            rootLayout = itemView.findViewById(R.id.codeRootLL);
            positionTV = itemView.findViewById(R.id.codePositionTV);
            codeNameTV = itemView.findViewById(R.id.codeNameTV);
            codePercentTV = itemView.findViewById(R.id.codePercentTV);
            isUsedCB = itemView.findViewById(R.id.codeUsedCB);
        }

        public void bind(Score score, int position){
            positionTV.setText(position + "");
            codePercentTV.setText(score.getCodePercent());
            codeNameTV.setText(score.getCode());
            isUsedCB.setChecked(score.isUsed());

            if(score.isUsed()){
                rootLayout.setBackgroundColor(Color.RED);
            }else{
                rootLayout.setBackgroundColor(Color.GREEN);
            }
        }
    }
}
