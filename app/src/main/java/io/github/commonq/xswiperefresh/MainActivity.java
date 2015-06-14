package io.github.commonq.xswiperefresh;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import io.github.commonq.lib.OnLoadListener;
import io.github.commonq.lib.RefreshLayout;
import io.github.commonq.lib.SwipyRefreshLayoutDirection;


public class MainActivity extends ActionBarActivity {

    private final static String TAG = "MainActivity";
    private RefreshLayout mRefreshLayout;
    private ListView mListView;
    private ArrayAdapter<String> mArrayAdapter;
    private ArrayList<String> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRefreshLayout = (RefreshLayout) findViewById(R.id.swipe_container);
        mListView = (ListView) findViewById(R.id.list);
        mRefreshLayout.setFooterView(this, mListView, R.layout.listview_footer);

        values = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            values.add("Item " + i);
        }
        mArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        mListView.setAdapter(mArrayAdapter);

        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        mRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);


        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        values.add(0, "Swipe Down to Refresh " + values.size());
                        mArrayAdapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        mRefreshLayout.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoad() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        values.add("Swipe Up to Load More " + values.size());
                        mArrayAdapter.notifyDataSetChanged();
                        mRefreshLayout.setLoading(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                if (mRefreshLayout !=null) {
                    mRefreshLayout.setRefreshing(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            values.add(0, "Swipe Down to Refresh " + values.size());
                            mArrayAdapter.notifyDataSetChanged();
                            mRefreshLayout.setRefreshing(false);
                        }
                    }, 2000);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
