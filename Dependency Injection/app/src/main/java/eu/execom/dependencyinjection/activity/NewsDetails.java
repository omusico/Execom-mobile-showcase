package eu.execom.dependencyinjection.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import eu.execom.dependencyinjection.R;
import eu.execom.dependencyinjection.model.News;

@EActivity(R.layout.activity_news_details)
public class NewsDetails extends AppCompatActivity {

    @Extra
    News news;

    @ViewById
    TextView title;

    @ViewById
    TextView text;

    @AfterViews
    void showNewsDetails(){
        title.setText(news.getTitle());
        text.setText(news.getText());
    }

}
