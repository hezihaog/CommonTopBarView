package com.hzh.common.view.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hzh.common.view.TopBarView;

public class MainActivity extends AppCompatActivity {

    private TopBarView mTopBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTopBarView = (TopBarView) findViewById(R.id.topBarView);
        mTopBarView.applyConfig(TopBarView.TopBarConfig.newBuilder()
                .withLeftText("返回")
                .withLeftTextClickListener(TopBarView.TopBarConfig.Builder.finishClickListener(this))
                .withRightText("更多")
                .withRightTextClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "更多", Toast.LENGTH_SHORT).show();
                    }
                })
                .withCenterTitle("TopBarView")
                .build());
    }
}