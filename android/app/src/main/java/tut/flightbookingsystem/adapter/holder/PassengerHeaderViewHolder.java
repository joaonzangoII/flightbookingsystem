package tut.flightbookingsystem.adapter.holder;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import tut.flightbookingsystem.R;

public class PassengerHeaderViewHolder extends GroupViewHolder {
    private AppCompatTextView mTxtTitle;
    private Button rLButton;

    public PassengerHeaderViewHolder(final View itemView) {
        super(itemView);
        mTxtTitle = (AppCompatTextView) itemView.findViewById(R.id.passengerNumber);
    }

    public void setHeaderTitle(final ExpandableGroup group,
                               final int position) {
        mTxtTitle.setText(group.getTitle());
        mTxtTitle.setText(String.format("Passenger: %1$s", (position + 1)));

    }

    @Override
    public void expand() {
        //animateExpand();
    }

    @Override
    public void collapse() {
        //animateCollapse();
    }
}
