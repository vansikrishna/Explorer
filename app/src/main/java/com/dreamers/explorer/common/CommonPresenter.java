package com.dreamers.explorer.common;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by c029312 on 2/13/18.
 */

public class CommonPresenter {

    private CommonView commonView;

    public CommonPresenter(){

    }

    public void attach(CommonView commonView) {
        this.commonView = commonView;
    }

    public void performRxJavaSample1(){
        Observable.just("long", "longer", "longest")
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        commonView.showLogMessage("Processing "+s+" on thread -  "+Thread.currentThread().getName());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String input) throws Exception {
                        return input.length();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer length) throws Exception {
                        commonView.showLogMessage("Result available as "+length);
                    }
                });
    }

    public void performRxJavaSample2(){
        Observable.just("long", "longer", "longest")
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        commonView.showLogMessage("Processing doOnNext with value "+s+" on thread -  "+Thread.currentThread().getName());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .flatMap(new Function<String, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(String s) throws Exception {
                        commonView.showLogMessage("Processing flatMap with value "+s+" on thread -  "+Thread.currentThread().getName());
                        return Observable.just(s.length()).subscribeOn(Schedulers.newThread());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer length) throws Exception {
                        commonView.showLogMessage("Length -> "+length+" -> received on "+Thread.currentThread().getName());
                    }
                });
    }

    public void performRxJavaSample3(){
        Observable.just("long", "longer", "longest")
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        commonView.showLogMessage("Processing doOnNext with value "+s+" on thread -  "+Thread.currentThread().getName());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .concatMap(new Function<String, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(String s) throws Exception {
                        commonView.showLogMessage("Processing concatMap with value "+s+" on thread -  "+Thread.currentThread().getName());
                        return Observable.just(s.length()).subscribeOn(Schedulers.newThread());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer length) throws Exception {
                        commonView.showLogMessage("Length -> "+length+" -> received on "+Thread.currentThread().getName());
                    }
                });
    }

    public void performRxJavaSample4(){
        Observable.just("long", "longer", "longest")
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        commonView.showLogMessage("Processing doOnNext with value "+s+" on thread -  "+Thread.currentThread().getName());
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        commonView.showLogMessage("mapped element "+s+" on thread -  "+Thread.currentThread().getName());
                        return s;
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.length() > 4;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        commonView.showLogMessage("String "+string+" with Length -> "+string.length()+" -> received on "+Thread.currentThread().getName());
                    }
                });
    }

    public void performRxJavaSample5(){
        Observable.just("long", "longer", "longest")
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        commonView.showLogMessage("first doOnNext with value "+s+" Processing on thread -  "+Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.computation())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        commonView.showLogMessage("first map with value "+s+" Processing on thread -  "+Thread.currentThread().getName());
                        return s+"m1";
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        commonView.showLogMessage( "second doOnNext with value "+s+" Processing on thread -  "+Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        commonView.showLogMessage("second map with value "+s+" Processing on thread -  "+Thread.currentThread().getName());
                        return s+"m2";
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        commonView.showLogMessage("third map - element "+s+" on thread -  "+Thread.currentThread().getName());
                        return s;
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.length() > 4;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        commonView.showLogMessage("String "+string+" with Length -> "+string.length()+" -> received on "+Thread.currentThread().getName());
                    }
                });
    }

}
