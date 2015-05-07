package com.sunshine.sihuo;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sunshine.sihuo.adapters.Show_GridView_Adapter;
import com.sunshine.sihuo.beans.Index_Info;
import com.sunshine.sihuo.urls.Kind_Url;
import com.sunshine.sihuo.utils.Parser_find_Kind;
import com.sunshine.sihuo.utils.SysApplication;
import com.sunshine.sihuo.views.GV_Info;

import java.util.ArrayList;
import java.util.List;


public class ShowKindActivity extends FragmentActivity implements PullToRefreshBase.OnRefreshListener2<GridView>, View.OnClickListener {

    private PullToRefreshGridView gridView;
    private List<Index_Info> listInfo = new ArrayList<>();

    private Button show_kind_btn1, show_kind_btn2, show_kind_btn3;
    private EditText choice_et1, choice_et2;

    private ListView show_kind_lv1;
    private String phone[] = {"手机", "相机/摄像机", "电脑/电脑配件", "数码3C产品", "奢侈品", "服饰鞋包", "美容/瘦身/香水", "家用电器/影音设备", "家居/日用品", "电动车/汽车/自行车", "宠物/宠物用品", "设备/办公用品", "虚拟币/票务/卡券", "书刊音像/文体用品", "礼品/饰品/玩偶", "商品/零食/保健品", "珠宝/黄金/手表", "艺术品/收藏品/古董古玩", "其他"};
    private String publish[] = {"价格最低", "折扣最大", "离我最近"};
    private CheckBox tv1, tv2, tv3, tv4, tv5;
    private Button choice_btn;

    private HttpUtils utils = new HttpUtils();
    private Show_GridView_Adapter adapter;
    private int position;

    private int index=1;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_kind);

        SysApplication.getInstance().addActivity(this);

        gridView = ((PullToRefreshGridView) findViewById(R.id.show_kind_grid));
        show_kind_btn1 = (Button) findViewById(R.id.show_kind_btn1);
        show_kind_btn2 = (Button) findViewById(R.id.show_kind_btn2);
        show_kind_btn3 = (Button) findViewById(R.id.show_kind_btn3);

        show_kind_btn1.setOnClickListener(this);
        show_kind_btn2.setOnClickListener(this);
        show_kind_btn3.setOnClickListener(this);

        // 获取从FindFragment中传过来的数据
        position = getIntent().getIntExtra("position", 0);
        url = Kind_Url.urlStr.get(position);

        // 获取集合数据
        getListInfo(position);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ShowKindActivity.this, GV_Info.class);
                intent.putExtra("gv_position", i);
                intent.putExtra("gv_url", url);
                startActivity(intent);


            }
        });
        // 下拉刷新
        gridView.setMode(PullToRefreshBase.Mode.BOTH);

        gridView.setOnRefreshListener(this);
    }

    private void getListInfo(final int position) {

        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {

                String result = objectResponseInfo.result;

                Log.v("result", result);
                // 解析数据
                listInfo.addAll(Parser_find_Kind.getListInfo(result));
                if(listInfo.size()>0){
                    gridView.onRefreshComplete();
                }
                adapter = new Show_GridView_Adapter(listInfo, ShowKindActivity.this);
                gridView.setAdapter(adapter);

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

        adapter.clear();
        getListInfo(position);

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
        getMoreListInfo();
    }

    /**
     * 上拉加载
     */
    private void getMoreListInfo() {
        // 获取json网络数据
        index++;

        String urlFS = url.replace("20", "" + (20 * index));
        Log.v("--DEBUG--","-->>"+urlFS+"   <<--"+index);
        utils.send(HttpRequest.HttpMethod.GET, urlFS, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {

                String result = objectResponseInfo.result;
                Log.v("result", result);
                listInfo.clear();
                // 解析数据
                listInfo.addAll(Parser_find_Kind.getListInfo(result));
                if(listInfo.size()>0){
                    gridView.onRefreshComplete();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.show_kind_btn1:
                showPopupWindow(view, phone);

                break;
            case R.id.show_kind_btn2:
                showPopupWindow(view, publish);
                break;
            //删选
            case R.id.show_kind_btn3:
                final View contentView = LayoutInflater.from(this).inflate(
                        R.layout.choose, null);
                choice_et1 = (EditText) contentView.findViewById(R.id.choice_et1);
                choice_et2 = (EditText) contentView.findViewById(R.id.choice_et2);
                tv1 = (CheckBox) contentView.findViewById(R.id.tv1);
                tv2 = (CheckBox) contentView.findViewById(R.id.tv2);
                tv3 = (CheckBox) contentView.findViewById(R.id.tv3);
                tv4 = (CheckBox) contentView.findViewById(R.id.tv4);
                tv5 = (CheckBox) contentView.findViewById(R.id.tv5);
                choice_btn = (Button) contentView.findViewById(R.id.choice_btn);

                tv1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        choice_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getJsonString(Kind_Url.newPrice);
                                contentView.setVisibility(View.GONE);

                            }
                        });
                    }
                });
                tv2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        choice_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getJsonString(Kind_Url.newPrice);
                                contentView.setVisibility(View.GONE);

                            }
                        });
                    }
                });
                tv3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        choice_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getJsonString(Kind_Url.halfPrice);
                                contentView.setVisibility(View.GONE);

                            }
                        });
                    }
                });
                tv4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        choice_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getJsonString(Kind_Url.halfPrice);
                                contentView.setVisibility(View.GONE);

                            }
                        });

                    }
                });
                tv5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        choice_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getJsonString(Kind_Url.oldPrice);
                                contentView.setVisibility(View.GONE);

                            }
                        });
                    }
                });


                final PopupWindow popupWindow = new PopupWindow(contentView,
                        WindowManager.LayoutParams.FILL_PARENT, 280, true);

                popupWindow.setTouchable(true);

                popupWindow.setTouchInterceptor(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        Log.i("mengdd", "onTouch : ");

                        return false;
                        // 这里如果返回true的话，touch事件将被拦截
                        // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                    }
                });
                popupWindow.setOutsideTouchable(true);

                // 设置好参数之后再show
                popupWindow.showAsDropDown(view);


                break;

        }
    }

    private void showPopupWindow(View view, final String[] subject) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_menulist, null);
        show_kind_lv1 = (ListView) contentView.findViewById(R.id.show_kind_lv1);

        show_kind_lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                    getListInfo(i-1);

                if (subject.equals(phone)) {

                    final String url = Kind_Url.MoreStr.get(i);
                    Log.v("getListInfo", url);
                    utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> objectResponseInfo) {

                            String result = objectResponseInfo.result;

                            Log.v("result", result);
                            // 解析数据
                            listInfo = Parser_find_Kind.getListInfo(result);
                            gridView.setAdapter(new Show_GridView_Adapter(listInfo, ShowKindActivity.this));
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    Intent intent = new Intent(ShowKindActivity.this, GV_Info.class);
                                    intent.putExtra("gv_position", i);
                                    intent.putExtra("gv_url", url);
                                    startActivity(intent);


                                }
                            });
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {

                        }
                    });
                    show_kind_btn1.setText(subject[i]);

                } else if (subject.equals(publish)) {
                    final String url = Kind_Url.publish.get(i);

                    utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> objectResponseInfo) {

                            String result = objectResponseInfo.result;

                            Log.v("result", result);
                            // 解析数据
                            listInfo = Parser_find_Kind.getListInfo(result);


                            gridView.setAdapter(new Show_GridView_Adapter(listInfo, ShowKindActivity.this));
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    Intent intent = new Intent(ShowKindActivity.this, GV_Info.class);
                                    intent.putExtra("gv_position", i);
                                    intent.putExtra("gv_url", url);
                                    startActivity(intent);


                                }
                            });
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {

                        }
                    });
                    show_kind_btn2.setText(subject[i]);
                }
            }

        });


        ArrayAdapter adapter = new ArrayAdapter(ShowKindActivity.this, android.R.layout.simple_list_item_1, subject);
        show_kind_lv1.setAdapter(adapter);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.WRAP_CONTENT, 200, true);

        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.btn_back_nor));
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
    }

    private void getJsonString(final String url) {


        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {

                String result = objectResponseInfo.result;

                Log.v("result", result);
                // 解析数据
                listInfo = Parser_find_Kind.getListInfo(result);
                gridView.setAdapter(new Show_GridView_Adapter(listInfo, ShowKindActivity.this));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Intent intent = new Intent(ShowKindActivity.this, GV_Info.class);
                        intent.putExtra("gv_position", i);
                        intent.putExtra("gv_url", url);
                        startActivity(intent);


                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


}
