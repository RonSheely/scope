////////////////////////////////////////////////////////////////////////////////
//
//  Scope - An Android scope written in Java.
//
//  Copyright (C) 2014	Bill Farmer
//
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Bill Farmer	 william j farmer [at] yahoo [dot] co [dot] uk.
//
///////////////////////////////////////////////////////////////////////////////

package org.billthefarmer.scope;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

// Scale
public class Scale extends View
{
    private static final int WIDTH_FRACTION = 24;

    private int width;
    private int height;
    private int textColour;

    private Paint paint;

    // Constructor
    @SuppressWarnings("deprecation")
    public Scale(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        Resources resources = getResources();

        final TypedArray typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.Scope);

        textColour =
            typedArray.getColor(R.styleable.Scope_TextColour,
                                resources.getColor(android.R.color.black));
        typedArray.recycle();

        // Create paint
        paint = new Paint();
    }

    // onMeasure
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Get offered dimension
        int d = Math.min(MeasureSpec.getSize(heightMeasureSpec),
                         MeasureSpec.getSize(widthMeasureSpec));

        // Set wanted dimensions
        setMeasuredDimension(d / WIDTH_FRACTION, d);
    }

    // onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        // Get actual dimensions
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        paint.setStrokeWidth(2);
        paint.setColor(textColour);

        canvas.translate(0, height);
        canvas.scale(1, -1);

        // Draw scale ticks
        for (int i = 0; i < height; i += Main.SIZE)
        {
            canvas.drawLine(width * 2 / 3, i, width, i, paint);
        }

        for (int i = 0; i < height; i += Main.SIZE * 5)
        {
            canvas.drawLine(width / 3, i, width, i, paint);
        }
    }
}
