package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import web.Entity.Materals_db;
import web.Entity.Moments_db;
import web.Entity.Thread_db;
import web.dao.Materals_db_DAO;
import web.dao.Moments_db_DAO;
import web.dao.Thread_db_DAO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Controller
public class ControllerMaterialsUpdate {

    @Autowired
    private Thread_db_DAO thread_db_dao;

    @Autowired
    private Moments_db_DAO moments_db_dao;

    @Autowired
    private Materals_db_DAO materals_db_dao;





    @GetMapping("/materials_update/{id}")
    public String update(Model model, HttpServletRequest request, @PathVariable("id") int id){


        Materals_db materals_db = materals_db_dao.materialsById(id);
        System.out.println("mat id = " + id);
        List<Thread_db> thread_dbs = thread_db_dao.getThread_db();
        System.out.println("List thread ");
        List<Moments_db> moments_dbs = materals_db.getMoments_db();
        System.out.println("List moment");



        List<Moments_db> sortedMoment = new ArrayList<>();
        for (Thread_db thread_db: thread_dbs ){
            Predicate<Moments_db> filterByThread = (moment) -> {
                System.out.println("predicate 9999999");
                return moment.getThread().getThread().equals(thread_db.getThread());
            };
            sortedMoment.add(moments_dbs.stream().filter(filterByThread).findAny().orElse(null));

        }
        System.out.println("SortedList");

        model.addAttribute("material", materals_db);
        model.addAttribute("theads", thread_dbs);
        model.addAttribute("moments", sortedMoment);

        return "update_material.html";
    }
    @PostMapping("/update/{id}")
    public String saveApdate(@PathVariable("id") int id, @ModelAttribute Materals_db materals_db
            , HttpServletRequest request){

        System.out.println(id + "   "+ materals_db.getMaterials());

        Materals_db materal_dbForUpdate = materals_db_dao.materialsById(id);  // материал по id
        List<Moments_db> moments_dbForUpdate = materal_dbForUpdate.getMoments_db(); //момент по материалу
        List<Thread_db> thread_dbs = thread_db_dao.getThread_db(); //резьба

        thread_dbs.forEach(f -> {
           Moments_db momentForUpdate = moments_dbForUpdate.stream()
                   .filter(fil -> fil.getThread().getThread().equals(f.getThread())).findAny().orElse(null);
            momentForUpdate.setMoments_nm(Double.valueOf(request.getParameter(f.getThread())));
            moments_db_dao.updateMoment(momentForUpdate);
        });

        materal_dbForUpdate.setMaterials(materals_db.getMaterials());
        materal_dbForUpdate.setLimitStrength13(materals_db.getLimitStrength13());
        materal_dbForUpdate.setMoments_db(moments_dbForUpdate);

        materals_db_dao.updateMaterial(materal_dbForUpdate);



        return "redirect:/moment_page_1";
    }

}
