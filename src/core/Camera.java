package core;

import java.util.HashMap;

public class Camera {
    private static int Height = 1000;
    private static int Weight = 600;
    public static HashMap<String, Integer> carsSpeed = new HashMap<>();

    public static Car getNextCar() {
        String randomNumber = Double.toString(Math.random()).substring(2, 5);
        int randomHeight = (int) (getHeight() + 3500 * Math.random());
        double randomWeight = getWeight() + getHeight()*10 * Math.random();
        Car car = new Car(randomNumber, randomHeight, randomWeight, Math.random() > 0.5);
        if (Math.random() < 0.15) {
            car.setIsSpecial();
        }
        Police.resetCalled();

        return car;
    }

    public static int getCarSpeed(Car car) {
        String carNumber = car.getNumber();
        if (!carsSpeed.containsKey(carNumber)) {
            carsSpeed.put(carNumber, (int) (180 * Math.random()));
        }
        return carsSpeed.get(carNumber);
    }

    public static int getHeight() {
        return Height;
    }

    public static int getWeight() {
        return Weight;
    }
}