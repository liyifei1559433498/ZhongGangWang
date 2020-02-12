package com.zgw.base.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.zgw.base.R;


/**
 * Created by JsonTai on 2016/3/31.
 */
public class SelectorButton extends LinearLayout implements View.OnClickListener{

    private boolean isSelected = false;

    public SelectorButton(Context context) {
        super(context);
    }

    public SelectorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public boolean isSelected(){
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        setBg();
    }

    private void setBg() {
        if(isSelected){
            setBackgroundResource(R.drawable.btn_open_bg);
        }else{
            setBackgroundResource(R.drawable.btn_close_bg);
        }
    }

    @Override
    public void onClick(View v) {
        this.isSelected = !isSelected;
        setBg();
    }
}
