package com.pdv.infra.restclient.vendus.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.json.bind.annotation.JsonbProperty;

public class VendusOrderDetailsDTO {

    @JsonbProperty("id")
    private Long id;

    @JsonbProperty("number")
    private String number;

    @JsonbProperty("date")
    private String date;

    @JsonbProperty("store_id")
    private Long storeId;

    @JsonbProperty("register_id")
    private Long registerId;

    @JsonbProperty("date_due")
    private String dateDue;

    @JsonbProperty("system_time")
    private String systemTime;

    @JsonbProperty("local_time")
    private String localTime;

    @JsonbProperty("amount_gross")
    private BigDecimal amountGross;

    @JsonbProperty("amount_net")
    private BigDecimal amountNet;

    @JsonbProperty("type")
    private String type;

    @JsonbProperty("total_unpaid")
    private Integer totalUnpaid;

    @JsonbProperty("payment_status")
    private String paymentStatus;

    @JsonbProperty("taxes")
    private List<VendusFeeDTO> fees;

    @JsonbProperty("payments")
    private List<VendusPaymentDTO> payments;

    @JsonbProperty("client")
    private VendusClientDTO customer;

    @JsonbProperty("items")
    private List<VendusItemDTO> items;

    @JsonbProperty("status")
    private VendusStatusDTO user;

    @JsonbProperty("debt")
    private VendusDebtDTO debt;

    @JsonbProperty("related_docs")
    private List<VendusRelatedDTO> infosTransaction;

    private String hash;

    private String atcud;

    @JsonbProperty("output_data")
    private String outputData;

    @JsonbProperty("user_id")
    private Long userId;

    private VendusOrderDiscountDTO discounts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Long registerId) {
        this.registerId = registerId;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public BigDecimal getAmountGross() {
        return amountGross;
    }

    public void setAmountGross(BigDecimal amountGross) {
        this.amountGross = amountGross;
    }

    public BigDecimal getAmountNet() {
        return amountNet;
    }

    public void setAmountNet(BigDecimal amountNet) {
        this.amountNet = amountNet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTotalUnpaid() {
        return totalUnpaid;
    }

    public void setTotalUnpaid(Integer totalUnpaid) {
        this.totalUnpaid = totalUnpaid;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<VendusFeeDTO> getFees() {
        return fees;
    }

    public void setFees(List<VendusFeeDTO> fees) {
        this.fees = fees;
    }

    public List<VendusPaymentDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<VendusPaymentDTO> payments) {
        this.payments = payments;
    }

    public VendusClientDTO getCustomer() {
        return customer;
    }

    public void setCustomer(VendusClientDTO customer) {
        this.customer = customer;
    }

    public List<VendusItemDTO> getItems() {
        return items;
    }

    public void setItems(List<VendusItemDTO> items) {
        this.items = items;
    }

    public VendusStatusDTO getUser() {
        return user;
    }

    public void setUser(VendusStatusDTO user) {
        this.user = user;
    }

    public VendusDebtDTO getDebt() {
        return debt;
    }

    public void setDebt(VendusDebtDTO debt) {
        this.debt = debt;
    }

    public List<VendusRelatedDTO> getInfosTransaction() {
        return infosTransaction;
    }

    public void setInfosTransaction(List<VendusRelatedDTO> infosTransaction) {
        this.infosTransaction = infosTransaction;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getAtcud() {
        return atcud;
    }

    public void setAtcud(String atcud) {
        this.atcud = atcud;
    }

    public String getOutputData() {
        return outputData;
    }

    public void setOutputData(String outputData) {
        this.outputData = outputData;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public VendusOrderDiscountDTO getDiscounts() {
        return discounts;
    }

    public void setDiscounts(VendusOrderDiscountDTO discounts) {
        this.discounts = discounts;
    }

}
