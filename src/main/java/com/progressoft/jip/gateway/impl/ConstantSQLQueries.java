package com.progressoft.jip.gateway.impl;

import static com.progressoft.jip.gateway.impl.SQLCurrencyGateway.CRNCY_CODE_COLOMN;
import static com.progressoft.jip.gateway.impl.SQLCurrencyGateway.CRNCY_DESC_COLOMN;
import static com.progressoft.jip.gateway.impl.SQLCurrencyGateway.CRNCY_RATE_COLOMN;
import static com.progressoft.jip.gateway.impl.SQLCurrencyGateway.DB_NAME;
import java.text.MessageFormat;
public class ConstantSQLQueries {

	static final String CRNCY_TABLE_NAME = DB_NAME + "." + "currencies";

	static final String UPDATE_CRNCY_RATE_BY_CODE = MessageFormat.format(
			"UPDATE {0} SET {1}=? where {2}=? AND {3} <> ?", CRNCY_TABLE_NAME, CRNCY_RATE_COLOMN, CRNCY_CODE_COLOMN,
			CRNCY_RATE_COLOMN);
	static final String SELECT_CRNCY_BY_CODE = MessageFormat.format("select * from {0} where {1} = ?", CRNCY_TABLE_NAME,
			CRNCY_CODE_COLOMN);
	static final String SELECT_ALL_CRNCYS = MessageFormat.format("select * from {0}", CRNCY_TABLE_NAME);
	static final String INSERT_CRNCY = MessageFormat.format("insert into {0} ({1},{2},{3}) values(?,?,?)",
			CRNCY_TABLE_NAME, CRNCY_CODE_COLOMN, CRNCY_RATE_COLOMN, CRNCY_DESC_COLOMN);
	static final String DELETE_CRNCY = MessageFormat.format("DELETE FROM {0} WHERE {1}= ? AND {2}=?", CRNCY_TABLE_NAME,
			CRNCY_CODE_COLOMN, CRNCY_DESC_COLOMN);
}
