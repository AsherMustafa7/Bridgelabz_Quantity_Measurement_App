package com.asher.quantitymeasurement.factory;

import com.asher.quantitymeasurement.controller.QuantityMeasurementController;
import com.asher.quantitymeasurement.facade.QuantityMeasurementFacade;
import com.asher.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.asher.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.asher.quantitymeasurement.service.IQuantityMeasurementService;
import com.asher.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementFactory {

    private QuantityMeasurementFactory() {
    }

    public static QuantityMeasurementFacade createFacade() {

        // Repository
        IQuantityMeasurementRepository repository =
                QuantityMeasurementCacheRepository.getInstance();

        // Service
        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(repository);

        // Controller
        QuantityMeasurementController controller =
                new QuantityMeasurementController(service);

        // Facade
        return new QuantityMeasurementFacade(controller);
    }
}