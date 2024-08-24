package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.Entity.Materals_db;
import web.Entity.Moments_db;
import web.Entity.Thread_db;
import web.dao.Materals_db_DAO;
import web.dao.Moments_db_DAO;
import web.dao.Thread_db_DAO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerMaterialSelect {
    @Autowired
    private Moments_db_DAO moments_db_dao;
    @Autowired
    private Materals_db_DAO materals_db_dao;

    @Autowired
    private Thread_db_DAO thread_db_dao;


    @GetMapping("/material_select")
    private String materialSelect(Model model){

    List<Materals_db> materals_dbs = materals_db_dao.getMaterals_db();
    model.addAttribute("materials", materals_dbs);

        return "material_select.html";
    }


    @GetMapping("/material_select_app")
    private String materialSelectapply(@RequestParam(value = "threadId", required = false)Integer threadId, Model model
            , HttpServletRequest request){


            List<Materals_db> Allmaterals_dbs = materals_db_dao.getMaterals_db();

            List<Materals_db> materals_dbs = Allmaterals_dbs.stream()
                    .filter(m -> String.valueOf(m.getId()).equals(request.getParameter((String.valueOf(m.getId())))))
                    .toList();


            model.addAttribute("materials", materals_dbs);

            List<Moments_db> moments_db = moments_db_dao.getMoments_db();

            List<Thread_db> thread_dbs = thread_db_dao.getThread_db();
            model.addAttribute("threads", thread_dbs);

            List<Moments_db> moments_dbsFilter;

            if (threadId != null) {
                moments_dbsFilter = moments_db.stream()
                        .filter(f -> f.getThread().getId() == threadId)
                        .toList();
            }
            else {
                moments_dbsFilter = moments_db.stream().filter(f -> f.getThread().getId() == thread_dbs.get(0).getId()).toList();
            }

            List<Moments_db> moments_dbSorted = new ArrayList<>();

            for (Materals_db mat: materals_dbs){

                moments_dbSorted.add(moments_dbsFilter.stream().filter(f -> f.getMaterals_db().getId() == mat.getId()).findAny().orElse(null));
            }
            model.addAttribute("moments", moments_dbSorted);

        System.out.println(request.getParameter("47"));

        return "redirect:/moment_page_1";
    }

}
