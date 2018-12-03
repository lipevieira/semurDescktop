package br.com.nti.database;

public class DatabaseFactory {
    public static Database getDatabase(String nome){
       if(nome.equals("mysql")){
            return new DatabaseMySQL();
        }
        return null;
    }
}
