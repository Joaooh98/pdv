package com.pdv.infra.data.panache.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Classe utilitária para tratamento de parâmetros de consultas Panache/HQL
 */
public class ParamSanitizerUtils {

    /**
     * Sanitiza parâmetros para consultas HQL, tratando corretamente parâmetros com
     * pontos
     * 
     * @param params Mapa com os parâmetros originais
     * @return QueryInfo contendo a string de consulta e o mapa de parâmetros
     *         sanitizados
     */
    public static QueryInfo sanitizeQueryParams(Map<String, Object> params) {
        StringJoiner query = new StringJoiner(" and ");
        Map<String, Object> sanitizedParams = new HashMap<>();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // Verifica se o parâmetro contém um ponto
            if (key.contains(".")) {
                // Para parâmetros como "enterprise.id", use a notação de caminho no HQL
                // mas crie um nome de parâmetro sem pontos para o bind
                String fieldPath = key;
                String paramName = key.replace(".", "_");
                query.add(fieldPath + " = :" + paramName);
                sanitizedParams.put(paramName, value);
            } else {
                // Para parâmetros normais, use-os diretamente
                query.add(key + " = :" + key);
                sanitizedParams.put(key, value);
            }
        }

        return new QueryInfo(query.toString(), sanitizedParams);
    }

    /**
     * Classe imutável para retornar informações da consulta
     */
    public static class QueryInfo {
        private final String queryString;
        private final Map<String, Object> sanitizedParams;

        public QueryInfo(String queryString, Map<String, Object> sanitizedParams) {
            this.queryString = queryString;
            this.sanitizedParams = sanitizedParams;
        }

        public String getQueryString() {
            return queryString;
        }

        public Map<String, Object> getSanitizedParams() {
            return sanitizedParams;
        }

        public boolean hasConditions() {
            return !queryString.isEmpty();
        }
    }
}