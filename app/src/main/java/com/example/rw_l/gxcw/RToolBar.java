package com.example.rw_l.gxcw;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class RToolBar extends Toolbar {
    private TextView mtextView;
    private String mtitle = "" ;
    private float mtextsize ;
    private int mtextColor;
    private boolean darkMode;
    private int navigationIconHeight;
    private int navigationIconWidth;

    public RToolBar(Context context) {
        super(context);
        this.init(context,null,0);
    }

    public RToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(context,attrs,0);
    }

    public RToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs, defStyleAttr);
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void init(final Context context, AttributeSet attrs, int defStyle){
//        View root = View.inflate(context, R.layout.base_tool_bar, this);
//        mtextView = root.findViewById(R.id.toolbar_title);

        final TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.RToolBar);
        //标题
        mtitle = array.getString(R.styleable.RToolBar_titleText);
        //默认标题字体大小
        mtextsize = array.getDimension(R.styleable.RToolBar_titleSize ,20);
        //默认字体颜色
        mtextColor = array.getColor(R.styleable.RToolBar_titleColor , 0);
        //返回按钮高度
        navigationIconHeight = array.getDimensionPixelSize(R.styleable.RToolBar_navigationIconHeight,0);
        //返回按钮宽度
        navigationIconWidth = array.getDimensionPixelSize(R.styleable.RToolBar_navigationIconWidth,0);
        //黑暗模式，按钮，字体颜色变白
        darkMode = array.getBoolean(R.styleable.RToolBar_darkMode,false);

        setTitleView(context);

        setNavigationButton(context);

//        for (int i = 0; i < getChildCount(); i++) {
//            final View view = getChildAt(i);
//            if (view instanceof ImageButton){
//                ImageButton imgbutton = (ImageButton) view;
//                if (imgbutton.getDrawable() == ContextCompat.getDrawable(context,R.drawable.qmui_icon_topbar_back)
//                        || imgbutton.getDrawable() == ContextCompat.getDrawable(context,R.drawable.ic_home_black_24dp)){
//                    final Toolbar.LayoutParams params = (Toolbar.LayoutParams) imgbutton.getLayoutParams();
//                    params.gravity = Gravity.CENTER_VERTICAL;
//                    params.height = dp2px(30);
//                    imgbutton.setLayoutParams(params);
//                    break;
//                }
//            }
//        }

//        final Drawable backgroundRes = array.getDrawable(R.styleable.RToolBar_backgroundRes);
//        if (backgroundRes != null){
//            this.setBackground(backgroundRes);
//        }else {
//            final int backgroundColor = array.getColor(R.styleable.RToolBar_backgroundColor,0);
//            if (backgroundColor != 0){
//                this.setBackgroundColor(backgroundColor);
//            }
//        }


        array.recycle();
    }


    //设置左侧返回按钮
    private void setNavigationButton(Context context){
        if (getNavigationIcon()==null){
            if (darkMode){
                setNavigationIcon(R.drawable.qmui_icon_topbar_back);
            }else {
                setNavigationIcon(R.drawable.ic_home_black_24dp);
            }
        }

        setNavigationContentDescription("back button");

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.getContentDescription() == getNavigationContentDescription()){
                Toolbar.LayoutParams params = (Toolbar.LayoutParams) view.getLayoutParams();
                params.gravity = Gravity.CENTER_VERTICAL;
                view.setLayoutParams(params);
                if(view instanceof ImageButton){
                    if (navigationIconHeight != 0 && navigationIconWidth != 0){
                        setNavigationIcon(zoomDrawableSize(context,((ImageButton) view).getDrawable(),(navigationIconWidth),(navigationIconHeight)));
//                        scaleIcon(context,((ImageButton) view).getDrawable(),(navigationIconWidth),(navigationIconHeight));
                    }
                }
                break;
            }
        }

    }

    private static Drawable zoomDrawableSize(Context context, Drawable drawable, int w, int h) {
        final int width = drawable.getIntrinsicWidth();
        final int height = drawable.getIntrinsicHeight();

        final Bitmap oldbmp = drawableToBitmap(drawable);
        final Matrix matrix = new Matrix();
        final float scaleWidth = ((float) w / width);
        final float scaleHeight = ((float) h / height);

        matrix.postScale(scaleWidth, scaleHeight);
        final Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);

        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (manager.getDefaultDisplay()==null){
            return drawable;
        }
        manager.getDefaultDisplay().getMetrics(metrics);
        final Resources resources = new Resources(context.getAssets(), metrics, null);
        return new BitmapDrawable(resources, newbmp);
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        final int width = drawable.getIntrinsicWidth();
        final int height = drawable.getIntrinsicHeight();
        final Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        final Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        final Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

//    public void setTitleCenter() {
////        String title = getTitle().toString();
//        final CharSequence originalTitle = getTitle();
////        setTitle(title);
//        for (int i = 0; i < getChildCount(); i++) {
//            View view = getChildAt(i);
//            if (view instanceof TextView) {
//                TextView textView = (TextView) view;
//                if (originalTitle.equals(textView.getText())) {
//                    textView.setGravity(Gravity.CENTER);
//                    Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
//                    params.gravity = Gravity.CENTER;
//                    textView.setLayoutParams(params);
//                }
//            }
//            if (view.getId() == android.R.id.home){
//                Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//                params.gravity = Gravity.CENTER_VERTICAL;
//                view.setLayoutParams(params);
//            }
////            setTitle(originalTitle);
//        }
//    }

    //设置标题，字体大小，颜色
    private void setTitleView(Context context){

        mtextView = new TextView(context);
        final Toolbar.LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        mtextView.setLayoutParams(lp);

        mtextView.setText(mtitle);
        if (mtextsize != 0){
            mtextView.setTextSize(mtextsize);
        }

        if (mtextColor !=0){
            mtextView.setTextColor(mtextColor);
        }else {
            //默认字体颜色
            if(darkMode){
                mtextView.setTextColor(Color.WHITE);
            }else {
                mtextView.setTextColor(Color.parseColor("#444444"));
            }
        }

        this.addView(mtextView);

        this.setContentInsetsRelative(0,0);
    }
}
