package com.nitin.pole.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nitin.pole.R;
import com.nitin.pole.repository.local.LocalCache;
import com.nitin.pole.repository.pojo.Survey;
import com.nitin.pole.views.fragments.DashBoardFragment.OnListFragmentInteractionListener;

import java.util.List;

public class SurveysRecyclerViewAdapter extends RecyclerView.Adapter<SurveysRecyclerViewAdapter.ViewHolder> {

    private List<Survey> mValues;
    private OnListFragmentInteractionListener mListener;
    private LocalCache localCacheInstance;
    private Context context;

    public SurveysRecyclerViewAdapter(Context context, List<Survey> items, OnListFragmentInteractionListener listener) {
        this.context = context;
        mValues = items;
        mListener = listener;
        localCacheInstance = LocalCache.getInstance();
    }

    public SurveysRecyclerViewAdapter(Context context, List<Survey> items) {
        this.context = context;
        mValues = items;
        localCacheInstance = LocalCache.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_survey_container, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);

        holder.tvName.setText(holder.mItem.getSurveyText());
        holder.tvDepartment.setText(holder.mItem.getDepartmentId());
        holder.tvDate.setText(holder.mItem.getTime());

        if (holder.mItem.getSTATUS_FLAG() == 0) {
            holder.tvStatus.setText("Participate");
        } else {
            holder.tvStatus.setText("View Details");
        }

        if (mListener != null) {
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvName;
        public final TextView tvDate;
        public final TextView tvDepartment;
        public final Button tvStatus;

        public Survey mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvName = (TextView) view.findViewById(R.id.tv_survey_name);
            tvDepartment = (TextView) view.findViewById(R.id.tv_survey_from);
            tvDate = (TextView) view.findViewById(R.id.tv_survey_date);
            tvStatus = (Button) view.findViewById(R.id.tv_survey_status_indicator);
        }
    }
}
