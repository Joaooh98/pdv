package com.pdv.domain.entities.bo;

import java.math.BigDecimal;
import java.util.List;

import com.pdv.domain.entities.enums.EnumProvider;
import com.pdv.domain.entities.vo.Amount;
import com.pdv.domain.utils.UuidVO;

public class OrderBO {

    private UuidVO id;
    private CustomerBO customer;
    private List<PaymentBO> payments;
    private List<OrderItemBO> items;
    private List<OrderFeeBO> fees;
    private Amount amount;
    private Amount amountCommission;
    private boolean isFinance;
    private DiscountBO discount;
    private EnumProvider provider;
    private ProfessionalBO professional;
    private String acquirerId;
    private String dataCreate;

    private OrderBO(Builder builder) {
        this.id = builder.id;
        this.customer = builder.customer;
        this.payments = builder.payments;
        this.items = builder.items;
        this.fees = builder.fees;
        this.amount = builder.amount;
        this.amountCommission = builder.amountCommission;
        this.isFinance = builder.isFinance;
        this.provider = builder.provider;
        this.discount = builder.discount;
        this.professional = builder.professional;
        this.acquirerId = builder.acquirerId;
        this.dataCreate = builder.dataCreate;
    }

    public static class Builder {
        private UuidVO id;
        private CustomerBO customer;
        private List<PaymentBO> payments;
        private List<OrderItemBO> items;
        private List<OrderFeeBO> fees;
        private Amount amount;
        private Amount amountCommission;
        private EnumProvider provider;
        private boolean isFinance;
        private DiscountBO discount;
        private ProfessionalBO professional;
        private String acquirerId;
        private String dataCreate;

        public Builder id(UuidVO id) {
            this.id = id;
            return this;
        }

        public Builder customer(CustomerBO customer) {
            this.customer = customer;
            return this;
        }

        public Builder payments(List<PaymentBO> payments) {
            this.payments = payments;
            return this;
        }

        public Builder items(List<OrderItemBO> items) {
            this.items = items;
            return this;
        }

        public Builder fees(List<OrderFeeBO> fees) {
            this.fees = fees;
            return this;
        }

        public Builder amount(Amount amount) {
            this.amount = amount;
            return this;
        }

        public Builder amountCommission(Amount amountCommission) {
            this.amountCommission = amountCommission;
            return this;
        }

        public Builder isFinance(boolean isFinance) {
            this.isFinance = isFinance;
            return this;
        }

        public Builder provider(EnumProvider provider) {
            this.provider = provider;
            return this;
        }

        public Builder discount(DiscountBO discount) {
            this.discount = discount;
            return this;
        }

        public Builder professional(ProfessionalBO professional) {
            this.professional = professional;
            return this;
        }

        public Builder acquirerId(String acquirerId) {
            this.acquirerId = acquirerId;
            return this;
        }

        public Builder dataCreate(String dataCreate) {
            this.dataCreate = dataCreate;
            return this;
        }

        public OrderBO build() {
            return new OrderBO(this);
        }
    }

    public void businessRulePayments() {
        // Implementação da validação de pagamentos
    }

    public void businessRuleItems() {
        // Implementação da validação de itens
    }

    public UuidVO getId() {
        return id;
    }

    public CustomerBO getCustomer() {
        return customer;
    }

    public List<PaymentBO> getPayments() {
        return payments;
    }

    public List<OrderItemBO> getItems() {
        return items;
    }

    public List<OrderFeeBO> getFees() {
        return fees;
    }

    public Amount getAmount() {
        return amount;
    }

    public Amount getAmountCommission() {
        return amountCommission;
    }

    public boolean isFinance() {
        return isFinance;
    }

    public EnumProvider getProvider() {
        return provider;
    }

    public DiscountBO getDiscount() {
        return discount;
    }

    public ProfessionalBO getProfessional() {
        return professional;
    }

    public String getAcquirerId() {
        return acquirerId;
    }

    public String getDataCreate() {
        return dataCreate;
    }

    public void calcAmountCommission() {
        this.amountCommission = new Amount(items.stream()
                .map(OrderItemBO::getProduct)
                .map(product -> product.calcCommission())
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

}
