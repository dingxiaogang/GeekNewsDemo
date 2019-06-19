package com.example.simpleshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private EditText mMineMoney;
    /**
     * 充值
     */
    private Button mRecharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mMineMoney = (EditText) findViewById(R.id.mineMoney);
        mRecharge = (Button) findViewById(R.id.recharge);
        mRecharge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.recharge:
                String trim = mMineMoney.getText().toString().trim();
                Intent intent = getIntent();
                intent.putExtra("mineMoney",trim);
                setResult(2,intent);
                finish();
                break;
        }
    }
}
