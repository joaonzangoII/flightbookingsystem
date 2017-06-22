package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.MainActivity;
import tut.flightbookingsystem.R;
import tut.flightbookingsystem.base.BaseActivity;
import tut.flightbookingsystem.manager.SessionManager;
import tut.flightbookingsystem.model.Schedule;

public class MainCarouselPagerAdapter extends BaseLoopPagerAdapter {
    private final List<Schedule> mSchedules;
    private final ViewGroup mIndicators;
    private int mLastPosition;
    private SessionManager sessionManager;
    private Context mContext;

    public MainCarouselPagerAdapter(final Context context,
                                    final ViewPager viewPager,
                                    final ViewGroup indicators) {
        super(viewPager);
        this.mContext = context;
        this.mIndicators = indicators;
        this.mSchedules = new ArrayList<>();
        this.sessionManager = new SessionManager(context);
    }

    public void setList(final List<Schedule> schedules) {
        mSchedules.clear();
        mSchedules.addAll(schedules);
        notifyDataSetChanged();
    }

    /**
     * oh shit! An indicator view is badly needed!
     * this shit have no animation at all.
     */
    private void initIndicators() {
        if (mIndicators.getChildCount() != mSchedules.size() && mSchedules.size() > 1) {
            mIndicators.removeAllViews();
            final Resources res = mIndicators.getResources();
            final int size = res.getDimensionPixelOffset(R.dimen.indicator_size);
            final int margin = res.getDimensionPixelOffset(R.dimen.indicator_margin);
            for (int i = 0; i < getPagerCount(); i++) {
                final ImageView indicator = new ImageView(mIndicators.getContext());
                indicator.setAlpha(180);
                final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
                lp.setMargins(margin, 0, 0, 0);
                lp.gravity = Gravity.CENTER;
                indicator.setLayoutParams(lp);
                final Drawable drawable = res.getDrawable(R.drawable.selector_indicator);
                indicator.setImageDrawable(drawable);
                mIndicators.addView(indicator);
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        initIndicators();
        super.notifyDataSetChanged();
    }

    @Override
    public int getPagerCount() {
        return mSchedules.size();
    }

    @Override
    public Object getItem(final int position) {
        return mSchedules.get(position);
    }

    @Override
    public View getView(final int position,
                        @NonNull View convertView,
                        @NonNull final ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.main_carousel_viewpager_item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Schedule schedule = mSchedules.get(position);
        GlideAdapter.setImage(mContext, schedule.destination_airport.getImage(sessionManager), holder.image);
        holder.departure.setText(String.format("From:%1$s(%2$s)",
                schedule.origin_airport.name,
                schedule.origin_airport.iata_airport_code));
        holder.arrival.setText(String.format("To:%1$s(%2$s)",
                schedule.destination_airport.name,
                schedule.destination_airport.iata_airport_code));
        holder.date.setText(String.format("%1$s",
                schedule.date));
        holder.price.setText(String.format("R%.2f", schedule.flight.getStartPrice()));
        //        click_listener(convertView, servicoModel);
        //        click_listener(holder.viewPagerItem.nome, servicoModel);

        return convertView;
    }

    @Override
    public void onPageItemSelected(final int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mIndicators.getChildAt(mLastPosition).setActivated(false);
            mIndicators.getChildAt(position).setActivated(true);
        }
        mLastPosition = position;
    }

    private static class ViewHolder {
        private ImageView image;
        private AppCompatTextView departure;
        private AppCompatTextView arrival;
        private AppCompatTextView date;
        private AppCompatTextView price;

        public ViewHolder(final View itemView) {
            this.image = (ImageView) itemView.findViewById(R.id.image);
            this.departure = (AppCompatTextView) itemView.findViewById(R.id.departure);
            this.arrival = (AppCompatTextView) itemView.findViewById(R.id.arrival);
            this.date = (AppCompatTextView) itemView.findViewById(R.id.date);
            this.price = (AppCompatTextView) itemView.findViewById(R.id.price);
        }
    }

    private void click_listener(final View view,
                                final Schedule schedule) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //BaseActivity.goToSchedule(schedule);
            }
        });
    }
}
