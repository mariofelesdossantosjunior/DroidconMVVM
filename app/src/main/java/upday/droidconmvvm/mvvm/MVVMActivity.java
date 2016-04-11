package upday.droidconmvvm.mvvm;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import upday.droidconmvvm.DroidconApplication;
import upday.droidconmvvm.R;
import upday.droidconmvvm.datamodel.IDataModel;

public class MVVMActivity extends AppCompatActivity {

    @NonNull
    private CompositeSubscription mSubscription;

    @NonNull
    private ViewModel mViewModel;

    @Nullable
    private TextView mGreetingView;

    @Nullable
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = new ViewModel(getDataModel());
        setupViews();
    }

    private void setupViews() {
        mGreetingView = (TextView) findViewById(R.id.greeting);
        mImageView = (ImageView) findViewById(R.id.image);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bind();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unBind();
    }

    private void unBind() {
        mSubscription.unsubscribe();
    }

    private void bind() {
        mSubscription = new CompositeSubscription();

        mSubscription.add(mViewModel.getGreeting()
                                    .subscribeOn(Schedulers.computation())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(this::setGreeting));
    }

    private void setGreeting(@NonNull final String greeting) {
        assert mGreetingView != null;

        mGreetingView.setText(greeting);
    }

    private void setImage(@DrawableRes final int resId) {


        mSubscription.add(mViewModel.getImage()
                                    .subscribeOn(Schedulers.computation())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(this::setImage));

        assert mImageView != null;

        mImageView.setImageResource(resId);
    }

    @NonNull
    private IDataModel getDataModel() {
        return ((DroidconApplication) getApplication()).getDataModel();
    }

//    private void setGreeting(@NonNull final String greeting) {
//        assert mGreetingView != null;
//
//        mGreetingView.setText(greeting);
//
//        if (greeting.contains("Zagreb")) {
//            setImage(R.drawable.zagreb);
//        }
//    }
}
