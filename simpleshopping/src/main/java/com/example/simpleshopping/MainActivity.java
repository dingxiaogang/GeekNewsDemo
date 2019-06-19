package com.example.simpleshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbdao.CollectBeanDao;

import java.util.ArrayList;
import java.util.List;

//丁晓刚 2019-6-17 20:07:28
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    String[] sNames = {"白菜", "黄瓜", "黄萝卜", "香菜", "小油菜", "菜花", "南瓜", "番茄", "西红柿", "大蒜"};
    double[] sPrices = {33.72, 22.21, 45.6, 8.11, 52, 7.23, 21.32, 11.22, 21.01, 12.02};
    String[] sImagePaths = {"http://cms-bucket.ws.126.net/2019/04/15/9547e9e31b774eedb8b3be306690c43a.png", "http://cms-bucket.ws.126.net/2019/04/15/3a83f6bad8b742d4a55d66d8c5a97d77.png",
            "http://cms-bucket.ws.126.net/2019/04/15/cb76985851414d61a99ce356a46bf096.png", "http://cms-bucket.ws.126.net/2019/04/15/852dcccc7d8141fd8d00a619145f1813.png",
            "http://cms-bucket.ws.126.net/2019/06/17/1b9828e349c440b3a7abe25d28b04cd4.jpeg", "http://cms-bucket.ws.126.net/2019/04/15/9547e9e31b774eedb8b3be306690c43a.png",
            "http://cms-bucket.ws.126.net/2019/04/15/cb76985851414d61a99ce356a46bf096.png", "http://cms-bucket.ws.126.net/2019/06/17/32c20ccf636a4d94a860d345da22f7c4.jpeg",
            "http://cms-bucket.ws.126.net/2019/04/15/852dcccc7d8141fd8d00a619145f1813.png", "http://cms-bucket.ws.126.net/2019/06/17/81d08d7eebf24f2a9448ca72a9603e55.jpeg"};
    /**
     * 余额：150.00￥
     */
    private TextView mBalance;
    private Toolbar mToolbar;
    private RecyclerView mRv;
    /**
     * 总计：0.00￥
     */
    private TextView mGrossPrice;
    /**
     * 购买
     */
    private Button mBuy;
    private RvMainAdapter mainAdapter;
    private ArrayList<CollectBean> collects;

    private double minePrice = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        mainAdapter.setOnClickListener(new RvMainAdapter.onClickListener() {
            @Override
            public void onClick(View view, int position, boolean isCheck, CollectBean collectBean) {
                if (isCheck){
                    double price = collectBean.getPrice();
                    minePrice += price;
                    String format = String.format("%.2f", minePrice);
                    mGrossPrice.setText("总价："+format+"￥");
                } else {
                    double price = collectBean.getPrice();
                    minePrice -= price;
                    String format = String.format("%.2f", minePrice);
                    mGrossPrice.setText("总价："+format+"￥");
                }
            }
        });

    }

    private void initData() {
        CollectBeanDao collectBeanDao = DbUtil.getDbUtil().getCollectBeanDao();
        int size = collectBeanDao.loadAll().size();
        if (size == 0){
            for (int i = 0; i < sNames.length; i++) {
                CollectBean collectBean = new CollectBean(null,sImagePaths[i],sNames[i],sPrices[i]);

                collectBeanDao.insertOrReplace(collectBean);
            }
        }

        List<CollectBean> collectBeans = collectBeanDao.loadAll();
        collects.addAll(collectBeans);
        mainAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mBalance = (TextView) findViewById(R.id.balance);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mGrossPrice = (TextView) findViewById(R.id.grossPrice);
        mBuy = (Button) findViewById(R.id.buy);
        mBuy.setOnClickListener(this);

        collects = new ArrayList<>();
        mainAdapter = new RvMainAdapter(collects);
        mRv.setAdapter(mainAdapter);
        mRv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.buy:
                String buyText = mBuy.getText().toString().trim();
                if (buyText.equals("购买")){
                    //获取的值:
                    String trim = mBalance.getText().toString().trim();
                    //转化为double类型
                    double aDouble = Double.parseDouble(trim);
                    //比较余额与购买价
                    if (aDouble > minePrice){
                        //购买完成后：
                        double v1 = aDouble - minePrice;
                        //防止小数点后出现多位
                        String format = String.format("%.2f", v1);
                        mBalance.setText(format);
                        Toast.makeText(this, "购买成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "余额不足，请充值", Toast.LENGTH_SHORT).show();
                        mBuy.setText("充值");
                    }
                }else if (buyText.equals("充值")){
                    Intent intent = new Intent(MainActivity.this, RechargeActivity.class);
                    startActivityForResult(intent,1);
                    mBuy.setText("购买");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "充值成功", Toast.LENGTH_SHORT).show();
        if (requestCode == 1 && resultCode == 2){
            //回传的值：
            String mineMoney = data.getStringExtra("mineMoney");
            double aDouble1 = Double.parseDouble(mineMoney);

            //余额：
            String trim = mBalance.getText().toString().trim();
            double aDouble2 = Double.parseDouble(trim);

            double v = aDouble1 + aDouble2;
            String format = String.format("%.2f", v);
            mBalance.setText(format);
        }
    }
}
