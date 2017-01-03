package com.progressoft.jip.model.actions.impl;

import java.time.LocalDate;

import com.progressoft.jip.model.actions.Rule;

public enum Rules {

	THIS_YEAR(paymentDate -> {
		LocalDate today = LocalDate.now();
		return !isPastDay(paymentDate, today) && Rules.yearsAreEqual(paymentDate, today);
	}),

	THIS_MONTH(paymentDate -> {
		LocalDate today = LocalDate.now();
		return /*!isPastDay(paymentDate, today) && yearsAreEqual(paymentDate, today)
				&& monthsAreEqual(paymentDate, today)*/ true;
	}),

	THIS_DAY(paymentDate -> {
		LocalDate today = LocalDate.now();
		return !isPastDay(paymentDate, today) && yearsAreEqual(paymentDate, today) && monthsAreEqual(paymentDate, today)
				&& daysAreEqual(paymentDate, today);
	});

	private Rule rule;

	private Rules(Rule rule) {
		this.rule = rule;

	}

	public Rule getRule() {
		return this.rule;
	}

	private static boolean isPastDay(LocalDate expectedPaymentDate, LocalDate today) {
		return expectedPaymentDate.getDayOfYear() >= today.getDayOfYear();
	}

	private static boolean daysAreEqual(LocalDate expectedPaymentDate, LocalDate today) {
		return expectedPaymentDate.getDayOfYear() == today.getDayOfYear();
	}

	private static boolean monthsAreEqual(LocalDate expectedPaymentDate, LocalDate today) {
		return expectedPaymentDate.getMonth().equals(today.getMonth());
	}

	private static boolean yearsAreEqual(LocalDate expectedPaymentDate, LocalDate today) {
		return expectedPaymentDate.getYear() == today.getYear();
	}

}
