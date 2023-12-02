package net.torrydev.ecombackend.config;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import net.torrydev.ecombackend.model.Product;
import net.torrydev.ecombackend.model.ProductCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
 Configuration to disable specific http methods being performed on
 certain entity (i.e. Product and Product-Category)
 */
@Configuration
public class ReadOnlyDataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    public ReadOnlyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] unSupportedActions = {HttpMethod.DELETE, HttpMethod.POST, HttpMethod.PUT};

        // Only Http Get is allowed on Product class
        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(unSupportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unSupportedActions)));

        // Only Http Get is allowed on ProductCategory class
        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(unSupportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unSupportedActions)));

        // Expose Ids for entities
        exposeEntityIds(config);
    }


    private void exposeEntityIds(RepositoryRestConfiguration config) {
        // Get all entities
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        
        // Create an array of all entities
        List<Class> entityClasses = new ArrayList<>();
        for (EntityType entityType: entities) {
            entityClasses.add(entityType.getJavaType());
        }

        // Expose the entity ids for the array of entity types
        Class[] domainTypes = entityClasses.toArray(entityClasses.toArray(new Class[0]));
        config.exposeIdsFor(domainTypes);
    }
}
