package main

import rx.subjects.AsyncSubject
import spock.lang.Specification


class AsyncSubjectTest extends Specification {

    // AsyncSubject cached last value. It doesnt emit anything before sequence finishes.
    // It is to be used to emit a single value and immediately terminate
    def "Standard AsyncSubject test"() {
        when:
        AsyncSubject<Integer> s = AsyncSubject.create()
        s.subscribe { v -> println("Emit: " + v) }

        s.onNext(0)
        s.onNext(1)

        s.onCompleted()

        then:
        s.hasValue() && s.hasCompleted()
    }

    def "AsyncSubject has no values because it did not complete"() {
        when:
        AsyncSubject<Integer> s = AsyncSubject.create()
        s.subscribe { v -> println("Emit: " + v) }

        s.onNext(0)
        s.onNext(1)

        //s.onCompleted() <- events didnt complete

        then:
        !s.hasValue() || !s.hasCompleted()
    }

}