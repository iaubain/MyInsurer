package utilities;

/**
 * Created by Hp on 11/12/2016.
 */
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.insurance.elephant.myinsurer.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import config.StatusConfig;
import uimodels.MotorHomeUI;

public class MotorHomeAdapter extends RecyclerView.Adapter<MotorHomeAdapter.ViewHolder> {
    private List<MotorHomeUI> mDataset;
    private MotorHomeAdapterInteraction motorHomeAdapterInteraction;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MotorHomeAdapter(MotorHomeAdapterInteraction motorHomeAdapterInteraction, Context context, List<MotorHomeUI> myDataset) {
        this.motorHomeAdapterInteraction=motorHomeAdapterInteraction;
        this.context=context;
        this.mDataset = myDataset;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.service)
        public TextView txtHeader;
        @Nullable
        @BindView(R.id.serviceDesc)
        public TextView txtFooter;
        @Nullable
        @BindView(R.id.icon)
        public ImageView icon;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MotorHomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.motor_home_style, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MotorHomeUI uiElement = mDataset.get(position);
        holder.txtHeader.setText(uiElement.getService());
        holder.txtFooter.setText(uiElement.getServiceDesc());
        holder.icon.setImageResource(uiElement.getThumb());

        holder.icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //send info about service
                motorHomeAdapterInteraction.onMotorAdapterInteraction(StatusConfig.PROCEED, uiElement);
            }
        });
        holder.txtHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send info about service
                motorHomeAdapterInteraction.onMotorAdapterInteraction(StatusConfig.PROCEED, uiElement);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MotorHomeAdapterInteraction{
        void onMotorAdapterInteraction(int status, MotorHomeUI motorHomeUI);
    }
}
