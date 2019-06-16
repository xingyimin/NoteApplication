package com.example.ruler.noteapplication.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ruler.noteapplication.R;

public class DialogAddTag extends Dialog {

    protected TextView hintTv;

    private EditText input_tag_name;

    /**
     * 左边按钮
     */
    protected Button doubleLeftBtn;

    /**
     * 右边按钮
     */
    protected Button doubleRightBtn;

    public DialogAddTag(Context context) {
        super(context, R.style.CustomDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);  // 是否可以撤销
        setContentView(R.layout.layout_dialog_add_tag);
        View view= LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_add_tag,null);
        view.setBackgroundResource(R.drawable.coner_bg);
        hintTv = (TextView) findViewById(R.id.text_add_tag_name);
        doubleLeftBtn = (Button) findViewById(R.id.btn_left_add_tag);
        doubleRightBtn = (Button) findViewById(R.id.btn_right_add_tag);
        input_tag_name=(EditText)findViewById(R.id.input_tag_name);

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

    public EditText getInput_tag_name() {
        return input_tag_name;
    }

    public void setInput_tag_name(EditText input_note_name) {
        this.input_tag_name = input_note_name;
    }

}
