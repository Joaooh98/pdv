/**
 * Arquivo de funções utilitárias para todo o sistema
 */

const Helpers = {
    /**
     * Formata um valor para moeda brasileira
     * @param {Number} value - Valor a ser formatado
     * @returns {String} Valor formatado (ex: € 1.234,56)
     */
    formatCurrency: function(value) {
        if (typeof value !== 'number') {
            value = parseFloat(value) || 0;
        }
        return value.toLocaleString('pt-BR', {
            style: 'currency',
            currency: 'EUR'
        });
    },
    
    /**
     * Formata uma data no padrão brasileiro
     * @param {Date|String} date - Data a ser formatada
     * @returns {String} Data formatada (DD/MM/YYYY)
     */
    formatDate: function(date) {
        if (!date) return '';
        const d = new Date(date);
        return `${d.getDate().toString().padStart(2, '0')}/${(d.getMonth() + 1).toString().padStart(2, '0')}/${d.getFullYear()}`;
    },
    
    /**
     * Formata um horário
     * @param {Date|String} date - Data/hora a ser formatada
     * @returns {String} Hora formatada (HH:MM)
     */
    formatTime: function(date) {
        if (!date) return '';
        const d = new Date(date);
        return `${d.getHours().toString().padStart(2, '0')}:${d.getMinutes().toString().padStart(2, '0')}`;
    },
    
    /**
     * Sanitiza uma string HTML para prevenir XSS
     * @param {String} html - String que pode conter HTML
     * @returns {String} String sanitizada
     */
    sanitizeHTML: function(html) {
        if (!html) return '';
        const temp = document.createElement('div');
        temp.textContent = html;
        return temp.innerHTML;
    },
    
    /**
     * Cria um elemento de toast para mensagens
     * @param {String} message - Mensagem a ser exibida
     * @param {String} type - Tipo de mensagem (success, error, warning, info)
     * @param {Number} timeout - Tempo em ms para fechar o toast
     */
    showToast: function(message, type = 'info', timeout = 3000) {
        // Sanitiza a mensagem para evitar XSS
        const safeMessage = this.sanitizeHTML(message);
        
        // Busca container de toasts ou cria se não existir
        let toastContainer = document.querySelector('.toast-container');
        if (!toastContainer) {
            toastContainer = document.createElement('div');
            toastContainer.className = 'toast-container position-fixed top-0 end-0 p-3';
            document.body.appendChild(toastContainer);
        }
        
        // Cria elemento de toast
        const toastElement = document.createElement('div');
        toastElement.className = `toast align-items-center text-white bg-${type} border-0`;
        toastElement.setAttribute('role', 'alert');
        toastElement.setAttribute('aria-live', 'assertive');
        toastElement.setAttribute('aria-atomic', 'true');
        
        // Cria o conteúdo do toast
        toastElement.innerHTML = `
            <div class="d-flex">
                <div class="toast-body">${safeMessage}</div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Fechar"></button>
            </div>
        `;
        
        // Adiciona o toast ao container
        toastContainer.appendChild(toastElement);
        
        // Inicializa o toast com Bootstrap
        const toast = new bootstrap.Toast(toastElement, {
            delay: timeout
        });
        
        // Exibe o toast
        toast.show();
        
        // Remove o elemento após fechado
        toastElement.addEventListener('hidden.bs.toast', function() {
            toastElement.remove();
        });
    },
    
    /**
     * Debounce para evitar múltiplas chamadas de função
     * @param {Function} func - Função a ser executada
     * @param {Number} wait - Tempo de espera em ms
     * @returns {Function} Função com debounce
     */
    debounce: function(func, wait = 500) {
        let timeout;
        return function(...args) {
            clearTimeout(timeout);
            timeout = setTimeout(() => func.apply(this, args), wait);
        };
    },
    
    /**
     * Valida um email
     * @param {String} email - Email a ser validado
     * @returns {Boolean} Se o email é válido
     */
    isValidEmail: function(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    },
    
    /**
     * Valida um CPF
     * @param {String} cpf - CPF a ser validado
     * @returns {Boolean} Se o CPF é válido
     */
    isValidCPF: function(cpf) {
        cpf = cpf.replace(/[^\d]/g, '');
        if (cpf.length !== 11) return false;
        
        // Validações básicas
        if (/^(\d)\1{10}$/.test(cpf)) return false;
        
        // Validação dos dígitos
        let sum = 0;
        let remainder;
        
        for (let i = 1; i <= 9; i++) {
            sum = sum + parseInt(cpf.substring(i-1, i)) * (11 - i);
        }
        
        remainder = (sum * 10) % 11;
        if ((remainder === 10) || (remainder === 11)) remainder = 0;
        if (remainder !== parseInt(cpf.substring(9, 10))) return false;
        
        sum = 0;
        for (let i = 1; i <= 10; i++) {
            sum = sum + parseInt(cpf.substring(i-1, i)) * (12 - i);
        }
        
        remainder = (sum * 10) % 11;
        if ((remainder === 10) || (remainder === 11)) remainder = 0;
        if (remainder !== parseInt(cpf.substring(10, 11))) return false;
        
        return true;
    },
    
    /**
     * Faz o parsing de um valor monetário
     * @param {String} value - Valor a ser convertido
     * @returns {Number} Valor numérico
     */
    parseCurrency: function(value) {
        if (!value) return 0;
        return parseFloat(value.replace(/[^\d,.-]/g, '').replace(',', '.'));
    },
    
    /**
     * Gera um ID único simples
     * @returns {String} ID único
     */
    generateId: function() {
        return Date.now().toString(36) + Math.random().toString(36).substr(2, 5);
    },
    
    /**
     * Verifica se um objeto é vazio
     * @param {Object} obj - Objeto a ser verificado
     * @returns {Boolean} Se o objeto é vazio
     */
    isEmptyObject: function(obj) {
        return Object.keys(obj).length === 0;
    },
    
    /**
     * Detecta dispositivo móvel
     * @returns {Boolean} Se é um dispositivo móvel
     */
    isMobileDevice: function() {
        return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);
    },
    
    /**
     * Limita um texto a um determinado tamanho
     * @param {String} text - Texto a ser limitado
     * @param {Number} maxLength - Tamanho máximo
     * @param {String} suffix - Sufixo a ser adicionado se o texto for cortado
     * @returns {String} Texto limitado
     */
    truncateText: function(text, maxLength = 100, suffix = '...') {
        if (!text || text.length <= maxLength) return text;
        return text.substring(0, maxLength) + suffix;
    }
};

// Exportar para uso global
if (typeof module !== 'undefined' && module.exports) {
    module.exports = Helpers;
}