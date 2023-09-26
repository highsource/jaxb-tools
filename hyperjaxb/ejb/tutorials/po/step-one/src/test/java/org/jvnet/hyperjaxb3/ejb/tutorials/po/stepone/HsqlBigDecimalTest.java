package org.jvnet.hyperjaxb3.ejb.tutorials.po.stepone;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Test;

public class HsqlBigDecimalTest {

	@Test
	public void test() throws Exception {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		String url = "jdbc:hsqldb:mem:data/test";
		Connection conn = DriverManager.getConnection(url, "sa", "");
		Statement stmt = conn.createStatement();

		stmt.executeUpdate("create table test (value NUMERIC(5,2));");

		String sql = "INSERT INTO test (value) VALUES(?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setBigDecimal(1, BigDecimal.ONE);

		// insert the data
		pstmt.executeUpdate();

		ResultSet rs = stmt.executeQuery("SELECT * FROM test");
		while (rs.next()) {
			BigDecimal result = rs.getBigDecimal(1);
			Assert.assertTrue(BigDecimal.ONE.compareTo(result) == 0);
			Assert.assertEquals(BigDecimal.ONE, result.setScale(0));
		}

		rs.close();
		stmt.close();
		conn.close();
	}

}
