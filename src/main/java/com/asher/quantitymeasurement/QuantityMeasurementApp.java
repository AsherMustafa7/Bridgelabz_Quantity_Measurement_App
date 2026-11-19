package com.asher.quantitymeasurement;

import com.asher.quantitymeasurement.dto.QuantityDTO;
import com.asher.quantitymeasurement.facade.QuantityMeasurementFacade;
import com.asher.quantitymeasurement.factory.QuantityMeasurementFactory;
import com.asher.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.asher.quantitymeasurement.repository.IQuantityMeasurementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class QuantityMeasurementApp {

        private static final Logger logger =
        LoggerFactory.getLogger(QuantityMeasurementApp.class);
    public static boolean demonstrateEquality(
            Quantity<?> first,
            Quantity<?> second) {

        return first != null && first.equals(second);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateConversion(
            Quantity<U> quantity,
            U targetUnit) {

        if (quantity == null) {
            throw new IllegalArgumentException(
                    "Measurement cannot be null.");
        }

        return quantity.convertTo(targetUnit);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(
            Quantity<U> first,
            Quantity<U> second,
            U targetUnit) {

        if (first == null) {
            throw new IllegalArgumentException(
                    "First measurement cannot be null.");
        }

        return first.add(second, targetUnit);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(
            Quantity<U> first,
            Quantity<U> second,
            U targetUnit) {

        if (first == null) {
            throw new IllegalArgumentException(
                    "First measurement cannot be null.");
        }

        return first.subtract(second, targetUnit);
    }

    public static <U extends IMeasurable> double demonstrateDivision(
            Quantity<U> first,
            Quantity<U> second) {

        if (first == null) {
            throw new IllegalArgumentException(
                    "First measurement cannot be null.");
        }

        return first.divide(second);
    }

    private static QuantityDTO createDTO(
        double value,
        String unit,
        String measurementType) {

    return new QuantityDTO(value, unit, measurementType);
}

    public static void main(String[] args) {

    IQuantityMeasurementRepository repository =
            QuantityMeasurementFactory.createRepository();

    QuantityMeasurementFacade facade =
            QuantityMeasurementFactory.createFacade(repository);

    Runtime.getRuntime().addShutdownHook(new Thread(() ->
            QuantityMeasurementFactory.closeResources(repository)));

    logger.info("Quantity Measurement Application started.");
    logger.info("Repository: {}", repository.getClass().getSimpleName());

    System.out.println("Quantity Measurement Application");
    System.out.println("--------------------------------");

    QuantityDTO length1 =
            createDTO(10.0, "FEET", "LENGTH");

    QuantityDTO length2 =
            createDTO(6.0, "INCHES", "LENGTH");

    System.out.println("\nLENGTH");
    System.out.println(facade.compare(length1, length2));
    System.out.println(facade.convert(length1, "INCHES"));
    System.out.println(facade.add(length1, length2, "FEET"));
    System.out.println(facade.subtract(length1, length2, "FEET"));
    System.out.println(facade.divide(
            createDTO(24.0, "INCHES", "LENGTH"),
            createDTO(2.0, "FEET", "LENGTH")));

    QuantityDTO weight1 =
            createDTO(10.0, "KILOGRAM", "WEIGHT");

    QuantityDTO weight2 =
            createDTO(5000.0, "GRAM", "WEIGHT");

    System.out.println("\nWEIGHT");
    System.out.println(facade.compare(weight1, weight2));
    System.out.println(facade.convert(weight1, "GRAM"));
    System.out.println(facade.add(weight1, weight2, "KILOGRAM"));
    System.out.println(facade.subtract(weight1, weight2, "KILOGRAM"));
    System.out.println(facade.divide(weight1, weight2));

    QuantityDTO volume1 =
            createDTO(5.0, "LITRE", "VOLUME");

    QuantityDTO volume2 =
            createDTO(500.0, "MILLILITRE", "VOLUME");

    System.out.println("\nVOLUME");
    System.out.println(facade.compare(volume1, volume2));
    System.out.println(facade.convert(volume1, "MILLILITRE"));
    System.out.println(facade.add(volume1, volume2, "LITRE"));
    System.out.println(facade.subtract(volume1, volume2, "LITRE"));
    System.out.println(facade.divide(volume1,
            createDTO(2.5, "LITRE", "VOLUME")));

    QuantityDTO temperature =
            createDTO(25.0, "CELSIUS", "TEMPERATURE");

    System.out.println("\nTEMPERATURE");
    System.out.println(facade.convert(temperature, "FAHRENHEIT"));

    System.out.println("\nDATABASE HISTORY");
    repository.getAllMeasurements().forEach(entity ->
            logger.info("{} | {} | {}",
                    entity.getOperation(),
                    entity.getThisMeasurementType(),
                    entity.isError()
                            ? entity.getErrorMessage()
                            : entity.getResultString() != null
                                    ? entity.getResultString()
                                    : entity.getResultValue()));

    logger.info("Total stored measurements: {}",
            repository.getTotalCount());

    logger.info("Connection pool: {}",
            repository.getPoolStatistics());
}
}