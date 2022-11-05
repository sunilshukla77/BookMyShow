package com.book.my.show.service.impl;

import com.book.my.show.factory.PaymentFactory;
import com.book.my.show.service.IPaymentOptionService;
import com.book.my.show.type.PaymentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DebitCardPaymentOptionService implements IPaymentOptionService {
    static {
        PaymentFactory.PAYMENT_TYPE_CLASS_MAP.put(PaymentType.DEBIT_CARD, DebitCardPaymentOptionService.class);
    }

    @Override
    public boolean processPayment(double payableAmount) throws InterruptedException {
        log.info("Payment initiated via Debit Card completed successfully");
        Thread.sleep(1000);
        log.info("Payment via Wallet completed successfully");
        return true;
    }
}
