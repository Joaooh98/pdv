package com.pdv.infra.restclient.vendus.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.IVendusRepository;
import com.pdv.domain.utils.DateUtils;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.infra.restclient.vendus.RestClientVendus;
import com.pdv.infra.restclient.vendus.dto.VendusItemDTO;
import com.pdv.infra.restclient.vendus.dto.VendusOrderDTO;
import com.pdv.infra.restclient.vendus.dto.VendusOrderDetailsDTO;
import com.pdv.infra.restclient.vendus.dto.VendusOrderResponseDTO;
import com.pdv.infra.restclient.vendus.dto.VendusUserDTO;
import com.pdv.infra.restclient.vendus.mappers.VedusOrderMapper;
import com.pdv.infra.restclient.vendus.mappers.VedusProductMapper;
import com.pdv.infra.restclient.vendus.mappers.VedusUserMapper;
import com.pdv.presentation.dto.OrderDTO;
import com.pdv.presentation.dto.ProductDTO;
import com.pdv.presentation.dto.ProfessionalDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class VendusRepository implements IVendusRepository {

    @Inject
    @RestClient
    RestClientVendus restClientVendus;

    @ConfigProperty(name = "vendus.consult.token")
    String tokenConsult;

    @Inject
    Logger logger;

    public String getEnvironment() {
        return Optional.ofNullable(System.getProperty("quarkus.profile"))
                .orElse("dev");
    }

    @Override
    public OrderDTO create(OrderDTO dto) {
        try {
            String environment = getEnvironment();

            VendusOrderResponseDTO response = restClientVendus.createOrder(
                    VedusOrderMapper.toVendusDto(dto, environment),
                    tokenConsult);

            return VedusOrderMapper.toDto(response);

        } catch (Exception e) {
            logger.warn(e);
            throw new PdvException(EnumErrorCode.COMMUNICATION_ERROR);
        }
    }

    @Override
    public OrderDTO findOrder(Map<String, Object> params) {
        var id = params.get("id").toString();

        try {
            VendusOrderDetailsDTO orderResponse = restClientVendus.findOrderbyId(tokenConsult, id);
            return VedusOrderMapper.toDto(orderResponse);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PdvException(EnumErrorCode.COMMUNICATION_ERROR, "VENDUS", "failed find order by id");
        }

    }

    @Override
    public List<ProfessionalDTO> findAllProfessional(Map<String, Object> params) {
        List<VendusUserDTO> users = new ArrayList<>();

        var clientId = Optional.ofNullable(params.get("clientId"))
                .map(Object::toString)
                .orElseThrow(() -> new PdvException(EnumErrorCode.REQUIRED_FIELD, "clientId"));

        try {
            users = restClientVendus.findUsers(clientId);
        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.COMMUNICATION_ERROR, "VENDUS", "failed to list users");
        }

        return Optional.ofNullable(users)
                .filter(list -> !list.isEmpty())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(VedusUserMapper::toDto)
                .toList();

    }

    @Override
    public List<OrderDTO> findAllOrder(Map<String, Object> params) {
        List<VendusOrderDTO> ordersVendusResponse = fetchAllOrdersFromVendus(params);
        return filterAndMapOrders(ordersVendusResponse, params);
    }

    private List<VendusOrderDTO> fetchAllOrdersFromVendus(Map<String, Object> params) {
        List<VendusOrderDTO> allOrders = new ArrayList<>();
        String dateCreate = (String) params.get("dateCreate");
        String dateEnd = (String) params.get("dateEnd");
        String size = (String) params.get("size");
        String until = (String) params.get("until");

        dateCreate = DateUtils.convertToDateFormat(dateCreate);
        dateEnd = DateUtils.convertToDateFormat(dateEnd);

        try {
            boolean hasMoreData = true;
            while (hasMoreData) {

                try {
                    var ordersPage = restClientVendus.findAllOrder(tokenConsult, dateCreate, dateEnd, size, until);
                    allOrders.addAll(ordersPage);
                    until = String.valueOf(Integer.parseInt(until) + 1);
                } catch (Exception e) {
                    hasMoreData = false;
                }
            }
            return allOrders;
        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.COMMUNICATION_ERROR, "Erro ao acessar o serviço externo.", e);
        }
    }

    private List<OrderDTO> filterAndMapOrders(List<VendusOrderDTO> ordersVendusResponse, Map<String, Object> params) {
        return ordersVendusResponse.stream()
                .filter(order -> order.getNumber().contains("FT"))
                .map(order -> {
                    params.put("id", order.getId());
                    return findOrder(params);
                })
                .filter(order -> order != null)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<ProductDTO> findAllProduct(Map<String, Object> params) {
        List<ProductDTO> products = new ArrayList<>();
        try {
            List<VendusItemDTO> allProduct = restClientVendus.findAllProduct(tokenConsult, "1000", "1");
            List<ProductDTO> productsVendus = allProduct.stream()
                    .map(VedusProductMapper::toDto)
                    .collect(Collectors.toList());
            products.addAll(productsVendus);

        } catch (Exception e) {
            logger.debug(e);
        }

        return products;
    }

    
}
