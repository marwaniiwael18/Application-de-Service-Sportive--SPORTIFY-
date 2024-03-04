package services;

import java.sql.SQLException;
import java.util.List;

public interface IServiceX<T>{
    public  void ajouter(T t ) throws SQLException;
    public  void modifier(T t ) throws SQLException;
    public  void supprimer(int t ) throws SQLException;
    void modifiert(int id, T t) throws SQLException; // Change the method signature
    T authentifier(String email, String password) throws SQLException;

    public List<T> afficher() throws SQLException;



}
