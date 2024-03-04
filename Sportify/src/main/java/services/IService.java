package services;

import entities.Terrain;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IService <T>{
    public  void ajouter(T t );

    public void ajouter(T t, String path) throws SQLException;

    public  void modifier(T t ) throws SQLException;
    public  void supprimer(int id )throws SQLException;
    public Set<T> afficher() throws SQLException ;





}
