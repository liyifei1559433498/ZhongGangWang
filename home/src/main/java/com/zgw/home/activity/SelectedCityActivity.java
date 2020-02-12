package com.zgw.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.zgw.base.ui.BaseActivity;
import com.zgw.base.util.Constants;
import com.zgw.base.util.LogUtil;
import com.zgw.home.R;
import com.zgw.home.R2;
import com.zgw.home.adapter.QueryCityAdapter;
import com.zgw.home.citylist.CityRecyclerAdapter;
import com.zgw.home.data.DBManager;
import com.zgw.home.model.City;
import com.zgw.home.view.SideBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class SelectedCityActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R2.id.recy_city)
    RecyclerView recyclerView;

    private DBManager dbManager;

    private List<City> allCities;

    private List<City> queryCityList;

    @BindView(R2.id.contact_sidebar)
    SideBar mContactSideber;

    private CityRecyclerAdapter adapter;

    private LinearLayoutManager linearLayoutManager;

    @BindView(R2.id.contact_dialog)
    TextView mContactDialog;

    @BindView(R2.id.searchList)
    ListView searchListView;

    private String location;

    private String locationCity = "";

    @BindView(R2.id.searchView)
    SearchView searchView;

    @BindView(R2.id.allCityLayout)
    RelativeLayout allCityLayout;

    @BindView(R2.id.queryList)
    ListView queryListView;

    private QueryCityAdapter queryCityAdapter;

    @BindView(R2.id.topBackBtn)
    FrameLayout topBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_layout);
        ButterKnife.bind(this);
        location = Constants.locationCity + "-" + Constants.district;
        intiView();
        initData();
    }

    private void intiView() {
        toolbar = $(R.id.toolbar);
        topBackBtn.setOnClickListener(this);
        initParameter();
//        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
        ImmersionBar.with(this).init();

        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        textView.setTextColor(getResources().getColor(R.color.home_top_search_hint));
        textView.setTextSize(13);

        dbManager = new DBManager(this);
        dbManager.copyDBFile();
        mContactSideber.setTextView(mContactDialog);

        queryCityAdapter = new QueryCityAdapter(this);
        queryListView.setAdapter(queryCityAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                LogUtil.outLog("TAG", "query == " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    allCityLayout.setVisibility(View.GONE);
                    queryListView.setVisibility(View.VISIBLE);
                    searchCites(newText);
                } else {
                    queryListView.setVisibility(View.GONE);
                    allCityLayout.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }

    /**
     * 检所输入地点
     */
    private void searchCites(String cityName) {
        queryCityList = dbManager.queryCity(cityName);
        queryCityAdapter.setQueryList(queryCityList);
        queryCityAdapter.notifyDataSetChanged();
    }

    private void initData() {
        allCities = dbManager.getAllCities();
        adapter = new CityRecyclerAdapter(this, allCities);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnCityClickListener(new CityRecyclerAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                Intent intent = new Intent();
                intent.putExtra("city", name);
                Constants.locationCity = name;
                Constants.city = name;
                Constants.district = name;
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onLocateClick() {
                //重新定位
                LogUtil.outLog("TAG", "onLocateClick");

                adapter.updateLocateState(CityRecyclerAdapter.LOCATING, null);

            }
        });

        mContactSideber.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {

                int position = adapter.getPositionForSection(s);
                if (position != -1) {
                    linearLayoutManager.scrollToPositionWithOffset(position, 0);
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.topBackBtn) {
            finish();
        }
    }
}
