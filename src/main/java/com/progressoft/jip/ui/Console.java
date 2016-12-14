package com.progressoft.jip.ui;

import com.progressoft.jip.context.AppContext;
import com.progressoft.jip.report.ReportProvider;
import com.progressoft.jip.report.impl.CSVReportWriter;
import com.progressoft.jip.report.impl.XMLReportWriter;
import com.progressoft.jip.usecase.CreateNewPaymentRequestUseCase;
import com.progressoft.jip.usecase.LoadAccountsUseCase;
import com.progressoft.jip.view.AccountView;

/**
 * Created by ahmad on 12/14/16.
 */
public class Console {

    private AppContext context;

    public Console(AppContext context) {
        this.context = context;
    }


    public void displayMainMenu() {
        Menu mainMenu = Menu.menu("Payment System");
        mainMenu.addMenuItem(ItemContentFactory.instance("Login", () -> {
            displayLoginMenu(context, mainMenu);
        }));
        mainMenu.display(System.out);
    }

    private void displayLoginMenu(AppContext context, Menu mainMenu) {
        Menu loginMenu = Menu.subMenu("Choose User Type", mainMenu);
        loginMenu.addMenuItem(ItemContentFactory.instance("Login as user", () -> {
            displayUserTypeSelectMenu(context, loginMenu);
        }));
        loginMenu.addMenuItem(ItemContentFactory.instance("Login as admin", () -> {
            System.out.println("Welcome Admin, Bye!");
        }));
        loginMenu.display(System.out);
    }

    private void displayUserTypeSelectMenu(AppContext context, Menu loginMenu) {
        Menu userMenu = Menu.subMenu("User Menu", loginMenu);
        userMenu.addMenuItem(ItemContentFactory.instance("Display Accounts", () -> {
            Menu accountOptionsMenu = Menu.subMenu("Accounts", userMenu);
            displayAccounts(context, accountOptionsMenu);
            accountOptionsMenu.display(System.out);
        }));
        userMenu.display(System.out);
    }

    private void displayAccounts(AppContext context, Menu accountOptionsMenu) {
        for (AccountView accountView : new LoadAccountsUseCase().loadAccounts()) {
            accountOptionsMenu.addMenuItem(ItemContentFactory.instance(accountView.iban(), () -> {

                Menu currentAccountMenu = Menu.subMenu("Account \"" + accountView.iban() + "\" Options", accountOptionsMenu);
                currentAccountMenu.addMenuItem(ItemContentFactory.instance("Create Payment Request", () -> {
                    CreateNewPaymentRequestUseCase useCase = new CreateNewPaymentRequestUseCase();
                    useCase.createPaymentRequestFrom(accountView.iban());
                }));
                currentAccountMenu.addMenuItem(ItemContentFactory.instance("Show Payments", () -> {
                    ReportProvider reportProvider = new ReportProvider(context.paymentRequestRepositroy());
                    Menu xmlCsvMenu = Menu.subMenu("Choose Report format please:", currentAccountMenu);
                    xmlCsvMenu.addMenuItem(ItemContentFactory.instance("XML", () -> {
                        reportProvider.print(accountView.iban(), new XMLReportWriter(), System.out);
                    }));
                    xmlCsvMenu.addMenuItem(ItemContentFactory.instance("CSV", () -> {
                        reportProvider.print(accountView.iban(), new CSVReportWriter(), System.out);
                    }));
                    xmlCsvMenu.display(System.out);
                }));

                currentAccountMenu.display(System.out);
            }));
        }
    }

}
