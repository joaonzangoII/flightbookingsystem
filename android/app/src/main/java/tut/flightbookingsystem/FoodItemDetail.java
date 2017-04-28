package tut.flightbookingsystem;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import tut.flightbookingsystem.adapter.GlideAdapter;
import tut.flightbookingsystem.model.Food;

public class FoodItemDetail extends DialogFragment {
    private static final String ARG_PARAM1 = "food";
    private Food mFood;

    private OnFragmentInteractionListener mListener;

    public FoodItemDetail() {
        // Required empty public constructor
    }

    public static FoodItemDetail newInstance(final Food food) {
        FoodItemDetail fragment = new FoodItemDetail();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, food);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFood = (Food) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_item_detail, container, false);
    }

    @Override
    public void onViewCreated(final View view,
                              @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView imageView = (ImageView) view.findViewById(R.id.image);
        final TextView name = (TextView) view.findViewById(R.id.name);
        final TextView description = (TextView) view.findViewById(R.id.description);

        getDialog().setTitle(mFood.name);

        GlideAdapter.setImage(getActivity(), mFood.image, imageView);
        name.setText(mFood.name);
        description.setText(mFood.description);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
