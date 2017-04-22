import core.Camera;
import core.Car;
import core.Police;
import core.WeightMeter;

public class RoadController {
    public static double passengerCarMaxWeight = 3500.0; // kg
    public static int passengerCarMaxHeight = 2000; // mm
    public static int controllerMaxHeight = 3500; // mm
    public static int passengerCarPrice = 100; // RUB
    public static int cargoCarPrice = 250; // RUB
    public static int vehicleAdditionalPrice = 200; // RUB


    public static int maxOncomingSpeed = 60; // km/h
    public static int speedFineGrade = 20; // km/h
    public static int finePerGrade = 500; // RUB
    public static int criminalSpeed = 160; // km/h

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Car car = Camera.getNextCar();
            System.out.println(car);
            System.out.println("Скорость: " + Camera.getCarSpeed(car) + " км/ч");

            for (String criminalNumber : Police.getCriminalNumbers()) {
                String carNumber = car.getNumber();
                if (carNumber.equals(criminalNumber) | (carNumber.equalsIgnoreCase(criminalNumber))) { // дополняем условие спец.машиной
                    Police.call("автомобиль нарушителя с номером " + carNumber);
                    blockWay("не двигайтесь с места! За вами уже выехали!");
                    break;
                }
            }
            if (Police.wasCalled()) {
                continue;
            }

            int carHeight = car.getHeight();
            int price = 0;
            if (carHeight > controllerMaxHeight) {
                blockWay("высота вашего ТС превышает высоту пропускного пункта!");
                continue;
            } else if (car.isSpecial()) {
                openWay();
                continue;
            } else if (carHeight > passengerCarMaxHeight) {
                double weight = WeightMeter.getWeight(car);
                if (weight > passengerCarMaxWeight) {
                    price = cargoCarPrice; // исправление
                    if (car.hasVehicle()) {
                        price = price + vehicleAdditionalPrice;
                    }
                } else {
                    price = passengerCarPrice; // исправление
                }
            } else {
                price = passengerCarPrice;
            }

            /**
             * Проверка скорости подъезда и выставление штрафа
             */
            int carSpeed = Camera.getCarSpeed(car);
            if (carSpeed > criminalSpeed) {
                Police.call("cкорость автомобиля - " + carSpeed + " км/ч, номер - " + car.getNumber());
                blockWay("вы значительно превысили скорость. Ожидайте полицию!");
                continue;
            } else if (carSpeed >= maxOncomingSpeed) // исправлено
            {
                int overSpeed = carSpeed - maxOncomingSpeed;
                int totalFine = finePerGrade * (1 + overSpeed / speedFineGrade);
                System.out.println("Вы превысили скорость! Штраф: " + totalFine + " руб.");
                price = price + totalFine;
            }

            /**
             * Отображение суммы к оплате
             */
            System.out.println("Общая сумма к оплате: " + price + " руб.");
        }

    }

    /**
     * Открытие шлагбаума
     */
    public static void openWay() {
        System.out.println("Шлагбаум открывается... Счастливого пути!");
    }

    public static void blockWay(String reason) {
        System.out.println("Проезд невозможен: " + reason);
    }
}