package in.bcs.codingexercise.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.bcs.codingexercise.R;

/**
 * Created by saran on 2/19/2018.
 */

public class FactsItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.rowTitle)
    public TextView factsRowTitle;
    @BindView(R.id.rowDescription)
    public TextView factsRowDescription;
    @BindView(R.id.rowImage)
    public ImageView factsRowImage;

    public FactsItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
