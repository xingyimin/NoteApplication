package com.example.ruler.noteapplication.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ruler.noteapplication.Data.Note;
import com.example.ruler.noteapplication.R;

import java.util.List;

public class BrvahAdapter extends BaseQuickAdapter<Note,BaseViewHolder>{

    public BrvahAdapter(int layoutId,List data){
        super(layoutId,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Note item) {
        helper.setText(R.id.name_item_brvah,item.getTitle());
        helper.setImageResource(R.id.img_add_note,R.drawable.user);

    }
}
