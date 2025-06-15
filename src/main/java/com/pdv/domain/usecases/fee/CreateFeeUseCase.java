package com.pdv.domain.usecases.fee;

import com.pdv.domain.entities.bo.FeeBO;
import com.pdv.domain.repositories.IFeeRepository;
import com.pdv.presentation.dto.FeeDTO;
import com.pdv.presentation.mapper.FeeDomainMapper;

public class CreateFeeUseCase {

    private final IFeeRepository feeRepository;

    public CreateFeeUseCase(IFeeRepository feeRepository) {
        this.feeRepository = feeRepository;
    }

    public FeeDTO execute(FeeDTO fee) {
        FeeBO response = feeRepository.save(FeeDomainMapper.toBO(fee));
        return FeeDomainMapper.toDTO(response);
    }
}
