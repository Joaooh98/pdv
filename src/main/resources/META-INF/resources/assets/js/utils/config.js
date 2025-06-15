/**
 * Arquivo de configuração do sistema PDV
 * Centraliza constantes, URLs e configurações do sistema
 */

const CONFIG = {
    // URLs da API
    API: {
        BASE_URL: '', // Base URL é relativa ao host atual
        ENDPOINTS: {
            LOGIN: '/professional/token',
            LOGIN_ENTERPRISE: '/enterprise/token',
            REGISTER: '/professional/register',
            PRODUCTS: '/product',
            ORDERS: '/order',
            PAYMENTS: '/payment',
            CUSTOMERS: '/customer'
        }
    },

    // Configurações gerais
    APP: {
        NAME: 'Barbearia PDV System',
        VERSION: '1.0.0',
        CURRENCY: 'EUR',
        CURRENCY_SYMBOL: '€',
        DATE_FORMAT: 'DD/MM/YYYY',
        TIME_FORMAT: 'HH:mm',
        DECIMAL_PLACES: 2
    },

    // Configurações de armazenamento local
    STORAGE: {
        TOKEN_KEY: 'userData',
        USER_KEY: 'userData',
        CART_KEY: 'pdv_cart_items',
        LOGGED_IN_KEY: 'loggedIn',
        LAST_ACTIVITY_KEY: 'lastActivity',
        CSRF_TOKEN_KEY: 'csrf_token'
    },

    // Constantes de Mensagens
    MESSAGES: {
        ERROR: {
            DEFAULT: 'Ocorreu um erro. Tente novamente mais tarde.',
            NETWORK: 'Erro de conexão. Verifique sua internet.',
            AUTH: 'Erro de autenticação. Faça login novamente.',
            VALIDATION: 'Dados inválidos. Verifique os campos.',
            SESSION_EXPIRED: 'Sua sessão expirou. Por favor, faça login novamente.'
        },
        SUCCESS: {
            SAVED: 'Dados salvos com sucesso!',
            DELETED: 'Item removido com sucesso!',
            PAYMENT: 'Pagamento realizado com sucesso!'
        }
    },

    // Configurações de UI
    UI: {
        ANIMATION_SPEED: 300,
        TOAST_TIMEOUT: 3000,
        DEBOUNCE_TIME: 500,
        PAGINATION_SIZES: [10, 20, 50, 100]
    },

    // Configurações de Sessão
    SESSION: {
        TIMEOUT: 1 * 60 * 1000, // 1 minuto em milissegundos
        CHECK_INTERVAL: 60 * 1000 // Verificar a cada minuto
    },

    // Configurações de segurança
    SECURITY: {
        // MIN_PASSWORD_LENGTH: 8,
        // REQUIRE_SPECIAL_CHARS: true,
        // ENABLE_CSRF_PROTECTION: true,
        // ENABLE_XSS_PROTECTION: true
    }
};

// Congelar o objeto para impedir modificações
Object.freeze(CONFIG);

// Exportar para uso global
if (typeof module !== 'undefined' && module.exports) {
    module.exports = CONFIG;
}