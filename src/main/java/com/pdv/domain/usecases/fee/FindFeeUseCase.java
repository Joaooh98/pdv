package com.pdv.domain.usecases.fee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.pdv.domain.entities.bo.FeeBO;
import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.IFeeRepository;
import com.pdv.domain.utils.StringUtils;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.presentation.dto.FeeDTO;
import com.pdv.presentation.mapper.FeeDomainMapper;

public class FindFeeUseCase {

    private final IFeeRepository feeRepository;

    public FindFeeUseCase(IFeeRepository feeRepository) {
        this.feeRepository = feeRepository;
    }

    public List<FeeDTO> execute(String description, String id) {
        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotNullOrEmpty(description)) {
            params.put("description", description);
        }

        if (StringUtils.isNotNullOrEmpty(id)) {
            params.put("id", UUID.fromString(id));
        }

        List<FeeBO> response = feeRepository.findAll(params);

        if (response == null || response.isEmpty()) {
            throw new PdvException(EnumErrorCode.NOT_FOUND);
        }

        List<FeeDTO> fee = new ArrayList<>();
        response.forEach(e -> fee.add(FeeDomainMapper.toDTO(e)));

        return fee;
    }
}
