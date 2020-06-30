package src.br.com.aula.factory;

import src.br.com.aula.util.TratamentoErro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String BANCODADOS = "sistemaweb";
    private static final String CONEXAO = "jdbc:mysql://localhost:3306/" + BANCODADOS + "?useTimezone=true&serverTimezone=America/Sao_Paulo";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws TratamentoErro {
        try {
            Connection conn = null;
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(CONEXAO, USER, PASSWORD);
            System.out.println("Conectado");
            return conn;
        } catch (ClassNotFoundException e) {
            throw new TratamentoErro("Driver do banco de dados não foi encontrado!", e);
        } catch (SQLException e) {
            throw new TratamentoErro("Não foi possivel conectar ao banco de dados!  ", e);
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Problema no fechamento da conex�o.");
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static Statement getStatement(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            return stmt;
        } catch (SQLException e) {
            System.out.println("Erro ao obter o Statement.");
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    public static void closeStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Problema no fechamento do Statement.");
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static PreparedStatement getPreparedStatement(Connection conn, String sql) {
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt;
        } catch (Exception e) {
            System.out.println("Erro ao obter o PreparedStatement.");
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }

    public static void closePreparedStatement(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Problema no fechamento do PreparedStatement");
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void closeResultSet(ResultSet result) {
        try {
            if (result != null) {
                result.close();
            }
        } catch (SQLException e) {
            System.out.println("Problema no fechamento do ResultSet");
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static PreparedStatement getPreparedStatement(Connection conn, String sql, int tipoRetorno) {
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, tipoRetorno);
            return stmt;
        } catch (Exception e) {
            System.out.println("Erro ao obter o PreparedStatement.");
            return null;
        }
    }
}
