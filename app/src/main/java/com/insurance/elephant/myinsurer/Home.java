package com.insurance.elephant.myinsurer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import config.ServiceCode;
import config.StatusConfig;
import uimodels.HomeUI;
import utilities.DataFactory;
import utilities.HomeAdapter;

public class Home extends AppCompatActivity implements HomeAdapter.HomeAdapterInteraction {
    @Nullable
    @BindString(R.string.app_name)
    String appName;
    @Nullable
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R.id.backdrop)
    ImageView coverImg;

    private HomeAdapter adapter;
    private List<HomeUI> homeUIList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        ButterKnife.bind(this);
//        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        homeUIList = new ArrayList<>();
        adapter = new HomeAdapter(this, this, homeUIList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.cover).into(coverImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHomeAdapterInteraction(int statusCode, Object object) {
        //(String name, int numOfHits, int serviceCode, int thumbnail)

        switch (statusCode){
            case StatusConfig.PROCEED:
                proceedToService((HomeUI) object);
                break;
            case StatusConfig.SHOW_CASE:
                showCaseOn((HomeUI) object);
                break;
            default:
                Toast.makeText(this, "Action not recognized", Toast.LENGTH_SHORT).show();
        }
    }

    private void proceedToService(HomeUI homeUI){
        if(homeUI == null){
            Toast.makeText(this, "Internal Error",Toast.LENGTH_SHORT).show();
            return;
        }
        int serviceCode=homeUI.getServiceCode();

        switch (serviceCode){
            case ServiceCode.MOTOR_INSURANCE:
                Intent intent = new Intent(this, ServicesActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("serviceCode", homeUI.getServiceCode());
                bundle.putString("serviceType", new DataFactory().jsonString(homeUI));
                intent.putExtras(bundle);

                intent.setFlags(IntentCompat.FLAG_ACTIVITY_TASK_ON_HOME | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case ServiceCode.GENERAL_INSURANCE:
                uiPop("Under construction");
                break;
            case ServiceCode.LIFE_INSURANCE:
                uiPop("Under construction");
                break;
            case ServiceCode.ABOUT_US:
                uiPop("Under construction");
                break;
            default:
                Toast.makeText(this, "Action not recognized", Toast.LENGTH_SHORT).show();
        }
    }

    private void showCaseOn(HomeUI homeUI){

    }

    private void uiPop(String message){
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message)
                    .setTitle(R.string.dialog_title);
            // Add the buttons
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();


            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.ic_health,
                R.drawable.ic_car,
                R.drawable.ic_general,
                R.drawable.ic_about};

        //(String name, int numOfHits, int serviceCode, int thumbnail)
        HomeUI homeUI = new HomeUI(ServiceCode.LIFE_INSURANCE_DESC, 0, ServiceCode.LIFE_INSURANCE, covers[0]);
        homeUIList.add(homeUI);

        homeUI = new HomeUI(ServiceCode.MOTOR_INSURANCE_DESC, 0, ServiceCode.MOTOR_INSURANCE, covers[1]);
        homeUIList.add(homeUI);

        homeUI = new HomeUI(ServiceCode.GENERAL_INSURANCE_DESC, 0, ServiceCode.GENERAL_INSURANCE, covers[2]);
        homeUIList.add(homeUI);

        homeUI = new HomeUI(ServiceCode.ABOUT_US_DESC, 0, ServiceCode.ABOUT_US, covers[3]);
        homeUIList.add(homeUI);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
