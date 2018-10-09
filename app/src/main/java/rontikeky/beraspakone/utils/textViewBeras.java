package rontikeky.beraspakone.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import rontikeky.beraspakone.R;


/**
 * Created by Acer on 3/20/2018.
 */

public class textViewBeras extends AppCompatTextView {
    public textViewBeras(Context context) {
        super(context);
    }

    public textViewBeras(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public textViewBeras(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null){
            TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.textViewBeras);

            Drawable drawableLeft = null;
            Drawable drawableRight = null;
            Drawable drawableTop = null;
            Drawable drawableBottom = null;

            if  (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                drawableLeft = attributeArray.getDrawable(R.styleable.textViewBeras_drawableLeftCompat);
                drawableRight = attributeArray.getDrawable(R.styleable.textViewBeras_drawableRightCompat);
                drawableTop = attributeArray.getDrawable(R.styleable.textViewBeras_drawableTopCompat);
                drawableBottom = attributeArray.getDrawable(R.styleable.textViewBeras_drawableBottomCompat);
            }else{
                final int drawableLeftId = attributeArray.getResourceId(R.styleable.textViewBeras_drawableLeftCompat, -1);
                final int drawableRightId = attributeArray.getResourceId(R.styleable.textViewBeras_drawableRightCompat, -1);
                final int drawableTopId = attributeArray.getResourceId(R.styleable.textViewBeras_drawableTopCompat, -1);
                final int drawableBottomId = attributeArray.getResourceId(R.styleable.textViewBeras_drawableBottomCompat, -1);

                if (drawableLeftId != -1)
                    drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId);
                if (drawableRightId != -1)
                    drawableRight = AppCompatResources.getDrawable(context, drawableRightId);
                if (drawableTopId != -1)
                    drawableTop = AppCompatResources.getDrawable(context, drawableTopId);
                if (drawableBottomId != -1)
                    drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId);
            }
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableRight, drawableTop, drawableBottom);
            attributeArray.recycle();
        }
    }
}
