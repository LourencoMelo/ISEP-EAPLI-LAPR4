package eapli.base.dashboard.application;

import eapli.base.dashboard.domain.HttpServer;
import eapli.base.warehousemanagement.Services.FindAllAGVService;

public class ShowDashboardController {

    private final FindAllAGVService findAllAGVService = new FindAllAGVService();

    public void showDashboard() {
        HttpServer server = new HttpServer(findAllAGVService.findAll());
        server.changeController(this);
        server.start();
    }
}
