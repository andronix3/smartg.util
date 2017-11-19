package com.smartg.jdbc;

import java.util.Calendar;

public class SQL_Param<T> {
	private SQL_ParamType type;
	private T value;
	private int parameterIndex;
	private Object length;
	private Object targetSqlType;
	private Calendar calendar;
	private String columnName;

	public SQL_Param() {

	}

	public SQL_Param(SQL_ParamType type, T value, int parameterIndex) {
		this.type = type;
		this.value = value;
		this.parameterIndex = parameterIndex;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public SQL_ParamType getType() {
		return type;
	}

	public SQL_Param<T> setType(SQL_ParamType type) {
		this.type = type;
		return this;
	}

	public T getValue() {
		return value;
	}

	public SQL_Param<T> setValue(T value) {
		this.value = value;
		return this;
	}

	public int getParameterIndex() {
		return parameterIndex;
	}

	public SQL_Param<T> setParameterIndex(int parameterIndex) {
		this.parameterIndex = parameterIndex;
		return this;
	}

	public Object getLength() {
		return length;
	}

	public SQL_Param<T> setLength(Object length) {
		this.length = length;
		return this;
	}

	public Object getTargetSqlType() {
		return targetSqlType;
	}

	public SQL_Param<T> setTargetSqlType(Object targetSqlType) {
		this.targetSqlType = targetSqlType;
		return this;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public SQL_Param<T> setCalendar(Calendar calendar) {
		this.calendar = calendar;
		return this;
	}
}
