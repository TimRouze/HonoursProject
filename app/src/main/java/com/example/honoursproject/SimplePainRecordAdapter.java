package com.example.honoursproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.honoursproject.Model.SimplifiedPainRecord;

import java.util.List;

public class SimplePainRecordAdapter extends RecyclerView.Adapter<SimplePainRecordAdapter.SimplePainRecordViewHolder>{

    private List<SimplifiedPainRecord> simplePainRecordList;

    public SimplePainRecordAdapter(List<SimplifiedPainRecord> simplePainRecordList) {
        this.simplePainRecordList = simplePainRecordList;
    }

    @Override
    public SimplePainRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element, parent, false);

        return new SimplePainRecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimplePainRecordViewHolder holder, int position) {
        holder.painLoc.setText(simplePainRecordList.get(position).getPainLocation());
        holder.avgPain.setText("Average Pain: " + simplePainRecordList.get(position).getAvgPainLevel());
    }

    @Override
    public int getItemCount() {
        return simplePainRecordList.size();
    }

    public class SimplePainRecordViewHolder extends RecyclerView.ViewHolder {
        public TextView painLoc;
        public TextView avgPain;

        public SimplePainRecordViewHolder(View view) {
            super(view);
            painLoc = (TextView) view.findViewById(R.id.pain_loc_text);
            avgPain = (TextView) view.findViewById(R.id.avg_pain_text);
        }
    }


}
