package com.pdv.services;

import java.time.LocalDateTime;

import com.pdv.domain.utils.DateUtils;
import com.pdv.presentation.dto.OrdersDTO;

import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduled.ConcurrentExecution;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JobService extends AbstractService {


    private static final String SIZE = "20";
    private static final String UNTIL = "1";

    private LocalDateTime getStartOfDay() {
        return DateUtils.resetTime(LocalDateTime.now().minusDays(1));
    }

    private LocalDateTime getEndOfDay(Integer days) {
        return getStartOfDay().plusDays(days);
    }

    @Scheduled(cron = "{updatedOrders.cron.expr}", identity = "importChargesJob", concurrentExecution = ConcurrentExecution.SKIP)
    public void registerOrders() {
        findOrders().orders().forEach(order -> {
            try {
                orderService.saveInternally(order);
                logger.warn("================.");
                logger.warn("atuzalizado.");
                logger.warn("data: .".concat(getStartOfDay().toString()));
                logger.warn("================.");
            } catch (Exception e) {
                logger.warn(e);
            }
        });

    }

    public OrdersDTO findOrders() {
        return orderService.findOrdersAcquirer(getStartOfDay().toString(), getEndOfDay(1).toString(),
                providersInvoce().getFirst().getKey(), SIZE, UNTIL);
    }
}