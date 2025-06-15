/**
 * Serviço de Autenticação - Gerencia login, registro e sessão de usuário
 */
class AuthService {
    constructor() {
        this.userKey = CONFIG.STORAGE.USER_KEY;
        this.loggedInKey = CONFIG.STORAGE.LOGGED_IN_KEY;
        this.lastActivityKey = CONFIG.STORAGE.LAST_ACTIVITY_KEY;
        this.sessionTimeout = CONFIG.SESSION.TIMEOUT;
        this.sessionTimer = null;
        this.sessionCheckInterval = null;
        this.isAuthenticated = this.checkAuth();
    }

    /**
     * Codifica string para base64
     * @param {String} str - String a ser codificada
     * @returns {String} String codificada em base64
     */
    encodeToBase64(str) {
        try {
            return btoa(unescape(encodeURIComponent(str)));
        } catch (e) {
            console.error('Erro ao codificar para base64:', e);
            return '';
        }
    }

    /**
     * Verifica se o usuário está autenticado
     * @returns {Boolean} Status de autenticação
     */
    checkAuth() {
        try {
            const userData = localStorage.getItem(this.userKey);
            if (!userData) return false;
            
            // Verificar se a sessão expirou
            const lastActivity = localStorage.getItem(this.lastActivityKey);
            if (lastActivity) {
                const now = Date.now();
                const lastActivityTime = parseInt(lastActivity);
                
                if (now - lastActivityTime > this.sessionTimeout) {
                    // Sessão expirada, fazer logout silenciosamente
                    this.clearAuthData();
                    return false;
                }
            }
            
            return true;
        } catch (e) {
            console.error('Erro ao verificar autenticação:', e);
            return false;
        }
    }

    /**
     * Obtém os dados do usuário logado
     * @returns {Object|null} Dados do usuário ou null se não estiver logado
     */
    getCurrentUser() {
        try {
            const userStr = localStorage.getItem(this.userKey);
            return userStr ? JSON.parse(userStr) : null;
        } catch (e) {
            console.error('Erro ao obter usuário atual:', e);
            return null;
        }
    }

    /**
     * Obtém o ID do usuário logado
     * @returns {String|null} ID do usuário ou null se não estiver logado
     */
    getUserId() {
        try {
            const userData = this.getCurrentUser();
            return userData ? userData.id : null;
        } catch (e) {
            console.error('Erro ao obter ID do usuário:', e);
            return null;
        }
    }

    /**
     * Reseta o timer de sessão
     */
    resetSession() {
        // Limpa o timer existente
        if (this.sessionTimer) {
            clearTimeout(this.sessionTimer);
        }
        
        // Define um novo timer
        this.sessionTimer = setTimeout(() => {
            console.log('Sessão expirada por inatividade');
            this.logout(true);
        }, this.sessionTimeout);
        
        // Atualiza o timestamp da última atividade
        localStorage.setItem(this.lastActivityKey, Date.now().toString());
    }
    
    /**
     * Inicia verificação periódica da sessão
     */
    startSessionCheck() {
        // Limpa o intervalo existente
        if (this.sessionCheckInterval) {
            clearInterval(this.sessionCheckInterval);
        }
        
        // Define um novo intervalo para verificar a sessão periodicamente
        this.sessionCheckInterval = setInterval(() => {
            if (!this.checkAuth()) {
                this.logout(true);
            }
        }, CONFIG.SESSION.CHECK_INTERVAL);
    }

    /**
     * Limpa os dados de autenticação
     */
    clearAuthData() {
        localStorage.removeItem(this.loggedInKey);
        localStorage.removeItem(this.lastActivityKey);
        localStorage.removeItem(this.userKey);
        
        // Limpar os timers
        if (this.sessionTimer) {
            clearTimeout(this.sessionTimer);
            this.sessionTimer = null;
        }
        
        if (this.sessionCheckInterval) {
            clearInterval(this.sessionCheckInterval);
            this.sessionCheckInterval = null;
        }
    }

    /**
     * Faz login do usuário
     * @param {String} usuario - Usuário ou email
     * @param {String} senha - Senha
     * @returns {Promise} Promise com o resultado do login
     */
    async login(usuario, senha) {
        try {
            const loginData = { usuario, senha };
            const encodedData = {
                data: this.encodeToBase64(JSON.stringify(loginData))
            };

            const response = await fetch(`/professional/token`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(encodedData)
            });

            if (!response.ok) {
                throw new Error('Credenciais inválidas ou servidor indisponível');
            }

            const professional = await response.json();

            if (professional) {
                localStorage.setItem(this.userKey, JSON.stringify(professional));
                localStorage.setItem(this.loggedInKey, 'true');
                localStorage.setItem(this.lastActivityKey, Date.now().toString());
                
                this.resetSession();
                this.startSessionCheck();
                
                return professional;
            } else {
                throw new Error('Profissional não encontrado');
            }
        } catch (error) {
            console.error('Erro ao fazer login:', error);
            throw error;
        }
    }

    /**
     * Faz logout do usuário
     * @param {Boolean} isSessionExpired - Indica se o logout ocorreu por expiração da sessão
     */
    logout(isSessionExpired = false) {
        this.clearAuthData();
        
        if (isSessionExpired) {
            // Se a sessão expirou, redirecionar com mensagem de expiração
            sessionStorage.setItem('auth_message', CONFIG.MESSAGES.ERROR.SESSION_EXPIRED);
        }
        
        window.location.href = '/index.html';
    }

    /**
     * Inicializa o serviço de autenticação
     */
    initialize() {
        // Verificar se existe uma mensagem de autenticação
        const authMessage = sessionStorage.getItem('auth_message');
        if (authMessage) {
            Helpers.showToast(authMessage, 'warning');
            sessionStorage.removeItem('auth_message');
        }
        
        if (window.location.pathname === '/index.html' || window.location.pathname === '/') {
            this.setupLoginEvents();
        } else {
            // Verifica a sessão em todas as outras páginas
            if (!this.checkAuth()) {
                this.logout();
                return;
            }
            
            // Reinicia a sessão e começa a monitorá-la
            this.resetSession();
            this.startSessionCheck();
            
            // Adiciona listeners para resetar o timer de sessão
            ['click', 'keypress', 'scroll', 'mousemove', 'touchstart'].forEach(event => {
                document.addEventListener(event, () => this.resetSession());
            });
        }
    }

    /**
     * Configura os eventos para o formulário de login
     */
    setupLoginEvents() {
        const loginForm = document.getElementById('loginForm');
        if (!loginForm) return;

        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const usuario = document.getElementById('usuario').value.trim();
            const senha = document.getElementById('senha').value.trim();
            
            try {
                await this.login(usuario, senha);
                window.location.href = '/order.html';
            } catch (error) {
                Helpers.showToast(error.message || 'Falha no login. Verifique suas credenciais.', 'danger');
            }
        });
    }
}

// Cria uma instância única do serviço para uso global
const Auth = new AuthService();

// Exportar para uso global
if (typeof module !== 'undefined' && module.exports) {
    module.exports = Auth;
}