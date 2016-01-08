package info.enjoycode.rxjava;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Observable<Void> verifyCodeObservable ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        EditText et_login = (EditText) findViewById(R.id.et_login);
        EditText et_code = (EditText) findViewById(R.id.et_code);
        Button btn_verify_code = (Button) findViewById(R.id.btn_verify_code);
        RxTextView.afterTextChangeEvents(et_login).debounce(500, TimeUnit.MILLISECONDS).subscribe((event) -> System.out.println(event.editable().toString()));
        verifyCodeObservable = RxView.clicks(btn_verify_code).subscribeOn(AndroidSchedulers.mainThread());
        verifyCodeObservable.subscribe(aVoid -> {
                            Observable.timer(0, 100, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).take(60).subscribe(aLong -> {
                                btn_verify_code.setText(60 - aLong + "'");
                                btn_verify_code.setEnabled(false);
                            }, (error) -> {
                            }, () -> {
                                        btn_verify_code.setEnabled(true);
                                        btn_verify_code.setText("获取验证码");
                                    }
                            );
                        }
                );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
