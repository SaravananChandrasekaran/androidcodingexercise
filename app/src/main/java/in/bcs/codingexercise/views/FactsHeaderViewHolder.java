package in.bcs.codingexercise.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.bcs.codingexercise.R;

/**
 * Created by saran on 2/19/2018.
 */

public class FactsHeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.factsHeader)
    public TextView headerTitle;

    public FactsHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
