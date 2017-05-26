package com.shen.keep.core.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shen.keep.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by edianzu on 2017/5/26.
 */
public class KeepTitleLayout extends RelativeLayout {

    @Bind(R.id.keep_title_left)
    ImageView keepTitleLeft;
    @Bind(R.id.keep_title_tv)
    TextView keepTitleTv;
    @Bind(R.id.keep_title_right)
    ImageView keepTitleRight;
    @Bind(R.id.left_title_layout)
    FrameLayout leftTitleLayout;
    @Bind(R.id.right_title_layout)
    FrameLayout rightTitleLayout;

    public KeepTitleLayout(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {

        View view = LayoutInflater.from(context).inflate(R.layout.keep_title_layout, this, true);
        ButterKnife.bind(this);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.KeepTitleStyle);
        if (attributes != null) {
            //处理titleBar背景色
            int titleBarBackGround = attributes.getResourceId(R.styleable.KeepTitleStyle_title_background_color, Color.GREEN);
            setBackgroundResource(titleBarBackGround);
            //先处理左边按钮
            //获取是否要显示左边按钮
            boolean leftButtonVisible = attributes.getBoolean(R.styleable.KeepTitleStyle_left_button_visible, true);
            if (leftButtonVisible) {
                keepTitleLeft.setVisibility(View.VISIBLE);
            } else {
                keepTitleLeft.setVisibility(View.INVISIBLE);
            }
            //设置左边按钮的文字
            String leftButtonText = attributes.getString(R.styleable.KeepTitleStyle_left_button_text);
            if (!TextUtils.isEmpty(leftButtonText)) {
                //keepTitleLeft.setText(leftButtonText);
                //设置左边按钮文字颜色
                int leftButtonTextColor = attributes.getColor(R.styleable.KeepTitleStyle_left_button_text_color, Color.WHITE);
                //keepTitleLeft.setTextColor(leftButtonTextColor);
            } else {
                //设置左边图片icon 这里是二选一 要么只能是文字 要么只能是图片
                int leftButtonDrawable = attributes.getResourceId(R.styleable.KeepTitleStyle_left_button_drawable, R.drawable.title_left_back_icon);
                if (leftButtonDrawable != -1) {
                    keepTitleLeft.setBackgroundResource(leftButtonDrawable);
                }
            }

            //处理标题
            //先获取标题是否要显示图片icon
            int titleTextDrawable = attributes.getResourceId(R.styleable.KeepTitleStyle_title_text_drawable, -1);
            if (titleTextDrawable != -1) {
                keepTitleTv.setBackgroundResource(titleTextDrawable);
            } else {
                //如果不是图片标题 则获取文字标题
                String titleText = attributes.getString(R.styleable.KeepTitleStyle_title_text);
                if (!TextUtils.isEmpty(titleText)) {
                    keepTitleTv.setText(titleText);
                }
                //获取标题显示颜色
                int titleTextColor = attributes.getColor(R.styleable.KeepTitleStyle_title_text_color, Color.WHITE);
                keepTitleTv.setTextColor(titleTextColor);
            }

            //先处理右边按钮
            //获取是否要显示右边按钮
            boolean rightButtonVisible = attributes.getBoolean(R.styleable.KeepTitleStyle_right_button_visible, true);
            if (rightButtonVisible) {
                keepTitleRight.setVisibility(View.VISIBLE);
            } else {
                keepTitleRight.setVisibility(View.INVISIBLE);
            }
            //设置右边按钮的文字
            String rightButtonText = attributes.getString(R.styleable.KeepTitleStyle_right_button_text);
            if (!TextUtils.isEmpty(rightButtonText)) {
                //keepTitleRight.setText(rightButtonText);
                //设置右边按钮文字颜色
                int rightButtonTextColor = attributes.getColor(R.styleable.KeepTitleStyle_right_button_text_color, Color.WHITE);
                //keepTitleRight.setTextColor(rightButtonTextColor);
            } else {
                //设置右边图片icon 这里是二选一 要么只能是文字 要么只能是图片
                int rightButtonDrawable = attributes.getResourceId(R.styleable.KeepTitleStyle_right_button_drawable, -1);
                if (rightButtonDrawable != -1) {
                    keepTitleRight.setBackgroundResource(rightButtonDrawable);
                }
            }
            attributes.recycle();
        }
    }

    public KeepTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public KeepTitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KeepTitleLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    @OnClick({R.id.keep_title_left, R.id.keep_title_right, R.id.left_title_layout, R.id.right_title_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.keep_title_left:
            case R.id.left_title_layout:
                if (null != leftClickListener) {
                    leftClickListener.onLeftClick();
                }
                break;
            case R.id.keep_title_right:
            case R.id.right_title_layout:
                if (null != rightClickListner) {
                    rightClickListner.onRightClick();
                }
                break;
        }
    }

    private LeftClickListener leftClickListener;
    private RightClickListener rightClickListner;


    public void setRightClickListner(RightClickListener rightClickListner) {
        this.rightClickListner = rightClickListner;
    }

    public void setLeftClickListener(LeftClickListener leftClickListener) {
        this.leftClickListener = leftClickListener;
    }

    public interface LeftClickListener {
        public void onLeftClick();
    }

    public interface RightClickListener {
        public void onRightClick();
    }
}
