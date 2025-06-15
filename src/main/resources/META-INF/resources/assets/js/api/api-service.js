/**
 * Serviço de API - Encapsula as chamadas HTTP para o backend
 */
class ApiService {
    constructor() {
        this.baseUrl = CONFIG.API.BASE_URL || '';
        this.endpoints = CONFIG.API.ENDPOINTS;
    }

    /**
     * Obtém um token CSRF de um cookie
     * @param {string} name - Nome do cookie
     * @returns {string|null} Valor do cookie ou null se não encontrado
     */
    getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
        return null;
    }

    /**
     * Processa a resposta da API
     * @param {Response} response - Resposta da requisição fetch
     * @returns {Promise} Promise com o resultado processado
     */
    async handleResponse(response) {
        const contentType = response.headers.get('content-type');
        const isJson = contentType && contentType.includes('application/json');
        
        try {
            const data = isJson ? await response.json() : await response.text();

            if (!response.ok) {
                const error = isJson && data.message 
                    ? data.message 
                    : `Erro ${response.status}: ${response.statusText}`;
                
                throw new Error(error);
            }

            return data;
        } catch (error) {
            console.error('Erro ao processar resposta:', error);
            throw error;
        }
    }

    /**
     * Método genérico para chamadas à API
     * @param {String} endpoint - Endpoint da API
     * @param {String} method - Método HTTP (GET, POST, PUT, DELETE)
     * @param {Object} body - Corpo da requisição (opcional)
     * @returns {Promise} Promise com o resultado da requisição
     */
    async callApi(endpoint, method = 'GET', body = null) {
        try {
            // Obter token CSRF do localStorage ou cookie
            const csrfToken = localStorage.getItem(CONFIG.STORAGE.CSRF_TOKEN_KEY) || 
                              this.getCookie('XSRF-TOKEN');
            
            const headers = {
                'Content-Type': 'application/json',
            };
            
            // Adicionar token CSRF se disponível e a configuração estiver ativada
            if (CONFIG.SECURITY.ENABLE_CSRF_PROTECTION && csrfToken) {
                headers['X-CSRF-TOKEN'] = csrfToken;
            }
            
            const response = await fetch(`${this.baseUrl}${endpoint}`, {
                method,
                headers,
                body: body ? JSON.stringify(body) : null,
                credentials: 'include', // Incluir cookies nas requisições
            });

            return await this.handleResponse(response);
        } catch (error) {
            console.error('API Error:', error);
            
            // Verificar se é um erro de autenticação
            if (error.message && (
                error.message.includes('401') || 
                error.message.toLowerCase().includes('unauthorized') ||
                error.message.toLowerCase().includes('não autorizado')
            )) {
                // Se o usuário estiver autenticado, fazer logout automaticamente
                if (typeof Auth !== 'undefined' && Auth.checkAuth()) {
                    Helpers.showToast(CONFIG.MESSAGES.ERROR.AUTH, 'danger');
                    setTimeout(() => Auth.logout(true), 1500);
                }
            }
            
            throw error;
        }
    }

    /**
     * Método GET
     * @param {String} endpoint - Endpoint da API
     * @param {Object} params - Parâmetros da consulta (opcional)
     * @returns {Promise} Promise com o resultado da requisição
     */
    async get(endpoint, params = {}) {
        // Constrói a URL com os parâmetros
        let url = endpoint;
        if (Object.keys(params).length > 0) {
            const queryParams = new URLSearchParams();
            Object.entries(params).forEach(([key, value]) => {
                if (value !== null && value !== undefined) {
                    queryParams.append(key, value);
                }
            });
            url = `${endpoint}?${queryParams.toString()}`;
        }
        
        return this.callApi(url, 'GET');
    }

    /**
     * Método POST
     * @param {String} endpoint - Endpoint da API
     * @param {Object} data - Dados da requisição
     * @returns {Promise} Promise com o resultado da requisição
     */
    async post(endpoint, data) {
        return this.callApi(endpoint, 'POST', data);
    }

    /**
     * Método PUT
     * @param {String} endpoint - Endpoint da API
     * @param {Object} data - Dados da requisição
     * @returns {Promise} Promise com o resultado da requisição
     */
    async put(endpoint, data) {
        return this.callApi(endpoint, 'PUT', data);
    }

    /**
     * Método PATCH
     * @param {String} endpoint - Endpoint da API
     * @param {Object} data - Dados da requisição
     * @returns {Promise} Promise com o resultado da requisição
     */
    async patch(endpoint, data) {
        return this.callApi(endpoint, 'PATCH', data);
    }

    /**
     * Método DELETE
     * @param {String} endpoint - Endpoint da API
     * @returns {Promise} Promise com o resultado da requisição
     */
    async delete(endpoint) {
        return this.callApi(endpoint, 'DELETE');
    }
}

// Cria uma instância única do serviço para uso global
const API = new ApiService();

// Exportar para uso global
if (typeof module !== 'undefined' && module.exports) {
    module.exports = API;
}