package com.book.my.show.service;

public interface IPaymentOptionService {
    boolean processPayment(double payableAmount) throws Exception;
}
