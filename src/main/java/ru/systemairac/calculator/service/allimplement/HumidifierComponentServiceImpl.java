package ru.systemairac.calculator.service.allimplement;

import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.service.allinterface.HumidifierComponentService;

import java.util.List;

@Service
public class HumidifierComponentServiceImpl implements HumidifierComponentService {

    @Override
    public List<HumidifierComponent> getAllComponent() {
        return null;
    }

    @Override
    public HumidifierComponent findById(Long id) {
        return null;
    }

    @Override
    public void save(HumidifierComponent humidifierComponent) {

    }

    @Override
    public void deleteById(Long id) {

    }
}