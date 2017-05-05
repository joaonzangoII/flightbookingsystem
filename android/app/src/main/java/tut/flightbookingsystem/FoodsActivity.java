package tut.flightbookingsystem;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tut.flightbookingsystem.adapter.FoodsAdapter;
import tut.flightbookingsystem.listener.RecyclerClickListener;
import tut.flightbookingsystem.model.Food;
import tut.flightbookingsystem.views.SupportSwipeRefreshLayout;

public class FoodsActivity extends AppCompatActivity implements FoodItemDetail.OnFragmentInteractionListener {
    private FoodsAdapter foodsAdapter;
    private List<Food> foodsList = new ArrayList<>();
    private ProgressBar progressBar;
    private SupportSwipeRefreshLayout mSwipeRefreshLayout;
    private SessionManager session;

    private RecyclerClickListener.OnItemClickCallback onItemClickCallback =
            new RecyclerClickListener.OnItemClickCallback() {

                @Override
                public void onItemClicked(final View view,
                                          final int parentPosition,
                                          final int childPosition) {
                }

                @Override
                public void onItemClicked(final View view,
                                          int position) {
                    if (position >= 0) {
                        final Food food = foodsList.get(position);
                        showEditDialog(food);
                    }
                }
            };

    final Handler requestHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            final Bundle data = message.getData();
            if (!data.getBoolean(Constant.ERROR)) {
                final Gson gson = new GsonBuilder().create();
                final Type type = new TypeToken<List<Food>>() {
                }.getType();
                progressBar.setVisibility(View.GONE);
                foodsList = gson.fromJson(data.getString(Constant.FOODS), type);
                foodsAdapter.setItems(foodsList);
                onItemsLoadComplete();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        setTitle("Foods");
        session = new SessionManager(this);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mSwipeRefreshLayout = (SupportSwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        RequestManager.getFoods(session, requestHandler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodsAdapter = new FoodsAdapter();
        foodsAdapter.setItems(foodsList);
        foodsAdapter.setOnItemClickCallback(onItemClickCallback);
        recyclerView.setAdapter(foodsAdapter);

        mSwipeRefreshLayout.setInternalRecyclerView(recyclerView);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
    }

    void refreshItems() {
        RequestManager.getFoods(session, requestHandler);
        // Load complete
    }

    void onItemsLoadComplete() {
        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void showEditDialog(final Food food) {
        FragmentManager fm = getSupportFragmentManager();
        FoodItemDetail foodItemDetail = FoodItemDetail.newInstance(food);
        foodItemDetail.show(fm, "fragment_food_detail");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
