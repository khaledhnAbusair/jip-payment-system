import java.util.Scanner;

import org.apache.commons.dbcp.BasicDataSource;

import com.progressoft.jip.datastructures.builder.impl.AccountView;
import com.progressoft.jip.model.MenuModel;
import com.progressoft.jip.model.MenuItemModel;
import com.progressoft.jip.model.StringOptionModel;
import com.progressoft.jip.model.actions.MenuItem;
import com.progressoft.jip.usecase.LoadAccountsUseCase;
import com.progressoft.jip.utilities.DataBaseSettings;

public class MainTest {

    private static BasicDataSource dataSource = new BasicDataSource();

    public static void main(String[] args) {

	DataBaseSettings dbSettings = DataBaseSettings.getInstance();

	dataSource.setUsername(dbSettings.username());
	dataSource.setPassword(dbSettings.password());
	dataSource.setUrl(dbSettings.url());
	dataSource.setDriverClassName(dbSettings.driverClassName());

	MenuModel mainMenu = new MenuModel("Payment System");
	mainMenu.addMenuItem(new MenuItemModel(1, new StringOptionModel("Login", () -> {
	    MenuModel loginMenu = new MenuModel("Login as");
	    loginMenu.addMenuItem(new MenuItemModel(1, new StringOptionModel("Login as Administrator", () -> {
		System.out.println("Weclome Admin");
	    })));
	    loginMenu.addMenuItem(new MenuItemModel(2, new StringOptionModel("Login as User", () -> {
		System.out.println("Welcome User");
		MenuModel userMenu = new MenuModel("Select an Account");

		int i = 0;
		LoadAccountsUseCase loadAccountsUseCase = new LoadAccountsUseCase();
		for (AccountView account : loadAccountsUseCase.loadAccounts()) {
		    userMenu.addMenuItem(new MenuItemModel(++i, new StringOptionModel(account.iban(), () -> {
			MenuModel accountOptions = new MenuModel("Account Options");
			accountOptions.addMenuItem(new MenuItemModel(1, new StringOptionModel("Create Payment Request",
				() -> {
				})));
			accountOptions.addMenuItem(new MenuItemModel(2, new StringOptionModel("Exit", () -> {
			})));
			accountOptions.print(System.out);
			accountOptions.execute(Integer.parseInt(new Scanner(System.in).next()));
		    })));
		}
		userMenu.addMenuItem(new MenuItemModel(++i, new StringOptionModel("Exit", () -> {
		    System.exit(0);
		})));
		userMenu.print(System.out);
		userMenu.execute(Integer.parseInt(new Scanner(System.in).next()));
	    })));
	    loginMenu.addMenuItem(new MenuItemModel(3, new StringOptionModel("Exit", () -> {
		System.exit(0);
	    })));
	    loginMenu.print(System.out);
	    loginMenu.execute(Integer.parseInt(new Scanner(System.in).next()));
	})));
	mainMenu.addMenuItem(new MenuItemModel(2, new StringOptionModel("Exit", () -> {
	    System.exit(0);
	})));
	mainMenu.print(System.out);
	mainMenu.execute(Integer.parseInt(new Scanner(System.in).next()));

    }

    private static BasicDataSource initDatasource() {
	BasicDataSource dataSource = new BasicDataSource();
	DataBaseSettings dbSettings = DataBaseSettings.getInstance();
	dataSource.setUsername(dbSettings.username());
	dataSource.setPassword(dbSettings.password());
	dataSource.setUrl(dbSettings.url());
	dataSource.setDriverClassName(dbSettings.driverClassName());
	return dataSource;
    }

}
