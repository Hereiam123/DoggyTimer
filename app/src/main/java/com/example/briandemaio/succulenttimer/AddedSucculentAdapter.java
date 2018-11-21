package com.example.briandemaio.succulenttimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;

public class AddedSucculentAdapter extends RecyclerView.Adapter<AddedSucculentAdapter.SucculentViewHolder> {

    private final Context mContext;

    private final LayoutInflater mInflater;
    private List<Succulent> mSucculents;


    AddedSucculentAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context); }

    @Override
    public SucculentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.added_view_item, parent, false);
        return new SucculentViewHolder(itemView, new ResetViewClick() {
                public void updateCountdown(final SucculentViewHolder holder, Succulent current){
                    if (holder.succulentCountdownTimer != null) {
                        holder.succulentCountdownTimer.cancel();
                    }

                    long timeLeft = current.getExpiryTime() - System.currentTimeMillis();

                    holder.succulentCountdownTimer = new CountDownTimer(timeLeft, 1000) {
                        public void onTick(long millisUntilFinished) {
                            holder.succulentCountdownView.setText("" + millisUntilFinished / 1000 + " Sec ");
                        }

                        public void onFinish() {
                            holder.succulentCountdownView.setText("Water me! ");
                        }
                    }.start();
                }
            }
        );
    }

    @Override
    public void onBindViewHolder(final SucculentViewHolder holder, int position) {
        if (mSucculents != null) {
            final Succulent current = mSucculents.get(position);
            holder.succulentItemView.setText(current.getName());
            Glide.with(mContext).load(current.getImageResource()).into(holder.succulentImageView);
        } else {
            // Covers the case of data not being ready yet.
            holder.succulentItemView.setText("No Succulent");
        }
    }

    public Succulent getSucculentAtPosition (int position) {
        return mSucculents.get(position);
    }

    void setSucculents(List<Succulent> succulents){
        mSucculents = succulents;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mSucculents != null)
            return mSucculents.size();
        else return 0;
    }

    class SucculentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView succulentItemView;
        private final ImageView succulentImageView;
        private final ImageButton succulentResetView;
        private final TextView succulentCountdownView;
        private CountDownTimer succulentCountdownTimer;
        ResetViewClick mListener;

        private SucculentViewHolder(View itemView, ResetViewClick listener) {
            super(itemView);
            succulentItemView = itemView.findViewById(R.id.recycler_textview_succulent_name);
            succulentImageView = itemView.findViewById(R.id.recycler_imageview_succulent_art);
            succulentResetView = itemView.findViewById(R.id.recycler_reset_timer);
            mListener = listener;
            succulentResetView.setOnClickListener(this);
            succulentResetView.setTag(this);
            succulentCountdownView = itemView.findViewById(R.id.recycler_textview_succulent_timeleft);
        }

        @Override
        public void onClick(View view) {
            SucculentViewHolder holder = (SucculentViewHolder) (view.getTag());
            int pos = holder.getAdapterPosition();
            Succulent editCurrent =  mSucculents.get(pos);
            long newExpiryTime = System.currentTimeMillis() + 30 * 300;
            editCurrent.setExpiryTime(newExpiryTime);
            mListener.updateCountdown(holder, editCurrent);
        }
    }
    public interface ResetViewClick
    {
        void updateCountdown(SucculentViewHolder holder, Succulent succulent);
    }
}
