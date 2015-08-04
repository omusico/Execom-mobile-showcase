package eu.execom.dependencyinjection.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;

import eu.execom.dependencyinjection.R;
import eu.execom.dependencyinjection.adapter.NewsAdapter;
import eu.execom.dependencyinjection.model.News;
import eu.execom.dependencyinjection.service.NewsService;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @RestService
    NewsService newsService;

    @ViewById
    RecyclerView list;

    private final NewsAdapter adapter = new NewsAdapter(this);

    @AfterViews
    void initNewsList(){
        list.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(layoutManager);
        getNews();
    }

    @Background
    void getNews(){
        populateNewsList(newsService.getNews());
    }

    @UiThread
    void populateNewsList(List<News> newsList){
        adapter.updateNews(newsList);
    }

}
