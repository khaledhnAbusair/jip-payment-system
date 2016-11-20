import org.apache.commons.dbcp2.BasicDataSource;

import com.progressoft.jip.datastructures.PaymentPurposeDataStructure;
import com.progressoft.jip.gateway.PaymentPurposeGateway;
import com.progressoft.jip.gateway.impl.MySQLPaymentPurposeGateway;

public class MainTest {
    public static void main(String[] args) {
	
	BasicDataSource dataSource = new BasicDataSource();
	dataSource.setUrl("jdbc:mysql://localhost:3306/PAYMENT_SYSTEM");
	dataSource.setUsername("root");
	dataSource.setPassword("root");
	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	
	PaymentPurposeGateway gateway = new MySQLPaymentPurposeGateway(dataSource);
	gateway.insertPaymentPurpose(new PaymentPurposeDataStructure("GHAD10", "Ghadeer"));
	
    }
}
