package com.progressoft.jip.utilities;

import java.text.MessageFormat;

public class Constants {
    public static final int PAYMENT_PURPOSE_CODE_COLUMN_MAX_LENGTH = 10;
    public static final int PAYMENT_PURPOSE_NAME_COLUMN_MAX_LENGTH = 45;
    public static final int PAYMENT_PURPOSE_CODE_COLUMN_INDEX = 1;
    public static final int PAYMENT_PURPOSE_NAME_COLUMN_INDEX = 2;
    public static final String DB_NAME = "PAYMENT_SYSTEM";
    public static final String PAYMENT_PURPOSE_TABLE = "PAYMENT_PURPOSE";
    public static final String PAYMENT_REQUEST_TABLE_NAME = "PAYMENT_REQUEST";
    public static final String TRUNCATE_PAYMENT_REQUEST_TABLE = "TRUNCATE `" + DB_NAME + "`.`"
	    + PAYMENT_REQUEST_TABLE_NAME + "`;";
    public static final String LOAD_PAYMENT_PURPOSE_BY_CODE_SQL_STATEMENT = "select CODE as code,NAME as name from "
	    + DB_NAME + "." + PAYMENT_PURPOSE_TABLE + " WHERE CODE = ?";
    public static final String INSERT_PAYMENT_PURPOSE_SQL_STATEMENT = "insert into " + DB_NAME + "."
	    + PAYMENT_PURPOSE_TABLE + " (CODE,NAME) VALUES(?,?)";
    public static final String LOAD_PAYMENT_PURPOSES_SQL_STATEMENT = "select * from " + DB_NAME + "."
	    + PAYMENT_PURPOSE_TABLE + "";
    public static final String DELETE_PAYMENT_PURPOSE_SQL_STATEMENT = "delete from " + DB_NAME + "."
	    + PAYMENT_PURPOSE_TABLE + " where CODE = ?";
    public static final String DELETE_PAYMENT_REQUEST_SQL_STATMENT = "delete from `" + DB_NAME + "`.`"
	    + PAYMENT_REQUEST_TABLE_NAME + "` where id = ?";
    public static final String UPDATE_PAYMENT_PURPOSE_SQL_STATEMENT = "UPDATE " + DB_NAME + "." + PAYMENT_PURPOSE_TABLE
	    + " SET NAME = ? WHERE CODE = ?";
    public static final String LOAD_PAYMENT_REQUEST_BY_ID_SQL_STATEMENT = "select ID as id,ORD_IBAN as orderingAccountIBAN,BENEF_IBAN as beneficiaryAccountIBAN,BENEF_NAME as beneficiaryName,AMOUNT as paymentAmount,CURRENCY_CODE as currencyCode,PURPOSE_CODE as purposeCode,PAYMENT_DATE as paymentDate from " + Constants.DB_NAME + "."
	    + Constants.PAYMENT_REQUEST_TABLE_NAME + " where id = ?";
    public static final String CRNCY_CODE_COLOMN = "CODE";
    public static final String CRNCY_RATE_COLOMN = "RATIO";
    public static final String CRNCY_DESC_COLOMN = "NAME";
    public static final String CRNCY_TABLE_NAME = Constants.DB_NAME + "." + "CURRENCY";
    public static final String SELECT_CRNCY_BY_CODE = MessageFormat.format(
	    "select CODE as currencyCode, NAME as currencyDescription, RATIO as currencyRate from {0} where {1} = ?",
	    CRNCY_TABLE_NAME, CRNCY_CODE_COLOMN);
    public static final String SELECT_ALL_CRNCYS = MessageFormat
	    .format("select CODE as currencyCode,NAME as currencyDescription,RATIO as currencyRate  from {0}",
		    CRNCY_TABLE_NAME);
    public static final String GET_ACCOUNT_BY_IBAN = "select IBAN as iban,TYPE as accountType,BALANCE as balance,STATUS as status,CURRENCY_CODE as currencyCode from ACCOUNT where iban = ?";
    public static final String GET_ACCOUNTS = "select IBAN as iban,TYPE as accountType,BALANCE as balance,STATUS as status,CURRENCY_CODE as currencyCode from ACCOUNT";

    public static final String SELECT_ALL_PAYMENT_REQUESTS = "select ID as id,ORD_IBAN as orderingAccountIBAN,BENEF_IBAN as beneficiaryAccountIBAN,BENEF_NAME as beneficiaryName,AMOUNT as paymentAmount,CURRENCY_CODE as currencyCode,PURPOSE_CODE as purposeCode,PAYMENT_DATE as paymentDate from PAYMENT_SYSTEM.PAYMENT_REQUEST";

    public static final String SELECT_PAYMENT_REQUESTS_BY_ORDERING_ACCOUNT_IBAN = "select ID as id,ORD_IBAN as orderingAccountIBAN,BENEF_IBAN as beneficiaryAccountIBAN,BENEF_NAME as beneficiaryName,AMOUNT as paymentAmount,CURRENCY_CODE as currencyCode,PURPOSE_CODE as purposeCode,PAYMENT_DATE as paymentDate from PAYMENT_SYSTEM.PAYMENT_REQUEST where ORD_IBAN = ?";

    public static final String INSERT_PAYMENT_REQUEST_SQL = "insert into PAYMENT_SYSTEM.PAYMENT_REQUEST VALUES (null,?,?,?,?,?,?,?)";

}
