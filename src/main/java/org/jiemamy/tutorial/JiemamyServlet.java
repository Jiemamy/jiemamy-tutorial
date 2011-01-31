package org.jiemamy.tutorial;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Jiemamyのチュートリアル用サーブレット。
 * 
 * @author daisuke
 */
@SuppressWarnings("serial")
public class JiemamyServlet extends HttpServlet {
	
	/** 改行文字列 */
	private static final String NL = System.getProperty("line.separator");

	/** DBユーザ名 */
	private static final String USERNAME = "postgres";
	
	/** DBパスワード */
	private static final String PASSWORD = "postgres";
	
	/** JDBC接続URI */
	private static final String URI = "jdbc:postgresql://localhost:5432/jiemamy";

	/** 表示テーブル名 */
	private static final String TABLE = "T_ITEM";

	/** IDカラム名 */
	private static final String COLUMN_ID = "ID";

	/** 商品名カラム名 */
	private static final String COLUMN_ITEM_NAME = "ITEM_NAME";

	/** 価格カラム名 */
	private static final String COLUMN_PRICE = "PRICE";

	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		java.io.PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Hello, Jiemamy</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Hello, Jiemamy!</h1>");
		
		out.println(databaseToTableHTML());
		
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
	
	private String databaseToTableHTML() {
		StringBuffer sb = new StringBuffer();
		
		Connection db = null; // DB接続オブジェクト
		Statement st = null; // SQL文オブジェクト
		ResultSet rs = null; // 問合せ結果オブジェクト
		
		sb.append("<table border=\"1\">").append(NL);
		sb.append("<caption>商品一覧</caption>").append(NL);
		sb.append("<tr><th>ID</th><th>商品名</th><th>価格</th></tr>").append(NL);
		try {
			Class.forName("org.postgresql.Driver");
			
			db = DriverManager.getConnection(URI, USERNAME, PASSWORD);
			st = db.createStatement();
			
			rs = st.executeQuery("SELECT * FROM " + TABLE);
			if (rs != null) {
				while (rs.next()) {
					int id = rs.getInt(COLUMN_ID);
					String item = rs.getString(COLUMN_ITEM_NAME);
					int price = rs.getInt(COLUMN_PRICE);
					
					sb.append("<tr><th>");
					sb.append(id);
					sb.append("</th><td>");
					sb.append(item);
					sb.append("</td><td style='text-align:right'>");
					sb.append(price);
					sb.append("</td></tr>").append(NL);
				}
			}
		} catch (Exception e) {
			sb.append("<tr><td colspan=\"2\">");
			sb.append(e.getMessage());
			sb.append("</td></tr>").append(NL);
		} finally {
			sb.append("</table>").append(NL);
			closeQuietly(db);
			closeQuietly(st);
			closeQuietly(rs);
		}
		
		return sb.toString();
	}
	
	private void closeQuietly(Connection db) {
		if(db != null) {
			try {
				db.close();
			} catch (SQLException e) {
				// ignore
			}
		}
	}
	
	private void closeQuietly(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				// ignore
			}
		}
	}
	
	private void closeQuietly(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// ignore
			}
		}
	}
}
