package main

import rx.exceptions.OnErrorNotImplementedException
import rx.subjects.ReplaySubject
import spock.lang.Specification


class ErrorHandlingTest extends Specification {

    def "onError action appeared in the event stream"() {
        when:
        ReplaySubject<Integer> s = ReplaySubject.create()
        s.subscribe(
                { v -> println "onNext: " + v },
                { e -> println "onError: " + e }
        )
        s.onNext(0)
        s.onError(new Exception("Oops"))

        then:
        s != null
    }

    def "onError action appeared in the event stream but no onError action was provided to the subscribe"() {
        when:
        ReplaySubject<Integer> s = ReplaySubject.create()
        s.subscribe(
                { v -> println "onNext: " + v },
//                { e -> println "onError: " + e }
        )
        s.onNext(0)
        s.onError(new Exception("Oops"))

        then:
        s != null
        thrown OnErrorNotImplementedException
    }

}