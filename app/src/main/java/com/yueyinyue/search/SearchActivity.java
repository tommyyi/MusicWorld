package com.yueyinyue.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.cmsc.cmmusic.common.data.MusicInfo;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.github.yoojia.fireeye.FireEye;
import com.github.yoojia.fireeye.Form;
import com.github.yoojia.fireeye.StaticPattern;
import com.github.yoojia.fireeye.ValuePattern;
import com.xk.m.R;
import com.xk.m.databinding.ActivitySearchBinding;
import com.yueyinyue.Model.EventBusMessage.PlayMusicMessage;
import com.yueyinyue.BaseActivity;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements SearchMusicViewImp
{
    private ActivitySearchBinding mActivitySearchBinding;
    private RecyclerView.Adapter mAdapter;
    private SearchMusicPresenterImp searchMusicPresenterImp;

    private Handler handler;
    public List<MusicInfo> musicInfoList = new ArrayList<MusicInfo>();

    public void onCloseSearchActivityClick(View view)
    {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.MyAppTheme);
        super.onCreate(savedInstanceState);
        mActivitySearchBinding=DataBindingUtil.setContentView(this,R.layout.activity_search);
        mLoadToast = new LoadToast(this).setText("").setTranslationY(100);

        searchMusicPresenterImp = new SearchMusicPresenter(getApplicationContext(),this);
        handler=new Handler();

        //recyclerView.setItemAnimator(new FlipInBottomXAnimator());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mActivitySearchBinding.recyclerView.setLayoutManager(layoutManager);
        mActivitySearchBinding.recyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new RecyclerViewAdapter(musicInfoList, SearchActivity.this));
        //AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        //ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        mActivitySearchBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mActivitySearchBinding.floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener()
        {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery)
            {
            }
        });

        mActivitySearchBinding.floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener()
        {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion)
            {
            }

            @Override
            public void onSearchAction()
            {
                Form form = new Form(mActivitySearchBinding.floatingSearchView);
                FireEye fireEye = new FireEye(SearchActivity.this);

                fireEye.add(form.byId(com.arlib.floatingsearchview.R.id.search_bar_text), StaticPattern.Required,StaticPattern.NotBlank);
                fireEye.add(form.byId(com.arlib.floatingsearchview.R.id.search_bar_text), ValuePattern.MinLength.setValue(2));
                if(fireEye.test().passed)
                    searchMusicPresenterImp.search(musicInfoList,mActivitySearchBinding.floatingSearchView.getQuery());
            }
        });
    }

    @Override
    public void searchOK()
    {
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mLoadToast.success();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void searchError(String error)
    {
        mLoadToast.error();
    }

    @Override
    public void searchStart(final String message)
    {
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mLoadToast.setTranslationY(300);
                mLoadToast.setText(message).show();
                mActivitySearchBinding.floatingSearchView.resetSearchInput();
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        setContentView(R.layout.empty);
        mActivitySearchBinding=null;
        mAdapter=null;
        handler=null;
        musicInfoList=null;
        searchMusicPresenterImp=null;
        super.onDestroy();
    }
}
