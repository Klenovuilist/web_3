package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.Entity.Materals_db;
import web.Entity.Moments_db;
import web.Entity.Thread_db;
import web.dao.Materals_db_DAO;
import web.dao.Moments_db_DAO;
import web.dao.Thread_db_DAO;
import web.service.MomentService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class Controller_Moment {

    @Autowired
    private Moments_db_DAO moments_db_dao;
    @Autowired
    private Materals_db_DAO materals_db_dao;

    @Autowired
    private Thread_db_DAO thread_db_dao;

    @Autowired
    private MomentService momentService;

    @GetMapping("/moment_page_0")
    public String momentPage0() {


        return "moment_page_0.html";
    }

    @GetMapping("/moment_page_1") //moment_page_1
    public String moment_page_1(@RequestParam(value = "threadId", required = false) Integer threadId
            , Model model, HttpServletRequest request, HttpServletResponse response) {  // модель для отображения во view
        System.out.println("@GetMapping(/moment_page_1");

        List<Materals_db> allMaterals_dbs = materals_db_dao.getMaterals_db();
        /**
         * фильтр списка материала по id из формы select?
         */
        List<Materals_db> materals_dbs = allMaterals_dbs.stream()
                .filter(m -> String.valueOf(m.getId()).equals(request.getParameter((String.valueOf(m.getId())))))
                .collect(Collectors.toList());

        /**
         * Проверка списка материалов на Empty, если пустой -> заполнить по данным от cookies(если есть cooki)
         * если кук нет заполнить по списку материалов из БД(если материалы есть в базе)
         *
         */
        Cookie[] cookies = request.getCookies();

        if (materals_dbs.isEmpty()) {

            if (cookies != null) {
                for (Cookie cook : cookies) {

                    materals_dbs.addAll(allMaterals_dbs.stream()
                            .filter(f -> ("id" + String.valueOf(f.getId())).equals(cook.getName())
                                    && cook.getValue().equals("1")).collect(Collectors.toList()));
                }
            }
        }
        if (materals_dbs.isEmpty()) {
            for (int i = 0; i <= allMaterals_dbs.size(); i++) {
                materals_dbs.add(allMaterals_dbs.get(i));
                if (i > 3) break;
            }
        }

        model.addAttribute("materials", materals_dbs);


        List<Moments_db> moments_db = moments_db_dao.getMoments_db();

        List<Thread_db> thread_dbs = thread_db_dao.getThread_db();
        model.addAttribute("threads", thread_dbs);
/**
 * фильтр списка moments_dbsFilter по значениЮ id Thread, если занчения Thread не пришло
 * взять значение из куков или
 * установить его как 0 элемент из списка Thread
 */
        List<Moments_db> moments_dbsFilter = new ArrayList<>();

        Cookie cookieThread = Arrays.stream(cookies)
                .filter(f -> f.getName().equals("ftreadId")).findAny().orElse(null);

        Thread_db threadСurrent;

        if (threadId != null) {
            moments_dbsFilter = moments_db.stream()
                    .filter(f -> f.getThread().getId() == threadId).toList();
            threadСurrent = thread_dbs.stream().filter(f -> f.getId() == threadId).findAny().orElse(null);
            model.addAttribute("thread", threadСurrent.getThread());

        } else if (cookieThread != null) {
            moments_dbsFilter = moments_db.stream()
                    .filter(f -> String.valueOf(f.getThread().getId()).equals(cookieThread.getValue())).toList();
            threadСurrent = thread_dbs.stream().filter(thread_db -> String.valueOf(thread_db.getId()).equals(cookieThread.getValue()))
                    .findAny().orElse(null);
            model.addAttribute("thread", threadСurrent.getThread());

        } else {
            moments_dbsFilter = moments_db.stream()
                    .filter(f -> f.getThread().getId() == thread_dbs.get(0).getId()).toList();
            threadСurrent = thread_dbs.get(0);
            model.addAttribute("thread", threadСurrent.getThread());
        }
/**
 * Создание нового списка моментов по соответствию с id материала
 */
            List<Moments_db> moments_dbSorted = new ArrayList<>();

            for (Materals_db mat : materals_dbs) {
                moments_dbSorted.add(moments_dbsFilter.stream()
                        .filter(f -> f.getMaterals_db().getId() == mat.getId()).findAny().orElse(null));
            }
            model.addAttribute("moments", moments_dbSorted);

            /**
             * Вычисление теоретического:
             * - силы затяжки
             * -момента затяжки
             * для листов материала
             */
            List<Integer> powerByMomentTheor_N = new ArrayList<>();
            List<Integer> stregthByPowerTheor_MPa = new ArrayList<>();

            if (!materals_dbs.isEmpty()) {
                for (Moments_db moment : moments_dbSorted) {

                    int powerKallerman_N = momentService.powerKellerman_N(
                            moment.getMoments_nm(),
                            moment.getThread().getDMidlethread_mm(),
                            moment.getThread().getStepThread_mm(),
                            0.14,
                            0.14,
                            moment.getThread().getDHead_mm(),
                            moment.getThread().getDhole_mm());

                    powerByMomentTheor_N.add(powerKallerman_N);

                    stregthByPowerTheor_MPa.add(momentService.strengthByPowerAndDiam(powerKallerman_N
                            , moment.getThread().getDBolt_mm()
                            , moment.getThread().getDMidlethread_mm()));
                }
            }
            model.addAttribute("powerByMomentTheor_N", powerByMomentTheor_N);
            model.addAttribute("stregthByPowerTheor_MPa", stregthByPowerTheor_MPa);


            /**
             * вычисление момента и силы
             */
            Map<String, String> dataForCalc = new HashMap<>();

        dataForCalc.put("limateStrength_Mpa", "110");
        dataForCalc.put("diametrThread_mm", String.valueOf(threadСurrent.getDBolt_mm()));
        dataForCalc.put("middleDiamThread_mm", String.valueOf(threadСurrent.getDMidlethread_mm()));
        dataForCalc.put("k_threadDepth", "1.0");
        dataForCalc.put("safetyFactor", "1.25");
        dataForCalc.put("powerMaxForMaterial_N", "0");

        dataForCalc.put("stepThread_mm", String.valueOf(threadСurrent.getStepThread_mm()));
        dataForCalc.put("coefficientOfFrictionThread", "0.14");
        dataForCalc.put("coefficientOfFrictionBoltHead", "0.14");
        dataForCalc.put("diametrHead_mm", String.valueOf(threadСurrent.getDHead_mm()));
        dataForCalc.put("diametrHole_mm", String.valueOf(threadСurrent.getDhole_mm()));
        dataForCalc.put("momentKellerman_NM", "0");


/**
 * установка новых значений в список параметров из куков
 */
//            if (cookies != null) {
//                for (Cookie cookie : cookies) {
//                    Map.Entry<String, String> parametr = dataForCalc.entrySet().stream()
//                            .filter(f -> f.getKey().equals(cookie.getName())).findAny().orElse(null);
//                    if (parametr != null) {
//                        parametr.setValue(cookie.getValue());
//                        dataForCalc.put(parametr.getKey(), parametr.getValue());
//                    }
//                }
//            }



            /**
             * установка значений  из формы
             */

            if (request.getParameter("limateStrength_Mpa") != null) {
                dataForCalc.put("limateStrength_Mpa", request.getParameter("limateStrength_Mpa"));
            }
            if (request.getParameter("diametrThread_mm") != null) {
                dataForCalc.put("diametrThread_mm", request.getParameter("diametrThread_mm"));
            }
            if (request.getParameter("middleDiamThread_mm") != null) {
                dataForCalc.put("middleDiamThread_mm", request.getParameter("middleDiamThread_mm"));
            }
            if (request.getParameter("k_threadDepth") != null) {
                dataForCalc.put("k_threadDepth", request.getParameter("k_threadDepth"));
            }
            if (request.getParameter("safetyFactor") != null) {
                dataForCalc.put("safetyFactor", request.getParameter("safetyFactor"));
            }
        if (request.getParameter("stepThread_mm") != null) {
            dataForCalc.put("stepThread_mm", request.getParameter("stepThread_mm"));
        }
        if (request.getParameter("coefficientOfFrictionThread") != null) {
            dataForCalc.put("coefficientOfFrictionThread", request.getParameter("coefficientOfFrictionThread"));
        }
        if (request.getParameter("coefficientOfFrictionBoltHead") != null) {
            dataForCalc.put("coefficientOfFrictionBoltHead", request.getParameter("coefficientOfFrictionBoltHead"));
        }
        if (request.getParameter("diametrHead_mm") != null) {
            dataForCalc.put("diametrHead_mm", request.getParameter("diametrHead_mm"));
        }
        if (request.getParameter("diametrHole_mm") != null) {
            dataForCalc.put("diametrHole_mm", request.getParameter("diametrHole_mm"));
        }

            int powerMaxForMaterial_N = momentService.powerMaxForMaterial_N(Integer.parseInt(dataForCalc.get("limateStrength_Mpa"))
                    , Double.parseDouble(dataForCalc.get("diametrThread_mm"))
                    , Double.parseDouble(dataForCalc.get("middleDiamThread_mm"))
                    , Double.parseDouble(dataForCalc.get("k_threadDepth"))
                    , Double.parseDouble(dataForCalc.get("safetyFactor")));

        dataForCalc.put("powerMaxForMaterial_N", String.valueOf(powerMaxForMaterial_N));

        double momentKellerman_NM = momentService.momentKellerman_NM( powerMaxForMaterial_N
                ,Double.parseDouble(dataForCalc.get("diametrThread_mm"))
                ,Double.parseDouble(dataForCalc.get("stepThread_mm"))
                ,Double.parseDouble(dataForCalc.get("coefficientOfFrictionThread"))
                ,Double.parseDouble(dataForCalc.get("coefficientOfFrictionBoltHead"))
                ,Double.parseDouble(dataForCalc.get("diametrHead_mm"))
                ,Double.parseDouble(dataForCalc.get("diametrHole_mm")));

        dataForCalc.put("momentKellerman_NM", String.valueOf(momentKellerman_NM));

/**
 * обнуление всех кукиес cookies для материала
 */
//        for (Materals_db mat: allMaterals_dbs){
//            Cookie oldCookie = new Cookie("id" + String.valueOf(mat.getId()), "0");
//            response.addCookie(oldCookie);
//        }
        Map<String, Cookie> newCookies = new HashMap<>();

            for (Cookie cookie : cookies) {
                cookie.setValue("0");
                cookie.setMaxAge(0);
                newCookies.put(cookie.getName(), cookie);
//
            }
/**
 * Установка новых cookies для отображения материала на странице
 */
            for (Materals_db mat : materals_dbs) {
                Cookie cookie = new Cookie("id" + String.valueOf(mat.getId()), "1");
                newCookies.put(cookie.getName(), cookie);
//
            }

        /**
         * Установка куков для резьбы
         */
        newCookies.put("ftreadId", new Cookie("ftreadId",String.valueOf(threadСurrent.getId())));

        /**
         * Установка куков для вычислений силы и момента
             */
//            for (Map.Entry<String, String> entry : dataForCalc.entrySet()) {
//                Cookie cookie = new Cookie(entry.getKey(), entry.getValue());
//                newCookies.put(cookie.getName(), cookie);

//            }
        response.addCookie(new Cookie("her2", "her2"));


        Collection<Cookie> collectionCookies = newCookies.values();
        ArrayList<Cookie> arrayListCookies = new ArrayList<>(collectionCookies);


        Cookie[] newCookiesResponce = arrayListCookies.toArray(new Cookie[0]);

        for (Cookie cookie: newCookiesResponce){
            response.addCookie(cookie);
        }


        model.addAttribute("dataForCalc", dataForCalc);


            return "moment_page_1.html";
        }


        @GetMapping("/moment_page_1/{id}")
        public String viewOneMaterial ( @PathVariable int id, HttpServletRequest request, Model model){
            System.out.println("Id material" + id);
            Materals_db materals_db = materals_db_dao.materialsById(id);
            List<Moments_db> moments_dbs = materals_db.getMoments_db();

            for (Moments_db momentN : moments_dbs) {
                System.out.println("момент" + momentN.getMoments_nm());
            }

            model.addAttribute("material", materals_db);
            model.addAttribute("moments", moments_dbs);

            return "OneMaterial.html";
        }

        /**
         на вычисление момментов и силы по параметрам
         * @return загружает начальную страницу
         */
        @GetMapping("/moment_calc")
        public String momentCalc (Model model, HttpServletRequest request, HttpServletResponse response){

//            Map<String, String> dataForCalc = new HashMap<>();
//            dataForCalc.put("limateStrength_Mpa", request.getParameter("limateStrength_Mpa"));
//            dataForCalc.put("diametrThread_mm", request.getParameter("diametrThread_mm"));
//            dataForCalc.put("middleDiamThread_mm", request.getParameter("middleDiamThread_mm"));
//            dataForCalc.put("k_threadDepth", request.getParameter("k_threadDepth"));
//            dataForCalc.put("safetyFactor", request.getParameter("safetyFactor"));
//
//            dataForCalc.put("stepThread_mm", request.getParameter("stepThread_mm"));
//            dataForCalc.put("coefficientOfFrictionThread", request.getParameter("coefficientOfFrictionThread"));
//            dataForCalc.put("coefficientOfFrictionBoltHead", request.getParameter("coefficientOfFrictionBoltHead"));
//            dataForCalc.put("diametrHead_mm", request.getParameter("diametrHead_mm"));
////            dataForCalc.put("momentKellerman_NM", request.getParameter("momentKellerman_NM"));
//
//
//
//
//            int powerMaxForMaterial_N = momentService.powerMaxForMaterial_N(Integer.parseInt(dataForCalc.get("limateStrength_Mpa"))
//                    , Double.parseDouble(dataForCalc.get("diametrThread_mm"))
//                    , Double.parseDouble(dataForCalc.get("middleDiamThread_mm"))
//                    , Double.parseDouble(dataForCalc.get("k_threadDepth"))
//                    , Double.parseDouble(dataForCalc.get("safetyFactor")));
//
//            /**
//             * Запись куков для вычислений силы и момента
//             */
//            for (Map.Entry<String, String> entry : dataForCalc.entrySet()) {
//                Cookie cookie = new Cookie(entry.getKey(), entry.getValue());
//                response.addCookie(cookie);
//            }
//            Cookie cookie = new Cookie("powerMaxForMaterial_N", String.valueOf(powerMaxForMaterial_N));
//            response.addCookie(cookie);
//
            response.addCookie(new Cookie("her", "her"));
//

//        model.addAttribute("powerMaxForMaterial_N",powerMaxForMaterial_N);
//
//        return "redirect:/moment_page_1";
//            return "moment_page_1.html";
            return "include:/moment_page_1";
        }
    }
