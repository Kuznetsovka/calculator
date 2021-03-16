package ru.systemairac.calculator.service;

public class CalculationServiceImpl implements CalculationService {

    @Override
    public double calcPower(double airFlow, PointDto point1, PointDto point2) {
        double averageDensity = (point1.getDensity()+point2.getDensity())/2;
        return airFlow * averageDensity  * (point2.getMoistureContent()-point1.getMoistureContent())/1000;
    }
}
