package com.zgw.home.baseadapter;


import com.zgw.home.R;
import com.zgw.home.model.ChatMessage;
import com.zgw.home.util.ViewHolder;

/**
 * Created by zhy on 16/6/22.
 */
public class MsgSendItemDelagate implements ItemViewDelegate<ChatMessage>
{

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.today_news_item_title_layout;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position)
    {
        return !item.isComMeg();
    }

    @Override
    public void convert(ViewHolder holder, ChatMessage chatMessage, int position)
    {
        holder.setText(R.id.titleTv, "第一种布局");
//        holder.setText(R.id.chat_send_name, chatMessage.getName());
//        holder.setImageResource(R.id.chat_send_icon, chatMessage.getIcon());
    }
}
