package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.eshop.model.Car;

@Repository
public class CarRepository {
    static int id = 0;
    private List<Car> carData = new ArrayList<>();

    public Car create(Car car) {
        if (car.getCarId() == null) {
            UUID uuid = UUID.randomUUID();
            car.setCarId(uuid.toString());
        }
        carData.add(car);
        return car;
    }

    public Iterator<Car> findAll() {
        return carData.iterator();
    }

    public Car findById(String carId) {
        for (Car car : carData) {
            if (car.getCarId().equals(carId)) {
                return car;
            }
        }
        return null;
    }

    public Car update(String carId, Car car) {
        for (int i = 0; i < carData.size(); i++) {
            if (carData.get(i).getCarId().equals(carId)) {
                carData.set(i, car);
                return car;
            }
        }
        return null;
    }

    public void delete(String carId) {
        carData.removeIf(car -> car.getCarId().equals(carId));
    }
}
