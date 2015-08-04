package eu.execom.dependencyinjection.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import eu.execom.dependencyinjection.R;
import eu.execom.dependencyinjection.activity.NewsDetails_;
import eu.execom.dependencyinjection.model.News;

@EViewGroup(R.layout.item_news)
public class NewsItemView extends LinearLayout {

    @ViewById
    TextView title;

    @ViewById
    CardView cardView;

    public NewsItemView(Context context) {
        super(context);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void bind(final News news) {
        title.setText(news.getTitle());

        cardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = NewsDetails_.intent(getContext()).news(news).get();
                getContext().startActivity(intent);
            }
        });
    }

}
