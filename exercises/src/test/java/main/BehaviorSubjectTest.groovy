package main

import rx.subjects.BehaviorSubject
import spock.lang.Specification


class BehaviorSubjectTest extends Specification {

    // BehavioSubject only remembers last value. Similar to REPLAYSubject with buffer of size 1
    def "Standard BehaviorSubject test"() {
        when:
        BehaviorSubject<Integer> s = BehaviorSubject.create()
        s.onNext(0)
        s.onNext(1)
        s.onNext(2)
        s.subscribe { v -> println "Late: " + v }
        s.onNext(3)
        then:
        s.getValues().size() == 1
    }

    def "BehaviorSubject that is empty because events finished before subscription"() {
        when:
        BehaviorSubject<Integer> s = BehaviorSubject.create();
        s.onNext(0)
        s.onNext(1)
        s.onNext(2)
        s.onCompleted();
        s.subscribe(
                { v -> println v },
                { println "Error" },
                { println "Completed" }
        )
        then:
        s.getValues().size() == 0
    }

    def "BehaviorSubject handles first event if it was placed before someone subscribed"() {
        when:
        BehaviorSubject<Integer> s = BehaviorSubject.create(0);
        s.subscribe({ v -> println v })
        s.onNext(1)
        then:
        s.getValues().size() == 1
    }

    def "If events are completed BehaviourSubject empties"() {
        when:
        BehaviorSubject<Integer> s = BehaviorSubject.create(0);
        s.subscribe({ v -> println v })
        s.onNext(1)
        s.onCompleted()
        then:
        s.getValues().size() == 0
    }

}