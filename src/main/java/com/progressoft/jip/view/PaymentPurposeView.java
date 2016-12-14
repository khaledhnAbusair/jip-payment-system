package com.progressoft.jip.view;

import com.progressoft.jip.datastructures.builder.PaymentPurposeBuilder;

/**
 * Created by ahmad on 12/14/16.
 */
public class PaymentPurposeView implements PaymentPurposeBuilder {

    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public PaymentPurposeView setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PaymentPurposeView setCode(String code) {
        this.code = code;
        return this;
    }

    public PaymentPurposeView build() {
        return new PaymentPurposeView().setCode(code).setName(name);
    }

}
