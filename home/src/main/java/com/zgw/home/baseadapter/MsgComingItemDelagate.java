package com.zgw.home.baseadapter;


import com.zgw.home.R;
import com.zgw.home.model.ChatMessage;
import com.zgw.home.util.ViewHolder;

/**
 * Created by zhy on 16/6/22.
 */
public class MsgComingItemDelagate implements ItemViewDelegate<ChatMessage>
{

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.today_news_item_layout;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position)
    {
        return item.isComMeg();
    }

    @Override
    public void convert(ViewHolder holder, ChatMessage chatMessage, int position)
    {
        holder.setText(R.id.contentTv, "第二种布局");
    }
}
