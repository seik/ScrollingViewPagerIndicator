package me.ivmg.scrollingviewpagerindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class ScrollViewPagerIndicator extends HorizontalScrollView {
    // In dp
    private int DEFAULT_DOT_SIZE = 15;
    private int DEFAULT_GAP_SIZE = 3;


    private LinearLayout linearLayout;

    private Integer dotSize;
    private Integer gapSize;
    private Integer itemMargin;
    private Integer visibleItems;
    private Integer extraItems;

    private int realNumItems = 0;
    private int numItems = 0;

    private int currentCenterLayoutPosition = 0;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            goToPosition(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    public ScrollViewPagerIndicator(Context context) {
        super(context);
    }

    public ScrollViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet,
                R.styleable.ScrollViewPagerIndicator);

        dotSize = typedArray.getDimensionPixelSize(R.styleable.ScrollViewPagerIndicator_dotSize,
                dpToPx(DEFAULT_DOT_SIZE));
        gapSize = typedArray.getDimensionPixelSize(R.styleable.ScrollViewPagerIndicator_gapSize,
                dpToPx(DEFAULT_GAP_SIZE));
        visibleItems = typedArray.getInteger(R.styleable.ScrollViewPagerIndicator_visibleItems, 0);

        typedArray.recycle();

        itemMargin = gapSize / 2;

        setHorizontalScrollBarEnabled(false);
    }

    public void attachViewPager(ViewPager viewPager) {
        if (dotSize == null) dotSize = (int) getResources().getDimension(DEFAULT_DOT_SIZE);
        if (gapSize == null) gapSize = (int) getResources().getDimension(DEFAULT_GAP_SIZE);

        if (viewPager.getAdapter() == null) return;

        viewPager.addOnPageChangeListener(onPageChangeListener);

        measureItems(viewPager);
        measureParentLayout();

        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(linearLayoutParams);

        addView(linearLayout);

        for (int i = 0; i < numItems; i++) {
            FrameLayout frameLayout = new FrameLayout(getContext());

            LinearLayout.LayoutParams frameLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            frameLayoutParams.width = dotSize;
            frameLayoutParams.height = dotSize;

            int drawable, marginStart, marginEnd;
            if (i < extraItems || i > realNumItems + 1) {
                drawable = R.drawable.circle_transparent;
            } else if (i == realNumItems + 1) {
                drawable = R.drawable.circle_selected;
            } else {
                drawable = R.drawable.circle_unselected;
            }

            if (i == 0) {
                marginStart = itemMargin;
                marginEnd = itemMargin * 2;
            } else if (i == numItems - 1) {
                marginStart = itemMargin * 2;
                marginEnd = itemMargin;
            } else {
                marginStart = itemMargin;
                marginEnd = itemMargin;
            }

            frameLayoutParams.setMargins(marginStart, 0, marginEnd, 0);

            frameLayout.setLayoutParams(frameLayoutParams);
            frameLayout.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),
                    drawable, null));

            linearLayout.addView(frameLayout, 0);
        }

        setAlphas();
    }

    private void measureItems(ViewPager viewPager) {
        this.realNumItems = viewPager.getAdapter().getCount();

        if (realNumItems < visibleItems) visibleItems = realNumItems;

        if (visibleItems > 5) {
            visibleItems = 5;
        } else if (visibleItems < 3) {
            visibleItems = 3;
        } else {
            if ((visibleItems % 2) == 0) visibleItems--;
        }

        extraItems = visibleItems / 2;

        numItems = realNumItems + extraItems * 2;

        currentCenterLayoutPosition = extraItems;
    }

    private void measureParentLayout() {
        ViewGroup.LayoutParams viewGroupLayoutParams = getLayoutParams();
        viewGroupLayoutParams.width = (itemMargin * 2) + (visibleItems * (dotSize + (itemMargin * 2)));
        viewGroupLayoutParams.height = dotSize;

        setLayoutParams(viewGroupLayoutParams);
    }

    private void goToPosition(int position) {
        int newCenterLayoutPosition = position + extraItems;
        View oldCenterView = linearLayout.getChildAt(currentCenterLayoutPosition);
        oldCenterView.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.circle_unselected, null));

        int positionToScroll = (gapSize * position) + (position * dotSize);
        smoothScrollTo(positionToScroll, 0);

        View newCenterView = linearLayout.getChildAt(newCenterLayoutPosition);
        newCenterView.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.circle_selected, null));

        currentCenterLayoutPosition = position + extraItems;

        setAlphas();
    }

    private void setAlphas() {
        // TODO check this with different number of items

        float alpha = 0.20f;
        for (int i = extraItems; i > 0; i--) {
            View leftView = linearLayout.getChildAt(currentCenterLayoutPosition - i);
            View rightView = linearLayout.getChildAt(currentCenterLayoutPosition + i);

            float currentAlpha = leftView.getAlpha();

            AlphaAnimation animation = new AlphaAnimation(currentAlpha, alpha);
            animation.setDuration(100);

            leftView.setAlpha(alpha);
            rightView.setAlpha(alpha);

            alpha = ((float) 1.0 / extraItems) + alpha;
        }

        View centerView = linearLayout.getChildAt(currentCenterLayoutPosition);
        centerView.setAlpha(1f);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}