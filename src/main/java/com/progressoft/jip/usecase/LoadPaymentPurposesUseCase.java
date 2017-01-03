package com.progressoft.jip.usecase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.progressoft.jip.repository.PaymentPurposeRepository;
import com.progressoft.jip.view.PaymentPurposeView;

public class LoadPaymentPurposesUseCase {

    private PaymentPurposeRepository repository;

    public LoadPaymentPurposesUseCase(PaymentPurposeRepository repository) {
        this.repository = repository;
    }

    public Collection<PaymentPurposeView> loadPaymentPurposes() {
        List<PaymentPurposeView> purposes = new ArrayList<>();
        PaymentPurposeView view = new PaymentPurposeView();
        repository.loadPaymentPurposes().stream().forEach((purpose) -> {
            purpose.buildPaymentPurposeView(view);
            purposes.add(view.build());
        });
        return purposes;
    }

}
