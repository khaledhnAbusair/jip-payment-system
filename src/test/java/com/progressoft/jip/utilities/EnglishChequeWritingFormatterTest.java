package com.progressoft.jip.utilities;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.progressoft.jip.utilities.chequewriting.impl.EnglishChequeAmountWriter;

public class EnglishChequeWritingFormatterTest {
	EnglishChequeAmountWriter formatter;
	String currencyCode;

	@Before
	public void setup() {
		currencyCode = "USD";
		formatter = new EnglishChequeAmountWriter();
	}

	@Test
	public void given1_WhenFormattingChequeAmount_ReturnsOne() {
		assertEquals("One US Dollar ", formatter.writeAmountInWords(new BigDecimal("1"), currencyCode));
	}

	@Test
	public void given2_WhenFormattingChequeAmount_ReturnsTwo() {
		assertEquals("Ten US Dollar ", formatter.writeAmountInWords(new BigDecimal("10"), currencyCode));
	}

	@Test
	public void given20_WhenFormattingChequeAmount_ReturnsTwenty() {
		assertEquals("Twenty US Dollar ", formatter.writeAmountInWords(new BigDecimal("20"), currencyCode));
	}

	@Test
	public void given22_WhenFormattingChequeAmount_ReturnsTwentyTwo() {
		assertEquals("Twenty Two US Dollar ", formatter.writeAmountInWords(new BigDecimal("22"), currencyCode));
	}

	@Test
	public void given32_WhenFormattingChequeAmount_ReturnsThirtyTwo() {
		assertEquals("Thirty Two US Dollar ", formatter.writeAmountInWords(new BigDecimal("32"), currencyCode));
	}

	@Test
	public void given55_WhenFormattingChequeAmount_ReturnsFiftyFive() {
		assertEquals("Fifty Five US Dollar ", formatter.writeAmountInWords(new BigDecimal("55"), currencyCode));
	}

	@Test
	public void given555_WhenFormattingChequeAmount_ReturnsFiveHundredFiftyFive() {
		assertEquals("Five Hundred Fifty Five US Dollar ",
				formatter.writeAmountInWords(new BigDecimal("555"), currencyCode));
	}

	@Test
	public void given567_WhenFormattingChequeAmount_ReturnsFiveHundredSixtySeven() {
		assertEquals("Five Hundred Sixty Seven US Dollar ",
				formatter.writeAmountInWords(new BigDecimal("567"), currencyCode));
	}

	@Test
	public void given351_WhenFormattingChequeAmount_ReturnsThreeHundredFiftyOne() {
		assertEquals("Three Hundred Fifty One US Dollar ",
				formatter.writeAmountInWords(new BigDecimal("351"), currencyCode));
	}

	@Test
	public void given300_WhenFormattingChequeAmount_ReturnsThreeHundred() {
		assertEquals("Three Hundred US Dollar ", formatter.writeAmountInWords(new BigDecimal("300"), currencyCode));
	}

	@Test
	public void given301_WhenFormattingChequeAmount_ReturnsThreeHundredOne() {
		assertEquals("Three Hundred One US Dollar ", formatter.writeAmountInWords(new BigDecimal("301"), currencyCode));
	}

	@Test
	public void given1000_WhenFormattingChequeAmount_ReturnsOneThousand() {
		assertEquals("One Thousand  US Dollar ", formatter.writeAmountInWords(new BigDecimal("1000"), currencyCode));
	}

	@Test
	public void given1050_WhenFormattingChequeAmount_ReturnsOneThousandFifty() {
		assertEquals("One Thousand Fifty US Dollar ",
				formatter.writeAmountInWords(new BigDecimal("1050"), currencyCode));
	}

	@Test
	public void given5423_WhenFormattingChequeAmount_ReturnsFiveThousandFourHundredTwentyThree() {
		assertEquals("Five Thousand Four Hundred Twenty Three US Dollar ",
				formatter.writeAmountInWords(new BigDecimal("5423"), currencyCode));
	}

	@Test
	public void given1000000_WhenFormattingChequeAmount_ReturnsOneMillion() {
		assertEquals("One Million  US Dollar ", formatter.writeAmountInWords(new BigDecimal("1000000"), currencyCode));
	}

	@Test
	public void given1000050_WhenFormattingChequeAmount_ReturnsOneMillionFifty() {
		assertEquals("One Million Fifty US Dollar ",
				formatter.writeAmountInWords(new BigDecimal("1000050"), currencyCode));
	}

	@Test
	public void given6432500_WhenFormattingChequeAmount_ReturnsSixMillionFourHundredThirtyTwoThousandFiveHundred() {
		assertEquals("Six Million Four Hundred Thirty Two Thousand Five Hundred US Dollar ",
				formatter.writeAmountInWords(new BigDecimal("6432500"), currencyCode));
	}

	@Test
	public void given1000000000_WhenFormattingChequeAmount_ReturnsOneBillion() {
		assertEquals("One Billion  US Dollar ",
				formatter.writeAmountInWords(new BigDecimal("1000000000"), currencyCode));
	}

	@Test
	public void given0_WhenFormattingChequeAmount_ReturnsEmptyString() {
		assertEquals("", formatter.writeAmountInWords(new BigDecimal("0"), currencyCode));
	}

	@Test
	public void given100_WhenFormattingChequeAmount_ReturnsOneHundred() {
		assertEquals("One Hundred US Dollar ", formatter.writeAmountInWords(new BigDecimal("100"), currencyCode));
	}

	@Test
	public void given999_WhenFormattingChequeAmount_ReturnsNineHundredNinetyNine() {
		assertEquals("Nine Hundred Ninety Nine US Dollar ",
				formatter.writeAmountInWords(new BigDecimal("999"), currencyCode));
	}

	@Test
	public void given101_WhenFormattingChequeAmount_ReturnsOneHundredOne() {
		assertEquals("One Hundred One US Dollar ", formatter.writeAmountInWords(new BigDecimal("101"), currencyCode));
	}

	@Test
	public void givenDecimalNumberInMillions_WhenFormattingChequeAmount_ReturnsFractionalNumber() {
		assertEquals("Six Million Nine Hundred Eighty One Thousand Two Hundred Thirty One US Dollar Sixty Three Cents ",
				formatter.writeAmountInWords(new BigDecimal("6981231.63"), currencyCode));

	}

	@Test
	public void givenDecimalNumber_WhenFormattingChequeAmount_ReturnsFractionalNumber() {
		assertEquals("Three Thousand Five Hundred Sixty Four US Dollar Fifty One Cents ",
				formatter.writeAmountInWords(new BigDecimal("3564.51"), currencyCode));
	}

}
