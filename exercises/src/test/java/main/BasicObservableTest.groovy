package main

import rx.Observable
import spock.lang.Specification


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
}
