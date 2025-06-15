package com.pdv.infra.data.panache.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.pdv.domain.entities.bo.ProfessionalBO;
import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.repositories.IProfessionalRepository;
import com.pdv.domain.utils.exception.PdvException;
import com.pdv.infra.data.panache.mapper.ModalProfessionalMapper;
import com.pdv.infra.data.panache.model.ModalProfessional;
import com.pdv.infra.data.panache.util.ParamSanitizerUtils;
import com.pdv.infra.data.panache.util.ParamSanitizerUtils.QueryInfo;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProfessionalRepository implements IProfessionalRepository {

    @Override
    public ProfessionalBO save(ProfessionalBO bo) {
        var modal = ModalProfessionalMapper.toModal(bo);
        try {
            modal.persistAndFlush();
        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
        return ModalProfessionalMapper.toBO(modal);
    }

    @Override
    public ProfessionalBO find(Map<String, Object> params) {
        try {

            StringJoiner query = new StringJoiner(" and ");
            for (String key : params.keySet()) {
                query.add(key + " = :" + key);
            }

            ModalProfessional modalProfessional = ModalProfessional.find(query.toString(), params).firstResult();

            if (modalProfessional == null) {
                return null;
            }

            return ModalProfessionalMapper.toBO(modalProfessional);

        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    @Override
    public void delete(ProfessionalBO bo) {
        try {
            var modal = ModalProfessionalMapper.toModal(bo);
            modal.delete();
        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

    public List<ProfessionalBO> findAll(Map<String, Object> params) {
        try {

            QueryInfo queryInfo = ParamSanitizerUtils.sanitizeQueryParams(params);

            List<ModalProfessional> professionals = ModalProfessional.find(queryInfo.getQueryString(), queryInfo.getSanitizedParams()).list();

            if (professionals == null) {
                return Collections.emptyList();
            }

            return professionals.stream().map(ModalProfessionalMapper::toBO).collect(Collectors.toList());

        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, e);
        }
    }

        /**
     * Busca profissionais por múltiplos acquirerIds de uma vez
     * Método otimizado para evitar N+1 queries
     */
    public List<ProfessionalBO> findByAcquirerIds(Set<String> acquirerIds) {
        try {
            if (acquirerIds == null || acquirerIds.isEmpty()) {
                return new ArrayList<>();
            }

            List<ModalProfessional> professionals = ModalProfessional
                .find("acquirerId in ?1", acquirerIds)
                .list();

            return professionals.stream()
                    .map(ModalProfessionalMapper::toBO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, 
                "Error finding professionals by acquirer IDs: " + e.getMessage());
        }
    }

    /**
     * Busca profissionais por múltiplos IDs de uma vez
     * Método otimizado para evitar N+1 queries
     */
    public List<ProfessionalBO> findByIds(Set<String> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                return new ArrayList<>();
            }

            List<ModalProfessional> professionals = ModalProfessional
                .find("id in ?1", ids)
                .list();

            return professionals.stream()
                    .map(ModalProfessionalMapper::toBO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR, 
                "Error finding professionals by IDs: " + e.getMessage());
        }
    }

}
