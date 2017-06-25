package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.R;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.MainItem;
import tut.flightbookingsystem.model.Schedule;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerClickListener.OnItemClickCallback onItemClickCallback;
    private static final int MAIN_CAROUSEL_VIEWPAGER = 0, CHILD = 1;
    private Context mContext;
    private List<MainItem> mMainItems;
    private List<Schedule> mSchedules;
    private MainCarouselPagerAdapter mMainCarouselPagerAdapter;
    private final int mType;

    public void setOnItemClickCallback(RecyclerClickListener.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public HomeAdapter(final Context context,
                       final int type) {
        mContext = context;
        mType = type;
        mMainItems = new ArrayList<>();
        mSchedules = new ArrayList<>();
    }

    public void setItems(@Nullable final List<MainItem> mainItems,
                         @Nullable final List<Schedule> schedules) {

        if (mainItems != null) {
            this.mMainItems.clear();
            this.mMainItems = mainItems;
        }

        if (schedules != null) {
            this.mSchedules.clear();
            this.mSchedules = schedules;
        }

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup,
                                                      final int viewType) {
        switch (viewType) {
            case MAIN_CAROUSEL_VIEWPAGER:
                return new ViewPagerHolder(inflate(viewGroup, R.layout.viewpager));
            case CHILD:
                final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
                return new MainItemsHolder(layoutInflater.inflate(R.layout.main_page_info_widget_layout, viewGroup, false));
        }
        throw new IllegalArgumentException("Wrong type!");
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder,
                                 final int position) {
        switch (viewHolder.getItemViewType()) {
            case MAIN_CAROUSEL_VIEWPAGER:
                onBindMainCarouselViewPagerHolder((ViewPagerHolder) viewHolder);
                break;
            case CHILD:
                onBindMainItemsHolder((MainItemsHolder) viewHolder, position);
                break;
        }
    }

    private void onBindMainCarouselViewPagerHolder(@NonNull final ViewPagerHolder holder) {
        final ViewPager viewPager = holder.viewPager;
        if (holder.viewPager != null) {
            if (viewPager.getAdapter() == null) {
                mMainCarouselPagerAdapter = new MainCarouselPagerAdapter(mContext,
                        viewPager,
                        holder.indicators);
                viewPager.setAdapter(mMainCarouselPagerAdapter);
                viewPager.addOnPageChangeListener(mMainCarouselPagerAdapter);
            }

            mMainCarouselPagerAdapter.setList(mSchedules);
        }
    }

    public void onBindMainItemsHolder(RecyclerView.ViewHolder holder, int position) {
        final MainItemsHolder vH = (MainItemsHolder) holder;
        final MainItem mainItem = getItem(position);
        if (mainItem != null) {
            vH.widget_layout.setBackgroundColor(mContext.getResources()
                    .getColor(mainItem.color));
            GlideAdapter
                    .setImage(mContext, R.drawable.ic_airplane, vH.imgImage);
            vH.txt_value.setText(mainItem.value);
            vH.txt_description.setText(mainItem.description);
            //vH.itemView.setOnClickListener(new RecyclerClickListener(position, onItemClickCallback));
        }
    }


    @Override
    public int getItemCount() {
        int count =
                mSchedules.size() == 0
                        ? 0
                        : 1;
        return count + mMainItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(final int position) {
                    final RecyclerView.Adapter adapter = recyclerView.getAdapter();
                    if (isFullSpanType(adapter.getItemViewType(position))) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Nullable
    public MainItem getItem(final int position) {
        if (getItemViewType(position) == CHILD) {
            return mMainItems.get(position - 1);
        } else {
            return null;
        }
    }

    private boolean isFullSpanType(final int type) {
        return type == MAIN_CAROUSEL_VIEWPAGER;
    }

    @Override
    public int getItemViewType(final int position) {
        if (position == 0) {
            return MAIN_CAROUSEL_VIEWPAGER;
        }

        return CHILD;
    }

    private static class ViewPagerHolder extends RecyclerView.ViewHolder {
        final ViewPager viewPager;
        final ViewGroup indicators;

        private ViewPagerHolder(@NonNull final View itemView) {
            super(itemView);
            viewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
            indicators = (ViewGroup) itemView.findViewById(R.id.indicators);
        }
    }

    class MainItemsHolder extends RecyclerView.ViewHolder {
        private LinearLayout widget_layout;
        private ImageView imgImage;
        private AppCompatTextView txt_value;
        private AppCompatTextView txt_description;

        public MainItemsHolder(final View itemView) {
            super(itemView);
            imgImage = (ImageView) itemView.findViewById(R.id.image);
            widget_layout = (LinearLayout) itemView.findViewById(R.id.widget_layout);
            txt_value = (AppCompatTextView) itemView.findViewById(R.id.value);
            txt_description = (AppCompatTextView) itemView.findViewById(R.id.description);
        }
    }

    private View inflate(final ViewGroup parent,
                         final int layoutRes) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
    }
}
