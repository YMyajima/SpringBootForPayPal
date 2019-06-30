package com.sample.paypal.component

import com.sample.paypal.PayPalApplication
import com.sample.paypal.setting.property.EncryptProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = PayPalApplication.class)
class EncryptorUnitTest extends Specification {

    @Autowired
    private Encryptor encryptor

    @Autowired
    private EncryptProperty encryptProperty

    /**
     * 正常系テスト。
     * @return
     */
    def "暗号化するためのテスト"() {
        when:
        def encrypt1 = encryptor.encrypt("root", encryptProperty.getDatabaseConnectionEncrypt())
        def encrypt2 = encryptor.encrypt("miyajimahogehoge", encryptProperty.getDatabaseConnectionEncrypt())

        then:
        println(encrypt1)
        println(encrypt2)
        true
    }
}
