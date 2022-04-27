package eapli.base.productmanagement.application;


import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.domain.AlphanumericCode;
import eapli.base.productmanagement.domain.ProductCategory;
import eapli.base.productmanagement.repositories.ProductCategoryRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.general.domain.model.Description;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

@UseCaseController
public class AddProductCategoryController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductCategoryRepository repository = PersistenceContext.repositories().productCategories();

    public ProductCategory addProductCategory(final String code, final String description) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,
                BaseRoles.SALES_CLERK);

        final ProductCategory newProductCategory = new ProductCategory(AlphanumericCode.valueOf(code), Description.valueOf(description));
        return repository.save(newProductCategory);
    }

}
