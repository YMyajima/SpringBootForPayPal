package com.sample.paypal.domain.paypal.payment.service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.sample.paypal.constants.PaymentIntent;
import com.sample.paypal.setting.exception.BusinessException;
import com.sample.paypal.setting.property.PayPalProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Slf4j
@Service
public class PayPalPaymentCreateService {

    @Autowired
    private PayPalProperty payPalProperty;

    public Payment payment() {
        APIContext apiContext = new APIContext(
                payPalProperty.getClientId(),
                payPalProperty.getSecret(),
                payPalProperty.getMode()
        );
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal("1.00");
        // customize amount.detail

        Item item = new Item();
        item.setName("sample item");
        item.setDescription("sample item description.");
        item.setQuantity("1");
        item.setCurrency(Currency.getInstance(Locale.US).getCurrencyCode());
        item.setPrice("1.00");
        List<Item> items = new ArrayList<>();
        items.add(item);
        ItemList itemList = new ItemList();
        itemList.setItems(items);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("sample paypal application.");
        transaction.setNoteToPayee("thank you.");
        transaction.setInvoiceNumber(UUID.randomUUID().toString());
        transaction.setItemList(itemList);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();

        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent(PaymentIntent.SALE);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(payPalProperty.makeCancelUrl());
        redirectUrls.setReturnUrl(payPalProperty.makeReturnUrl());
        payment.setRedirectUrls(redirectUrls);

        try {
            Payment createdPayment = payment.create(apiContext);
            log.debug(createdPayment.toJSON());
            return createdPayment;
        } catch (PayPalRESTException e) {
            log.error("paypal payment create error. error={}", e.getMessage());
            throw new BusinessException("paypal payment create error.");
        }
    }
}
