package com.eltonkola.demoapp;

/**
 * Created by Elton on 07/09/2014.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {

    private List<JokeElement> itemsData;

    OnItemClickListenerVk mItemClickListener;

    private Context cntx;
    private DateFormat df;

    public JokeAdapter(Context cntx, List<JokeElement> itemsData) {
        this.itemsData = itemsData;
        this.cntx=cntx;
        df = new SimpleDateFormat("d/MMM/yyyy");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_joke, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        JokeElement elem = itemsData.get(position);

        viewHolder.jokeId.setText(elem.getTitle() + " " + df.format(elem.getCreation_date()));

        viewHolder.jokeTxt.setText(Html.fromHtml(elem.getDescription()));

    }

    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView jokeTxt, jokeId;
        public ImageView avatar;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            jokeId= (TextView)itemLayoutView.findViewById(R.id.jokeId);
            jokeTxt= (TextView)itemLayoutView.findViewById(R.id.jokeTxt);

            avatar= (ImageView) itemLayoutView.findViewById(R.id.avatar);
            itemLayoutView.setOnClickListener(this);

            itemLayoutView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemLongClick(v, getPosition());
                    }
                    return true;
                }
            });
        }


        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }

    public interface OnItemClickListenerVk {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListenerVk mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}