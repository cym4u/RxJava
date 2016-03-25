package info.enjoycode.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenyuanming on 16/3/25.
 */
public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    @Bind(R.id.et_search)
    EditText etSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        addSubscription(RxTextView.textChanges(etSearch).debounce(300, TimeUnit.MILLISECONDS).filter(charSequence -> !TextUtils.isEmpty(charSequence))
                .subscribe(charSequence -> {
            Log.d(TAG, "call() called with: " + "charSequence = [" + charSequence + "]");
        }));
    }

}
