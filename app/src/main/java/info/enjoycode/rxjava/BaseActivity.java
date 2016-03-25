package info.enjoycode.rxjava;

import android.support.v7.app.AppCompatActivity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chenyuanming on 16/3/25.
 */
public class BaseActivity extends AppCompatActivity {
    private CompositeSubscription _compositeSubscription = new CompositeSubscription();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(_compositeSubscription.isUnsubscribed()){
            _compositeSubscription.unsubscribe();
        }
    }

    public void addSubscription(Subscription subscription){
        _compositeSubscription.add(subscription);
    }


}
