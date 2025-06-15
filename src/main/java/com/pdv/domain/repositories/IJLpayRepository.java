package com.pdv.domain.repositories;

import com.pdv.presentation.dto.RegisterMovimentDTO;

public interface IJLpayRepository {

    void registerMoviment(RegisterMovimentDTO moviment);
}
