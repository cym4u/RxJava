package info.enjoycode.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hanks.htextview.HTextView;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class CountDownDemoActivity extends AppCompatActivity {
    Observable<Void> verifyCodeObservable ;
    HTextView hTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        RxView.clicks(findViewById(R.id.btnRegister)).throttleFirst(2,TimeUnit.SECONDS).subscribe((a)-> Toast.makeText(CountDownDemoActivity.this,"clicked",Toast.LENGTH_SHORT).show());


        EditText et_login = (EditText) findViewById(R.id.et_login);
        EditText et_code = (EditText) findViewById(R.id.et_code);
        Button btn_verify_code = (Button) findViewById(R.id.btn_verify_code);
        RxTextView.afterTextChangeEvents(et_login).debounce(500, TimeUnit.MILLISECONDS).subscribe((event) -> System.out.println(event.editable().toString()));
        verifyCodeObservable = RxView.clicks(btn_verify_code).throttleFirst(60,TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread());

        verifyCodeObservable.subscribe(aVoid -> {
                            Observable.timer(0, 1000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).take(60).subscribe(aLong -> {
                                btn_verify_code.setText(60 - aLong + "'");
                            }, (error) -> {
                            }, () -> {
                                        btn_verify_code.setText("获取验证码");
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
