package main

import rx.Observable
import rx.Subscription
import spock.lang.Specification

import java.util.concurrent.TimeUnit


class InspectionTest extends Specification {

    def "all() stablishes that every value emitted by an observable meets given criteria - emits single 'false' because all is not met"() {
        when:
        Observable<Integer> obs = Observable.create {
            o ->
                o.onNext(0);
                o.onNext(10);
                o.onNext(10);
                o.onNext(2);
                o.onNext(5);
                o.onCompleted();
        }

        Subscription subscription = obs
                .all { i -> i % 2 == 0 }
                .subscribe(
                { v -> println "Emited: " + v },
                { e -> println "Error: " + e },
                { println "onCompleted " }
        )
        then:
        obs != null
    }

    def "now emits single 'true' because all is met"() {
        when:
        Observable<Integer> obs = Observable.create {
            o ->
                o.onNext(0);
                o.onNext(10);
                o.onNext(10);
                o.onNext(2);
                o.onCompleted();
        }

        Subscription subscription = obs
                .all { i -> i % 2 == 0 }
                .subscribe(
                { v -> println "Emited: " + v },
                { e -> println "Error: " + e },
                { println "onCompleted " }
        )
        then:
        obs != null
    }

    def "other example that shows how all() fails safely"() {
        when:
        Observable<Long> values = Observable.interval(150, TimeUnit.MILLISECONDS).take(5);

        Subscription subscription = values
                .all { i -> i < 3 } // Will fail eventually
                .subscribe(
                { v -> System.out.println("All: " + v) },
                { e -> System.out.println("All: Error: " + e) },
                { System.out.println("All: Completed") }
        );
        Subscription subscription2 = values
                .subscribe(
                { v -> System.out.println(v) },
                { e -> System.out.println("Error: " + e) },
                { System.out.println("Completed") }
        );
        System.in.read()
        then:
        values != null
    }
}