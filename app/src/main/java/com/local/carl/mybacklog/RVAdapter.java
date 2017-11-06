package com.local.carl.mybacklog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.CardView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * Created by carlr on 10/24/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ItemHolder> implements View.OnClickListener, View.OnLongClickListener {


    public static class ItemHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView cardItemName, cardItemPriority;
        RelativeLayout hiddenItemContent;
        TextView hiddenItemDescription, hiddenItemCategory;

        public ItemHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            cardItemName = (TextView) itemView.findViewById(R.id.card_item_name);
            cardItemPriority = (TextView) itemView.findViewById(R.id.card_item_priority);
            hiddenItemContent = (RelativeLayout) itemView.findViewById(R.id.hidden_item_content);
            hiddenItemDescription = (TextView) itemView.findViewById(R.id.hidden_item_description);
            hiddenItemCategory = (TextView) itemView.findViewById(R.id.hidden_item_category);
        }
    }


    int mExpandedPosition = -1;
    private List<Item> items;
    private Context mContext;

    RVAdapter(Context context, List<Item> items){
        this.mContext = context;
        this.items = items;
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_template, viewGroup, false);
        ItemHolder ih = new ItemHolder(v);
        ih.itemView.setOnClickListener(RVAdapter.this);
        ih.itemView.setOnLongClickListener(RVAdapter.this);
        ih.itemView.setTag(ih);
        return ih;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public void onBindViewHolder(ItemHolder itemHolder, final int i){
        final boolean isExpanded = i==mExpandedPosition;

        itemHolder.cardItemName.setText(items.get(i).getName());
        itemHolder.cardItemPriority.setText(((Integer) items.get(i).getPriority()).toString());
        itemHolder.hiddenItemDescription.setText(items.get(i).getDesc());
        itemHolder.hiddenItemCategory.setText(items.get(i).getCategoryName());

        if (i==mExpandedPosition){
            itemHolder.hiddenItemContent.setVisibility(View.VISIBLE);
        } else {
            itemHolder.hiddenItemContent.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        ItemHolder holder = (ItemHolder) view.getTag();
        Item item = items.get(holder.getPosition());

        if(mExpandedPosition >= 0){
            int prev = mExpandedPosition;
            notifyItemChanged(prev);
        }

        mExpandedPosition = holder.getPosition();
        notifyItemChanged(mExpandedPosition);
        Toast.makeText(view.getContext(), "Clicked: " + item.getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onLongClick(View v){
        ItemHolder holder = (ItemHolder) v.getTag();
        final Item item = items.get(holder.getPosition());
        //Intent intent = new Intent(v.getContext(), AddItemActivity.class);
        //v.getContext().startActivity(intent);
        Toast.makeText(v.getContext(), "Long Clicked: " + item.getName(), Toast.LENGTH_SHORT).show();


        final PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.getMenuInflater().inflate(R.menu.context_popup, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
        public boolean onMenuItemClick(MenuItem menuItem) {
            int i = menuItem.getItemId();
            if (i == R.id.context_edit) {
                //do something
                return true;
            }
            else if (i == R.id.context_delete){
                //do something
                return true;
            }
            else if (i == R.id.context_mark_as_done) {
                item.setFinished(true);
                return true;
            }
            else {
                return onMenuItemClick(menuItem);
            }
        }});
        popup.show();
        Toast.makeText(v.getContext(), "Item Finished: " + item.isFinished(), Toast.LENGTH_SHORT).show();

        return true;
    }


    public List<Item> getItems(){
        return items;
    }

    public void setItems(List<Item> itemList){
        this.items = itemList;
    }
}
