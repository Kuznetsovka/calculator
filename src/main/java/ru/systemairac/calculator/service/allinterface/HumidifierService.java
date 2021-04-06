package ru.systemairac.calculator.service.allinterface;

import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.EnumVoltageType;

import java.util.HashMap;
import java.util.List;

public interface HumidifierService {
    List<Humidifier> findHumidifiersByIds(List<Long> ids);

    List<Humidifier> findHumidifiers(double capacity, EnumHumidifierType humidifierType, EnumVoltageType voltage);

    List<HumidifierDto> findDtoHumidifiers(double power, EnumHumidifierType humidifier, EnumVoltageType voltage);

    HumidifierDto findById(Long id);

    List<HumidifierDto> getAll();

    void save(HumidifierDto humidifierDto);

    void save(Humidifier humidifier);

    void saveAll(List<Humidifier> humidifier);

    void deleteById(Long id);

    Humidifier findHumidifierById(Long id);

    HashMap<Long, Integer> getAllDiameters(List<Humidifier> humidifiers);
}
