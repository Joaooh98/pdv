package com.pdv.infra.restclient.vendus.mappers;

import com.pdv.infra.restclient.vendus.dto.VendusFeeDTO;
import com.pdv.presentation.dto.FeeDTO;
import com.pdv.presentation.dto.OrderFeeDTO;

public class VedusOrderFeeMapper {

    public static OrderFeeDTO toDto(VendusFeeDTO vendusFee) {
        var dto = new OrderFeeDTO();
        var feeDto = new FeeDTO();

        feeDto.setAmount(vendusFee.getAmount());
        feeDto.setPercentage(vendusFee.getRate());
        
        dto.setFee(feeDto);
        return dto;
    }

}
