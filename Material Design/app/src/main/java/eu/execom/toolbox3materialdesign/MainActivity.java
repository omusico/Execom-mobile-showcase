package eu.execom.toolbox3materialdesign;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import eu.execom.toolbox3materialdesign.adapter.RecyclerAdapter;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;

    @ViewById
    RecyclerView list;

    @Bean
    RecyclerAdapter adapter;

    @AfterViews
    void init(){
        setSupportActionBar(toolbar);

        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));

        final ArrayList<String> strings = new ArrayList<>();
        for (int i = 1; i <= 100; i++){
            strings.add("Some text " + i);
        }
        adapter.update(strings);
    }

}
