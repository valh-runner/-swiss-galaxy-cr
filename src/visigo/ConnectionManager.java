/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visigo;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Home
 */
public class ConnectionManager {

    private static String driverName = "com.mysql.jdbc.Driver";
    //private static String url = "jdbc:mysql://localhost:3306/visigo";
    //private static String url = "jdbc:mysql://localhost:3306/visigo?autoReconnect=true&useSSL=false";
    private static String url = "jdbc:mysql://localhost:3306/gsb_cr?autoReconnect=true&useSSL=false";
    private static String username = "root";
    private static String password = "";
    private static Connection connection = null;
    private static boolean isAuth = false;
    private static String userMatricule = null;

    private static void connect() {
        try{
            Class.forName(driverName);
            try{
                ConnectionManager.connection = DriverManager.getConnection(url, username, password);
            }catch(SQLException e){
                System.out.println("Failed to create the database connection");
            }
        }catch(ClassNotFoundException e){
            System.out.println("Failed to reach mysql JDBC driver");
        }
    }
    
    public static Connection getConnection() {
        //if(ConnectionManager.connection == null){
            ConnectionManager.connect();
        //}
        return ConnectionManager.connection;
    }
    
    public static boolean auth(String login, String password) {
        
        Connection con;
        Statement stmt = null;
        ResultSet rs = null;
        
        con = ConnectionManager.getConnection();
        try{
            stmt = (Statement) con.createStatement();
            String req= "SELECT VIS_MATRICULE, VIS_DATEEMBAUCHE FROM visiteur WHERE VIS_NOM = '"+ login +"';";
            rs = stmt.executeQuery(req);
            while(rs.next()){
                String date_embauche = rs.getString("VIS_DATEEMBAUCHE");
                String date_embauche_notime = date_embauche.substring(0, 10); //convertion datetime en time

                if (password.equals(date_embauche_notime)) {
                    ConnectionManager.isAuth = true;
                    ConnectionManager.userMatricule = rs.getString("VIS_MATRICULE");
                }
            }
            rs.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Failed to create connection statement");
        }finally{
            /*if (con != null) {
                try {
                    con.close(); //fermeture connexion
                } catch (SQLException e) {
                    //si erreur lors de la fermeture, on l'ignore
                }
            }*/
        }
        return ConnectionManager.isAuth;
    }
    
    public static String getUserMatricule(){
        return ConnectionManager.userMatricule;
    }

    public static Object[] requestRead(String req){
        Object[] resTab = null;
        
        Connection con = ConnectionManager.getConnection();
        try {
            Statement stmt = (Statement) con.createStatement();
//            String req = "SELECT PRA_NUM, PRA_NOM, PRA_PRENOM, PRA_CP FROM praticien ORDER BY PRA_NOM;";
            ResultSet rs = stmt.executeQuery(req);

            //On récupère les MetaData
            ResultSetMetaData resultMeta = rs.getMetaData();
            int resColCount = resultMeta.getColumnCount();
            String[] tableColNames = new String[resColCount];
            int[] tableColTypes = new int[resColCount];
            String[] tableColTypeNames = new String[resColCount];
            
            for(int i = 1; i <= resColCount; i++){
                System.out.println(resultMeta.getColumnName(i)+" \t "+resultMeta.getColumnType(i)+" \t "+resultMeta.getColumnTypeName(i));
                tableColNames[i-1] = resultMeta.getColumnName(i);
                tableColTypes[i-1] = resultMeta.getColumnType(i);
                tableColTypeNames[i-1] = resultMeta.getColumnTypeName(i);
            }

            rs.last(); //placement curseur sur le dernier tuple
            int nbrLignes = rs.getRow(); //récup num ligne
            rs.beforeFirst(); //replacement curseur au début
            
            resTab = new Object[nbrLignes];
            int index = 0;
            
            while (rs.next()) {
                TreeMap < String, Object > resLine = new TreeMap < String, Object > ( );
                for(int j = 0; j < resColCount; j++){
                    try{
                        switch(tableColTypes[j]){
                            case -7:
                                resLine.put(tableColNames[j], rs.getInt(tableColNames[j]));
                                break;
                            case 4:
                                resLine.put(tableColNames[j], rs.getInt(tableColNames[j]));
                                break;
                            case 5:
                                resLine.put(tableColNames[j], rs.getInt(tableColNames[j]));
                                break;
                            case 12:
                                resLine.put(tableColNames[j], rs.getString(tableColNames[j]));
                                break;
                            case 93:
                                resLine.put(tableColNames[j], rs.getDate(tableColNames[j]));
                                break;
                            default:
                                throw (new Exception("Type de colonne Mysql non géré: "+ tableColTypes[j]));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                resTab[index] = resLine;
                index++;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Failed to create connection statement");
        } finally {
            if (con != null) {
                try {
                    con.close(); //fermeture connexion
                } catch (SQLException e) {
                    //si erreur lors de la fermeture, on l'ignore
                }
            }
        }
        return resTab;
    }
    
    public static void requestWrite(String req, TreeMap < Integer, Object > requestValues){
        
        Connection con = ConnectionManager.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(req);
            
//            Set<String> keys = tm.keySet();
//            for(String requestValue: requestValues){
//                preparedStatement.setObject(, tm.get(key));
//            }
            
            // Parcours des entrées (clef, valeur)
            for (Map.Entry < Integer, Object > requestValue : requestValues.entrySet()) {
                    System.out.println("Clé : "+requestValue.getKey()+" Valeur : "+requestValue.getValue());
                    preparedStatement.setObject(requestValue.getKey(), requestValue.getValue());
            }
            
            /*preparedStatement.setString(1, ConnectionManager.getUserMatricule());
            preparedStatement.setInt(2, pra_num);
            preparedStatement.setInt(3, rap_estremplace);
            if(pra_num_remplacant == 0){
                preparedStatement.setNull(4, Types.NULL);
            }else{
                preparedStatement.setInt(4, pra_num_remplacant);
            }
            preparedStatement.setDate(5, date_sql);
            preparedStatement.setString(6, rap_bilan);
            preparedStatement.setString(7, rap_motif);*/
            preparedStatement.executeUpdate(); 
            
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Failed to create connection statement");
            System.out.println(e);
        } finally {
            if (con != null) {
                try {
                    con.close(); //fermeture connexion
                } catch (SQLException e) {
                    //si erreur lors de la fermeture, on l'ignore
                }
            }
        }
        
    }

}
