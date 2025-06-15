package com.pdv.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;

import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.entities.enums.EnumProvider;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.infra.data.panache.repositories.CustomerRepository;
import com.pdv.infra.data.panache.repositories.EnterpriseRepository;
import com.pdv.infra.data.panache.repositories.FeeRepository;
import com.pdv.infra.data.panache.repositories.OrderRepository;
import com.pdv.infra.data.panache.repositories.ProductRepository;
import com.pdv.infra.data.panache.repositories.ProfessionalRepository;
import com.pdv.infra.restclient.jlpay.repositories.JLpayRepository;
import com.pdv.infra.restclient.vendus.repositories.VendusRepository;
import com.pdv.infra.strategys.interfaces.IStrategyCreateOrder;
import com.pdv.infra.strategys.interfaces.IStrategyFindOrder;
import com.pdv.infra.strategys.interfaces.IStrategyFindProduct;
import com.pdv.infra.strategys.interfaces.IStrategyFindProfessional;
import com.pdv.infra.strategys.providers.VendusStrategyCreateOrder;
import com.pdv.infra.strategys.providers.VendusStrategyFindOrder;
import com.pdv.infra.strategys.providers.VendusStrategyFindProduct;
import com.pdv.infra.strategys.providers.VendusStrategyFindProfessional;
import com.pdv.presentation.controller.templetmethod.OrderValidade;

import jakarta.inject.Inject;

public abstract class AbstractService {
    // repositories

    @Inject
    ProfessionalRepository professionalRepository;
    
    @Inject
    CustomerRepository customerRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    EnterpriseRepository enterpriseRepository;

    @Inject
    FeeRepository feeRepository;

    @Inject
    OrderRepository orderRepository;

    @Inject
    VendusRepository vendusRepository;

    @Inject
    JLpayRepository jLpayRepository;

    // services

    @Inject
    ProfessionalService professonalService;

    @Inject
    OrderService orderService;

    @Inject
    ProductService productService;

    @Inject
    FeeService feeService;

    @Inject
    OrderValidade validateOrder;

    @Inject
    CustomerService customerService;

    // strategies

    @Inject
    VendusStrategyFindProfessional vendusStrategyFindProfessional;

    @Inject
    VendusStrategyFindOrder vendusStrategyFindOrder;

    @Inject
    VendusStrategyCreateOrder vendusStrategyCreateOrder;

    @Inject
    VendusStrategyFindProduct vendusStrategyFindProduct;

    // Utils metohds

    @Inject
    Logger logger;

    protected Map<EnumProvider, IStrategyFindProfessional> acquirerFindProfessional() {
        return Map.of(
                EnumProvider.VENDUS, vendusStrategyFindProfessional);
    }

    protected Map<EnumProvider, IStrategyFindProduct> acquirerFindProduct() {
        return Map.of(
                EnumProvider.VENDUS, vendusStrategyFindProduct);
    }

    protected Map<EnumProvider, IStrategyFindOrder> acquirerFindOrder() {
        return Map.of(
                EnumProvider.VENDUS, vendusStrategyFindOrder);
    }

    protected List<EnumProvider> providers() {
        return List.of(EnumProvider.VENDUS, EnumProvider.STRIPE);
    }

    protected List<EnumProvider> providersInvoce() {
        return List.of(EnumProvider.VENDUS);
    }

    protected Map<EnumProvider, IStrategyCreateOrder> acquirerCreateOrder() {
        return Map.of(
                EnumProvider.VENDUS, vendusStrategyCreateOrder);
    }

    protected void validateFields(List<String> values) {
        if (values == null || values.isEmpty()) {
            throw new PdvException(EnumErrorCode.REQUIRED_FIELD, "params");
        }

        values.forEach(value -> {
            if (value == null || value.isBlank()) {
                throw new PdvException(EnumErrorCode.REQUIRED_FIELD, "param");
            }
        });
    }

    protected Map<String, Object> buildParams(String dateStart, String dateFinal, String size, String until,
            String provider) {
        Map<String, Object> params = new HashMap<>();

        addParamIfNotNullOrEmpty(params, "dateCreate", dateStart);
        addParamIfNotNullOrEmpty(params, "dateEnd", dateFinal);
        addParamIfNotNullOrEmpty(params, "size", size);
        addParamIfNotNullOrEmpty(params, "until", until);
        addParamIfNotNullOrEmpty(params, "provider", EnumProvider.parseByKey(provider));

        return params;
    }

    private void addParamIfNotNullOrEmpty(Map<String, Object> params, String key, Object value) {
        if (value != null && !(value instanceof String && ((String) value).isBlank())) {
            params.put(key, value);
        }
    }

    public EnumProvider validateProvider(String provider) {
        if (provider != null) {
            EnumProvider enumProvider = EnumProvider.parseByKey(provider);
            if (enumProvider == null) {
                throw new PdvException(EnumErrorCode.INVALID_FORMAT, "provider");
            }
            return enumProvider;
        } else {
            return EnumProvider.VENDUS;
        }
    }

    public void validateRequiredObject(Object obj, String message) {
        if (obj == null) {
            throw new PdvException(EnumErrorCode.REQUIRED_OBJECT, message);
        }
    }

}
