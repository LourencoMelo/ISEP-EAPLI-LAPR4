package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.customermanagement.application.RegisterCustomerController;
import eapli.base.customermanagement.domain.Address;
import eapli.base.customermanagement.domain.Customer;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordermanagement.application.ChangerOrderToDispatchedController;
import eapli.base.ordermanagement.application.CreateOrderForClientController;
import eapli.base.ordermanagement.domain.Order;
import eapli.base.ordermanagement.domain.PaymentMethod;
import eapli.base.ordermanagement.domain.ShipmentMethod;
import eapli.base.ordermanagement.repositories.OrderRepository;
import eapli.base.productmanagement.application.ListProductController;
import eapli.base.productmanagement.domain.Cash;
import eapli.base.productmanagement.domain.Product;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OrderBootstrapper implements Action {

    private final ListProductController catalogController = new ListProductController();
    final CreateOrderForClientController orderController = new CreateOrderForClientController();
    final RegisterCustomerController registerCustomerController = new RegisterCustomerController();
    private final ChangerOrderToDispatchedController changerOrderToDispatchedController = new ChangerOrderToDispatchedController();
    private final OrderRepository orderRepository = PersistenceContext.repositories().orders();

    @Override
    public boolean execute() {
        createOrders();

        System.out.println("Order Bootstrap done.");

        return true;
    }

    private void createOrders() {
        Map<Product, Integer> map1 = new HashMap<>();
        Map<Product, Integer> map2 = new HashMap<>();
        Map<Product, Integer> map3 = new HashMap<>();
        //System.out.println(catalogController.activeProducts());
        Optional<Product> product = catalogController.findById(9L);
        final int quantityMap1 = 4;
        if (product.isPresent()) {
            map1.put(product.get(), quantityMap1);
        } else product.ifPresent(value -> map1.put(value, quantityMap1));

        product = catalogController.findById(10L);
        final int quantityMap1_2 = 1;
        if (product.isPresent()) {
            map1.put(product.get(), quantityMap1_2);
        } else product.ifPresent(value -> map1.put(value, quantityMap1_2));

        product = catalogController.findById(11L);
        final int quantityMap2 = 3;
        if (product.isPresent()) {
            map2.put(product.get(), quantityMap2);
        } else product.ifPresent(value -> map2.put(value, quantityMap2));

        product = catalogController.findById(12L);
        final int quantityMap3 = 3;
        if (product.isPresent()) {
            map3.put(product.get(), quantityMap3);
        } else product.ifPresent(value -> map3.put(value, quantityMap3));

        Address addressBilling = new Address("Street x", 63, "4460-392", "Porto", "Portugal");
        Address addressDelivering = new Address("Street y", 33, "4460-440", "Porto", "Portugal");
        PaymentMethod paymentMethod = new PaymentMethod("Card");
        ShipmentMethod shipmentMethod = new ShipmentMethod("Car", new Cash(40, null));
        Customer customer = registerCustomerController.registerCustomer("Jose", "Soares", "jose@gmail.com", "Male", 911108523L, "123432234", 13, 2, 2001, null);
        Customer customer2 = registerCustomerController.registerCustomer("Fabio", "Soares", "fabio@gmail.com", "Male", 911108312L, "432234654", 13, 2, 1990, null);
        Customer customer3 = registerCustomerController.registerCustomer("Adriano", "Soares", "adriano@gmail.com", "Male", 911308312L, "234856234", 13, 2, 1960, null);


        Optional<Order> order1 = registerOrder(map1, addressBilling, addressDelivering, paymentMethod, shipmentMethod, "method", null, "comment", customer);
        Optional<Order> order2 = registerOrder(map2, addressBilling, addressDelivering, paymentMethod, shipmentMethod, "method", null, "comment", customer2);
        Optional<Order> order3 = registerOrder(map3, addressBilling, addressDelivering, paymentMethod, shipmentMethod, "method", null, "comment", customer3);

        order1.ifPresent(this::changeToPrepared);
        order2.ifPresent(this::changeToPrepared);
        order3.ifPresent(this::changeToPrepared);
    }

    private Optional<Order> registerOrder(final Map<Product, Integer> products, final Address billing, final Address delivering, final PaymentMethod paymentMethod,
                                          final ShipmentMethod shipmentMethod, final String method, final Calendar interactionDate, final String comment, final Customer customer) {

        try {
            return Optional.of(
                    orderController.registerOrder(products, billing, delivering, paymentMethod, shipmentMethod, method, interactionDate, comment, customer));
        } catch (final IntegrityViolationException | ConcurrencyException
                | TransactionSystemException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated object
            return Optional.empty();
        }

    }

    public String changeToPrepared(Order order) {
        try {
            order.isPrepared();
            orderRepository.save(order);
            return "Succesfull";
        } catch (Exception e) {
            return "Error dispatching order";
        }
    }
}