package com.pdv.infra.restclient.vendus.mappers;

import com.pdv.infra.restclient.vendus.dto.VendusOrderCreateDTO;
import com.pdv.infra.restclient.vendus.dto.VendusOrderDTO;
import com.pdv.infra.restclient.vendus.dto.VendusOrderDetailsDTO;
import com.pdv.infra.restclient.vendus.dto.VendusOrderResponseDTO;
import com.pdv.presentation.dto.OrderDTO;

public class VedusOrderMapper {

    public static OrderDTO toDto(VendusOrderDetailsDTO vendusDto) {
        var dto = new OrderDTO();

        dto.setAcquirerId(vendusDto.getId().toString());
        dto.setAmount(vendusDto.getAmountGross());
        dto.setAmountNet(vendusDto.getAmountNet());
        dto.setProvider("VENDUS");
        dto.setDateCreate(vendusDto.getLocalTime());
        dto.setProfessionalAcquirerId(vendusDto.getUserId().toString());
        dto.setCustomer(VedusOrderClientMapper.toDto(vendusDto.getCustomer()));
        dto.setPayments(vendusDto.getPayments()
                .stream()
                .map(VedusPaymentsMapper::toDto)
                .toList());
        dto.setItems(vendusDto.getItems()
                .stream()
                .map(VedusOrderItemsMapper::toDto)
                .toList());
        dto.setFee(vendusDto.getFees()
                .stream()
                .map(VedusOrderFeeMapper::toDto)
                .toList());
        dto.setFinance(true);

        if (vendusDto.getDiscounts() != null) {
            dto.setDiscount(VedusDiscountMapper.toDto(vendusDto.getDiscounts()));
        }

        return dto;
    }

    public static OrderDTO toDto(VendusOrderDTO vendusDto) {
        var dto = new OrderDTO();

        dto.setAcquirerId(vendusDto.getId().toString());
        dto.setAmount(vendusDto.getAmountGross());
        dto.setAmountNet(vendusDto.getAmountNet());
        dto.setProvider("VENDUS");
        dto.setDateCreate(vendusDto.getLocalTime());
        dto.setCheckoutId(vendusDto.getCheckoutId());

        return dto;
    }

    public static OrderDTO toDto(VendusOrderResponseDTO vendusDto) {
        var dto = new OrderDTO();

        dto.setAcquirerId(vendusDto.getId().toString());
        dto.setAmount(vendusDto.getAmountGross());
        dto.setAmountNet(vendusDto.getAmountNet());
        dto.setProvider("VENDUS");
        dto.setDateCreate(vendusDto.getLocalTime());

        return dto;
    }

    public static VendusOrderCreateDTO toVendusDto(OrderDTO myDto,
            String token) {
                
        var vendusDto = new VendusOrderCreateDTO();
        if (myDto.getCheckoutId() != null) {
            vendusDto.setCheckoutId(Long.valueOf(myDto.getCheckoutId()));
        }
        vendusDto.setType(myDto.getType());
        // vendusDto.setDiscountCode(myDto.getDiscount() != null ?
        // myDto.getDiscount().getCode() : null);
        vendusDto.setDiscountAmount(myDto.getDiscount() != null && myDto.getDiscount().getAmount() != null
                ? myDto.getDiscount().getAmount().toString()
                : null);
        // vendusDto.setDiscountPercentage(myDto.getDiscount() != null &&
        // myDto.getDiscount().getPercentage() != null ?
        // myDto.getDiscount().getPercentage().toString() : null);
        // vendusDto.setDateDue(myDto.getDateDue());
        vendusDto.setMode("normal");
        vendusDto.setDate(myDto.getDateCreate());
        vendusDto.setDateSupply(myDto.getDateCreate());
        // vendusDto.setNotes(myDto.getNotes());
        // vendusDto.setNcrId(myDto.getNcrId());
        // vendusDto.setExteranalReference(myDto.getExternalReference());
        // vendusDto.setStockOperation(myDto.getStockOperation());
        // vendusDto.setHenPay(myDto.getIfthenpay());
        // vendusDto.setiPay(myDto.getEupago());
        // vendusDto.setMultibanco(myDto.getMultibanco());
        // vendusDto.setCustomer(myDto.getCustomer() != null ?
        // myDto.getCustomer().toVendusDto() : null);
        // vendusDto.setSupplier(myDto.getSupplier() != null ?
        // myDto.getSupplier().toVendusDto() : null);
        // vendusDto.setItems(myDto.getItems() != null ?
        // myDto.getItems().stream().map(item -> item.toVendusDto()).toList() : null);
        // vendusDto.setPayments(myDto.getPayments() != null ?
        // myDto.getPayments().stream().map(payment -> payment.toVendusDto()).toList() :
        // null);
        // vendusDto.setInvoices(myDto.getInvoices() != null ?
        // myDto.getInvoices().stream().map(inv -> inv.toVendusDto()).toList() : null);
        // vendusDto.setPrintDiscount(myDto.getPrintDiscount());
        // vendusDto.setOutput(myDto.getOutput());
        // vendusDto.setOutPutTemplateId(myDto.getOutputTemplateId());
        // vendusDto.setTxId(myDto.getTxId());
        // vendusDto.setErrosFull(myDto.getErrorsFull());
        // vendusDto.setRestRoom(myDto.getRestRoom());
        // vendusDto.setRestTable(myDto.getRestTable());
        // vendusDto.setOccupation(myDto.getOccupation());
        // vendusDto.setStampRentionAmount(myDto.getStampRetentionAmount());
        // vendusDto.setIrcRetentionId(myDto.getIrcRetentionId());
        // vendusDto.setRelatedDocumentId(myDto.getRelatedDocumentId());
        // vendusDto.setReturnQrcode(myDto.getReturnQrcode());
        // vendusDto.setDocTogernerate(myDto.getDocToGenerate());
        return vendusDto;
    }

}
