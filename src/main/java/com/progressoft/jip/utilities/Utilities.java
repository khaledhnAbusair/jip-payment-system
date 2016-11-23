package com.progressoft.jip.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class Utilities {
    public static PreparedStatement preparedStatement(Connection connection, String sql, Object... vals) {
	try {
	    PreparedStatement prepareStatement = connection.prepareStatement(sql);
	    for (int i = 0; i < vals.length; ++i)
		prepareStatement.setObject(i + 1, vals[i]);
	    return prepareStatement;
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }
}
