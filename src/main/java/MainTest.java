import com.progressoft.jip.report.ReportProvider;
import com.progressoft.jip.report.impl.XMLReportWriter;

public class MainTest {

    public static void main(String[] args) {
	ReportProvider.print("", new XMLReportWriter(), System.out);//
    }

}
