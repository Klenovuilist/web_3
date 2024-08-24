package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import web.Entity.Materals_db;
import web.Entity.Moments_db;
import web.Entity.Thread_db;
import web.dao.Materals_db_DAO;
import web.dao.Moments_db_DAO;
import web.dao.Thread_db_DAO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControllerMaterialCreate {

    @Autowired
    Materals_db_DAO materals_db_dao;

    @Autowired
    Moments_db_DAO moments_db_dao;

    @Autowired
    Thread_db_DAO thread_db_dao;

    @GetMapping("/materials_create")
    public String materialsCreate(Model model){

        System.out.println("materials_create - 1");

        var materals_db = new Materals_db();
        List<Thread_db> thread_dbs = thread_db_dao.getThread_db();
        var moments_db = new Moments_db();

        model.addAttribute("newMaterial",materals_db);
        model.addAttribute("threads", thread_dbs);
        model.addAttribute("newThreads", moments_db);

        return "materials_create.html";
    }

    @PostMapping("/materials_create")
    public String materialSave(@ModelAttribute Materals_db materals_db
            , @ModelAttribute Moments_db moments_db
            , @ModelAttribute Thread_db thread_db, HttpServletRequest request){

        System.out.println(materals_db.getMaterials());
        System.out.println(materals_db.getLimitStrength());
        System.out.println(request.getParameter("M3"));

//        List<Moments_db> moments_dbs = new ArrayList<>();

        materals_db_dao.save(materals_db);
        System.out.println("мат. сохрн.");
        List<Thread_db> thread_dbs= thread_db_dao.getThread_db();

        for (Thread_db thread: thread_dbs){
           double moment = 0.0;
           if(!request.getParameter(thread.getThread()).isEmpty()) {
               moment = Double.parseDouble(request.getParameter(thread.getThread()));
           }
           Moments_db  n = Moments_db.builder()
                   .thread(thread)
                   .moments_nm(moment)
                   .materals_db(materals_db)
            .build();
//            moments_dbs.add(n);
            moments_db_dao.saveAll(n);
        }
//        materals_db.setMoments_db()
        System.out.println("moment. сохрн.");

        return "redirect:/moment_page_1";
    }
}
