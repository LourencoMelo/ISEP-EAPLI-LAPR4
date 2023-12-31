package eapli.base.app.backoffice.console.presentation.Products.UI;

import eapli.base.app.backoffice.console.presentation.Products.Printer.ProductPrinter;
import eapli.base.productmanagement.application.ListProductController;
import eapli.base.productmanagement.domain.Product;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

public class ListProductUI extends AbstractListUI<Product> {

    private final ListProductController theController = new ListProductController();

    @Override
    protected Iterable<Product> elements() {
        return this.theController.activeProducts();
    }

    @Override
    protected Visitor<Product> elementPrinter() {
        return new ProductPrinter();
    }

    @Override
    protected String elementName() {
        return "Product";
    }

    @Override
    protected String listHeader() {
        return String.format("%-30s%-25s%-25s%-10s%-20s%-20s", "Name", "Category","Super Category", "Price With Taxes", "Brand", "Internal Code");
    }

    @Override
    protected String emptyMessage() {
        return "No data.";
    }

    @Override
    public String headline() {
        return "List products";
    }

}
