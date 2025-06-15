package com.pdv.infra.restclient.vendus.dto;

import java.util.List;

import jakarta.json.bind.annotation.JsonbProperty;

public class VendusOrderCreateDTO {

    @JsonbProperty("register_id")
    private Long checkoutId;

    private String type;

    @JsonbProperty("discount_code")
    private String discountCode;

    @JsonbProperty("discount_amount")
    private String discountAmount;

    @JsonbProperty("discount_percentage")
    private String discountPercentage;

    @JsonbProperty("date_due")
    private String dateDue;

    private List<VendusPaymentDTO> payments;

    private String mode;

    private String date;

    @JsonbProperty("date_supply")
    private String dateSupply;

    private String notes;

    @JsonbProperty("ncr_id")
    private String ncrId;

    @JsonbProperty("external_reference")
    private String exteranalReference;

    @JsonbProperty("stock_operation")
    private String stockOperation;

    @JsonbProperty("ifthenpay")
    private String henPay;

    @JsonbProperty("eupago")
    private String iPay;

    @JsonbProperty("multibanco")
    private VendusMultibancoDTO multibanco;

    @JsonbProperty("client")
    private VendusCustomerDTO customer;

    @JsonbProperty("supplier")
    private VendusSupplierDTO supplier;

    @JsonbProperty("items")
    private List<VendusItemDTO> items;

    @JsonbProperty("invoices")
    private List<VendusInvoiceDTO> invoices;

    @JsonbProperty("print_discount")
    private String printDiscount;

    @JsonbProperty("output")
    private String output;

    @JsonbProperty("output_template_id")
    private String outPutTemplateId;

    @JsonbProperty("tx_id")
    private String txId;

    @JsonbProperty("errors_full")
    private String errosFull;

    @JsonbProperty("rest_room")
    private String restRoom;

    @JsonbProperty("rest_table")
    private String restTable;

    @JsonbProperty("occupation")
    private String occupation;

    @JsonbProperty("stamp_retention_amount")
    private String stampRentionAmount;

    @JsonbProperty("irc_retention_id")
    private String ircRetentionId;

    @JsonbProperty("related_document_id")
    private String relatedDocumentId;

    @JsonbProperty("return_qrcode")
    private String returnQrcode;

    @JsonbProperty("doc_to_generate")
    private String docTogernerate;

    public Long getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(Long checkoutId) {
        this.checkoutId = checkoutId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public List<VendusPaymentDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<VendusPaymentDTO> payments) {
        this.payments = payments;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateSupply() {
        return dateSupply;
    }

    public void setDateSupply(String dateSupply) {
        this.dateSupply = dateSupply;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNcrId() {
        return ncrId;
    }

    public void setNcrId(String ncrId) {
        this.ncrId = ncrId;
    }

    public String getExteranalReference() {
        return exteranalReference;
    }

    public void setExteranalReference(String exteranalReference) {
        this.exteranalReference = exteranalReference;
    }

    public String getStockOperation() {
        return stockOperation;
    }

    public void setStockOperation(String stockOperation) {
        this.stockOperation = stockOperation;
    }

    public String getHenPay() {
        return henPay;
    }

    public void setHenPay(String henPay) {
        this.henPay = henPay;
    }

    public String getiPay() {
        return iPay;
    }

    public void setiPay(String iPay) {
        this.iPay = iPay;
    }

    public VendusMultibancoDTO getMultibanco() {
        return multibanco;
    }

    public void setMultibanco(VendusMultibancoDTO multibanco) {
        this.multibanco = multibanco;
    }

    public VendusCustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(VendusCustomerDTO customer) {
        this.customer = customer;
    }

    public VendusSupplierDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(VendusSupplierDTO supplier) {
        this.supplier = supplier;
    }

    public List<VendusItemDTO> getItems() {
        return items;
    }

    public void setItems(List<VendusItemDTO> items) {
        this.items = items;
    }

    public List<VendusInvoiceDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<VendusInvoiceDTO> invoices) {
        this.invoices = invoices;
    }

    public String getPrintDiscount() {
        return printDiscount;
    }

    public void setPrintDiscount(String printDiscount) {
        this.printDiscount = printDiscount;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getOutPutTemplateId() {
        return outPutTemplateId;
    }

    public void setOutPutTemplateId(String outPutTemplateId) {
        this.outPutTemplateId = outPutTemplateId;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getErrosFull() {
        return errosFull;
    }

    public void setErrosFull(String errosFull) {
        this.errosFull = errosFull;
    }

    public String getRestRoom() {
        return restRoom;
    }

    public void setRestRoom(String restRoom) {
        this.restRoom = restRoom;
    }

    public String getRestTable() {
        return restTable;
    }

    public void setRestTable(String restTable) {
        this.restTable = restTable;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getStampRentionAmount() {
        return stampRentionAmount;
    }

    public void setStampRentionAmount(String stampRentionAmount) {
        this.stampRentionAmount = stampRentionAmount;
    }

    public String getIrcRetentionId() {
        return ircRetentionId;
    }

    public void setIrcRetentionId(String ircRetentionId) {
        this.ircRetentionId = ircRetentionId;
    }

    public String getRelatedDocumentId() {
        return relatedDocumentId;
    }

    public void setRelatedDocumentId(String relatedDocumentId) {
        this.relatedDocumentId = relatedDocumentId;
    }

    public String getReturnQrcode() {
        return returnQrcode;
    }

    public void setReturnQrcode(String returnQrcode) {
        this.returnQrcode = returnQrcode;
    }

    public String getDocTogernerate() {
        return docTogernerate;
    }

    public void setDocTogernerate(String docTogernerate) {
        this.docTogernerate = docTogernerate;
    }

}
