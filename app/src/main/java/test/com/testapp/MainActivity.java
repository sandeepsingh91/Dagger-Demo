package test.com.testapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import test.com.testapp.components.ActivityComponent;
import test.com.testapp.components.DaggerActivityComponent;
import test.com.testapp.module.ActivityModule;

public class MainActivity extends AppCompatActivity {

    @Inject
    DataManager mDataManager;

    private ActivityComponent activityComponent;

    private TextView mTvUserInfo;
    private TextView mTvAccessToken;


    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(DemoApplication.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        mTvUserInfo = (TextView) findViewById(R.id.tv_user_info);
        mTvAccessToken = (TextView) findViewById(R.id.tv_access_token);

    }

    public void onClick(View v) {
        System.out.println("MainActivity.onClick");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        System.out.println("MainActivity.onPostCreate");
        createUser();
        getUser();
        mDataManager.saveAccessToken("ASDR12443JFDJF43543J543H3K543");

        String token = mDataManager.getAccessToken();
        if (token != null) {
            mTvAccessToken.setText(token);
        }
    }

    private void createUser() {
        try {
            mDataManager.createUser(new User("test", "1367, Gurgaon, Haryana, India"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUser() {
        try {
            User user = mDataManager.getUser(1L);
            mTvUserInfo.setText("count : " + mDataManager.getUsers() + "\n" + user.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
