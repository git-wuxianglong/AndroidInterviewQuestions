package com.wuxl.interviewquestions.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuxl.interviewquestions.R;
import com.wuxl.interviewquestions.bean.AndroidSenior;
import com.wuxl.interviewquestions.bean.JavaQuestions;
import com.wuxl.interviewquestions.bean.JavaWebQuestions;
import com.wuxl.interviewquestions.bean.Subject;

import java.util.List;

import static com.wuxl.interviewquestions.AppConfig.ANDROID_BASE_FLAG;
import static com.wuxl.interviewquestions.AppConfig.ANDROID_SENIOR_FLAG;
import static com.wuxl.interviewquestions.AppConfig.JAVA_FLAG;
import static com.wuxl.interviewquestions.AppConfig.JAVA_WEB_FLAG;

/**
 * RecyclerViewAdapter
 * Created by wuxianglong on 2016/9/9.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private String flag;
    private List<Subject> androidBaseList;
    private List<JavaQuestions> javaQuestionsList;
    private List<AndroidSenior> androidSeniorList;
    private List<JavaWebQuestions> javaWebQuestionsList;
    private LayoutInflater inflater;

    public MyRecyclerViewAdapter(Context context, String flag, List<JavaQuestions> javaQuestionsList, List<Subject> androidBaseList, List<AndroidSenior> androidSeniorList) {
        this.context = context;
        this.flag = flag;
        this.androidBaseList = androidBaseList;
        this.androidSeniorList = androidSeniorList;
        this.javaQuestionsList = javaQuestionsList;
        inflater = LayoutInflater.from(context);

    }

    public void setDataChange(String flag, List<JavaQuestions> javaQuestionsList, List<Subject> androidBaseList, List<AndroidSenior> androidSeniorList, List<JavaWebQuestions> javaWebList) {
        this.flag = flag;
        //判断数据源
        if (flag.equals(JAVA_FLAG)) {
            this.javaQuestionsList = javaQuestionsList;
        } else if (flag.equals(ANDROID_BASE_FLAG)) {
            this.androidBaseList = androidBaseList;
        } else if (flag.equals(ANDROID_SENIOR_FLAG)) {
            this.androidSeniorList = androidSeniorList;
        } else if (flag.equals(JAVA_WEB_FLAG)) {
            this.javaWebQuestionsList = javaWebList;
        }
        this.notifyDataSetChanged();
    }

    /**
     * 点击事件
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(inflater.inflate(R.layout.listview_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }

        if (flag.equals(JAVA_FLAG)) {
            //Java
            holder.ask.setText(position + 1 + "：" + javaQuestionsList.get(position).getQuestions());
            holder.time.setText(javaQuestionsList.get(position).getCreatedAt());
        } else if (flag.equals(ANDROID_BASE_FLAG)) {
            //Android基础
            holder.ask.setText(position + 1 + "：" + androidBaseList.get(position).getSubject_title());
            holder.time.setText(androidBaseList.get(position).getCreatedAt());
        } else if (flag.equals(ANDROID_SENIOR_FLAG)) {
            //Android高级
            holder.ask.setText(position + 1 + "：" + androidSeniorList.get(position).getQuestions());
            holder.time.setText(androidSeniorList.get(position).getCreatedAt());
        } else if (flag.equals(JAVA_WEB_FLAG)) {
            //Java Web
            holder.ask.setText(position + 1 + "：" + javaWebQuestionsList.get(position).getQuestions());
            holder.time.setText(javaWebQuestionsList.get(position).getCreatedAt());
        }
    }

    @Override
    public int getItemCount() {
        if (flag.equals(JAVA_FLAG)) {
            return javaQuestionsList == null ? 0 : javaQuestionsList.size();
        } else if (flag.equals(ANDROID_BASE_FLAG)) {
            return androidBaseList == null ? 0 : androidBaseList.size();
        } else if (flag.equals(ANDROID_SENIOR_FLAG)) {
            return androidSeniorList == null ? 0 : androidSeniorList.size();
        } else if (flag.equals(JAVA_WEB_FLAG)) {
            return javaWebQuestionsList == null ? 0 : javaWebQuestionsList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ask;
        TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            ask = (TextView) itemView.findViewById(R.id.ask);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
