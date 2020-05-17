package com.example.honoursproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.honoursproject.Model.SimplifiedPainRecord;

import org.w3c.dom.Text;

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
        holder.painLoc.setText(simplePainRecordList.get(position).getPainLocation().get(0));
        holder.avgPain.setText("Average Pain: " + simplePainRecordList.get(position).getAvgPainLevel());
        holder.maxPain.setText("Maximum pain: " + simplePainRecordList.get(position).getMaxPainLevel());
        holder.minPain.setText("Minimum pain: " + simplePainRecordList.get(position).getMinPainLevel());
        holder.eDate.setText("Ended: " + simplePainRecordList.get(position).getEndDate());
        holder.sDate.setText("Started: " + simplePainRecordList.get(position).getStartDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = v.findViewById(R.id.linear_item);
                if(layout.getVisibility() == View.GONE) {
                    layout.setVisibility(View.VISIBLE);
                }
                else{
                    layout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return simplePainRecordList.size();
    }

    public class SimplePainRecordViewHolder extends RecyclerView.ViewHolder {
        public TextView painLoc;
        public TextView avgPain;
        public TextView maxPain;
        public TextView minPain;
        public TextView sDate;
        public TextView eDate;
        public LinearLayout layout;

        public SimplePainRecordViewHolder(View view) {
            super(view);
            painLoc = (TextView) view.findViewById(R.id.pain_loc_text);
            avgPain = (TextView) view.findViewById(R.id.avg_pain_text);
            maxPain = (TextView) view.findViewById(R.id.max_pain_text);
            minPain = (TextView) view.findViewById(R.id.min_pain_text);
            eDate = (TextView) view.findViewById(R.id.end_date_text);
            sDate = (TextView) view.findViewById(R.id.date_text);
            layout = (LinearLayout) view.findViewById(R.id.linear_item);
        }
    }


}
