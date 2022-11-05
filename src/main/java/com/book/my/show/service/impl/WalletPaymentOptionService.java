package com.book.my.show.service.impl;

import com.book.my.show.factory.PaymentFactory;
import com.book.my.show.service.IPaymentOptionService;
import com.book.my.show.type.PaymentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WalletPaymentOptionService implements IPaymentOptionService {
    static {
        PaymentFactory.PAYMENT_TYPE_CLASS_MAP.put(PaymentType.WALLET, WalletPaymentOptionService.class);
    }

    @Override
    public boolean processPayment(double payableAmount) throws InterruptedException {
        log.info("Payment initiated via Wallet completed successfully");
        Thread.sleep(1000);
        log.info("Payment via Wallet completed successfully");
        return true;
    }
}
