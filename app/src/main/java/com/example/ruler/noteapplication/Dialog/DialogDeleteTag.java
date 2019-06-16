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

public class DialogDeleteTag extends Dialog {

    private TextView hint_line;
    private EditText input_tag_delete;
    private Button btn_tag_delete_left;
    private Button btn_tag_delete_right;
    /**
     * 左边按钮
     */
    protected Button doubleLeftBtn;

    /**
     * 右边按钮
     */
    protected Button doubleRightBtn;
    public DialogDeleteTag(@NonNull Context context) {
        super(context,R.style.CustomDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);  // 是否可以撤销
        setContentView(R.layout.layout_dialog_delete_tag);
        //hintTv = (TextView) findViewById(R.id.);
        doubleLeftBtn = (Button) findViewById(R.id.btn_left_delete_tag);
        doubleRightBtn = (Button) findViewById(R.id.btn_right_delete_tag);
        input_tag_delete=(EditText)findViewById(R.id.input_tag_group);
        hint_line=(TextView)findViewById(R.id.text_delete_tag_group);
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
        hint_line.setText(str);
        hint_line.setVisibility(View.VISIBLE);
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


    public EditText getInput_tag_delete() {
        return input_tag_delete;
    }
}
