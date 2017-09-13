package main

import rx.schedulers.Schedulers
import rx.subjects.ReplaySubject
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.TimeUnit


class ReplaySubjectTest extends Specification {

    @Unroll
    def "Standard ReplaySubject"() {
        when:
        ReplaySubject<Integer> s = ReplaySubject.create();
        s.subscribe { v -> println "Early: " + v }
        s.onNext(0)
        s.onNext(1)
        s.subscribe { v -> println "Late: " + v }
        s.onNext(2)
        then:
        s != null
    }

    def "ReplaySubject with size 2"() {
        when:
        ReplaySubject<Integer> s = ReplaySubject.createWithSize(2)
        s.onNext(0)
        s.onNext(1)
        s.onNext(2)
        s.onNext(3)
        s.subscribe { v -> println "Late: " + v }
        s.onNext(4)
        then:
        s != null
    }

    def "ReplaySubject with createWithTime"() {
        when:
        ReplaySubject<Integer> s = ReplaySubject.createWithTime(550, TimeUnit.MILLISECONDS, Schedulers.immediate())
        s.onNext(0)
        Thread.sleep(200)
        s.onNext(1)
        Thread.sleep(200)
        s.onNext(2)
        s.subscribe { v -> println "Late: " + v }
        s.onNext(3)
        then:
        s != null
    }

}