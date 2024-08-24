package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import web.Entity.Materals_db;
import web.Entity.Moments_db;
import web.dao.Materals_db_DAO;
import web.dao.Moments_db_DAO;

import java.util.List;


@Controller
public class ControllerMaterialDelete {

    @Autowired
    Materals_db_DAO materals_db_dao;
    @Autowired
    Moments_db_DAO moments_db_dao;

    @GetMapping("/materials_delete/{id}")
    public String materialsDelete(@PathVariable("id") int id){
        Materals_db materalForDelete = materals_db_dao.materialsById(id);
        List<Moments_db> momentsForDelete = materalForDelete.getMoments_db();

        momentsForDelete.forEach(m -> moments_db_dao.momentRemove(m));

        materalForDelete.getMoments_db().clear();
        materals_db_dao.materialRemove(materalForDelete);

        return "redirect:/moment_page_1";
    }
}
