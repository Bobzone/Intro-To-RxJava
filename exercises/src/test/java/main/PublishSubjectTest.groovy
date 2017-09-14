package main

import rx.subjects.PublishSubject
import spock.lang.Specification


class PublishSubjectTest extends Specification {

    def "When value is pushed to PublishSubject it pushes it down to subscribers at the given moment"() {
        when:
        PublishSubject<Integer> s = PublishSubject.create()
        s.onNext(0)
        s.subscribe{ v -> println "Event:" + v }
        s.onNext(1)
        s.onNext(2)

        then:
        s != null
    }


}