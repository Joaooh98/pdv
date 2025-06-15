package com.pdv.domain.usecases.fee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.pdv.domain.entities.bo.FeeBO;
import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.IFeeRepository;
import com.pdv.domain.utils.StringUtils;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.presentation.dto.FeeDTO;
import com.pdv.presentation.mapper.FeeDomainMapper;

public class UpdatedFeeUseCase {

    private final IFeeRepository feeRepository;

    public UpdatedFeeUseCase(IFeeRepository feeRepository) {
        this.feeRepository = feeRepository;
    }

    public FeeDTO execute(FeeDTO fee) {
        Map<String, Object> params = new HashMap<>();

        if (StringUtils.isNotNullOrEmpty(fee.getId())) {
            params.put("id", UUID.fromString(fee.getId()));

            List<FeeBO> response = feeRepository.findAll(params);

            Optional<FeeBO> first = response.stream().findFirst();

            if (first.isPresent()) {
                FeeBO newFee = FeeDomainMapper.toBO(fee);
                newFee.update(first.get().getId());

                feeRepository.merge(newFee);

                return FeeDomainMapper.toDTO(newFee);
            }
        }

        throw new PdvException(EnumErrorCode.REQUIRED_FIELD, "id");
    }
}
