package com.dreamers.explorer.common;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by c029312 on 2/13/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class CommonPresenterTest {

    @InjectMocks
    CommonPresenter commonPresenter;
    @Mock
    CommonView commonView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });
    }

    @After
    public void tearDown(){
        commonPresenter = null;
        commonView = null;
    }

    @Test
    public void performRxJavaSample1() throws Exception {
//        CommonView commonView = Mockito.mock(CommonView.class);
        commonPresenter.attach(commonView);
        commonPresenter.performRxJavaSample1();
        Mockito.verify(commonView).showLogMessage(Mockito.anyString());
    }

    @Test
    public void performRxJavaSample2() throws Exception {
//        CommonView commonView = Mockito.mock(CommonView.class);
        commonPresenter.attach(commonView);
        commonPresenter.performRxJavaSample2();
        Mockito.verify(commonView).showLogMessage(Mockito.anyString());
    }

    @Test
    public void performRxJavaSample3() throws Exception {
        CommonView commonView = Mockito.mock(CommonView.class);
        commonPresenter.attach(commonView);
        commonPresenter.performRxJavaSample3();
        Mockito.verify(commonView).showLogMessage(Mockito.anyString());
    }

    @Test
    public void performRxJavaSample4() throws Exception {
        CommonView commonView = Mockito.mock(CommonView.class);
        commonPresenter.attach(commonView);
        commonPresenter.performRxJavaSample4();
        Mockito.verify(commonView).showLogMessage(Mockito.anyString());
    }

    @Test
    public void performRxJavaSample5() throws Exception {
        CommonView commonView = Mockito.mock(CommonView.class);
        commonPresenter.attach(commonView);
        commonPresenter.performRxJavaSample5();
        Mockito.verify(commonView).showLogMessage(Mockito.anyString());
    }

}