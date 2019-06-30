package com.sample.paypal.domain.paypal.payment.service.PayPalPaymentService

import com.paypal.api.payments.Links
import com.paypal.api.payments.Payment
import com.sample.paypal.PayPalApplication
import com.sample.paypal.domain.paypal.payment.service.PayPalPaymentCreateService
import com.sample.paypal.setting.exception.BusinessException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = PayPalApplication.class)
class PaymentTest extends Specification {

    @Autowired
    private PayPalPaymentCreateService payPalPaymentService

    def "paypal-payment test"() {
        when:
        Payment payment = payPalPaymentService.payment()
        List<Links> links = payment.getLinks()
        for (Links link : links) {
            if (link.getRel() == "approval_url") {
                String href = link.getHref()
                println("redirect to: " + href)
            }
        }

        then:
        notThrown(BusinessException)
        println(payment)
    }
}
