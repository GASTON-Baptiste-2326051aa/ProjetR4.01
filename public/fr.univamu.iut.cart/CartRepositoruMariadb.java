import java.io.Closeable;
import java.sql.*;
import java.util.*;

/**
 * Classe permettant d'accéder aux paniers stockés dans une base de données MariaDB
 */
public class CartRepositoruMariadb implements CartRepositoryInterface{

    /**
     * Accès à la base de données
     */
    protected Connection dbConnection;


    public CartRepositoryMariadb(String infoConnecion, String user, String pwd) throws java.sql.SQLException,
            java.lang.ClassNotFoundException {
            Class.forName("org.mariadb.jdbc.Driver");
            dbConnection = DriverManager.getConnection(infoConnecion, user, pwd);

    }

    /**
     * Fermeture de la connexion à la base de données
     */
    @Override
    public void close(){
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage())
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Cart getCark(int id){

    }

    /**
     *
     */
    @Override
    public ArrayList<Cart> getAllCarts(){

    }

    /**
     *
     */
    @Override
    public boolean updateBook(int id, String name, String description, int price, int available_quantity){




    }





}