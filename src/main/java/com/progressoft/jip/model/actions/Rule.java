package com.progressoft.jip.model.actions;

import java.time.LocalDate;

@FunctionalInterface
public interface Rule {

	public boolean satistfy(LocalDate paymentDate);

}
