package com.pdv.infra.restclient.vendus.enums;

import com.pdv.domain.entities.enums.EnumCoinType;
import com.pdv.domain.entities.enums.IEnum;
import com.pdv.domain.utils.EnumUtil;

public enum EnumVendusTypeOrder implements IEnum {

    FT("FT", "Fatura"),
    FS("FS", "Fatura Simplificada"),
    PR("PR", "Fatura Recibo"),
    NC("NC", "Nota de Crédito"),
    DC("DC", "Consulta de Mesa"),
    PF("PF", "Fatura Pró-Forma"),
    OT("OT", "Orçamento"),
    EC("EC", "Encomenda"),
    GA("GA", "Guia de Ativos Próprios"),
    GT("GT", "Guia de Transporte"),
    GR("GR", "Guia de Remessa"),
    GD("GD", "Guia de Devolução"),
    RG("RG", "Recibo");

    private final String key;

    private final String value;

    EnumVendusTypeOrder(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean containsInEnum(String key) {
        return parseByKey(key) != null;
    }

    public static EnumCoinType parseByKey(String key) {
        return EnumUtil.parseByKey(EnumCoinType.class, key);
    }

    public static EnumCoinType parseByValue(String value) {
        return EnumUtil.parseByValue(EnumCoinType.class, value);
    }
}
