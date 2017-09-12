package main

import spock.lang.Specification
import spock.lang.Unroll


class BookTest extends Specification {

    @Unroll
    def "this test should work because it does nothing"() {
        given:
        def book = null
        when:
        book = new Book()
        then:
        book != null
    }
}
