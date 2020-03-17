package com.jdeveloperapps.weather.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.jdeveloperapps.weather.R;

public class MyCard extends View {

    private Paint paintBackground;
    private Paint paintLine;
    private Paint paintTextBolt;
    private int backgroundColor;
    private int lineColor;
    private int cornerRadius;
    private String humidity = "";

    public MyCard(Context context) {
        super(context);
        init();
    }

    public MyCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init();
    }

    public MyCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius, paintBackground);

        //вертикальная линия
        canvas.drawLine(getWidth()/2, getHeight()/10,
                getWidth()/2, getHeight() - getHeight()/10, paintLine);

        //горизонтальная линия
        canvas.drawLine(getWidth()/20, getHeight()/2,
                getWidth() - getWidth()/20, getHeight()/2, paintLine);


        canvas.drawBitmap(drawableToBitmap(
                getResources().getDrawable(R.drawable.ic_humidity),
                getHeight()/3 - 30, getHeight()/3 - 30),
                getWidth()/2 - getHeight()/2 + getHeight()/7,
                getHeight()/7, paintLine);

        //надпись ВЛАЖНОСТЬ
        canvas.drawText("Влажность", getWidth()/20, getHeight()/10 + 30, paintLine);
        // значвение влажности
        canvas.drawText(humidity, getWidth()/20, (getHeight()/10 + 35)*2, paintTextBolt);
    }

    private void init() {
        paintBackground = new Paint();
        paintBackground.setColor(getContext().getResources().getColor(backgroundColor));
        paintBackground.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(getContext().getResources().getColor(lineColor));
        paintLine.setStrokeWidth(1);
        paintLine.setTextSize(50);
        paintLine.setTextAlign(Paint.Align.LEFT);

        paintTextBolt = new Paint();
        paintTextBolt.setColor(getContext().getResources().getColor(lineColor));
        paintTextBolt.setTextSize(60);
        paintLine.setTextAlign(Paint.Align.LEFT);
        paintTextBolt.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyCard);
        backgroundColor = typedArray.getResourceId(R.styleable.MyCard_backgroundColor, android.R.color.holo_red_dark);
        lineColor = typedArray.getResourceId(R.styleable.MyCard_lineColor, android.R.color.holo_blue_light);
        cornerRadius = typedArray.getLayoutDimension(R.styleable.MyCard_cornerRadius, 20);
        typedArray.recycle();
    }

    private Bitmap drawableToBitmap(Drawable drawable, int w, int h) {
        Bitmap bitmap;

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
        invalidate();
    }
}
