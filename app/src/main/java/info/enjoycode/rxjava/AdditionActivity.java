package info.enjoycode.rxjava;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by chenyuanming on 16/3/25.
 */
public class AdditionActivity extends BaseActivity {
    @Bind(R.id.etNum1)
    EditText etNum1;
    @Bind(R.id.etNum2)
    EditText etNum2;
    @Bind(R.id.tvResult)
    TextView tvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_addtion);
        ButterKnife.bind(this);

        addSubscription(Observable.combineLatest(
                RxTextView.textChanges(etNum1).map(convertString2Int()),
                RxTextView.textChanges(etNum2).map(convertString2Int()),
                (num1, num2) -> num1 + num2
        ).subscribe(
                integer -> tvResult.setText(integer + "")
        ));
    }

    @NonNull
    private Func1<CharSequence, Integer> convertString2Int() {
        return charSequence -> {
            try {
                return Integer.parseInt(charSequence.toString());
            } catch (Exception e) {
                return 0;
            }
        };
    }
}
