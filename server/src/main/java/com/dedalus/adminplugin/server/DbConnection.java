package com.dedalus.adminplugin.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

enum DbType {
  ORACLE,
  POSTGRES
}

public class DbConnection {

  private static Connection connection;

  private DbConnection() {
  }

  public static DbType getDatabasType() {
    String envValue = System.getenv("DATABASE");
    if (envValue == null)
      return DbType.ORACLE;
    switch (envValue.toLowerCase()) {
      case "postgres":
        return DbType.POSTGRES;
      case "oracle":
      default:
        return DbType.ORACLE;
    }
  }

  public static Connection getConnection() {
    if (connection == null) {
      try {
        return (connection = DriverManager.getConnection(
            System.getenv("DATABASE_URL"),
            System.getenv("DATABASE_USERNAME"),
            System.getenv("DATABASE_PASSWORD")));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return connection;
  }

  public static void closeConnection() {
    try {
      if (!connection.getAutoCommit()) {
        connection.rollback();
      }
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      connection = null;
    }
  }

}
