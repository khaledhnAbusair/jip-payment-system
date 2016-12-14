package com.progressoft.jip.datastructures.builder;

public interface PaymentPurposeBuilder {

    PaymentPurposeBuilder setName(String name);

    PaymentPurposeBuilder setCode(String code);

    PaymentPurposeBuilder build();

}
