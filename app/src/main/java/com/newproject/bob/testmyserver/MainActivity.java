package com.newproject.bob.testmyserver;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.newproject.bob.testmyserver.constant.AppConfig;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mLv;
    private TextView mTvShow;
    private Context mContext;
    private String urlStr = "http://172.20.25.153:8080/hello";
    private List<Student> studentList = null;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            studentList = (List<Student>)msg.obj;
            if(null != studentList){
                mLv.setVisibility(View.VISIBLE);
                MyAdapter adapter = new MyAdapter(mContext,studentList);
                mLv.setAdapter(adapter);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mLv = findViewById(R.id.lv_show_data);
        mTvShow = (TextView)findViewById(R.id.tv_show);
//        getData();
//        getJsonData();
//        try {
//            delteStudentByName("Alex");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        insertInto("Alext","23");
        List<Student> studentList1 = new ArrayList<>();
        for(int i = 0;i<6;i++){
            Student student = new Student();
            student.setName("Alex"+i);
            student.setAge(String.valueOf(Integer.valueOf("23")+i));
            studentList1.add(student);
        }
        addMoreStudents(studentList1);
    }

    /**
     * 批量添加student到mysql数据库
     * */
    private void addMoreStudents(List<Student> studentList){
        String studentsStr = JSON.toJSONString(studentList);
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("students",studentsStr);
        final Request request = new Request.Builder()
                .url(AppConfig.ADD_MORE_STUDENTS)
                .post(builder.build())
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseStr = response.body().string();
                List<Student> studentEntities = new ArrayList<>();
                studentEntities = com.alibaba.fastjson.JSONArray.parseArray(responseStr,Student.class);
                Message msg = mHandler.obtainMessage();
                msg.obj = studentEntities;
                mHandler.sendMessage(msg);
            }
        });
    }

    /**
     * 插入单个student到mysql
     * **/
    private void insertInto(String name,String age){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("name",name);
        builder.add("age",age);
        final Request request = new Request.Builder()
                .url(AppConfig.INSERT_STUDENT)
                .post(builder.build())
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseStr = response.body().string();
                List<Student> studentEntities = new ArrayList<>();
                studentEntities = com.alibaba.fastjson.JSONArray.parseArray(responseStr,Student.class);
                Message msg = mHandler.obtainMessage();
                msg.obj = studentEntities;
                mHandler.sendMessage(msg);
            }
        });
    }

    /**
     * 根据name删除student
     * **/
    private void delteStudentByName(String name) throws IOException {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("name",name);
        final Request request = new Request.Builder()
                            .url(AppConfig.DELETE_STUDENT_BY_NAME)
                            .post(builder.build())
                .build();
      /*  Response response = mOkHttpClient.newCall(request).execute();*/
      Call call = mOkHttpClient.newCall(request);
      call.enqueue(new Callback() {
          @Override
          public void onFailure(Request request, IOException e) {

          }

          @Override
          public void onResponse(Response response) throws IOException {
              String responseStr = response.body().string();
              List<Student> studentEntities = new ArrayList<>();
              studentEntities = com.alibaba.fastjson.JSONArray.parseArray(responseStr,Student.class);
              Message msg = mHandler.obtainMessage();
              msg.obj = studentEntities;
              mHandler.sendMessage(msg);
          }
      });
    }

    /**
     * 得到数据库所有的数据
     * **/
    private void getData(){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(urlStr).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseStr = response.body().string();
                Message msg = mHandler.obtainMessage();
                msg.obj = responseStr;
                mHandler.sendMessage(msg);
            }
        });
    }


    private void getJsonData(){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("name","Bob");
        final Request request = new Request.Builder().url(urlStr).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseStr = response.body().string();
                List<Student> studentEntities = new ArrayList<>();
                studentEntities = com.alibaba.fastjson.JSONArray.parseArray(responseStr,Student.class);
                Message msg = mHandler.obtainMessage();
                msg.obj = studentEntities;
                mHandler.sendMessage(msg);
            }
        });
    }




    private class MyAdapter extends BaseAdapter{

        private List<Student> studentEntities = new ArrayList<>();
        private Context context;

        public MyAdapter(Context context,List<Student> girlEntities){
            this.context = context;
            this.studentEntities = girlEntities;
        }


        @Override
        public int getCount() {
            if(studentEntities.size() != 0){
                return studentEntities.size();
            }else{
                return 0;
            }
        }

        @Override
        public Object getItem(int i) {
            if(studentEntities.size() != 0){
                return studentEntities.get(i);
            }else{
                return null;
            }
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder mViewHolder = null;
            if(null == view){
                mViewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_adapter,null);
                mViewHolder.mTvName = (TextView) view.findViewById(R.id.name);
                mViewHolder.mTvAge = (TextView) view.findViewById(R.id.age);
                view.setTag(mViewHolder);
            }else{
                mViewHolder = (ViewHolder) view.getTag();
            }

            Student girlEntity = studentEntities.get(i);
            mViewHolder.mTvName.setText(girlEntity.getName());
            mViewHolder.mTvAge.setText(girlEntity.getAge()+"");
            return view;
        }

    }

    private class ViewHolder{
        private TextView mTvName,mTvAge;
    }

}
