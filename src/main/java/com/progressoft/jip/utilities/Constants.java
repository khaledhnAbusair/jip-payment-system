package com.progressoft.jip.utilities;

public class Constants {
    public static final int PAYMENT_PURPOSE_CODE_COLUMN_MAX_LENGTH = 10;
    public static final int PAYMENT_PURPOSE_NAME_COLUMN_MAX_LENGTH = 45;
    public static final int PAYMENT_PURPOSE_CODE_COLUMN_INDEX = 1;
    public static final int PAYMENT_PURPOSE_NAME_COLUMN_INDEX = 2;
    public static final String DB_NAME = "PAYMENT_SYSTEM";
    public static final String PAYMENT_PURPOSE_TABLE = "PAYMENT_PURPOSE";
    public static final String LOAD_PAYMENT_PURPOSE_BY_CODE_SQL_STATEMENT = "select * from "+DB_NAME+"."+PAYMENT_PURPOSE_TABLE+" WHERE CODE = ?";
    public static final String INSERT_PAYMENT_PURPOSE_SQL_STATEMENT = "insert into "+DB_NAME+"."+PAYMENT_PURPOSE_TABLE+" (CODE,NAME) VALUES(?,?)";
    public static final String LOAD_PAYMENT_PURPOSES_SQL_STATEMENT = "select * from "+DB_NAME+"."+PAYMENT_PURPOSE_TABLE+"";
    public static final String DELETE_PAYMENT_PURPOSE_SQL_STATEMENT = "delete from "+DB_NAME+"."+PAYMENT_PURPOSE_TABLE+" where CODE = ?";
    public static final String UPDATE_PAYMENT_PURPOSE_SQL_STATEMENT = "UPDATE "+DB_NAME+"."+PAYMENT_PURPOSE_TABLE+" SET NAME = ? WHERE CODE = ?";
}