import com.progressoft.jip.context.AppContext;
import com.progressoft.jip.ui.Console;
import com.progressoft.jip.utilities.DataBaseSettings;
import org.apache.commons.dbcp.BasicDataSource;

public class MainTest {


    public static void main(String[] args) {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(DataBaseSettings.getInstance().username());
        dataSource.setPassword(DataBaseSettings.getInstance().password());
        dataSource.setUrl(DataBaseSettings.getInstance().url());
        dataSource.setDriverClassName(DataBaseSettings.getInstance().driverClassName());
        AppContext context = new AppContext(dataSource);

        Console console = new Console(context);
        console.displayMainMenu();

    }

}
