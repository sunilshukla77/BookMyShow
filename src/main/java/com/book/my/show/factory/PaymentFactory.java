package com.book.my.show.factory;

import com.book.my.show.service.IPaymentOptionService;
import com.book.my.show.type.PaymentType;

import java.util.HashMap;
import java.util.Map;

public abstract class PaymentFactory {
    public final static Map<PaymentType, Class<? extends IPaymentOptionService>> PAYMENT_TYPE_CLASS_MAP = new HashMap<>();
}
