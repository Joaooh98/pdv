package com.pdv.services;

import java.util.List;

import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.usecases.fee.CreateFeeUseCase;
import com.pdv.domain.usecases.fee.FindFeeUseCase;
import com.pdv.domain.usecases.fee.UpdatedFeeUseCase;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.presentation.dto.FeeDTO;

import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FeeService extends AbstractService {

    @Transactional
    public FeeDTO create(FeeDTO fee) {
        if (fee == null) {
            throw new PdvException(EnumErrorCode.REQUIRED_OBJECT, fee);
        }

        return new CreateFeeUseCase(feeRepository).execute(fee);
    }

    @CacheResult(cacheName = "default-fee")
    public List<FeeDTO> findAll(String description, String id) {
        return new FindFeeUseCase(feeRepository).execute(description, id);
    }

    @Transactional
    public FeeDTO updateFee(FeeDTO fee) {
        if (fee == null) {
            throw new PdvException(EnumErrorCode.REQUIRED_OBJECT, fee);
        }
        return new UpdatedFeeUseCase(feeRepository).execute(fee);
    }

}
