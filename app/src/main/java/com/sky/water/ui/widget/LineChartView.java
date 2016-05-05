package com.sky.water.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.sky.water.R;
import com.sky.water.model.RateDate;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/24 上午12:32
 */
public class LineChartView extends View {

    private Paint gridPaint;//网格线画笔
    private Paint textPaint;//坐标轴文字画笔
    private Paint paint10;//10cm画笔
    private Paint paint20;//20cm画笔
    private Paint paint40;//40cm画笔

    public List<RateDate> rateDates;

    /**
     * 为填充数据预留
     *
     * @param rateDates
     */
    public void setRateDates(List<RateDate> rateDates) {
        this.rateDates = rateDates;
        invalidate();
    }

    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {

        //网格的画笔
        gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#d1d1d1"));
        gridPaint.setStrokeWidth(3);

        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#999999"));
        textPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.micros));
        textPaint.setAntiAlias(true);

        paint10 = new Paint();
        paint10.setColor(getResources().getColor(R.color.common_main_orange));
        paint10.setAntiAlias(true);
        paint10.setStrokeWidth(10);
        paint10.setStyle(Paint.Style.STROKE);


        paint20 = new Paint();
        paint20.setColor(getResources().getColor(R.color.darkbule));
        paint20.setAntiAlias(true);
        paint20.setStrokeWidth(10);
        paint20.setStyle(Paint.Style.STROKE);

        paint40 = new Paint();
        paint40.setColor(getResources().getColor(R.color.chocolate));
        paint40.setAntiAlias(true);
        paint40.setStrokeWidth(10);
        paint40.setStyle(Paint.Style.STROKE);
    }

    private int width;// 每格宽度
    private int everyWidth;// 每格宽度
    private int everyHeigt;// 每格高度

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
    }

    private int line;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rateDates == null) return;
        line = rateDates.size();
        float maxSoil = Math.max(Math.max(rateDates.get(0).getSoil10(), rateDates.get(0).getSoil20()), rateDates.get(0).getSoil40());
        float minSoil = Math.min(Math.min(rateDates.get(0).getSoil10(), rateDates.get(0).getSoil20()), rateDates.get(0).getSoil40());
        for (int i = 0; i < line; i++) {
            maxSoil = Math.max(maxSoil, Math.max(Math.max(rateDates.get(i).getSoil10(), rateDates.get(i).getSoil20()), rateDates.get(i).getSoil40()));
            minSoil = Math.min(minSoil, Math.min(Math.min(rateDates.get(i).getSoil10(), rateDates.get(i).getSoil20()), rateDates.get(i).getSoil40()));
        }

        //刻度，间距（y2-y1的值）
        float scaleY = 0.15f;
        //规定年率的范围在4个格之内，
        int number = (int) ((maxSoil - minSoil) / (scaleY * (line - 3)));
        if (number > 0) {
            scaleY = scaleY * (number + 1);
        }
        int num = (int) (minSoil / scaleY);
        if (num < 0) num--;
        float minY = num * scaleY;
        float maxY = minY + scaleY * (line - 1);

        int maxTextWidth = 0;//Y轴刻度的最大宽度
        DecimalFormat df = new DecimalFormat("##0.0##");

        for (int i = 0; i < line; i++) {
            //画Y轴刻度
            Rect boundY = new Rect();
            String YY = df.format(minY + scaleY * (line - 1 - i)) + rateDates.get(i).getType();
            textPaint.getTextBounds(YY, 0, YY.length(), boundY);
            maxTextWidth = Math.max(boundY.width(), maxTextWidth);
        }
        maxTextWidth += 5;
        everyHeigt = everyWidth = (width - maxTextWidth) / line;
        Path path10 = new Path();
        Path path20 = new Path();
        Path path40 = new Path();
        float lastX10 = 0, lastX20 = 0, lastX40 = 0, lastY10 = 0, lastY20 = 0, lastY40 = 0;
        int radius=20;
        for (int i = 0; i < line; i++) {

            //横线
            if (i != line - 1 && i != 0) {
                canvas.drawLine(maxTextWidth, i * everyHeigt, width, i * everyHeigt, gridPaint);
            } else if (i == line - 1) {
                int y0 = i * everyHeigt;
                canvas.drawLine(maxTextWidth, i * everyHeigt, width, y0,gridPaint);
                Path pathJ = new Path();
                pathJ.moveTo((float) (width-radius* Math.cos(Math.PI/4)), (float) (y0-radius* Math.cos(Math.PI/4)));
                pathJ.lineTo(width,y0);
                pathJ.lineTo((float) (width-radius* Math.cos(Math.PI/4)), (float) (y0+radius* Math.cos(Math.PI/4)));
                canvas.drawPath(pathJ,gridPaint);
            }
            //竖线
            canvas.drawLine(i * everyWidth + maxTextWidth, 0, i * everyWidth + maxTextWidth, (line - 1) * everyHeigt, gridPaint);
            if (i==0){
                Path pathJ = new Path();
                pathJ.moveTo((float) (maxTextWidth-radius* Math.cos(Math.PI/4)), (float) (radius* Math.cos(Math.PI/4)));
                pathJ.lineTo(maxTextWidth,0);
                pathJ.lineTo((float) (maxTextWidth+radius* Math.cos(Math.PI/4)), (float) (radius* Math.cos(Math.PI/4)));
                canvas.drawPath(pathJ,gridPaint);
            }

            //X轴上的日期
            Rect textRect = new Rect();
            textPaint.getTextBounds(rateDates.get(i).getDate(),
                    0, rateDates.get(i).getDate().length(), textRect);
            int textWidth = textRect.width();
            canvas.drawText(rateDates.get(i).getDate(),
                    i * everyWidth + maxTextWidth - textWidth / 2, //相对于竖线居中
                    (line - 1) * everyHeigt + textRect.height() / 2 * 3,
                    textPaint);
            //画Y轴刻度
            Rect boundY = new Rect();
            String YY = df.format(minY + scaleY * (line - 1 - i)) + rateDates.get(i).getType();
            textPaint.getTextBounds(YY, 0, YY.length(), boundY);
            int boundYH = boundY.height();
            if (i != line - 1 && i % 2 == 0) {
                if (i == 0) canvas.drawText(YY + "", 0, i * everyHeigt + boundYH+radius, textPaint);
                else canvas.drawText(YY, 0, i * everyHeigt + boundYH / 2, textPaint);
            }
            RateDate rate = rateDates.get(i);
            float y10 = (maxY - rate.getSoil10()) / scaleY;
            float y20 = (maxY - rate.getSoil20()) / scaleY;
            float y40 = (maxY - rate.getSoil40()) / scaleY;
            if (i == 0) {
                path10.moveTo(i * everyWidth + maxTextWidth, y10 * everyHeigt);
                path20.moveTo(i * everyWidth + maxTextWidth, y20 * everyHeigt);
                path40.moveTo(i * everyWidth + maxTextWidth, y40 * everyHeigt);
            } else {
                path10.lineTo(i * everyWidth + maxTextWidth, y10 * everyHeigt);
                path20.lineTo(i * everyWidth + maxTextWidth, y20 * everyHeigt);
                path40.lineTo(i * everyWidth + maxTextWidth, y40 * everyHeigt);
            }
            if (i == line - 1) {
                lastX10 = lastX20 = lastX40 = i * everyWidth + maxTextWidth;
                lastY10 = y10 * everyHeigt;
                lastY20 = y20 * everyHeigt;
                lastY40 = y40 * everyHeigt;
            }

        }
        //开始画出折线
        canvas.drawPath(path10, paint10);
        canvas.drawPath(path20, paint20);
        canvas.drawPath(path40, paint40);

        textPaint.setTextSize(32);
        String color10 = "10cm:";
        Rect bound10 = new Rect();
        textPaint.getTextBounds(color10, 0, color10.length(), bound10);
        canvas.drawText(color10, 0, line * everyHeigt + bound10.height() / 2 * 2, textPaint);
        canvas.drawLine(bound10.width() + 5, line * everyHeigt + bound10.height() / 2,
                bound10.width() * 2, line * everyHeigt + bound10.height() / 2, paint10);
        String color20 = "20cm:";
        Rect bound20 = new Rect();
        textPaint.getTextBounds(color20, 0, color20.length(), bound20);
        canvas.drawText(color20, 0, line * everyHeigt + bound20.height() / 2 * 5, textPaint);
        canvas.drawLine(bound20.width() + 5, line * everyHeigt + bound20.height() / 2 * 4,
                bound20.width() * 2, line * everyHeigt + bound20.height() / 2 * 4, paint20);

        String color40 = "40cm:";
        Rect bound40 = new Rect();
        textPaint.getTextBounds(color40, 0, color40.length(), bound40);
        canvas.drawText(color40, 0, line * everyHeigt + bound40.height() / 2 * 8, textPaint);
        canvas.drawLine(bound40.width() + 5, line * everyHeigt + bound40.height() / 2 * 7,
                bound40.width() * 2, line * everyHeigt + bound40.height() / 2 * 7, paint40);


        //为最后一个点画上空心圆
        paint10.setStyle(Paint.Style.FILL);
        paint20.setStyle(Paint.Style.FILL);
        paint40.setStyle(Paint.Style.FILL);
        canvas.drawCircle(lastX10, lastY10, 12, paint10);
        canvas.drawCircle(lastX20, lastY20, 12, paint20);
        canvas.drawCircle(lastX40, lastY40, 12, paint40);
        paint10.setShader(null);
        paint20.setShader(null);
        paint40.setShader(null);
        paint10.setColor(Color.parseColor("#ffffff"));
        paint20.setColor(Color.parseColor("#ffffff"));
        paint40.setColor(Color.parseColor("#ffffff"));
        canvas.drawCircle(lastX10, lastY10, 6, paint10);
        canvas.drawCircle(lastX20, lastY20, 6, paint20);
        canvas.drawCircle(lastX40, lastY40, 6, paint40);
    }
}
