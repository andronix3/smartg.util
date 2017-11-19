package com.smartg.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQL_Getter {
	public void setFromRS(Object target, ResultSet rs, String columnLabel, int type) throws SQLException {
		switch (type) {
		case Types.ARRAY:
			setArray(target, rs, columnLabel);
			break;
		case Types.NUMERIC:
		case Types.DECIMAL:
			setBigDecimal(target, rs, columnLabel);
			break;
		case Types.BLOB:
			setBlob(target, rs, columnLabel);
			break;
		case Types.BIT:
		case Types.BOOLEAN:
			setBoolean(target, rs, columnLabel);
			break;
		case Types.CLOB:
			setClob(target, rs, columnLabel);
			break;
		case Types.DATE:
			setDate(target, rs, columnLabel);
			break;
		case Types.TIME:
			setTime(target, rs, columnLabel);
			break;
		case Types.TIMESTAMP:
			setTimestamp(target, rs, columnLabel);
			break;
		case Types.DOUBLE:
			setDouble(target, rs, columnLabel);
			break;
		case Types.CHAR:
		case Types.VARCHAR:
		case Types.LONGNVARCHAR:
			setString(target, rs, columnLabel);
			break;
		case Types.BINARY:
		case Types.VARBINARY:
		case Types.LONGVARBINARY:
			setBytes(target, rs, columnLabel);
			break;
		case Types.TINYINT:
			setByte(target, rs, columnLabel);
			break;
		case Types.SMALLINT:
			setShort(target, rs, columnLabel);
			break;
		case Types.INTEGER:
			setInteger(target, rs, columnLabel);
			break;
		case Types.BIGINT:
			setLong(target, rs, columnLabel);
			break;
		case Types.FLOAT:
		case Types.REAL:
			setFloat(target, rs, columnLabel);
			break;			
		}
	}
	
	private void setLong(Object target, ResultSet rs, String columnLabel) throws SQLException {
		Long b = rs.getLong(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, b.getClass());
		if (m != null) {
			try {
				m.invoke(target, b);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
	
	private void setByte(Object target, ResultSet rs, String columnLabel) throws SQLException {
		Byte b = rs.getByte(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, b.getClass());
		if (m != null) {
			try {
				m.invoke(target, b);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}


	
	private void setShort(Object target, ResultSet rs, String columnLabel) throws SQLException {
		Short s = rs.getShort(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, s.getClass());
		if (m != null) {
			try {
				m.invoke(target, s);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	
	private void setBytes(Object target, ResultSet rs, String columnLabel) throws SQLException {
		byte[] bytes = rs.getBytes(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, bytes.getClass());
		if (m != null) {
			try {
				m.invoke(target, bytes);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}


	private void setString(Object target, ResultSet rs, String columnLabel) throws SQLException {
		String s = rs.getString(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, s.getClass());
		if (m != null) {
			try {
				m.invoke(target, s);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private void setInteger(Object target, ResultSet rs, String columnLabel) throws SQLException {
		Integer date = rs.getInt(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, date.getClass());
		if (m != null) {
			try {
				m.invoke(target, date);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private void setFloat(Object target, ResultSet rs, String columnLabel) throws SQLException {
		Float date = rs.getFloat(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, date.getClass());
		if (m != null) {
			try {
				m.invoke(target, date);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private void setDouble(Object target, ResultSet rs, String columnLabel) throws SQLException {
		Double date = rs.getDouble(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, date.getClass());
		if (m != null) {
			try {
				m.invoke(target, date);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private void setTimestamp(Object target, ResultSet rs, String columnLabel) throws SQLException {
		Timestamp timestamp = rs.getTimestamp(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, timestamp.getClass());
		if (m != null) {
			try {
				m.invoke(target, timestamp);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
	
	private void setTime(Object target, ResultSet rs, String columnLabel) throws SQLException {
		Time time = rs.getTime(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, time.getClass());
		if (m != null) {
			try {
				m.invoke(target, time);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
	
	private void setDate(Object target, ResultSet rs, String columnLabel) throws SQLException {
		Date date = rs.getDate(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, date.getClass());
		if (m != null) {
			try {
				m.invoke(target, date);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private void setClob(Object target, ResultSet rs, String columnLabel) throws SQLException {
		Clob clob = rs.getClob(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, clob.getClass());
		if (m != null) {
			try {
				m.invoke(target, clob);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private void setBoolean(Object target, ResultSet rs, String columnLabel) throws SQLException {
		Boolean b = rs.getBoolean(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, b.getClass());
		if (m != null) {
			try {
				m.invoke(target, b);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private void setBlob(Object target, ResultSet rs, String columnLabel) throws SQLException {
		Blob blob = rs.getBlob(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, blob.getClass());
		if (m != null) {
			try {
				m.invoke(target, blob);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private void setBigDecimal(Object target, ResultSet rs, String columnLabel) throws SQLException {
		BigDecimal decimal = rs.getBigDecimal(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, decimal.getClass());
		if (m != null) {
			try {
				m.invoke(target, decimal);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private void setArray(Object target, ResultSet rs, String columnLabel) throws SQLException {
		Array array = rs.getArray(columnLabel);
		Method[] methods = target.getClass().getMethods();
		Method m = getMethod(methods, "set" + columnLabel, array.getClass());
		if (m != null) {
			try {
				m.invoke(target, array);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private Method getMethod(Method[] methods, String name, Class<?> argType) {
		for (Method m : methods) {
			if (m.getName().equalsIgnoreCase(name)) {
				Class<?>[] parameterTypes = m.getParameterTypes();
				if (parameterTypes.length == 1 && parameterTypes[0].isAssignableFrom(argType)) {
					return m;
				}
			}
		}
		return null;

	}
}
