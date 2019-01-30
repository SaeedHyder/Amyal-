package com.app.amyal.ui.views;

import android.content.Context;
import android.util.AttributeSet;

public class AnyTextView1 extends android.support.v7.widget.AppCompatTextView {

    public AnyTextView1( Context context ) {
        super( context );
    }

    public AnyTextView1( Context context, AttributeSet attrs ) {
        super( context, attrs );

        if ( !isInEditMode() ) {
            Util.setTypeface( attrs, this );
        }
    }

    public AnyTextView1( Context context, AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );

        if ( !isInEditMode() ) {
            Util.setTypeface( attrs, this );
        }
    }
}
