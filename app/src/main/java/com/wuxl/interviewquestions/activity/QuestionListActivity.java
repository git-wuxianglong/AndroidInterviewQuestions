package com.wuxl.interviewquestions.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.wuxl.interviewquestions.R;
import com.wuxl.interviewquestions.adapter.MyRecyclerViewAdapter;
import com.wuxl.interviewquestions.bean.AndroidSenior;
import com.wuxl.interviewquestions.bean.JavaQuestions;
import com.wuxl.interviewquestions.bean.JavaWebQuestions;
import com.wuxl.interviewquestions.bean.Subject;
import com.wuxl.interviewquestions.utils.CacheUtils;
import com.wuxl.interviewquestions.utils.SendMsg;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.wuxl.interviewquestions.AppConfig.ANDROID_BASE_DIR;
import static com.wuxl.interviewquestions.AppConfig.ANDROID_BASE_FLAG;
import static com.wuxl.interviewquestions.AppConfig.ANDROID_SENIOR_DIR;
import static com.wuxl.interviewquestions.AppConfig.ANDROID_SENIOR_FLAG;
import static com.wuxl.interviewquestions.AppConfig.JAVA_FLAG;
import static com.wuxl.interviewquestions.AppConfig.JAVA_QUESTION_DIR;
import static com.wuxl.interviewquestions.AppConfig.JAVA_WEB_DIR;
import static com.wuxl.interviewquestions.AppConfig.JAVA_WEB_FLAG;

/**
 * Android问题列表
 */
public class QuestionListActivity extends AppCompatActivity {

    private String CACHE_DIR = null;//默认缓存目录

    private PullLoadMoreRecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<Subject> androidBaseList = new ArrayList<>();
    private List<JavaQuestions> javaQuestionsList = new ArrayList<>();
    private List<AndroidSenior> androidSeniorList = new ArrayList<>();
    private List<JavaWebQuestions> javaWebQuestionsList = new ArrayList<>();

    private boolean isRefreshOrLoadMore = false;//true:加载更多;false:刷新
    private static final int COUNT = 20;//每页数据个数

    private String questionFlag;

    private Snackbar snackbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeActionOverflowMenuShown();
        setContentView(R.layout.activity_android);

        //缓存目录
        CACHE_DIR = this.getCacheDir().getPath();

        loadCache();
        initView();
        queryData();
    }

    /**
     * 首先加载缓存数据
     */
    private void loadCache() {
        Intent intent = getIntent();
        ActionBar actionBar = getSupportActionBar();
        if (!TextUtils.isEmpty(intent.getStringExtra("flag")) && actionBar != null) {
            questionFlag = intent.getStringExtra("flag");
            if (questionFlag.equals(JAVA_FLAG)) {
                //Java面试题
                actionBar.setTitle(R.string.java_questions);
                //加载缓存数据
                javaQuestionsList = (List<JavaQuestions>) CacheUtils.load(CACHE_DIR + JAVA_QUESTION_DIR);
            } else if (questionFlag.equals(ANDROID_BASE_FLAG)) {
                //Android基础
                actionBar.setTitle(R.string.android_base_questions);
                //加载缓存数据
                androidBaseList = (List<Subject>) CacheUtils.load(CACHE_DIR + ANDROID_BASE_DIR);
            } else if (questionFlag.equals(ANDROID_SENIOR_FLAG)) {
                //Android高级
                actionBar.setTitle(R.string.android_senior_questions);
                //加载缓存数据
                androidSeniorList = (List<AndroidSenior>) CacheUtils.load(CACHE_DIR + ANDROID_SENIOR_DIR);
            } else if (questionFlag.equals(JAVA_WEB_FLAG)) {
                //java web题
                actionBar.setTitle(R.string.java_web_questions);
                //加载缓存数据
                javaWebQuestionsList = (List<JavaWebQuestions>) CacheUtils.load(CACHE_DIR + JAVA_QUESTION_DIR);
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 初始化view
     */
    private void initView() {
        mRecyclerView = (PullLoadMoreRecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setLinearLayout();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new MyRecyclerViewAdapter(this, questionFlag, javaQuestionsList, androidBaseList, androidSeniorList);
        mRecyclerView.setAdapter(adapter);
        //点击事件
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (questionFlag.equals(JAVA_FLAG)) {
                    //Java面试题
                    toResultActivity(position, view.findViewById(R.id.layoutAsk));
                } else if (questionFlag.equals(ANDROID_BASE_FLAG)) {
                    //Android基础
                    toResultActivity(position, view.findViewById(R.id.layoutAsk));
                } else if (questionFlag.equals(ANDROID_SENIOR_FLAG)) {
                    //Android高级
                    toResultActivity(position, view.findViewById(R.id.layoutAsk));
                } else if (questionFlag.equals(JAVA_WEB_FLAG)) {
                    toResultActivity(position, view.findViewById(R.id.layoutAsk));
                }
            }
        });

        /**
         * 刷新和加载更多
         */
        mRecyclerView.setRefreshing(true);
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                //刷新
                isRefreshOrLoadMore = false;
                queryData();
            }

            @Override
            public void onLoadMore() {
                //加载更多
                isRefreshOrLoadMore = true;
                queryData();
            }
        });

    }

    /**
     * 跳转到答案界面
     *
     * @param position
     */
    private void toResultActivity(int position, View view) {
        Intent intent = new Intent(QuestionListActivity.this, ResultActivity.class);
        intent.putExtra("flag", questionFlag);
        if (questionFlag.equals(JAVA_FLAG)) {
            //Java面试题
            intent.putExtra("question", javaQuestionsList.get(position));
        } else if (questionFlag.equals(ANDROID_BASE_FLAG)) {
            //Android基础
            intent.putExtra("question", androidBaseList.get(position));
        } else if (questionFlag.equals(ANDROID_SENIOR_FLAG)) {
            //Android高级
            intent.putExtra("question", androidSeniorList.get(position));
        } else if (questionFlag.equals(JAVA_WEB_FLAG)) {
            //JavaWeb面试题
            intent.putExtra("question", javaWebQuestionsList.get(position));
        }
        intent.putExtra("position", position + 1);

        /**
         * 这种页面跳转方法存在小部分手机不兼容（记得好像是努比亚Z9 mini，网上说是手机厂商更改了跳转源代码！）
         * 目前就这部手机遇到过跳转崩溃问题……
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, view, "title");
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    /**
     * 查询数据
     */
    private void queryData() {
        if (questionFlag.equals(JAVA_FLAG)) {
            //Java面试题
            BmobQuery<JavaQuestions> javaQuery = new BmobQuery<>();
            javaQuery.order("-createdAt");
            //设置每页数据个数
            javaQuery.setLimit(COUNT);

            if (isRefreshOrLoadMore) {
                //加载更多，只查询小于等于最后一个item发表时间的数据
                int size = javaQuestionsList.size() - 1;
                Date date = strToDate(javaQuestionsList.get(size).getCreatedAt());
                javaQuery.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
            }

            javaQuery.findObjects(new FindListener<JavaQuestions>() {
                @Override
                public void done(List<JavaQuestions> list, BmobException e) {
                    if (e == null) {
                        if (isRefreshOrLoadMore) {
                            //加载更多
                            javaQuestionsList.addAll(list);
                        } else {
                            //下拉刷新
                            javaQuestionsList = list;
                        }
                        adapter.setDataChange(questionFlag, javaQuestionsList, androidBaseList, androidSeniorList, javaWebQuestionsList);

                        //缓存数据
                        CacheUtils.save(javaQuestionsList, CACHE_DIR + JAVA_QUESTION_DIR);

                    } else {
                        showSnackBar(mRecyclerView, "网络好像有点问题");
                    }
                    //刷新结束
                    mRecyclerView.setPullLoadMoreCompleted();
                }
            });

        } else if (questionFlag.equals(ANDROID_BASE_FLAG)) {
            //Android基础题
            BmobQuery<Subject> androidBasenQuery = new BmobQuery<>();
            androidBasenQuery.order("-createdAt");
            //设置每页数据个数
            androidBasenQuery.setLimit(COUNT);

            if (isRefreshOrLoadMore) {
                //加载更多，只查询小于等于最后一个item发表时间的数据
                int size = androidBaseList.size() - 1;
                Date date = strToDate(androidBaseList.get(size).getCreatedAt());
                androidBasenQuery.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
            }

            androidBasenQuery.findObjects(new FindListener<Subject>() {
                @Override
                public void done(List<Subject> list, BmobException e) {
                    if (e == null) {
                        if (isRefreshOrLoadMore) {
                            //加载更多
                            androidBaseList.addAll(list);
                        } else {
                            //下拉刷新
                            androidBaseList = list;
                        }
                        adapter.setDataChange(questionFlag, javaQuestionsList, androidBaseList, androidSeniorList, javaWebQuestionsList);

                        //缓存数据
                        CacheUtils.save(androidBaseList, CACHE_DIR + ANDROID_BASE_DIR);

                    } else {
                        showSnackBar(mRecyclerView, "网络好像有点问题");
                    }
                    //刷新结束
                    mRecyclerView.setPullLoadMoreCompleted();
                }
            });
        } else if (questionFlag.equals(ANDROID_SENIOR_FLAG)) {
            //Android高级题
            BmobQuery<AndroidSenior> androidSeniorQuery = new BmobQuery<>();
            androidSeniorQuery.order("-createdAt");
            //设置每页数据个数
            androidSeniorQuery.setLimit(COUNT);

            if (isRefreshOrLoadMore) {
                //加载更多，只查询小于等于最后一个item发表时间的数据
                int size = androidSeniorList.size() - 1;
                Date date = strToDate(androidSeniorList.get(size).getCreatedAt());
                androidSeniorQuery.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
            }

            androidSeniorQuery.findObjects(new FindListener<AndroidSenior>() {
                @Override
                public void done(List<AndroidSenior> list, BmobException e) {
                    if (e == null) {
                        if (isRefreshOrLoadMore) {
                            //加载更多
                            androidSeniorList.addAll(list);
                        } else {
                            //下拉刷新
                            androidSeniorList = list;
                        }
                        adapter.setDataChange(questionFlag, javaQuestionsList, androidBaseList, androidSeniorList, javaWebQuestionsList);

                        //缓存数据
                        CacheUtils.save(androidSeniorList, CACHE_DIR + ANDROID_SENIOR_DIR);

                    } else {
                        showSnackBar(mRecyclerView, "网络好像有点问题");
                    }
                    //刷新结束
                    mRecyclerView.setPullLoadMoreCompleted();
                }
            });
        } else if (questionFlag.equals(JAVA_WEB_FLAG)) {
            //JavaWeb题
            BmobQuery<JavaWebQuestions> javaWebSeniorQuery = new BmobQuery<>();
            javaWebSeniorQuery.order("-createdAt");
            //设置每页数据个数
            javaWebSeniorQuery.setLimit(COUNT);

            if (isRefreshOrLoadMore) {
                //加载更多，只查询小于等于最后一个item发表时间的数据
                int size = javaWebQuestionsList.size() - 1;
                Date date = strToDate(javaWebQuestionsList.get(size).getCreatedAt());
                javaWebSeniorQuery.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
            }

            javaWebSeniorQuery.findObjects(new FindListener<JavaWebQuestions>() {
                @Override
                public void done(List<JavaWebQuestions> list, BmobException e) {
                    if (e == null) {
                        if (isRefreshOrLoadMore) {
                            //加载更多
                            javaWebQuestionsList.addAll(list);
                        } else {
                            //下拉刷新
                            javaWebQuestionsList = list;
                        }
                        adapter.setDataChange(questionFlag, javaQuestionsList, androidBaseList, androidSeniorList, javaWebQuestionsList);

                        //缓存数据
                        CacheUtils.save(javaWebQuestionsList, CACHE_DIR + JAVA_WEB_DIR);

                    } else {
                        showSnackBar(mRecyclerView, "网络好像有点问题");
                    }
                    //刷新结束
                    mRecyclerView.setPullLoadMoreCompleted();
                }
            });
        }


    }

    /**
     * 将string类型的时间转换为date类型
     *
     * @param time
     * @return
     */
    private static Date strToDate(String time) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sim.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                //关于
                SendMsg.sendMsgToZhuge(this, getResources().getString(R.string.action_about));
                startActivity(new Intent(QuestionListActivity.this, AboutActivity.class));
                break;
            case R.id.action_setting:
                //设置
                SendMsg.sendMsgToZhuge(this, getResources().getString(R.string.action_setting));
                startActivity(new Intent(QuestionListActivity.this, SettingActivity.class));
                break;
            case android.R.id.home:
                //返回
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 有的手机不显示菜单栏
     */
    private void makeActionOverflowMenuShown() {
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {

        }
    }

    /**
     * SnackBar
     *
     * @param view
     * @param text
     */
    private void showSnackBar(View view, String text) {
        if (snackbar == null) {
            snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        } else {
            snackbar.setText(text);
            snackbar.setDuration(Snackbar.LENGTH_SHORT);
        }
        snackbar.show();
    }

}
