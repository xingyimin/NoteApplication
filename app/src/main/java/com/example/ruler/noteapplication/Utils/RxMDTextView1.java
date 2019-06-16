package com.example.ruler.noteapplication.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.example.ruler.noteapplication.R;
import com.yydcdut.rxmarkdown.RxMDTextView;

public class RxMDTextView1 extends RxMDTextView {
    private  Bitmap mBitmap;

    public RxMDTextView1(Context context) {
        super(context);
    }

    public RxMDTextView1(Context context, AttributeSet attrs,Bitmap bitmap) {
        super(context, attrs);
        mBitmap = bitmap;
        setTextSize(40);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, getPaint());
        super.onDraw(canvas);
    }
}
