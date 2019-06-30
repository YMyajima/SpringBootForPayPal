package com.sample.paypal.utils.DateFormatUtils


import spock.lang.Specification

import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Format extends Specification {

    def "paypal-payment test"() {
        when:
        final String format = "yyyy-mm-dd'T'hh:mm:ss'Z'"
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format)
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC)
        String strDateTimeFormat = now.format(dtf)
        println(strDateTimeFormat)

        then:
        true
    }
}
