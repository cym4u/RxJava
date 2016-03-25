package info.enjoycode.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;

/**
 * Created by chenyuanming on 16/3/25.
 */
public class FormValidateActivity extends BaseActivity {
    @Bind(R.id.etUserName)
    EditText etUserName;
    @Bind(R.id.etPassWord)
    EditText etPassWord;
    @Bind(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form_validate);
        ButterKnife.bind(this);

        //方案1
        Observable<CharSequence> _nameChangeObservable = RxTextView.textChanges(etUserName);
        Observable<CharSequence> _pwdChangeObservable = RxTextView.textChanges(etPassWord);
        addSubscription(Observable.combineLatest(_nameChangeObservable, _pwdChangeObservable, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence userName, CharSequence userPwd) {
                return !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPwd);
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean isValid) {
                btn.setEnabled(isValid);
            }
        }));

        //简化版
        addSubscription(Observable.combineLatest(
                RxTextView.textChanges(etUserName)
                , RxTextView.textChanges(etPassWord)
                , (userName, userPwd) -> !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPwd)
        ).subscribe(isValid -> btn.setEnabled(isValid)));
    }
}
