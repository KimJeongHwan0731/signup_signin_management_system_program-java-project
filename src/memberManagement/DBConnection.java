package memberManagement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DBConnection {

	private Connection connection = null;

	public void connect() {
		Properties properties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("C:/SelfStudyJava/java0303/src/memberManagement/db.properties");
			properties.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println("FileInputStream error" + e.getStackTrace());
		} catch (IOException e) {
			System.out.println("Properties.load error" + e.getStackTrace());
		}

		try {
			Class.forName(properties.getProperty("driverName"));
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
					properties.getProperty("password"));
		} catch (ClassNotFoundException e) {
			System.out.println("[데이터베이스 로드오류]" + e.getStackTrace());
		} catch (SQLException e) {
			System.out.println("[데이터베이스 연결오류]" + e.getStackTrace());
		}

	}

	public int insert(Login l) {
		this.connect();
		PreparedStatement ps = null;
		int returnValue = -1;
		String query = "insert into loginTBL values(null, ?, ?, ?, ?, ?, ?, ?)";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, l.getId());
			ps.setString(2, l.getPassword());
			ps.setString(3, l.getName());
			ps.setString(4, l.getDatebirth());
			ps.setString(5, l.getGender());
			ps.setString(6, l.getEmail());
			ps.setString(7, l.getPhoneNumber());
			returnValue = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("insert 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("ps close 오류" + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("connection close 오류" + e.getMessage());
			}
		}
		return returnValue;
	}

	public ArrayList<Login> infSearchSelect(String id2, String password2) {
		ArrayList<Login> list = new ArrayList<>();
		this.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select * from loginTBL where id = ? and password = ?";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, id2);
			ps.setString(2, password2);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				int userCode = rs.getInt("userCode");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String datebirth = rs.getString("datebirth");
				String gender = rs.getString("gender");
				String email = rs.getString("email");
				String phoneNumber = rs.getString("phoneNumber");
				list.add(new Login(userCode, id, password, name, datebirth, gender, email, phoneNumber));
			}
		} catch (Exception e) {
			System.out.println("select 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("ps close 오류" + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("connection close 오류" + e.getMessage());
			}
		}
		return list;
	}

	public String selectId(String id) {
		this.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String returnid = null;
		String query = "select id from loginTBL WHERE id = ?";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				returnid = rs.getString("id");
			}
		} catch (Exception e) {
			System.out.println("select 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("ps close 오류" + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("connection close 오류" + e.getMessage());
			}
		}
		return returnid;
	}

	public String selectPhoneNumber(String phoneNumber) {
		this.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String returnPhoneNumber = null;
		String query = "select phoneNumber from loginTBL WHERE phoneNumber = ?";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, phoneNumber);
			rs = ps.executeQuery();
			if (rs.next()) {
				returnPhoneNumber = rs.getString("phoneNumber");
			}
		} catch (Exception e) {
			System.out.println("select 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("ps close 오류" + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("connection close 오류" + e.getMessage());
			}
		}
		return returnPhoneNumber;
	}

	public int modify(String type, String oldInfo, String newInfo) {
		this.connect();
		PreparedStatement ps = null;
		int returnValue = -1;
		String query = "update loginTBL set " + type + " = '" + newInfo + "' where id = '" + oldInfo + "'";
		try {
			ps = connection.prepareStatement(query);
			returnValue = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("update 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("ps close 오류" + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("connection close 오류" + e.getMessage());
			}
		}
		return returnValue;
	}

	public int membershipWithdrawal(String id, String password) {
		this.connect();
		PreparedStatement ps = null;
		int returnValue = -1;
		String query = "delete from loginTBL where id = ? and password = ?";
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, password);
			returnValue = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("delete 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("ps close 오류" + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("connection close 오류" + e.getMessage());
			}
		}
		return returnValue;
	}

	public String searchInfo1(String name, String phoneNumber) {
	    this.connect();
	    PreparedStatement ps = null;
	    String returnValue = null;
	    String query = "SELECT id FROM loginTBL WHERE name = ? AND phoneNumber = ?";
	    try {
	        ps = connection.prepareStatement(query);
	        ps.setString(1, name);
	        ps.setString(2, phoneNumber);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            returnValue = rs.getString("id");
	        }
	    } catch (Exception e) {
	        System.out.println("searchId 오류발생: " + e.getMessage());
	    } finally {
	        try {
	            if (ps != null) {
	                ps.close();
	            }
	        } catch (SQLException e) {
	            System.out.println("ps close 오류: " + e.getMessage());
	        }
	        try {
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            System.out.println("connection close 오류: " + e.getMessage());
	        }
	    }
	    return returnValue;
	}

	public String searchInfo2(String id, String name, String phoneNumber) {
	    this.connect();
	    PreparedStatement ps = null;
	    String returnValue = null;
	    String query = "SELECT password FROM loginTBL WHERE id = ? AND name = ? AND phoneNumber = ?";
	    try {
	        ps = connection.prepareStatement(query);
	        ps.setString(1, id);
	        ps.setString(2, name);
	        ps.setString(3, phoneNumber);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            returnValue = rs.getString("password");
	        }
	    } catch (Exception e) {
	        System.out.println("searchPassword 오류발생: " + e.getMessage());
	    } finally {
	        try {
	            if (ps != null) {
	                ps.close();
	            }
	        } catch (SQLException e) {
	            System.out.println("ps close 오류: " + e.getMessage());
	        }
	        try {
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            System.out.println("connection close 오류: " + e.getMessage());
	        }
	    }
	    return returnValue;
	}
}
