package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Car;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CarDaoImpl implements CarDao{
    private List<Car>listOfCars;
    {
        listOfCars = new ArrayList<>();
        listOfCars.add(new Car("brand1", "model1", 1));
        listOfCars.add(new Car("brand2", "model2", 2));
        listOfCars.add(new Car("brand3", "model3", 3));
        listOfCars.add(new Car("brand4", "model4", 4));
        listOfCars.add(new Car("brand5", "model5", 5));
    }

    @Override
    public List<Car> getCars(int count) {
        if (count >= listOfCars.size() || count < 0) {
            return listOfCars;
        } else {
            return listOfCars.stream().limit(count).toList();
        }
    }
}
