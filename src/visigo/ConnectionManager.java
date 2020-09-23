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
import java.util.Map;
import java.util.TreeMap;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


/**
 *
 * @author Home
 */
public class ConnectionManager {

    static int lport = 3306; // default port
    static String rhost = "localhost"; //even for distant connection via SSH
    static int rport; // used for forwading port on distant server
    static boolean isDistantDB = true;
    
    private static Connection connection = null;
    private static boolean isAuth = false;
    private static String userMatricule = null;
    private static String userRole = null;
    private static String userRegCode = null;
    private static String userSecCode = null;

    private static void connect() {
        if(ConnectionManager.isDistantDB){
            try {
                go();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        Connection con = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://" + rhost + ":" + lport + "/";
        String db = "gsb_cr";
        String dbUser;
        String dbPasswd;
        
        if(ConnectionManager.isDistantDB){
            dbUser = "gsb_cr";
            dbPasswd = "5ilver-Stone";
        }else{
            dbUser = "root";
            dbPasswd = "";
        }

        try{
            Class.forName(driver);
            try{
                con = DriverManager.getConnection(url + db +"?autoReconnect=true&useSSL=false", dbUser, dbPasswd);
            }catch(SQLException e){
                System.out.println("Failed to create the database connection");
                System.out.print(e);
            }
        } catch (Exception e) {
            System.out.println("Failed to reach mysql JDBC driver");
            e.printStackTrace();
        }
        
        ConnectionManager.connection = con;
    }

    /**
     * Fonction permettant la connexion SSH
     */
    public static void go() {
        String user = "root";
        String password = "1Xbk#Y?3nb";
        String host = "82.165.126.248";
        int port = 22;
 
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            lport = 4321;
            rhost = "localhost";
            rport = 3306;
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            int assinged_port = session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);
        } catch (Exception e) {
            System.err.print(e);
        }
    }
    
    /**
     * Récupération de la connexion
     * @return
     */
    public static Connection getConnection() {
        if(ConnectionManager.connection == null){
            ConnectionManager.connect();
        }
        return ConnectionManager.connection;
    }
    
    /**
     * Authentification de l'utilisateur
     * @param login
     * @param password
     * @return
     */
    public static boolean auth(String login, String password) {
        
        Connection con;
        Statement stmt = null;
        ResultSet rs = null;
        
        con = ConnectionManager.getConnection();
        try{
            stmt = (Statement) con.createStatement();
            
            String req=   "SELECT trava.REG_CODE, trava.TRA_ROLE, `region`.REG_NOM, `region`.`SEC_CODE` AS SECTEUR_CODE, `secteur`.`SEC_LIBELLE`, `visiteur`.* "
                        + "FROM ("
                        + "    SELECT tr.* "
                        + "    FROM `travailler` tr "
                        + "    JOIN ("
                        + "        SELECT VIS_MATRICULE, MAX(jjmmaa) AS maxDate "
                        + "        FROM `travailler` "
                        + "        GROUP BY VIS_MATRICULE    ) groupetr "
                        + "    ON tr.VIS_MATRICULE = groupetr.VIS_MATRICULE "
                        + "    AND tr.jjmmaa = groupetr.maxDate "
                        + ") AS trava, `region`, `secteur`, `visiteur` "
                        + "WHERE `region`.REG_CODE = trava.REG_CODE  "
                        + "AND `region`.SEC_CODE = `secteur`.SEC_CODE "
                        + "AND trava.VIS_MATRICULE = `visiteur`.VIS_MATRICULE "
                        + "AND `visiteur`.VIS_NOM = '"+ login +"';";
            
            rs = stmt.executeQuery(req);
            while(rs.next()){
                String date_embauche = rs.getString("VIS_DATEEMBAUCHE");
                String date_embauche_notime = date_embauche.substring(0, 10); //convertion datetime en time
                
                if (password.equals(date_embauche_notime)) {
                    ConnectionManager.isAuth = true;
                    ConnectionManager.userMatricule = rs.getString("VIS_MATRICULE");
                    ConnectionManager.userRole = rs.getString("TRA_ROLE");
                    ConnectionManager.userRegCode = rs.getString("REG_CODE");
                    ConnectionManager.userSecCode = rs.getString("SECTEUR_CODE");
                }
            }
            rs.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Failed to create connection statement");
            System.out.print(e);
        }
        return ConnectionManager.isAuth;
    }
    
    /**
     * Renvoi le matricule de l'utilisateur connecté
     * @return
     */
    public static String getUserMatricule(){
        return ConnectionManager.userMatricule;
    }
    
    /**
     * Renvoi le role de l'utilisateur connecté
     * @return
     */
    public static String getUserRole(){
        return ConnectionManager.userRole;
    }
    
    /**
     * Renvoi le code de région de l'utilisateur connecté
     * @return
     */
    public static String getRegCode(){
        return ConnectionManager.userRegCode;
    }
    
    /**
     * Renvoi le role de secteur l'utilisateur connecté
     * @return
     */
    public static String getSecCode(){
        return ConnectionManager.userSecCode;
    }

    /**
     * Procède à une requète en lecture
     * @param req
     * @return
     */
    public static Object[] requestRead(String req){
        Object[] resTab = null;
        
        Connection con = ConnectionManager.getConnection();
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery(req);

            //On récupère les MetaData
            ResultSetMetaData resultMeta = rs.getMetaData();
            int resColCount = resultMeta.getColumnCount();
            String[] tableColNames = new String[resColCount];
            int[] tableColTypes = new int[resColCount];
            String[] tableColTypeNames = new String[resColCount];
            
            for(int i = 1; i <= resColCount; i++){
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
                            case -5:
                                resLine.put(tableColNames[j], rs.getInt(tableColNames[j]));
                                break;
                            case 4:
                                resLine.put(tableColNames[j], rs.getInt(tableColNames[j]));
                                break;
                            case 5:
                                resLine.put(tableColNames[j], rs.getInt(tableColNames[j]));
                                break;
                            case 8:
                                resLine.put(tableColNames[j], rs.getDouble(tableColNames[j]));
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
            e.printStackTrace();
        }
        return resTab;
    }
    
    /**
     * Procède à une requète en écriture
     * @param req
     * @param requestValues
     * @return
     */
    public static int requestWrite(String req, TreeMap < Integer, Object > requestValues){
        int generatedKey = 0;
        Connection con = ConnectionManager.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            
            // Parcours des entrées (clef, valeur)
            for (Map.Entry < Integer, Object > requestValue : requestValues.entrySet()) {
                    preparedStatement.setObject(requestValue.getKey(), requestValue.getValue());
            }
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Failed to create connection statement");
            System.out.println(e);
        }
        return generatedKey;
    }
}
