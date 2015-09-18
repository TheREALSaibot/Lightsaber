package com.example.blintbence.lightsaber;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class StarWarsView extends TextView
{
    public StarWarsView(Context context)
    {
        super(context);
        setFont();
    }

    public StarWarsView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setFont();
    }

    public StarWarsView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont()
    {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Starjhol.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}
