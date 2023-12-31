package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.customermanagement.domain.Address;
import eapli.base.customermanagement.domain.Customer;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordermanagement.domain.Order;
import eapli.base.ordermanagement.domain.PaymentMethod;
import eapli.base.ordermanagement.domain.ShipmentMethod;
import eapli.base.productmanagement.domain.Product;
import eapli.base.warehousemanagement.application.ConfigureAGVController;
import eapli.base.warehousemanagement.domain.agv.AGV;
import eapli.base.warehousemanagement.repositories.AGVRepository;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.TransactionSystemException;

import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

public class AGVBootstrapper implements Action {

    ConfigureAGVController controller = new ConfigureAGVController();

    @Override
    public boolean execute() {

        registerAGV("agv-1", "agv number 1", "weak", 25.00, 30.00, 1, 3,
                120, "D1");
        registerAGV("agv-2","agv number 2","super",200.00,50000.00,1,13,180,"D2");
        registerAGV("agv-3","agv number 3","super",200.00,50000.00,1,15,180,"D3");
        registerAGV("agv-4","agv number 4","mega",20000.00,5000000.00,20,4,180,"D4");

        System.out.println("AGV Bootstrapp done.");
        return true;
    }

    private Optional<AGV> registerAGV(final String agvId, final String description, final String model, final double maxWeight,
                                      final double maxVolume, int x, int y, int autonomyMin, String agvDockId) {
        try {
            return Optional.of(controller.configureAGV(agvId, description, model, maxWeight, maxVolume, x, y,
                    autonomyMin, agvDockId));
        } catch (final IntegrityViolationException | ConcurrencyException
                | TransactionSystemException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated object
            return Optional.empty();
        }

    }
}
