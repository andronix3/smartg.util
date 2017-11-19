package com.smartg.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SQL_Utils {
	@SuppressWarnings("rawtypes")
	public static List<Map<String, Object>> queryAsList(Connection connection, String sql, List<SQL_Param> params) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			SQL_Setter setter = new SQL_Setter();
			Iterator<SQL_Param> iterator = params.iterator();
			while (iterator.hasNext()) {
				setter.set(ps, iterator.next());
			}

			try (ResultSet rs = ps.executeQuery()) {
				if (rs != null) {
					ResultSetMetaData metaData = rs.getMetaData();
					int columnCount = metaData.getColumnCount();
					Map<String, Object> map = new LinkedHashMap<>();
					list.add(map);

					while (rs.next()) {
						for (int i = 0; i < columnCount; i++) {
							int columnIndex = i + 1;
							String columnName = metaData.getColumnName(columnIndex);
							Object object = rs.getObject(columnIndex);
							map.put(columnName, object);
						}
					}
				}
			}
		}
		return list;
	}
	
	public static <T> T create(ResultSet rs, Class<T> cls)
			throws InstantiationException, IllegalAccessException, SQLException {
		T instance = cls.newInstance();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		SQL_Getter getter = new SQL_Getter();
		if (rs.next()) {
			for (int i = 1; i <= columnCount; i++) {
				int columnType = metaData.getColumnType(i);
				getter.setFromRS(instance, rs, metaData.getColumnLabel(i), columnType);
			}
		}
		return instance;
	}
	
}
