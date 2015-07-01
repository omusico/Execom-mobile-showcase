package eu.execom.toolbox3materialdesign;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

import eu.execom.toolbox3materialdesign.adapter.RecyclerAdapter;


public class MainActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    private View imageView;
    private View overlayView;
    private View recyclerViewBackground;
    private TextView titleView;
    private int actionBarSize;
    private int flexibleSpaceImageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        actionBarSize = getActionBarSize();

        final ObservableRecyclerView recyclerView = (ObservableRecyclerView) findViewById(R.id.recycler);
        recyclerView.setScrollViewCallbacks(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);

        final View headerView = LayoutInflater.from(this).inflate(R.layout.recycler_header, null);
        headerView.post(new Runnable() {
            @Override
            public void run() {
                headerView.getLayoutParams().height = flexibleSpaceImageHeight;
            }
        });
        final RecyclerAdapter adapter = new RecyclerAdapter(this, getDummyData(20), headerView);
        recyclerView.setAdapter(adapter);

        imageView = findViewById(R.id.image);
        overlayView = findViewById(R.id.overlay);

        titleView = (TextView) findViewById(R.id.title);
        titleView.setText(getTitle());
        setTitle(null);

        // recyclerViewBackground makes RecyclerView's background except header view.
        recyclerViewBackground = findViewById(R.id.list_background);

        // since you cannot programmatically add a header view to a RecyclerView we added an empty view as the header
        // in the adapter and then are shifting the views OnCreateView to compensate
        final float scale = 1 + MAX_TEXT_SCALE_DELTA;
        recyclerViewBackground.post(new Runnable() {
            @Override
            public void run() {
                ViewHelper.setTranslationY(recyclerViewBackground, flexibleSpaceImageHeight);
            }
        });
        ViewHelper.setTranslationY(overlayView, flexibleSpaceImageHeight);
        titleView.post(new Runnable() {
            @Override
            public void run() {
                ViewHelper.setTranslationY(titleView, (int) (flexibleSpaceImageHeight - titleView.getHeight() * scale));
                ViewHelper.setPivotX(titleView, 0);
                ViewHelper.setPivotY(titleView, 0);
                ViewHelper.setScaleX(titleView, scale);
                ViewHelper.setScaleY(titleView, scale);
            }
        });
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

        // Translate overlay and image
        final float flexibleRange = flexibleSpaceImageHeight - actionBarSize;
        final int minOverlayTransitionY = actionBarSize - overlayView.getHeight();
        ViewHelper.setTranslationY(overlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(imageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Translate list background
        ViewHelper.setTranslationY(recyclerViewBackground, Math.max(0, -scrollY + flexibleSpaceImageHeight));

        // Change alpha of overlay
        ViewHelper.setAlpha(overlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        final float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        setPivotXToTitle();
        ViewHelper.setPivotY(titleView, 0);
        ViewHelper.setScaleX(titleView, scale);
        ViewHelper.setScaleY(titleView, scale);

        // Translate title text
        final int maxTitleTranslationY = (int) (flexibleSpaceImageHeight - titleView.getHeight() * scale);
        final int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(titleView, titleTranslationY);

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setPivotXToTitle() {
        final Configuration config = getResources().getConfiguration();
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            ViewHelper.setPivotX(titleView, findViewById(android.R.id.content).getWidth());
        } else {
            ViewHelper.setPivotX(titleView, 0);
        }
    }

    protected int getActionBarSize() {
        final TypedValue typedValue = new TypedValue();
        final int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        final int indexOfAttrTextSize = 0;
        final TypedArray typedArray = obtainStyledAttributes(typedValue.data, textSizeAttr);
        final int actionBarSize = typedArray.getDimensionPixelSize(indexOfAttrTextSize, -1);
        typedArray.recycle();
        return actionBarSize;
    }

    public static ArrayList<String> getDummyData(int numberOfItems) {
        final ArrayList<String> items = new ArrayList<>();
        for (int i = 1; i <= numberOfItems; i++) {
            items.add("Item " + i);
        }
        return items;
    }

}
