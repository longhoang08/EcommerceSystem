package com.example.mobile_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


public class ExpandHeightGridView extends GridView {
    public ExpandHeightGridView(Context context) {
        super(context);
    }
    public ExpandHeightGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ExpandHeightGridView(Context context, AttributeSet attrs,
                                int defStyle)
    {
        super(context, attrs, defStyle);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    public View getViewByPosition(int position) {
        int firstItemPosition = getFirstVisiblePosition();
        int lastItemPosition = firstItemPosition+getChildCount()-1;
        if (position < firstItemPosition || position > lastItemPosition) {
            return getAdapter().getView(position, null, this);
        } else {
            int childIndex = position-firstItemPosition;
            return getChildAt(childIndex);
        }
    }
}
