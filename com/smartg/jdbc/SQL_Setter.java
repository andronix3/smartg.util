package com.smartg.jdbc;


import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class SQL_Setter {
	
	public String createInsertStatement(List<SQL_Param<?>> list) {
		StringBuilder sql = new StringBuilder("INSERT INTO (");
		for(SQL_Param<?> p: list) {
			sql.append(p.getColumnName());
			sql.append(' ');
		}
		sql.append(") VALUES (");
		for(int i = 0; i < list.size() - 1; i++) {
			sql.append("?, ");
		}
		sql.append("?)");
		return sql.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void set(PreparedStatement ps, SQL_Param param) throws SQLException {
		switch (param.getType()) {
		case ARRAY:
			ps.setArray(param.getParameterIndex(), (Array) param.getValue());
			break;
		case ASCII_STREAM:
			setAsciiStream(ps, param);
			break;
		case BIG_DECIMAL:
			ps.setBigDecimal(param.getParameterIndex(), (BigDecimal) param.getValue());
			break;
		case BINARY_STREAM:
			setBinaryStream(ps, param);
			break;
		case BLOB:
			ps.setBlob(param.getParameterIndex(), (Blob) param.getValue());
			break;
		case BLOB_STREAM:
			setBlobStream(ps, param);
			break;
		case BOOLEAN:
			ps.setBoolean(param.getParameterIndex(), (boolean) param.getValue());
			break;
		case BYTE:
			ps.setByte(param.getParameterIndex(), (byte) param.getValue());
			break;
		case BYTES:
			ps.setBytes(param.getParameterIndex(), (byte[]) param.getValue());
			break;
		case CHAR_STREAM:
			setCharacterStream(ps, param);
			break;
		case CLOB:
			ps.setClob(param.getParameterIndex(), (Clob) param.getValue());
			break;
		case CLOB_STREAM:
			setClobStream(ps, param);
			break;
		case DATE:
			ps.setDate(param.getParameterIndex(), (Date) param.getValue());
			break;
		case DOUBLE:
			ps.setDouble(param.getParameterIndex(), (double) param.getValue());
			break;
		case FLOAT:
			ps.setFloat(param.getParameterIndex(), (float) param.getValue());
			break;
		case INT:
			ps.setInt(param.getParameterIndex(), (int) param.getValue());
			break;
		case LONG:
			ps.setLong(param.getParameterIndex(), (long) param.getValue());
			break;
		case N_CHAR_STREAM:
			setNCharacterStream(ps, param);
			break;
		case N_CLOB:
			ps.setNClob(param.getParameterIndex(), (NClob) param.getValue());
			break;
		case N_CLOB_STREAM:
			setNClobStream(ps, param);
			break;
		case N_STRING:
			ps.setNString(param.getParameterIndex(), (String) param.getValue());
			break;
		case OBJECT:
			setObject(ps, param);
			break;
		case REF:
			ps.setRef(param.getParameterIndex(), (Ref) param.getValue());
			break;
		case ROWID:
			ps.setRowId(param.getParameterIndex(), (RowId) param.getValue());
			break;
		case SHORT:
			ps.setShort(param.getParameterIndex(), (short) param.getValue());
			break;
		case SQL_XML:
			ps.setSQLXML(param.getParameterIndex(), (SQLXML) param.getValue());
			break;
		case STRING:
			ps.setString(param.getParameterIndex(), (String) param.getValue());
			break;
		case TIME:
			if (param.getCalendar() == null) {
				ps.setTime(param.getParameterIndex(), (Time) param.getValue());
			} else {
				ps.setTime(param.getParameterIndex(), (Time) param.getValue(), param.getCalendar());
			}
			break;
		case TIMESTAMP:
			if (param.getCalendar() == null) {
				ps.setTimestamp(param.getParameterIndex(), (Timestamp) param.getValue());
			} else {
				ps.setTimestamp(param.getParameterIndex(), (Timestamp) param.getValue(), param.getCalendar());
			}
			break;
		case UNICODE_STREAM:
			ps.setUnicodeStream(param.getParameterIndex(), (InputStream) param.getValue(), (int) param.getLength());
			break;
		case URL:
			ps.setURL(param.getParameterIndex(), (URL) param.getValue());
			break;
		default:
			break;

		}
	}

	private void setCharacterStream(PreparedStatement ps, SQL_Param<Reader> param) throws SQLException {
		Object length = param.getLength();
		if (length == null) {
			ps.setCharacterStream(param.getParameterIndex(), param.getValue());
		} else if (length instanceof Integer) {
			ps.setCharacterStream(param.getParameterIndex(), param.getValue(), (int) length);
		} else if (length instanceof Long) {
			ps.setCharacterStream(param.getParameterIndex(), param.getValue(), (long) length);
		} else {
			throw new IllegalArgumentException("Unexpected Parameter Type: " + length);
		}
	}

	private void setClobStream(PreparedStatement ps, SQL_Param<Reader> param) throws SQLException {
		Object length = param.getLength();
		if (length == null) {
			ps.setClob(param.getParameterIndex(), param.getValue());
		} else if (length instanceof Long) {
			ps.setClob(param.getParameterIndex(), param.getValue(), (long) length);
		} else {
			throw new IllegalArgumentException("Unexpected Parameter Type: " + length);
		}
	}

	private void setNClobStream(PreparedStatement ps, SQL_Param<Reader> param) throws SQLException {
		Object length = param.getLength();
		if (length == null) {
			ps.setNClob(param.getParameterIndex(), param.getValue());
		} else if (length instanceof Long) {
			ps.setNClob(param.getParameterIndex(), param.getValue(), (long) length);
		} else {
			throw new IllegalArgumentException("Unexpected Parameter Type: " + length);
		}
	}

	private void setBlobStream(PreparedStatement ps, SQL_Param<InputStream> param) throws SQLException {
		Object length = param.getLength();
		if (length == null) {
			ps.setBlob(param.getParameterIndex(), param.getValue());
		} else if (length instanceof Long) {
			ps.setBlob(param.getParameterIndex(), param.getValue(), (long) length);
		} else {
			throw new IllegalArgumentException("Unexpected Parameter Type: " + length);
		}
	}

	private void setBinaryStream(PreparedStatement ps, SQL_Param<InputStream> param) throws SQLException {
		Object length = param.getLength();
		if (length == null) {
			ps.setBinaryStream(param.getParameterIndex(), param.getValue());
		} else if (length instanceof Integer) {
			ps.setBinaryStream(param.getParameterIndex(), param.getValue(), (int) length);
		} else if (length instanceof Long) {
			ps.setBinaryStream(param.getParameterIndex(), param.getValue(), (long) length);
		} else {
			throw new IllegalArgumentException("Unexpected Parameter Type: " + length);
		}
	}

	private void setAsciiStream(PreparedStatement ps, SQL_Param<InputStream> param) throws SQLException {
		Object length = param.getLength();
		if (length == null) {
			ps.setAsciiStream(param.getParameterIndex(), param.getValue());
		} else if (length instanceof Integer) {
			ps.setAsciiStream(param.getParameterIndex(), param.getValue(), (int) length);
		} else if (length instanceof Long) {
			ps.setAsciiStream(param.getParameterIndex(), param.getValue(), (long) length);
		} else {
			throw new IllegalArgumentException("Unexpected Parameter Type: " + length);
		}
	}

	private void setNCharacterStream(PreparedStatement ps, SQL_Param<Reader> param) throws SQLException {
		Object length = param.getLength();
		if (length == null) {
			ps.setNCharacterStream(param.getParameterIndex(), param.getValue());
		} else if (length instanceof Long) {
			ps.setNCharacterStream(param.getParameterIndex(), param.getValue(), (long) length);
		} else {
			throw new IllegalArgumentException("Unexpected Parameter Type: " + length);
		}
	}

	private void setObject(PreparedStatement ps, SQL_Param<Object> param) throws SQLException {
		Object length = param.getLength();
		Object targetSqlType = param.getTargetSqlType();
		int parameterIndex = param.getParameterIndex();
		Object value = param.getValue();

		if (length == null && targetSqlType == null) {
			ps.setObject(parameterIndex, value);
		} else if (length == null && targetSqlType != null) {
			if (targetSqlType instanceof Integer) {
				ps.setObject(parameterIndex, value, (int) targetSqlType);
			} else if (targetSqlType instanceof SQLType) {
				ps.setObject(parameterIndex, value, (SQLType) targetSqlType);
			} else {
				throw new IllegalArgumentException("Unexpected Parameter Type: " + targetSqlType);
			}
		} else if (length != null && targetSqlType != null) {
			if (targetSqlType instanceof Integer) {
				ps.setObject(parameterIndex, value, (int) targetSqlType, (int) length);
			} else if (targetSqlType instanceof SQLType) {
				ps.setObject(parameterIndex, value, (SQLType) targetSqlType, (int) length);
			} else {
				throw new IllegalArgumentException("Unexpected Parameter Type: " + targetSqlType);
			}

		}
	}

}
