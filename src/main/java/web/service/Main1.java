package web.service;

public class Main1 {
    public static void main(String[] args) {

        MomentService momPowercalc = new MomentService();

        System.out.println(momPowercalc.momentKellerman_NM(25230
                        , 9.026
                        ,  1.5
                        , 0.14
                        , 0.14
                        , 15.3
                        , 10.5));


        System.out.println(momPowercalc.powerKellerman_N(47.4159381
                , 9.026
                ,  1.5
                , 0.14
                , 0.14
                , 15.3
                , 10.5));
    }


}
