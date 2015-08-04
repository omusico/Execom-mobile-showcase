package eu.execom.dependencyinjection.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import eu.execom.dependencyinjection.model.News;

public class NewsAdapter extends RecyclerViewAdapterBase<News, NewsItemView> {

    Context context;

    public NewsAdapter(Context context){
        this.context = context;
    }

    @Override
    protected NewsItemView onCreateItemView(ViewGroup parent, int viewType) {
        return NewsItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<NewsItemView> holder, int position) {
        final NewsItemView view = holder.getView();
        final News news = items.get(position);
        view.bind(news);
    }

    public void updateNews(List<News> newsList){
        items = newsList;
        notifyDataSetChanged();
    }

}
