package com.example.ruler.noteapplication.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ruler.noteapplication.R;

public class DialogAddNote extends Dialog{


    protected TextView hintTv;

    private EditText input_author_name;
    private EditText input_note_title;


    /**
     * 左边按钮
     */
    protected Button doubleLeftBtn;

    /**
     * 右边按钮
     */
    protected Button doubleRightBtn;

    public DialogAddNote(Context context) {
        super(context, R.style.CustomDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);  // 是否可以撤销
        setContentView(R.layout.layout_dialog_add_note);
        //hintTv = (TextView) findViewById(R.id.);
        doubleLeftBtn = (Button) findViewById(R.id.btn_left);
        doubleRightBtn = (Button) findViewById(R.id.btn_right);
        input_author_name=(EditText)findViewById(R.id.input_author_name);
        input_note_title=(EditText)findViewById(R.id.input_title);
    }

    /**
     * 设置右键文字和点击事件
     *
     * @param rightStr 文字
     * @param clickListener 点击事件
     */
    public void setRightButton(String rightStr, View.OnClickListener clickListener) {
        doubleRightBtn.setOnClickListener(clickListener);
        doubleRightBtn.setText(rightStr);
    }

    /**
     * 设置左键文字和点击事件
     *
     * @param leftStr 文字
     * @param clickListener 点击事件
     */
    public void setLeftButton(String leftStr, View.OnClickListener clickListener) {
        doubleLeftBtn.setOnClickListener(clickListener);
        doubleLeftBtn.setText(leftStr);
    }

    /**
     * 设置提示内容
     *
     * @param str 内容
     */
    public void setHintText(String str) {
        hintTv.setText(str);
        hintTv.setVisibility(View.VISIBLE);
    }

    /**
     * 给两个按钮 设置文字
     *
     * @param leftStr 左按钮文字
     * @param rightStr 右按钮文字
     */
    public void setBtnText(String leftStr, String rightStr) {
        doubleLeftBtn.setText(leftStr);
        doubleRightBtn.setText(rightStr);
    }

    public EditText getInput_note_name() {
        return input_author_name;
    }

    public void setInput_note_name(EditText input_note_name) {
        this.input_author_name = input_note_name;
    }

    public EditText getInput_note_title() {
        return input_note_title;
    }

    public void setInput_note_title(EditText input_note_title) {
        this.input_note_title = input_note_title;
    }
}
