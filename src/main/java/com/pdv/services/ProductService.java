package com.pdv.services;

import java.util.List;
import java.util.Map;

import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.usecases.product.CreateProductUseCase;
import com.pdv.domain.usecases.product.FindProductUseCase;
import com.pdv.domain.usecases.product.FindProductUseCaseOnAcquirer;
import com.pdv.domain.usecases.product.UpdatedProductUseCase;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.presentation.dto.ProductDTO;

import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductService extends AbstractService {

    @Transactional
    public ProductDTO create(ProductDTO product) {
        if (product == null) {
            throw new PdvException(EnumErrorCode.REQUIRED_OBJECT, product);
        }

        return new CreateProductUseCase(productRepository).execute(product);
    }

    public List<ProductDTO> findAll(String type, String id) {
        return new FindProductUseCase(productRepository).execute(type, id, null, true);
    }

    @CacheResult(cacheName = "productAll")
    public List<ProductDTO> findAllOnAcquirer(String provider) {
        validateRequiredObject(provider, "Provider cannot be null");

        if (provider == null) {
            throw new PdvException(EnumErrorCode.REQUIRED_OBJECT, provider);
        }
        List<ProductDTO> response = new FindProductUseCaseOnAcquirer(acquirerFindProduct())
                .execute(Map.of("provider", validateProvider(provider)));

        response.forEach(product -> {
            ProductDTO internalProduct = findByAcquirerId(product.getAcquirerId(), false);
            if (internalProduct != null) {
                handleExistingProduct(product, internalProduct);
            } else {
                handleNewProduct(product);
            }
        });

        return response;
    }

    public ProductDTO findByAcquirerId(String acquirerId, boolean isExeception) throws PdvException {
        validateRequiredObject(acquirerId, "Acquirer ID cannot be null");

        List<ProductDTO> products = new FindProductUseCase(productRepository).execute(null, null, acquirerId,
                isExeception);
        return (products == null || products.isEmpty()) ? null : products.get(0);

    }

    @Transactional
    public ProductDTO updateProduct(ProductDTO product) {
        validateRequiredObject(product, "Product cannot be null");
        return new UpdatedProductUseCase(productRepository).execute(product);
    }

    private void handleExistingProduct(ProductDTO product, ProductDTO internalProduct) {
        product.setId(internalProduct.getId());
    }

    private void handleNewProduct(ProductDTO product) {
        ProductDTO newProductDTO = create(product);
        product.setId(newProductDTO.getId());
    }

}
