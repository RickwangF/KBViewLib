package com.example.kbviewlib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.kbviewlib.databinding.ActivityMainBinding;
import com.example.kbviewlib.databinding.HeaderTestBinding;
import com.kbit.kbbaselib.util.DateUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBind;

    HeaderTestBinding headBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
        initHeadView();
    }

    private void initView() {
        TestModel model = new TestModel();
        model.setImageUrl("https://dingyue.ws.126.net/2020/0623/04b53c08j00qccvia000lc000hs009nm.jpg");
        mBind.setModel(model);
//        long timestamp = DateUtil.getNowTimeStamp(true);
//        Log.e("date", "timestamp is " + timestamp);
//
//        List<TestModel> modelList = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            TestModel model = new TestModel();
//            model.setTitle("这是一条新闻的标题，这是一条标题" + i);
//            model.setImageUrl("http://kmzsccfile.kmzscc.com/Uploads/image/2018/08/20/5b7a96b76aabe.jpg");
//            model.setTime(1588855251);
//            modelList.add(model);
//        }
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        TestAdapter adapter = new TestAdapter(this, modelList);
//        mBind.rvMain.setLayoutManager(layoutManager);
//        mBind.rvMain.setAdapter(adapter);
//        mBind.rvMain.setHasFixedSize(true);
    }

    private void initHeadView() {
//        BigImageModel bigImageModel = new BigImageModel();
//        bigImageModel.setImageUrl("https://rmt-chenggong-file.vcdn.kmzscc.com/cg/2020/04/5e893000d85b9/2yaYmkri7W.jpg");
//        headBind = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.header_test, mBind.rvMain, false);
//        headBind.setModel(bigImageModel);
//        mBind.rvMain.addHeaderView(headBind.getRoot());
    }
}
