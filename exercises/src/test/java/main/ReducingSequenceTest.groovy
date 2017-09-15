package main

import rx.Observable
import rx.Subscription
import spock.lang.Specification

import java.util.concurrent.TimeUnit


class ReducingSequenceTest extends Specification {

    def "test of the distinct() transformation"() {
        when:
        Observable<Integer> obs = Observable.create { o ->
            o.onNext(1)
            o.onNext(2)
            o.onNext(2)
            o.onNext(3)
            o.onNext(3)
            o.onNext(1)
            o.onCompleted()
        }

        Subscription subscribe = obs
                .distinct()
                .subscribe(
                { v -> println "Emited: " + v },
                { e -> println "Error: " + e },
                { println "onCompleted " }
        )

        then:
        obs != null
    }

    def "you can supply the distinct key inside the distinct method"() {
        when:
        Observable<String> obs = Observable.create { o ->
            o.onNext("a")
            o.onNext("a")
            o.onNext("b")
            o.onNext("b")
            o.onNext("bb")
            o.onNext("aa")
            o.onCompleted()
        }

        Subscription subscribe = obs
                .distinct { event -> event.charAt(0) }
                .subscribe(
                { v -> println "Emited: " + v },
                { e -> println "Error: " + e },
                { println "onCompleted " }
        )

        then:
        obs != null
    }

    def "you can also use the distinctUntilChanged that filters out only consecutive same events"() {
        when:
        Observable<String> obs = Observable.create { o ->
            o.onNext("a")
            o.onNext("b")
            o.onNext("b")
            o.onNext("aa")
            o.onNext("bb")
            o.onNext("bb")
            o.onCompleted()
        }

        Subscription subscribe = obs
                .distinctUntilChanged { event -> event.charAt(0) }
                .subscribe(
                { v -> println "Emited: " + v },
                { e -> println "Error: " + e },
                { println "onCompleted " }
        )

        then:
        //expecting a, b, aa, bb
        obs != null
    }

    def "takeWhile tests"() {
        when:
        Observable<Integer> obs = Observable
                .create { o ->
            o.onNext(1);
            o.onNext(1);
            o.onNext(2);
            o.onNext(3);
            o.onNext(2);
            o.onCompleted();
        }

        Subscription subscription = obs
                .takeWhile { v -> v < 2 }
                .subscribe(
                { v -> println "Emited: " + v },
                { e -> println "Error: " + e },
                { println "onCompleted " }
        )

        then:
        obs != null
    }
}