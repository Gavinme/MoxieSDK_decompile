package com.moxie.crawler.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moxie.crawler.R;

/**
 * 通用标题
 *
 * @author Taoweisong
 */
public class TitleLayout extends RelativeLayout {

    private TextView mTextTitle;
    private TextView mTextLeft;
    private TextView mTextRight;
    private View mTitleLayoutView;

    public TitleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.common_title, this);
        mTextTitle = (TextView) view.findViewById(R.id.TextView_Title);
        mTextLeft = (TextView) view.findViewById(R.id.TextView_Back);
        mTextRight = (TextView) view.findViewById(R.id.TextView_Refresh);
        mTitleLayoutView = view.findViewById(R.id.RelativeLayout_Title);
    }

    public View getTitleLayout() {
        return mTitleLayoutView;
    }

    public TextView getLeftImage() {
        return mTextLeft;
    }

    public TextView getRightImage() {
        return mTextRight;
    }

    public void setTitle(String title) {
        mTextTitle.setText(title);
    }
}
