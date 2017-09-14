package main

import rx.Observable
import rx.Subscription
import spock.lang.Specification

import java.util.concurrent.FutureTask
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class TransitionToObservableTest extends Specification {

//    Observable<ActionEvent> events = Observable.create(o -> {
//        button2.setOnAction(new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent e) {
//                o.onNext(e)
//            }
//        });
//    })

    def "turn future into observable"() {
        when:
        FutureTask<Integer> f = new FutureTask<Integer>({
            Thread.sleep(2000);
            return 21;
        });
        new Thread(f).start();

        Observable<Integer> values = Observable.from(f);

        Subscription subscription = values.subscribe(
                { v -> System.out.println("Received: " + v) },
                { e -> System.out.println("Error: " + e) },
                { System.out.println("Completed") }
        );

        then:
        f != null
    }

    def "turn future into observable, but await only certain time before an event gets emitted"() {
        when:
        FutureTask<Integer> future = new FutureTask<Integer>({
            Thread.sleep(3000)
            return 30
        })

        Observable<Integer> obs = Observable.from(future, 2000, TimeUnit.MILLISECONDS)

        Subscription subscription = obs.subscribe(
                { v -> System.out.println("Received: " + v) },
                { e -> System.out.println("Error: " + e) },
                { System.out.println("Completed") }
        );

        then:
        thrown TimeoutException
    }

    def "You can turn any collection into observables as well, elements of the collection will get emitted and then the onComplete event"() {
        when:
        Integer[] array = [1, 2, 3, 4]
        Observable<Integer> obs = Observable.from(array)

        Subscription subscription = obs.subscribe(
                { v -> System.out.println("Received: " + v) },
                { e -> System.out.println("Error: " + e) },
                { System.out.println("Completed") }
        );
        then:// TODO implement then
        obs != null
    }

}