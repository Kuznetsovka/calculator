package ru.systemairac.calculator.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import ru.systemairac.calculator.domain.TechData;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.myenum.EnumHumidifierType;
import ru.systemairac.calculator.myenum.TypeCylinder;
import ru.systemairac.calculator.myenum.TypeMontage;
import ru.systemairac.calculator.myenum.TypeWater;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TechDataMapperTest {

    private final TechDataMapper mapper = TechDataMapper.MAPPER;
    private final Random random = new Random();

    private TechDataDto fakeTechDataDto() {
        return TechDataDto.builder()
                .airFlow(random.nextInt(300))
                .calcCapacity(random.nextInt(300)) // а это не правда
                .enumHumidifierType(EnumHumidifierType.values()[random.nextInt(EnumHumidifierType.values().length)])
                .humIn(random.nextInt(100))
                .humOut(random.nextInt(100))
                .tempIn(random.nextInt(80) - 40)
                .tempOut(random.nextInt(80) - 40)
                .phase(random.nextInt(2) + 1)
                .typeMontage(TypeMontage.values()[random.nextInt(TypeMontage.values().length)])
                .typeCylinder(TypeCylinder.values()[random.nextInt(TypeCylinder.values().length)])
                .typeWater(TypeWater.values()[random.nextInt(TypeWater.values().length)])
                .voltage(random.nextInt(500))
                .width(random.nextInt(1000))
                .length(random.nextInt(1000))
                .build();
    }

    private TechData fakeTechData() {
        Random random = new Random();
        return TechData.builder()
                .airFlow(random.nextInt(300))
                .calcCapacity(random.nextInt(300)) // а это не правда
                .enumHumidifierType(EnumHumidifierType.values()[random.nextInt(EnumHumidifierType.values().length)])
                .id(random.nextLong())
                .humIn(random.nextInt(100))
                .humOut(random.nextInt(100))
                .tempIn(random.nextInt(80) - 40)
                .tempOut(random.nextInt(80) - 40)
                .phase(random.nextInt(2) + 1)
                .typeMontage(TypeMontage.values()[random.nextInt(TypeMontage.values().length)])
                .typeCylinder(TypeCylinder.values()[random.nextInt(TypeCylinder.values().length)])
                .typeWater(TypeWater.values()[random.nextInt(TypeWater.values().length)])
                .voltage(random.nextInt(500))
                .width(random.nextInt(1000))
                .length(random.nextInt(1000))
                .build();
    }

    @Test
    void toTechData() {
        for (int i = 0; i < 100; i++) {
            TechDataDto techDataDto = fakeTechDataDto();
            TechData techData = mapper.toTechData(techDataDto);
            assertFieldsEqual(techDataDto, techData);
        }
    }

    private void assertFieldsEqual(TechDataDto techDataDto, TechData techData) {
        assertEquals(techDataDto.getAirFlow(), techData.getAirFlow());
        assertEquals(techDataDto.getCalcCapacity(), techData.getCalcCapacity());
        assertEquals(techDataDto.getHumIn(), techData.getHumIn());
        assertEquals(techDataDto.getHumOut(), techData.getHumOut());
        assertEquals(techDataDto.getLength(), techData.getLength());
        assertEquals(techDataDto.getEnumHumidifierType(), techData.getEnumHumidifierType());
        assertEquals(techDataDto.getPhase(), techData.getPhase());
        assertEquals(techDataDto.getTempIn(), techData.getTempIn());
        assertEquals(techDataDto.getTempOut(), techData.getTempOut());
        assertEquals(techDataDto.getTypeCylinder(), techData.getTypeCylinder());
        assertEquals(techDataDto.getTypeMontage(), techData.getTypeMontage());
        assertEquals(techDataDto.getTypeWater(), techData.getTypeWater());
        assertEquals(techDataDto.getVoltage(), techData.getVoltage());
        assertEquals(techDataDto.getWidth(), techData.getWidth());
    }

    @Test
    void fromTechData() {
        for (int i = 0; i < 100; i++) {
            TechData techData = fakeTechData();
            TechDataDto techDataDto = mapper.fromTechData(techData);
            assertFieldsEqual(techDataDto, techData);
        }
    }
}