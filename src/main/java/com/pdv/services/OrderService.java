package com.pdv.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.pdv.domain.entities.bo.OrderBO;
import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.entities.enums.EnumOrderStatus;
import com.pdv.domain.entities.enums.EnumProvider;
import com.pdv.domain.usecases.order.CreateOrderUseCase;
import com.pdv.domain.usecases.order.FindAllOrderAcquirerUseCase;
import com.pdv.domain.usecases.order.FindAllOrderUseCase;
import com.pdv.domain.usecases.order.FindOrderAcquirerUseCase;
import com.pdv.domain.usecases.order.FindOrderUseCase;
import com.pdv.domain.utils.DateUtils;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.presentation.dto.FeeDTO;
import com.pdv.presentation.dto.InfoProfessionalDTO;
import com.pdv.presentation.dto.OrderDTO;
import com.pdv.presentation.dto.OrderFeeDTO;
import com.pdv.presentation.dto.OrderItemDTO;
import com.pdv.presentation.dto.OrdersDTO;
import com.pdv.presentation.dto.PaymentDTO;
import com.pdv.presentation.dto.ProfessionalDTO;
import com.pdv.presentation.dto.input.OrderInputDTO;
import com.pdv.presentation.mapper.OrderDomainMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class OrderService extends AbstractService {

    private static final String DEFAULT_FEE_ID = "f5086e43-b820-476c-ae81-aff49bb2f44d";
    private static final Set<String> CASH_PAYMENT_TYPES = Set.of("CASH", "DINHEIRO", "MONEY");
    private static final Set<String> CARD_PAYMENT_TYPES = Set.of("CARD", "CARTAO", "CREDIT", "DEBIT", "CREDITO",
            "DEBITO", "MULTIBANCO");

    public OrderDTO create(OrderInputDTO order) {
        OrderDTO orderDTO = new OrderDTO().toDTO(order);

        for (EnumProvider provider : providersInvoce()) {
            orderDTO.setProvider(provider.getKey());
            validateOrder.validate(orderDTO);
            preProcessOrder(orderDTO);
            saveInternally(orderDTO);
        }

        return orderDTO;
    }

    public OrderDTO findById(String id, String provider) {
        if (provider != null) {
            FindOrderAcquirerUseCase useCase = new FindOrderAcquirerUseCase(acquirerFindOrder());
            EnumProvider enumProvider = EnumProvider.parseByKey(provider);
            return useCase.execute(Map.of("id", id, "provider", enumProvider));
        }
        return new FindOrderUseCase(orderRepository).execute(id);
    }

    public OrdersDTO findAllOrdersInternal(String dateStart, String dateFinal, String provider, Integer page,
            Integer size,
            String professionalId) {

        LocalDateTime dateTimeStart = DateUtils.parseDateTime(dateStart);
        LocalDateTime dateTimeEnd = DateUtils.parseDateTime(dateFinal);
        ProfessionalDTO professional = professonalService.findById(professionalId);

        OrdersDTO orders = professional.isAdmin()
                ? new FindAllOrderUseCase(orderRepository).execute(dateTimeStart, dateTimeEnd, provider, page, size,
                        null)
                : new FindAllOrderUseCase(orderRepository).execute(dateTimeStart, dateTimeEnd, provider, page, size,
                        professionalId);

        return orders != null && orders.orders() != null && !orders.orders().isEmpty()
                ? orders
                : new OrdersDTO(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                        BigDecimal.ZERO, new ArrayList<>(), new ArrayList<>());
    }

    @Transactional
    public OrderDTO saveInternally(OrderDTO order) {
        return new CreateOrderUseCase(orderRepository).execute(order);
    }

    public void preProcessOrder(OrderDTO order) {
        findParamsForOrder(order);
        order.setStatus(EnumOrderStatus.PENDING);
    }

    public void findParamsForOrder(OrderDTO order) {
        defineFees(order);
        defineItems(order.getItems());
        defineProfessional(order);
        defineFinance(order, order.getProfessional());
        defineCustomer(order);
    }

    private void defineCustomer(OrderDTO order) {
        if (order.getCustomer() != null && order.getCustomer().getDocument() != null) {
            var customer = Optional.ofNullable(customerService.find(order.getCustomer().getDocument(), null))
                    .orElseGet(() -> customerService.create(order.getCustomer()));

            order.setCustomer(customer);
        }
    }

    public void defineFees(OrderDTO order) {
        List<OrderFeeDTO> fees = order.getFee();

        if (fees == null || fees.isEmpty()) {
            FeeDTO defaultFee = feeService.findAll(null, DEFAULT_FEE_ID).get(0);
            OrderFeeDTO orderFee = new OrderFeeDTO();
            order.setFee(List.of(orderFee));
            orderFee.setFee(defaultFee);
        } else {
            fees.getFirst().getFee().setId(DEFAULT_FEE_ID);
        }
    }

    public void defineItems(List<OrderItemDTO> items) {
        try {
            for (OrderItemDTO item : items) {
                if (item.getProduct().getId() != null) {
                    item.setProduct(productService.findAll(null, item.getProduct().getId()).get(0));
                } else if (item.getProduct().getAcquirerId() != null) {
                    item.setProduct(productService.findByAcquirerId(item.getProduct().getAcquirerId(), true));
                }
            }
        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, "Error defining items");
        }
    }

    public void defineProfessional(OrderDTO order) {

        try {
            if (order.getProfessional() != null) {
                return;
            }

            if (order.getProfessionalAcquirerId() != null) {
                var professional = professonalService.findByAcquirerId(order.getProfessionalAcquirerId());
                order.setProfessional(professional);
                return;
            }

            var professional = professonalService.findById(order.getProfessionalId());
            order.setProfessional(professional);

        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, "Error defining professional");
        }
    }

    public void defineFinance(OrderDTO order, ProfessionalDTO professional) {

        if (order == null || professional == null) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, "Order and Professional cannot be null");
        }

        if (order.isFinance()) {
            return;
        }

        if (order.getCustomer() != null) {
            order.setFinance(true);
            return;
        }

        if (professional.isAdmin()) {
            order.setFinance(hasCardPayment(order.getPayments()));
        } else {
            order.setFinance(true);
        }
    }

    private boolean hasCardPayment(List<PaymentDTO> payments) {
        if (payments == null) {
            return false;
        }

        for (PaymentDTO payment : payments) {
            if (isCardPaymentOptimized(payment.getPaymentType())) {
                return true;
            }
        }
        return false;
    }

    public OrdersDTO findOrdersAcquirer(String dateStart, String dateFinal, String provider, String size,
            String until) {

        validateProvider(provider);

        var params = buildParams(dateStart, dateFinal, size, until, provider);

        var orders = new FindAllOrderAcquirerUseCase(acquirerFindOrder()).execute(params);

        orders.orders().forEach(order -> findParamsForOrder(order));

        return orders;
    }

    public OrdersDTO findAllOrders(String dateStart, String dateFinal, String provider, String size,
            String until, String professionalId) {

        Integer untilInt = until != null ? Integer.parseInt(until) : 0;
        Integer sizeInt = size != null ? Integer.parseInt(size) : 1000;

        OrdersDTO orders = findAllOrdersInternal(dateStart, dateFinal, provider, untilInt, sizeInt, professionalId);

        if (orders.orders() == null || orders.orders().isEmpty()) {
            return orders;
        }

        for (OrderDTO order : orders.orders()) {
            findParamsForOrder(order);
        }

        ProcessingResult result = processOrdersOptimized(orders);
        return orders.withUpdatedData(result.totalDiscount, result.professionalInfos);
    }

    private static class ProcessingResult {
        List<InfoProfessionalDTO> professionalInfos;
        BigDecimal totalDiscount;

        ProcessingResult(List<InfoProfessionalDTO> infos, BigDecimal discount) {
            this.professionalInfos = infos;
            this.totalDiscount = discount;
        }
    }

    private ProcessingResult processOrdersOptimized(OrdersDTO orders) {
        Map<ProfessionalDTO, ProfessionalTotals> professionalTotalsMap = new HashMap<>();
        BigDecimal totalDiscount = BigDecimal.ZERO;

        for (OrderDTO order : orders.orders()) {
            if (order.getDiscount() != null && order.getDiscount().getAmount() != null) {
                totalDiscount = totalDiscount.add(order.getDiscount().getAmount());
            }

            ProfessionalDTO professional = order.getProfessional();
            if (professional != null) {
                ProfessionalTotals totals = professionalTotalsMap.computeIfAbsent(
                        professional,
                        k -> new ProfessionalTotals());
                totals.addOrder(order);
            }
        }

        List<InfoProfessionalDTO> professionalInfos = new ArrayList<>(professionalTotalsMap.size());

        for (Map.Entry<ProfessionalDTO, ProfessionalTotals> entry : professionalTotalsMap.entrySet()) {
            ProfessionalDTO professional = entry.getKey();
            ProfessionalTotals totals = entry.getValue();

            calcCommissionsForProfessional(professional, totals.orders);

            InfoProfessionalDTO info = createProfessionalInfo(professional, totals);
            professionalInfos.add(info);
        }

        return new ProcessingResult(professionalInfos, totalDiscount);
    }

    private static class ProfessionalTotals {
        List<OrderDTO> orders = new ArrayList<>();
        BigDecimal totalCash = BigDecimal.ZERO;
        BigDecimal totalCard = BigDecimal.ZERO;

        void addOrder(OrderDTO order) {
            orders.add(order);

            List<PaymentDTO> payments = order.getPayments();
            if (payments != null) {
                for (PaymentDTO payment : payments) {
                    if (payment.getAmount() != null) {
                        if (isCashPaymentOptimized(payment.getPaymentType())) {
                            totalCash = totalCash.add(payment.getAmount());
                        } else if (isCardPaymentOptimized(payment.getPaymentType())) {
                            totalCard = totalCard.add(payment.getAmount());
                        }
                    }
                }
            }
        }
    }

    private InfoProfessionalDTO createProfessionalInfo(ProfessionalDTO professional, ProfessionalTotals totals) {
        InfoProfessionalDTO info = new InfoProfessionalDTO();
        info.setName(professional.getName());
        info.setAmount(totals.totalCash.add(totals.totalCard));
        info.setAmountCash(totals.totalCash);
        info.setAmountCard(totals.totalCard);

        BigDecimal totalCommission = BigDecimal.ZERO;
        for (OrderDTO order : totals.orders) {
            if (order.getAmountCommission() != null) {
                totalCommission = totalCommission.add(order.getAmountCommission().setScale(2, RoundingMode.HALF_UP));
            }
        }
        info.setAmountComission(totalCommission);

        return info;
    }

    private void calcCommissionsForProfessional(ProfessionalDTO professional, List<OrderDTO> orders) {
        if (professional.isAdmin()) {
            for (OrderDTO order : orders) {
                order.setAmountCommission(BigDecimal.ZERO);
            }
            return;
        }

        List<OrderBO> ordersBO = new ArrayList<>(orders.size());
        for (OrderDTO order : orders) {
            ordersBO.add(OrderDomainMapper.toBO(order));
        }

        for (int i = 0; i < orders.size(); i++) {
            OrderBO orderBO = ordersBO.get(i);
            OrderDTO orderDTO = orders.get(i);

            orderBO.calcAmountCommission();
            orderDTO.setAmountCommission(orderBO.getAmountCommission().getValue());
        }
    }

    private static boolean isCashPaymentOptimized(String paymentType) {
        return paymentType != null && CASH_PAYMENT_TYPES.contains(paymentType.toUpperCase());
    }

    private static boolean isCardPaymentOptimized(String paymentType) {
        return paymentType != null && CARD_PAYMENT_TYPES.contains(paymentType.toUpperCase());
    }

}