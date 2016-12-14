package com.progressoft.jip.usecase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.sql.DataSource;

import com.progressoft.jip.ui.Menu;
import com.progressoft.jip.ui.ItemContentFactory;
import org.apache.commons.dbcp.BasicDataSource;

import com.progressoft.jip.context.AppContext;
import com.progressoft.jip.datastructures.PaymentRequestDataStructure;
import com.progressoft.jip.model.PaymentRequest;
import com.progressoft.jip.utilities.DataBaseSettings;

public class CreateNewPaymentRequestUseCase {
    private AppContext appContext;

    public CreateNewPaymentRequestUseCase() {
        appContext = new AppContext(dataSourceConfiguration());
    }

    public void createPaymentRequestFrom(String orderingIBAN) {
        PaymentRequestDataStructure paymentRequest = new PaymentRequestDataStructure();
        paymentRequest.setOrderingAccountIBAN(orderingIBAN);
        Scanner input = new Scanner(System.in);

        System.out.println("Enter Beneficiary Account IBAN:");
        paymentRequest.setBeneficiaryAccountIBAN(input.nextLine());
        System.out.println("Enter Beneficiary Name: ");
        paymentRequest.setBeneficiaryName(input.nextLine());

        System.out.println("Select Currency Code: ");

        Menu currencyMenu = Menu.menu("Currencies");
        LoadCurrenciesUseCase currenciesUseCase = new LoadCurrenciesUseCase(appContext.currencyRepository());
        currenciesUseCase.loadCurrencies().stream().forEach((currencyView) -> {
            currencyMenu.addMenuItem(ItemContentFactory.instance(currencyView.getName(), () -> {
                paymentRequest.setCurrencyCode(currencyView.getCode());
            }));
        });
        currencyMenu.display(System.out);

        System.out.println("Enter Payment Amount");
        paymentRequest.setPaymentAmount(Double.parseDouble(input.nextLine()));

        paymentRequest.setPaymentDate(toSqlDate(input));

        System.out.println("Select Payment Purpose: ");
        Menu purposesMenu = Menu.menu("Purposes");
        LoadPaymentPurposesUseCase purposesUseCase = new LoadPaymentPurposesUseCase(appContext.paymentPurposeRepository());
        purposesUseCase.loadPaymentPurposes().stream().forEach((paymentPurposeView) -> {
            purposesMenu.addMenuItem(ItemContentFactory.instance(paymentPurposeView.getName(), () -> {
                paymentRequest.setPurposeCode(paymentPurposeView.getCode());
            }));
        });
        purposesMenu.display(System.out);


        appContext.paymentRequestRepositroy().inserPaymentRequest(new PaymentRequest(paymentRequest));
        System.out.println("Payment Request Added Successfully");
    }

    private java.sql.Date toSqlDate(Scanner input) {
        try {
            System.out.println("Enter Payment Date dd/mm/yyyy:");
            return new java.sql.Date(new SimpleDateFormat("dd/mm/yyyy").parse(input.nextLine()).getTime());
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    private DataSource dataSourceConfiguration() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(DataBaseSettings.getInstance().username());
        dataSource.setPassword(DataBaseSettings.getInstance().password());
        dataSource.setUrl(DataBaseSettings.getInstance().url());
        dataSource.setDriverClassName(DataBaseSettings.getInstance().driverClassName());
        return dataSource;
    }

}
