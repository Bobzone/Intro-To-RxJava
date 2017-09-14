package main

import rx.Subscription
import rx.functions.Action0
import rx.subjects.ReplaySubject
import rx.subjects.Subject
import rx.subscriptions.Subscriptions
import spock.lang.Specification


class UnsubscribingTest extends Specification {

    def "Unsubscribe will cause stop receiving values"() {
        when:
        Subject<Integer, Integer> values = ReplaySubject.create()
        def subscription = values.subscribe(
                { v -> println v },
                { e -> println e },
                { println "Done" }
        )
        values.onNext(0)
        values.onNext(1)
        subscription.unsubscribe()
        values.onNext(2)
        then:
        values != null
    }

    def "Unsubscribing one obs does not interfere with obs on the same observable"() {
        when:
        Subject<Integer, Integer> s = ReplaySubject.create()
        def subscription1 = s.subscribe { v -> println "First:" + v }
        def subscription2 = s.subscribe { v -> println "Second:" + v }

        s.onNext(0)
        subscription2.unsubscribe()
        s.onNext(1)

        then:
        s != null
        !subscription1.isUnsubscribed()
        subscription2.isUnsubscribed()
    }

    def "Create takes an Action that will be performed upon unsubscription "() {
        when:
        Subscription s = Subscriptions.create { println "Clean" }
        s.unsubscribe()
        then:
        s.isUnsubscribed()
    }

}