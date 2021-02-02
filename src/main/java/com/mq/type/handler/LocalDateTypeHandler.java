package com.mq.type.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.time.LocalDate;

public class LocalDateTypeHandler extends BaseTypeHandler<LocalDate> {
	public LocalDateTypeHandler() {
	}

	public void setNonNullParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType) throws
			SQLException {
		ps.setDate(i, Date.valueOf(parameter));
	}

	public LocalDate getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Date date = rs.getDate(columnName);
		return getLocalDate(date);
	}

	public LocalDate getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Date date = rs.getDate(columnIndex);
		return getLocalDate(date);
	}

	public LocalDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Date date = cs.getDate(columnIndex);
		return getLocalDate(date);
	}

	private static LocalDate getLocalDate(Date date) {
		return date != null ? date.toLocalDate() : null;
	}
}
