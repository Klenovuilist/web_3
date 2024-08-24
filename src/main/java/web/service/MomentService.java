package web.service;

import org.springframework.stereotype.Service;

@Service
public class MomentService {

    /**
     * Расчет по формуле Биргера
     */


    public Double momentBirger_NM(int power_N
            , double middleDiamThread_mm
            , double stepThread_mm
            , double coefficientOfFrictionThread
            , double coefficientOfFrictionBoltHead
            , double diametrHead_mm
            , double diametrHole_mm) {

        double middleDiamThread_m = middleDiamThread_mm * 0.001;
        double stepThread_m = stepThread_mm * 0.001;
//    double coefficientOfFrictionThread
//    double coefficientOfFrictionBoltHead
        double diametrHead_m = diametrHead_mm * 0.001;
        double diametrHole_m = diametrHole_mm * 0.001;


        double radFriction_m = (Math.pow(diametrHead_m, 3) - Math.pow(diametrHole_m, 3))
                / 3 * ((Math.pow(diametrHead_m, 2) - Math.pow(diametrHole_m, 2)));

        return power_N * (((stepThread_m + Math.PI * coefficientOfFrictionThread * middleDiamThread_m)
                / 2 * (Math.PI - coefficientOfFrictionThread * stepThread_m / middleDiamThread_m))
                + coefficientOfFrictionBoltHead * radFriction_m);

    }
    /**
     * Расчет по формуле Каллермана Кляйна
     */
    public Double momentKellerman_NM(int power_N
            , double middleDiamThread_mm
            , double stepThread_mm
            , double coefficientOfFrictionThread
            , double coefficientOfFrictionBoltHead
            , double diametrHead_mm
            , double diametrHole_mm) {

        double middleDiamThread_m = middleDiamThread_mm * 0.001;
        double stepThread_m = stepThread_mm * 0.001;
        double diametrHead_m = diametrHead_mm * 0.001;
        double diametrHole_m = diametrHole_mm * 0.001;

        return Math.round((power_N * (0.5 * (stepThread_m + 1.154 * 3.14 * coefficientOfFrictionThread * middleDiamThread_m)
                / (3.14 - 1.154 * coefficientOfFrictionThread * stepThread_m / middleDiamThread_m)
                + coefficientOfFrictionBoltHead * (diametrHead_m + diametrHole_m) * 0.25)) * 10) * 0.1;
    }
    /**
     * Расчет силы по формуле Каллермана Кляйна
     */
    public int powerKellerman_N(double moment_Nm
            , double middleDiamThread_mm
            , double stepThread_mm
            , double coefficientOfFrictionThread
            , double coefficientOfFrictionBoltHead
            , double diametrHead_mm
            , double diametrHole_mm){

        double middleDiamThread_m = middleDiamThread_mm * 0.001;
        double stepThread_m = stepThread_mm * 0.001;
        double diametrHead_m = diametrHead_mm * 0.001;
        double diametrHole_m = diametrHole_mm * 0.001;


        return (int)(moment_Nm / (0.5 * (stepThread_m + 1.154 * 3.14 * coefficientOfFrictionThread * middleDiamThread_m)
                / (3.14 - 1.154 * coefficientOfFrictionThread * stepThread_m / middleDiamThread_m)
                + coefficientOfFrictionBoltHead * (diametrHead_m + diametrHole_m) * 0.25));
    }

    public Double power_N(double moment_NM
            , double middleDiamThread_mm
            , double stepThread_mm
            , double coefficientOfFrictionThread
            , double coefficientOfFrictionBoltHead
            , double diametrHead_mm
            , double diametrHole_mm) {

        double middleDiamThread_m = middleDiamThread_mm * 0.001;
        double stepThread_m = stepThread_mm * 0.001;
//    double coefficientOfFrictionThread
//    double coefficientOfFrictionBoltHead
        double diametrHead_m = diametrHead_mm * 0.001;
        double diametrHole_m = diametrHole_mm * 0.001;


        double radFriction_m = (Math.pow(diametrHead_m, 3) - Math.pow(diametrHole_m, 3))
                / 3 * ((Math.pow(diametrHead_m, 2) - Math.pow(diametrHole_m, 2)));

        return moment_NM / (((stepThread_m + Math.PI * coefficientOfFrictionThread * middleDiamThread_m)
                / 2 * (Math.PI - coefficientOfFrictionThread * stepThread_m / middleDiamThread_m))
                + coefficientOfFrictionBoltHead * radFriction_m);
    }

    public double midleDiametr(double diametrThread){
        return diametrThread * 0.96;
    }
    public int powerMaxForMaterial_N(int ultimateStrength_Mpa
            , double diametrThread_mm
            , double middleDiamThread_mm
            , double k_threadDepth
            , double safetyFactor){
    return (int)(ultimateStrength_Mpa * 1000000 * 3.14 * middleDiamThread_mm * 0.001
            * diametrThread_mm * 0.001 * k_threadDepth / safetyFactor);
    }
    public int strengthByPowerAndDiam(int power_N, double dBolt_mm, double dmidleThread_mm){
        return (int) ((power_N / (3.14 * dmidleThread_mm * 0.001 * dBolt_mm * 0.001 * 1.0)) * 0.000001);
    }

}
