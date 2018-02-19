package in.bcs.codingexercise.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import in.bcs.codingexercise.R;
import in.bcs.codingexercise.model.FactsRowItems;
import in.bcs.codingexercise.views.FactsHeaderViewHolder;
import in.bcs.codingexercise.views.FactsItemViewHolder;


/**
 * Created by saran on 2/19/2018.
 */

public class FactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = FactsAdapter.class.getSimpleName();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<FactsRowItems> itemObjects;
    private String factsTitle;

    private int lastPosition = -1;

    private Context context;

    /**
     * Constructor for the Facts Adapter
     *
     * @param context
     * @param factsTitle  Header Title
     * @param itemObjects List items to be shown in recycler view
     */
    public FactsAdapter(Context context, String factsTitle, List<FactsRowItems> itemObjects, OnItemClickListener listener) {
        this.itemObjects = itemObjects;
        this.factsTitle = factsTitle;
        this.context = context;
        this.listener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(FactsRowItems item);
    }

    private final OnItemClickListener listener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            // Inflates to Header Layout if the Type header is same as view type
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout, parent, false);
            return new FactsHeaderViewHolder(layoutView);
        } else if (viewType == TYPE_ITEM) {
            //Inflates to row items
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.facts_row, parent, false);
            return new FactsItemViewHolder(layoutView);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FactsRowItems mObject = itemObjects.get(position);
        if (holder instanceof FactsHeaderViewHolder) {
            //Setting the Header
            ((FactsHeaderViewHolder) holder).headerTitle.setText(factsTitle);
        } else if (holder instanceof FactsItemViewHolder) {
            //Setting the title if available
            if (mObject.getTitle() != null) {
                ((FactsItemViewHolder) holder).factsRowTitle.setText(mObject.getTitle());
            } else {
                ((FactsItemViewHolder) holder).factsRowTitle.setText("");
            }
            //Setting the Description if available
            if (mObject.getDescription() != null) {
                ((FactsItemViewHolder) holder).factsRowDescription.setText(mObject.getDescription());
            } else {
                ((FactsItemViewHolder) holder).factsRowDescription.setText("");
            }
            if (mObject.getImageHref() != null) {
                // Downloading the image using Glide
                //Handling place holder and error image
                Glide
                        .with(context)
                        .load(mObject.getImageHref())
                        .apply(new RequestOptions()
                                .placeholder(context.getResources().getDrawable(R.drawable.ic_broken_image))
                                .fitCenter().error(context.getResources().getDrawable(R.drawable.ic_broken_image)))
                        .into(((FactsItemViewHolder) holder).factsRowImage);

            } else {
                ((FactsItemViewHolder) holder).factsRowImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_broken_image));
            }
        }

        setAnimation(holder.itemView, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(mObject);
            }
        });
    }


    @Override
    public int getItemCount() {
        return itemObjects.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    /**
     * Position of the header in the list
     *
     * @param position
     * @return
     */
    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    /**
     * Perform recycler animation on scroll
     *
     * @param viewToAnimate view to animate
     * @param position      views position in list
     */
    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}