package com.asher.quantitymeasurement.factory;

import com.asher.quantitymeasurement.controller.QuantityMeasurementController;
import com.asher.quantitymeasurement.facade.QuantityMeasurementFacade;
import com.asher.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.asher.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.asher.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.asher.quantitymeasurement.service.IQuantityMeasurementService;
import com.asher.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.asher.quantitymeasurement.util.ApplicationConfig;

public class QuantityMeasurementFactory {

    private QuantityMeasurementFactory() {
    }

    public static IQuantityMeasurementRepository createRepository() {
        ApplicationConfig config = new ApplicationConfig();

        String repositoryType = config.getRepositoryType();

        if ("database".equalsIgnoreCase(repositoryType)) {
            return new QuantityMeasurementDatabaseRepository();
        }

        if ("cache".equalsIgnoreCase(repositoryType)) {
            return QuantityMeasurementCacheRepository.getInstance();
        }

        throw new IllegalArgumentException(
                "Unsupported repository type: " + repositoryType);
    }

    public static QuantityMeasurementFacade createFacade() {
        return createFacade(createRepository());
    }

    public static QuantityMeasurementFacade createFacade(
            IQuantityMeasurementRepository repository) {

        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(repository);

        QuantityMeasurementController controller =
                new QuantityMeasurementController(service);

        return new QuantityMeasurementFacade(controller);
    }

    public static void closeResources(
            IQuantityMeasurementRepository repository) {

        if (repository != null) {
            repository.releasingResources();
        }
    }
}