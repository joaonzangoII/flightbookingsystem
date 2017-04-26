package tut.flightbookingsystem;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

public class FoodsActivity extends AppCompatActivity {
    private FoodsAdapter foodsAdapter;
    private List<Food> foodsList = new ArrayList<>();
    private ProgressBar progressBar;

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
                 /*   final Intent i = new Intent(FoodsActivity.this, BookingActivity.class);
                    if (position >= 0) {
                                Schedule schedule = mSchedules.get(position);
                                final Gson gson = new GsonBuilder().create();
                                final Type type = new TypeToken<Schedule>() {
                                }.getType();
                                session.setSchedule(gson.toJson(schedule, type));
                                Log.e(TAG, schedule.toString());
                                i.putExtra(Constant.NUM_PEOPLE, Integer.valueOf(num_people));
                                startActivity(i);
                    }*/
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
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        setTitle("Foods");
        final SessionManager session = new SessionManager(this);
        RequestManager.getFoods(session, requestHandler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodsAdapter = new FoodsAdapter();
        foodsAdapter.setItems(foodsList);
        foodsAdapter.setOnItemClickCallback(onItemClickCallback);
        recyclerView.setAdapter(foodsAdapter);
    }
}
