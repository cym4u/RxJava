package info.enjoycode;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by chenyuanming on 16/1/3.
 */
public class Filtering {
    public static void debounce() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 50; i++) {
                    subscriber.onNext(i);
                    try {
                        Thread.sleep(new Random().nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        }).debounce(200, TimeUnit.MILLISECONDS).subscribe(System.out::println);
    }


    public static void distinct() {
        Observable.just(1, 2, 3, 2, 1, 5, 4).distinct().subscribe(System.out::println);
    }

    public static void distinctUntilChanged() {
        Observable.just(1, 2, 2,2, 3, 2,  5, 4, 4, 5).distinctUntilChanged().subscribe(System.out::println);
    }


    public static void distinctUntilChanged2() {
        Observable.just(1, 2, 3, 4, 6, 7, 8, 10).distinctUntilChanged(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer % 2 == 0;
            }
        }).subscribe(System.out::println);
    }

    public static void elemntAtOrDefault() {
        Observable.empty().elementAtOrDefault(0, "default value").subscribe(System.out::println);
//        Observable.empty().elementAt(0).subscribe(System.out::println);
    }

    public static void filter() {
        Observable.range(1, 100).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer % 7 == 0 || integer % 10 == 7 || integer / 10 == 7;
            }
        }).subscribe(System.out::println);
    }

    public static void ofType() {
        Observable.just(1, 2, "text", "abc", 5, false).ofType(String.class).subscribe(System.out::println);
    }

    public static void first() {
//      Observable.empty().first().subscribe(System.out::println);//Exception in thread "main" rx.exceptions.OnErrorNotImplementedException: Sequence contains no elements
        Observable.just(1).first().subscribe(System.out::println);
        Observable.empty().firstOrDefault("defalut value").subscribe(System.out::println);
    }

    public static void first2() {

//        Observable.just(1, 2, "text",true, "abc", 5, false).first(new Func1<Serializable, Boolean>() {
//            @Override
//            public Boolean call(Serializable serializable) {
//
//
//                return serializable instanceof String;
//            }
//        }).subscribe(System.out::println);


        Observable.just(1, 2, "text",true, "abc", 5, false).filter(new Func1<Serializable, Boolean>() {
            @Override
            public Boolean call(Serializable serializable) {
                return serializable instanceof String;
            }
        }).subscribe(System.out::println);
    }

    public static void ignoreElements() {
        Observable.range(0, 10).ignoreElements().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext");

            }
        });
    }

    public static void last() {
        Observable.empty().lastOrDefault("default value").subscribe(System.out::println);
    }

    public static void sample() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 50; i++) {
                    subscriber.onNext(i);
                    try {
                        Thread.sleep(new Random().nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        }).sample(200, TimeUnit.MILLISECONDS).subscribe(System.out::println);
    }

    public static void skip() {
        Observable.range(0, 15).skip(7).subscribe(System.out::println);
    }

    public static void skipLast() {
        Observable.range(0, 15).skipLast(7).subscribe(System.out::println);
    }


    public static void take(){
      Observable.range(1,10).take(5).subscribe(System.out::println);
    }

    public static void takeLastBuffer(){
      Observable.range(1,10).takeLastBuffer(3).subscribe(System.out::println);
    }


    public static void throttleFirst() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5000; i++) {
                    subscriber.onNext(i);
                    System.out.println("----------"+i);
                    try {
                        Thread.sleep(new Random().nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        }).throttleFirst(2000, TimeUnit.MILLISECONDS).subscribe(System.out::println);
    }

    public static void main(String[] args) {
//        debounce();
//        distinct();
        distinctUntilChanged();
//        distinctUntilChanged2();//过滤奇偶数
//        elemntAtOrDefault();
//        filter();//逢七过
//        ofType();
//        first();
//        first2();
//        ignoreElements();
//        last();
//        sample();
//        skip();
//        skipLast();
//        take();
//        takeLastBuffer();

//        throttleFirst();



    }
}
