package info.enjoycode;


import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

/**
 * Created by chenyuanming on 16/1/3.
 */
public class Transforming {
    public static void buffer1() {
        Observable.range(0, 10).buffer(3).subscribe(System.out::println);
    }

    public static void buffer2() {
        Observable.range(0, 20).buffer(2, 5).subscribe(System.out::println);
    }

    public static void buffer3() {
        Observable.range(0, 20).buffer(2, 3).subscribe(System.out::println);
    }


    public static void flatMap() {
        Observable.range(0, 5).flatMap(new Func1<Integer, Observable<?>>() {
            @Override
            public Observable<?> call(Integer integer) {
                return Observable.just("observable " + integer);
            }
        }).subscribe(System.out::println);
    }

    public static void concatMap() {
        Observable.range(0, 5).concatMap(new Func1<Integer, Observable<?>>() {
            @Override
            public Observable<?> call(Integer integer) {
                return Observable.just("concat " + integer);
            }
        }).subscribe(System.out::println);
    }

    public static void switchMap() {
        Observable.range(0, 5).switchMap(new Func1<Integer, Observable<?>>() {
            @Override
            public Observable<?> call(Integer integer) {
                return Observable.just("switch " + integer);
            }
        }).subscribe(System.out::println);
    }


    public static void groupBy() {
//        Observable.range(0, 5).groupBy(new Func1<Integer, Boolean>() {
//            @Override
//            public Boolean call(Integer integer) {
//                return integer % 2 == 0;
//            }
//        }).subscribe(new Action1<GroupedObservable<Boolean, Integer>>() {
//            @Override
//            public void call(GroupedObservable<Boolean, Integer> result) {
//                result.subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.println(integer + "%2==0-->" + result.getKey());
//                    }
//                });
//            }
//        });

        Observable.range(1,10).groupBy(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer%3;
            }
        }).subscribe(new Action1<GroupedObservable<Integer, Integer>>() {
            @Override
            public void call(GroupedObservable<Integer, Integer> result) {
                result.subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer + "%3==" + result.getKey());
                    }
                });
            }
        });



    }

    public static void map() {
        Observable.range(0, 5).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return "number " + integer;
            }
        }).subscribe(System.out::println);
    }

    public static void scan() {
        Observable.range(1, 10).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        }).subscribe(System.out::println);

    }

    public static void scan2() {
        Observable.range(1, 10).scan((i1, i2) -> i1 + i2).takeLast(1).subscribe(System.out::println);

    }


    public static void window() {
        Observable.range(1, 10).window(1,3).subscribe(new Action1<Observable<Integer>>() {
            @Override
            public void call(Observable<Integer> integerObservable) {
                integerObservable.subscribe(System.out::println);
                System.out.println("----------------");
            }
        });
    }

    public static  void test(){
        Observable.just("1")
                .map(s -> { print("default "); return s;})
                .subscribeOn(Schedulers.newThread()).map(s -> 	{ print("subscribeOn(Schedulers.newThread() "); return s;})
                .subscribeOn(Schedulers.io()).map(s -> 	{ print("subscribeOn(Schedulers.io() "); return s;})
                .observeOn(Schedulers.io()).map(s -> 	{  print("observeOn(Schedulers.io()  "); return s;})
                .subscribeOn(Schedulers.immediate()).map(s -> 	{  print("subscribeOn(Schedulers.immediate() "); return s;  })
                .observeOn(Schedulers.computation()).map(s -> 	{  print("observeOn(Schedulers.computation() "); return s;})
                .subscribeOn(Schedulers.immediate()).map(s -> 	{  print("subscribeOn(Schedulers.immediate() "); return s;  })
                .subscribe((a) -> { print("onComplete ");  });
    }

    private static void print(String s) {
        System.out.println(s);
    }


    public static void main(String[] args) {
//        buffer1();
//        buffer2();
//        buffer3();
//        flatMap();
//        concatMap();//类似于最简单版本的flatMap，但是它按次序连接而不是合并那些生成的Observables，然后产生自己的数据序列。
//        switchMap();
//        groupBy();
//        map();
        scan();
//        scan2();
        window();
//        test();
    }
}
