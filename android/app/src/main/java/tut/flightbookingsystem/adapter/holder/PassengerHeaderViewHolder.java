package tut.flightbookingsystem.adapter.holder;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RelativeLayout;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import tut.flightbookingsystem.R;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.PassengerHeader;

public class PassengerHeaderViewHolder extends ParentViewHolder {
    private AppCompatTextView mTxtTitle;
    private RelativeLayout rLButton;

    public PassengerHeaderViewHolder(final View itemView) {
        super(itemView);
        mTxtTitle = (AppCompatTextView) itemView.findViewById(R.id.passengerNumber);
        rLButton = (RelativeLayout) itemView.findViewById(R.id.button);
        rLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded()) {
                    collapseView();
                } else {
                    expandView();
                }
            }
        });
    }

    public void bind(final PassengerHeader passengerHeader,
                     final int position) {
        //mTxtTitle.setText(passengerHeader.getName());
        mTxtTitle.setText(String.format("Passenger: %1$s", (position + 1)));
    }

    @Override
    public boolean shouldItemViewClickToggleExpansion() {
        return false;
    }
}
