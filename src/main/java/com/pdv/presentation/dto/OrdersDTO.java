package com.pdv.presentation.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record OrdersDTO(
    int totalOrders,
    BigDecimal totalAmount,
    BigDecimal totalAmountCard,
    BigDecimal totalAmountCash,
    BigDecimal totalAmountDiscont,
    List<OrderDTO> orders,
    List<InfoProfessionalDTO> infos
) {
    public static class Builder {
        private int totalOrders;
        private BigDecimal totalAmount = BigDecimal.ZERO;
        private BigDecimal totalAmountCard = BigDecimal.ZERO;
        private BigDecimal totalAmountCash = BigDecimal.ZERO;
        private BigDecimal totalAmountDiscont = BigDecimal.ZERO;
        private List<OrderDTO> orders;
        private List<InfoProfessionalDTO> infos;
        
        public Builder totalOrders(int totalOrders) {
            this.totalOrders = totalOrders;
            return this;
        }
        
        public Builder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }
        
        public Builder totalAmountCard(BigDecimal totalAmountCard) {
            this.totalAmountCard = totalAmountCard;
            return this;
        }
        
        public Builder totalAmountCash(BigDecimal totalAmountCash) {
            this.totalAmountCash = totalAmountCash;
            return this;
        }
        
        public Builder totalAmountDiscont(BigDecimal totalAmountDiscont) {
            this.totalAmountDiscont = totalAmountDiscont;
            return this;
        }
        
        public Builder orders(List<OrderDTO> orders) {
            this.orders = new ArrayList<>(orders);
            return this;
        }
        
        public Builder infos(List<InfoProfessionalDTO> infos) {
            this.infos = new ArrayList<>(infos);
            return this;
        }
        
        public OrdersDTO build() {
            return new OrdersDTO(
                totalOrders,
                totalAmount,
                totalAmountCard,
                totalAmountCash,
                totalAmountDiscont,
                orders,
                infos
            );
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public OrdersDTO withTotalAmountDiscont(BigDecimal newDiscount) {
        return new OrdersDTO(
            totalOrders,
            totalAmount,
            totalAmountCard,
            totalAmountCash,
            newDiscount, 
            orders,
            infos
        );
    }
    
    public OrdersDTO withInfos(List<InfoProfessionalDTO> newInfos) {
        return new OrdersDTO(
            totalOrders,
            totalAmount,
            totalAmountCard,
            totalAmountCash,
            totalAmountDiscont,
            orders,
            newInfos  
        );
    }
    
    public OrdersDTO withUpdatedData(BigDecimal newDiscount, List<InfoProfessionalDTO> newInfos) {
        return new OrdersDTO(
            totalOrders,
            totalAmount,
            totalAmountCard,
            totalAmountCash,
            newDiscount,
            orders,
            newInfos
        );
    }
}