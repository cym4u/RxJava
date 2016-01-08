package info.enjoycode;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

public class Creating {
    public static void create1() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).subscribe(System.out::println);
    }

    public static void defer1() {
        Observable.defer(() -> {
            //需要的时候才创建
            Student student = new Student();
            student.name = "student";
            return Observable.just(student);
        }).subscribe(student -> {
            System.out.println(String.format("id:%d,name:%s", student.id, student.name));
        });


        Observable.just("1","2").subscribe((s)->{
            System.out.println(s);
        });


    }


    public static void empty() {
        Observable.empty();
    }

    public static void never() {
        Observable.never();
    }

    public static void error() {
        Observable.error(null);
    }


    public static void from() {
        Integer[] datas = {1, 2, 3, 4, 5, 6};
        Observable.from(datas).subscribe(System.out::println);
    }


    public static void interval() {
        Observable.interval(100, TimeUnit.MILLISECONDS).subscribe(System.out::println);
    }

    public static void just() {
        Observable.just(1, "text", 2).subscribe(System.out::println);
    }

    public static void range() {
        Observable.range(1, 100).subscribe(System.out::println);
    }

    public static void repeat(){
      Observable.range(0,3).repeat(2).subscribe(System.out::println);

    }
    public static void timer(){
      Observable.timer(100,TimeUnit.MILLISECONDS).takeLast(2).subscribe(System.out::println);
    }

    public static void main(String[] args) {
//        create1();
//        defer1();
//        from();
//        interval();//TODO 无效
//        just();
//        range();
//        repeat();
//        timer();//TODO 无效

        defer1();
    }

}


class Student {
    public int id;
    public String name;
}