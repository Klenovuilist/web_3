//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.stereotype.Component;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.util.Assert;
//import web.Entity.Materals_db;
//import web.Entity.Moments_db;
//import web.dao.Materals_db_DAO;
//import web.dao.Material_db_DAO_impl;
//import web.dao.Moments_db_DAO;
//import web.dao.Moments_db_DAO_impl;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//
//class ControllerTest {
//
//
// SessionFactory sessionFactory;
//
// @Test
// void listMomentIsNotEmpty() {
//
//  Materals_db_DAO materals_db_dao = new Material_db_DAO_impl(sessionFactory);
//
//  List<Materals_db> list = materals_db_dao.getMaterals_db();
//
//// List<Moments_db> moments_dbList = new ArrayList<>();
//// moments_dbList.add(new Moments_db());
////
//// Materals_db materals_db = Materals_db.builder()
////          .limit_strength(120)
////          .id(2)
////          .moments_db(moments_dbList)
////          .materials("SD")
////          .build();
//
//
////  List <Materals_db> list = new ArrayList<>();
////  list.add(materals_db);
//  for (Materals_db mat : list) {
//
//
//  }
//
// }
//}
