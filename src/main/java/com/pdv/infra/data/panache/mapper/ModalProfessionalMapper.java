package com.pdv.infra.data.panache.mapper;

import com.pdv.domain.entities.bo.ProfessionalBO;
import com.pdv.domain.utils.UuidVO;
import com.pdv.infra.data.panache.model.ModalProfessional;

public class ModalProfessionalMapper {

    public static ModalProfessional toModal(ProfessionalBO bo) {
        ModalProfessional modal = new ModalProfessional();

        if (bo.getId() != null) {
            modal.setId(bo.getId().getValue());
        }
        
        modal.setName(bo.getName());
        modal.setEmail(bo.getEmail());
        modal.setDocument(bo.getDocument());
        modal.setUsername(bo.getUserName());
        modal.setPassword(bo.getPassword());
        modal.setAdmin(bo.isAdmin());

        return modal;
    }

    public static ProfessionalBO toBO(ModalProfessional modal) {
        ProfessionalBO bo = new ProfessionalBO.Builder()
                .id(new UuidVO(modal.getId().toString()))
                .name(modal.getName())
                .email(modal.getEmail())
                .document(modal.getDocument())
                .userName(modal.getUsername())
                .password(modal.getPassword())
                .isAdmin(modal.isAdmin())
                .build();

        return bo;
    }
}
