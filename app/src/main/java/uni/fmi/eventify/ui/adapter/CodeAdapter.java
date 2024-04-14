package uni.fmi.eventify.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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

        holder.bind(score);
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    public void addAndUpdate(ArrayList<Score> allCodes) {
        scoreList = allCodes;
        notifyDataSetChanged();
    }

    public static class CodeViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout rootLayout;
        private TextView percentTV;
        private TextView codeNameTV;
        private TextView expireDateTV;
        private ImageButton copyB;


        public CodeViewHolder(@NonNull View itemView) {
            super(itemView);

            rootLayout = itemView.findViewById(R.id.codeRootLL);
            percentTV = itemView.findViewById(R.id.percentTV);
            codeNameTV = itemView.findViewById(R.id.codeNameTV);
            expireDateTV = itemView.findViewById(R.id.codeExpireTV);
            copyB = itemView.findViewById(R.id.copyB);
        }

        public void bind(Score score){
            percentTV.setText(score.getCodePercent());
            expireDateTV.setText(score.getExpireDate());
            codeNameTV.setText(score.getCode());

            if(score.isUsed()){
                rootLayout.setBackgroundColor(Color.RED);
            }else{
                rootLayout.setBackgroundColor(Color.GREEN);
            }
        }
    }
}
