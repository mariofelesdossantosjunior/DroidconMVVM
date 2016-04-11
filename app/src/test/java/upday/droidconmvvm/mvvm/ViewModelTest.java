package upday.droidconmvvm.mvvm;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.observers.TestSubscriber;
import upday.droidconmvvm.R;
import upday.droidconmvvm.datamodel.IDataModel;

public class ViewModelTest {

    @Mock
    private IDataModel mDataModel;

    private ViewModel mViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mViewModel = new ViewModel(mDataModel);
    }

    @Test
    public void testGetGreeting_emitsCorrectGreeting() {
        String greeting = "Hello!";
        Mockito.when(mDataModel.getGreeting()).thenReturn(Observable.just(greeting));
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();

        mViewModel.getGreeting().subscribe(testSubscriber);

        testSubscriber.assertValue(greeting);
    }

    @Test
    public void testGetImage_emits_WhenGreetingContainsZagreb() {
        String greeting = "Hello " + ViewModel.ZAGREB;
        Mockito.when(mDataModel.getGreeting()).thenReturn(Observable.just(greeting));
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        mViewModel.getImage().subscribe(testSubscriber);

        testSubscriber.assertValue(R.drawable.zagreb);
    }

    @Test
    public void testGetImage_doesNotEmit_WhenGreetingDoesNotContainZagreb() {
        String greeting = "Hello, Berlin!";
        Mockito.when(mDataModel.getGreeting()).thenReturn(Observable.just(greeting));
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        mViewModel.getImage().subscribe(testSubscriber);

        testSubscriber.assertNoValues();
    }
}

