package utilities;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.insurance.elephant.myinsurer.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import config.StatusConfig;
import uimodels.HomeUI;

/**
 * Created by Hp on 11/7/2016.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context mContext;
    private List<HomeUI> homeUIList;
    private HomeAdapterInteraction onHomeAdapterInteraction;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        public TextView title;
        @BindView(R.id.count)
        public TextView count;
        @BindView(R.id.thumbnail)
        public ImageView thumbnail;
        @BindView(R.id.overflow)
        public ImageView overflow;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public HomeAdapter(Context mContext, HomeAdapterInteraction onHomeAdapterInteraction, List<HomeUI> homeUIList) {
        this.mContext = mContext;
        this.onHomeAdapterInteraction=onHomeAdapterInteraction;
        this.homeUIList = homeUIList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_style, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final HomeUI homeUI = homeUIList.get(position);
        holder.title.setText(homeUI.getName());
        //holder.count.setText(service.getNumOfSongs() + " songs");

        // loading album cover using Glide library
        Glide.with(mContext).load(homeUI.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, homeUI);
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToService(homeUI);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, HomeUI homeUI) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.service_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(homeUI));
        popup.show();
    }

    /**
     * go to selected service
     */
    private void goToService(HomeUI homeUI){
        onHomeAdapterInteraction.onHomeAdapterInteraction(StatusConfig.PROCEED, homeUI);
        Toast.makeText(mContext, homeUI.getName(), Toast.LENGTH_SHORT).show();
    }


    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private HomeUI homeUI;

        public MyMenuItemClickListener(HomeUI homeUI) {
            this.homeUI=homeUI;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_goto_services:
                    onHomeAdapterInteraction.onHomeAdapterInteraction(StatusConfig.PROCEED, homeUI);
                    Toast.makeText(mContext, homeUI.getName(), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_showcase:
                    onHomeAdapterInteraction.onHomeAdapterInteraction(StatusConfig.SHOW_CASE, homeUI);
                    Toast.makeText(mContext, homeUI.getName()+" SHOW CASE", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return homeUIList.size();
    }

    public interface HomeAdapterInteraction{
        void onHomeAdapterInteraction(int statusCode, Object object);
    }
}
