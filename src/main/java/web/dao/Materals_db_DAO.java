package web.dao;

import web.Entity.Materals_db;

import java.util.List;

public interface Materals_db_DAO {

    public List<Materals_db> getMaterals_db();

    public void save(Materals_db materals_db);

    public Materals_db materialsById(int id);

    public void updateMaterial(Materals_db materals_db);

    public void materialRemove (Materals_db materals_db);

}


