package main

import rx.Observable
import rx.Subscription
import spock.lang.Specification

import java.util.concurrent.TimeUnit


class BasicObservableTest extends Specification {

    def "Emit a series of values then terminate"() {
        when:
        Observable<String> s = Observable.just("one", "two", "three")
        s.subscribe(
                { v -> println "onNext: " + v },
                { e -> println "onError: " + e },
                { println "Completed" }
        )
        then:
        s != null
    }

    def "Observable.empty emits single onCompleted and nothing else"() {
        when:
        Observable<String> s = Observable.empty()
        s.subscribe(
                { v -> println "onNext: " + v },
                { e -> println "onError: " + e },
                { println "Completed" }
        )
        then:
        s != null
    }

    def "just() caches one value, which might not be preferred solution"() {
        when:
        Observable<Long> time = Observable.just(System.currentTimeMillis())
        time.subscribe { v -> println "Emit: " + v }
        Thread.sleep(1000)
        time.subscribe { v -> println "Emit: " + v }
        then:
        time != null
    }

    def "using defer() might solve this problem"() {
        when:
        Observable<Long> defer = Observable.defer { Observable.just(System.currentTimeMillis()) }
        defer.subscribe { v -> println "Emit: " + v }
        Thread.sleep(1000)
        defer.subscribe { v -> println "Emit: " + v }

        then:
        defer != null
    }

    def "Supply a Subscriber to create"() {
        when:
        Observable<String> values = Observable.create { o ->
            o.onNext("Hello");
            o.onCompleted();
        }
        values.subscribe(
                { v -> println "onNext: " + v },
                { e -> println "onError: " + e },
                { println "Completed" }
        )
        then:
        values != null
    }

    def "Emit specified range of integers"() {
        when:
        Observable<Integer> numbers = Observable.range(1, 15)
        numbers.subscribe { v -> println v }

        then:
        numbers != null
    }

    def "timer() usage example"() {
        when:
        Observable<Long> values = Observable.timer(2, 1, TimeUnit.SECONDS);
        Subscription subscription = values.subscribe(
                { v -> System.out.println("Received: " + v) },
                { e -> System.out.println("Error: " + e) },
                { System.out.println("Completed") }
        )
        System.in.read();

        then:
        values != null
    }

}
