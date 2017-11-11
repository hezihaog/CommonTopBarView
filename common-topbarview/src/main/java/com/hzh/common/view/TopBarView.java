package com.hzh.common.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.util.SparseArrayCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Package: com.hzh.liba.core.ui.activity.widget
 * FileName: LoginTopBar
 * Date: on 2017/11/11  上午10:24
 * Auther: zihe
 * Descirbe: 登录注册顶部TopBar
 * Email: hezihao@linghit.com
 */

public class TopBarView extends FrameLayout {
    private View rootLayout;
    //左边的图标
    private TextView tvLeft;
    //左边的文字
    private ImageView ivLeft;
    //中间的标题
    private TextView tvTitle;
    //右边的文字
    private TextView tvRight;
    //右边的图标
    private ImageView ivRight;
    private ViewHolder viewHolder;

    public TopBarView(@NonNull Context context) {
        super(context);
        init();
    }

    public TopBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TopBarView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TopBarView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        rootLayout = LayoutInflater.from(getContext()).inflate(R.layout.view_top_bar_view, this, true);
        viewHolder = ViewHolder.newInstance(rootLayout);
        tvLeft = viewHolder.findView(R.id.tv_left);
        ivLeft = viewHolder.findView(R.id.iv_left);
        tvTitle = viewHolder.findView(R.id.tv_title);
        tvRight = viewHolder.findView(R.id.tv_right);
        ivRight = viewHolder.findView(R.id.iv_right);
    }

    /**
     * 应用配置对象
     *
     * @param config 配置对象
     */
    public void applyConfig(TopBarConfig config) {
        if (config != null) {
            viewHolder.setText(R.id.tv_left, config.getLeftText());
            viewHolder.displayImg(R.id.iv_left, config.getLeftImageRes());
            //将res绑定到view的tag，方便恢复用
            viewHolder.findView(R.id.iv_left).setTag(ViewHolder.TAG_LEFT_IMG_RES_ID, config.getLeftImageRes());
            viewHolder.setText(R.id.tv_title, config.getCenterTitle());
            viewHolder.setText(R.id.tv_right, config.getRightText());
            viewHolder.displayImg(R.id.iv_right, config.getRightImageRes());
            viewHolder.findView(R.id.iv_right).setTag(ViewHolder.TAG_RIGHT_IMG_RES_ID, config.getRightImageRes());
            viewHolder.setVisible(new int[]{R.id.tv_left,
                    R.id.iv_left, R.id.tv_title, R.id.tv_right, R.id.iv_right});
            viewHolder.setOnclickListener(R.id.tv_left, config.getLeftTextClickListener());
            viewHolder.setOnclickListener(R.id.iv_left, config.getLeftImgClickListener());
            viewHolder.setOnclickListener(R.id.tv_right, config.getRightTextClickListener());
            viewHolder.setOnclickListener(R.id.iv_right, config.getRightImgClickListener());
        }
    }

    /**
     * viewHolder
     */
    private static class ViewHolder {
        private View targetLayout;
        private SparseArrayCompat<View> viewHolderList = new SparseArrayCompat<View>();
        public static final int TAG_LEFT_IMG_RES_ID = "tag_left_img_id".hashCode();
        public static final int TAG_RIGHT_IMG_RES_ID = "tag_right_img_id".hashCode();

        private ViewHolder() {
        }

        public static ViewHolder newInstance(View targetLayout) {
            ViewHolder holder = new ViewHolder();
            holder.targetLayout = targetLayout;
            return holder;
        }

        /**
         * 泛型findView
         *
         * @param viewId 控件id
         * @param <T>
         * @return
         */
        private <T extends View> T findView(@IdRes int viewId) {
            View view;
            if (viewHolderList.get(viewId) != null) {
                return (T) viewHolderList.get(viewId);
            } else {
                view = targetLayout.findViewById(viewId);
                if (view != null) {
                    viewHolderList.put(viewId, view);
                    return (T) view;
                } else {
                    return null;
                }
            }
        }

        /**
         * 设置文字，使用资源id设置
         *
         * @param viewId
         * @param resId
         */
        private void setText(@IdRes int viewId, @StringRes int resId) {
            if (targetLayout == null) {
                return;
            }
            setText(viewId, targetLayout.getContext().getResources().getString(resId));
        }

        /**
         * 设置文字，默认值为空字符串
         *
         * @param viewId 控件资源id
         * @param text   要设置的文字
         */
        private void setText(@IdRes int viewId, String text) {
            if (targetLayout == null) {
                return;
            }
            setTextWithDefault(viewId, text, "");
        }

        /**
         * 设置文字，可以设置默认值，如果不设置，默认为null
         *
         * @param viewId      控件资源id
         * @param text        要设置文字
         * @param defaultText 为null时设置的默认值
         */
        private void setTextWithDefault(@IdRes int viewId, String text, String defaultText) {
            TextView view = findView(viewId);
            if (!TextUtils.isEmpty(text)) {
                view.setText(text);
            } else {
                view.setText(defaultText);
            }
        }

        /**
         * 以资源id，设置图片到ImageView
         *
         * @param viewId
         * @param resId
         */
        private void displayImg(@IdRes int viewId, @DrawableRes int resId) {
            if (targetLayout == null && resId > 0) {
                return;
            }
            ImageView imageView = findView(viewId);
            imageView.setImageResource(resId);
        }

        /**
         * 设置多个View为Visible
         *
         * @param viewIds
         */
        public void setVisible(int[] viewIds) {
            for (int viewId : viewIds) {
                View view = findView(viewId);
                view.setVisibility(View.VISIBLE);
            }
        }

        /**
         * 设置多个View为Gone
         *
         * @param viewIds
         */
        public void setGone(int[] viewIds) {
            for (int viewId : viewIds) {
                View view = findView(viewId);
                view.setVisibility(View.GONE);
            }
        }

        /**
         * 给单个View设置点击事件
         *
         * @param viewId
         * @param listener
         */
        public void setOnclickListener(@IdRes int viewId, OnClickListener listener) {
            findView(viewId).setOnClickListener(listener);
        }

        /**
         * 给多个View设置点击事件
         *
         * @param viewIds
         * @param listener
         */
        public void setOnclickListener(@IdRes int[] viewIds, OnClickListener listener) {
            for (int id : viewIds) {
                findView(id).setOnClickListener(listener);
            }
        }
    }

    /**
     * 配置类
     */
    public static class TopBarConfig {
        private String leftText;
        private int leftImageRes;
        private String centerTitle;
        private String rightText;
        private int rightImageRes;
        private OnClickListener leftTextClickListener;
        private OnClickListener rightTextClickListener;
        private OnClickListener leftImgClickListener;
        private OnClickListener rightImgClickListener;

        private TopBarConfig() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public TopBarConfig(Builder builder) {
            this.leftText = builder.leftText;
            this.leftImageRes = builder.leftImageRes;
            this.centerTitle = builder.centerTitle;
            this.rightText = builder.rightText;
            this.rightImageRes = builder.rightImageRes;
            this.leftTextClickListener = builder.leftTextClickListener;
            this.rightTextClickListener = builder.rightTextClickListener;
            this.leftImgClickListener = builder.leftImgClickListener;
            this.rightImgClickListener = builder.rightImgClickListener;
        }

        public String getLeftText() {
            return leftText;
        }

        public int getLeftImageRes() {
            return leftImageRes;
        }

        public String getCenterTitle() {
            return centerTitle;
        }

        public String getRightText() {
            return rightText;
        }

        public int getRightImageRes() {
            return rightImageRes;
        }

        public OnClickListener getLeftTextClickListener() {
            return leftTextClickListener;
        }

        public OnClickListener getRightTextClickListener() {
            return rightTextClickListener;
        }

        public OnClickListener getLeftImgClickListener() {
            return leftImgClickListener;
        }

        public OnClickListener getRightImgClickListener() {
            return rightImgClickListener;
        }

        public static class Builder {
            private String leftText;
            private int leftImageRes;
            private String centerTitle;
            private String rightText;
            private int rightImageRes;
            private OnClickListener leftTextClickListener;
            private OnClickListener rightTextClickListener;
            private OnClickListener leftImgClickListener;
            private OnClickListener rightImgClickListener;

            private Builder() {
            }

            public Builder withLeftText(String leftText) {
                this.leftText = leftText;
                return this;
            }

            public Builder withLeftText(Context context, @StringRes int resId) {
                this.leftText = context.getResources().getString(resId);
                return this;
            }

            public Builder withLeftImageRes(int leftImageRes) {
                this.leftImageRes = leftImageRes;
                return this;
            }

            public Builder withCenterTitle(String centerTitle) {
                this.centerTitle = centerTitle;
                return this;
            }

            public Builder withCenterTitle(Context context, @StringRes int resId) {
                this.centerTitle = context.getResources().getString(resId);
                return this;
            }

            public Builder withRightText(String rightText) {
                this.rightText = rightText;
                return this;
            }

            public Builder withRightText(Context context, @StringRes int resId) {
                this.rightText = context.getResources().getString(resId);
                return this;
            }

            public Builder withRightImageRes(int rightImageRes) {
                this.rightImageRes = rightImageRes;
                return this;
            }

            public Builder withLeftTextClickListener(OnClickListener leftTextClickListener) {
                this.leftTextClickListener = leftTextClickListener;
                return this;
            }

            public Builder withRightTextClickListener(OnClickListener rightTextClickListener) {
                this.rightTextClickListener = rightTextClickListener;
                return this;
            }

            public Builder withLeftImgClickListener(OnClickListener leftImgClickListener) {
                this.leftImgClickListener = leftImgClickListener;
                return this;
            }

            public Builder withRightImgClickListener(OnClickListener rightImgClickListener) {
                this.rightImgClickListener = rightImgClickListener;
                return this;
            }

            /**
             * 重置，恢复默认值
             */
            public void reset() {
                withLeftText("");
                withLeftImageRes(-1);
                withRightText("");
                withRightImageRes(-1);
                withCenterTitle("");
                withLeftTextClickListener(null);
                withLeftImgClickListener(null);
                withRightTextClickListener(null);
                withRightImgClickListener(null);
            }

            /**
             * 提供一个默认的finishActivityListener
             * @param activity
             * @return
             */
            public static OnClickListener finishClickListener(final Activity activity) {
                return new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                };
            }

            public TopBarConfig build() {
                return new TopBarConfig(this);
            }
        }
    }
}
