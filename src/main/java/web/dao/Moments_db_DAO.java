package web.dao;

import web.Entity.Moments_db;

import java.util.List;

public interface Moments_db_DAO {

    public List<Moments_db> getMoments_db();

    public void saveAll(Moments_db moments_dbs);

    public  void updateMoment(Moments_db moments_db);

    public  List<Moments_db> momentsByIdMaterial(int idMaterial);

    public void momentRemove(Moments_db moments_db);


}
