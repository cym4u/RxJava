package info.enjoycode.rxjava;

import android.os.Bundle;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class CountDownDemoActivity extends BaseActivity {
    Observable<Void> verifyCodeObservable;
    @Bind(R.id.btn_verify_code)
    Button btnVerifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        ButterKnife.bind(this);


        verifyCodeObservable = RxView.clicks(btnVerifyCode).throttleFirst(60, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread());

        verifyCodeObservable.subscribe(aVoid -> {
                    Observable.timer(0, 1000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).take(60).subscribe(aLong -> {
                                btnVerifyCode.setText(60 - aLong + "'");
                            }, (error) -> {
                            }, () -> {
                                btnVerifyCode.setText("获取验证码");
                            }
                    );
                }
        );
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribeObservables();
    }

    private void unSubscribeObservables() {
        verifyCodeObservable.unsubscribeOn(AndroidSchedulers.mainThread());
    }
}
