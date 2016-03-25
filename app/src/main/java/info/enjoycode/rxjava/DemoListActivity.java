package info.enjoycode.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenyuanming on 16/3/25.
 */
public class DemoListActivity extends BaseActivity {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    Class[] clazz = new Class[]{
            CountDownDemoActivity.class
            , SearchActivity.class
            , FormValidateActivity.class
            , AdditionActivity.class
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_list);
        ButterKnife.bind(this);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter<DemoListVH>() {

            @Override
            public DemoListVH onCreateViewHolder(ViewGroup parent, int viewType) {
                return new DemoListVH(View.inflate(DemoListActivity.this, R.layout.item, null));
            }

            @Override
            public void onBindViewHolder(DemoListVH holder, int position) {
                holder.tv.setText(clazz[position].getSimpleName());

                holder.tv.setOnClickListener((v -> startActivity(clazz[position])));
            }

            @Override
            public int getItemCount() {
                return clazz.length;
            }
        });
    }

    private void startActivity(Class aClass) {
        startActivity(new Intent(this, aClass));
    }

    class DemoListVH extends RecyclerView.ViewHolder {
        @Bind(R.id.tv)
        TextView tv;

        public DemoListVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
