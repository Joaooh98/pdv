/**
 * Sistema de Gestão de Pedidos
 * report.js - Funções de gerenciamento de dados e interface na tela de relatórios
 */

document.addEventListener('DOMContentLoaded', () => {
    // Verificar autenticação antes de qualquer coisa
    checkAuthentication();
});

/**
 * Verifica se o usuário está autenticado e inicializa a aplicação
 */
function checkAuthentication() {
    if (typeof Auth === 'undefined') {
        loadAuthenticationScripts(() => {
            if (!Auth.checkAuth()) {
                window.location.href = '/index.html?redirect=reportOrder.html';
                return;
            }
            initializeApp();
        });
    } else {
        if (!Auth.checkAuth()) {
            window.location.href = '/index.html?redirect=reportOrder.html';
            return;
        }
        initializeApp();
    }
}

/**
 * Inicializa a aplicação após autenticação confirmada
 */
function initializeApp() {
    const today = new Date();
    const formattedDate = formatDateForInput(today);
    
    document.getElementById('startDate').value = formattedDate;
    document.getElementById('endDate').value = formattedDate;

    document.getElementById('dateFilterForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;
        
        if (startDate && endDate) {
            if (new Date(startDate) > new Date(endDate)) {
                showError('A data inicial não pode ser posterior à data final.');
                return;
            }
            
            fetchData(startDate, endDate);
        } else {
            showError('Por favor, selecione as datas inicial e final.');
        }
    });
    
    initTabs();
    
    fetchData(formattedDate, formattedDate);
}

/**
 * Carrega scripts necessários para autenticação
 * @param {Function} callback - Função a ser executada após carregar scripts
 */
function loadAuthenticationScripts(callback) {
    const scriptsToLoad = [
        'assets/js/utils/config.js',
        'assets/js/utils/helpers.js',
        'assets/js/auth/auth-service.js'
    ];
    
    let loadedScripts = 0;
    
    scriptsToLoad.forEach(src => {
        const script = document.createElement('script');
        script.src = src;
        script.onload = () => {
            loadedScripts++;
            if (loadedScripts === scriptsToLoad.length) {
                callback();
            }
        };
        document.head.appendChild(script);
    });
}

/**
 * Obtém o ID do profissional do usuário logado
 * @returns {string|null} ID do profissional ou null se não encontrado
 */
function getProfessionalId() {
    try {
        // Tenta obter o ID do profissional através do módulo Auth
        if (typeof Auth !== 'undefined' && Auth.getCurrentUser) {
            const currentUser = Auth.getCurrentUser();
            return currentUser?.professionalId || currentUser?.id || null;
        }
        
        // Alternativa: tentar obter de localStorage se disponível
        const userData = localStorage.getItem('currentUser');
        if (userData) {
            const user = JSON.parse(userData);
            return user.professionalId || user.id || null;
        }
        
        return null;
    } catch (error) {
        console.error('Erro ao obter ID do profissional:', error);
        return null;
    }
}

/**
 * Inicializa o comportamento das abas
 */
function initTabs() {
    const tabs = document.querySelectorAll('.tab');
    
    tabs.forEach(tab => {
        tab.addEventListener('click', () => {
            // Remove a classe active de todas as tabs
            tabs.forEach(t => t.classList.remove('active'));
            
            // Adiciona a classe active à tab clicada
            tab.classList.add('active');
            
            // Mostra o conteúdo correspondente
            const tabContentId = tab.getAttribute('data-tab') + 'Tab';
            const tabContents = document.querySelectorAll('.tab-content');
            
            tabContents.forEach(content => {
                content.classList.remove('active');
            });
            
            document.getElementById(tabContentId).classList.add('active');
        });
    });
}

/**
 * Formata uma data como string para o formato aceito pelos inputs de data
 * @param {Date} date - Objeto de data a ser formatado
 * @returns {string} Data formatada como YYYY-MM-DD
 */
function formatDateForInput(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

/**
 * Formata uma data para o formato da API
 * @param {string} dateString - Data no formato YYYY-MM-DD
 * @returns {string} Data formatada para a API
 */
function formatDateForApi(dateString) {
    return dateString;
}

/**
 * Busca os dados da API conforme o intervalo de datas selecionado
 * @param {string} startDateString - Data inicial no formato YYYY-MM-DD
 * @param {string} endDateString - Data final no formato YYYY-MM-DD
 */
async function fetchData(startDateString, endDateString) {
    try {
        // Obter o ID do profissional
        const professionalId = getProfessionalId();
        
        if (!professionalId) {
            showError('Não foi possível identificar o profissional. Por favor, faça login novamente.');
            return;
        }
        
        // Resetar elementos
        document.getElementById('loading').style.display = 'flex';
        document.getElementById('summarySection').style.display = 'none';
        document.getElementById('tabContainer').style.display = 'none';
        document.getElementById('errorMessage').style.display = 'none';
        
        const formattedStartDate = formatDateForApi(startDateString);
        const formattedEndDate = formatDateForApi(endDateString);
        
        // URL da API com as datas selecionadas e o ID do profissional
        const apiUrl = `/checkout-api/orders?dateCreate=${formattedStartDate}&dateEnd=${formattedEndDate}&professionalId=${professionalId}`;
        
        const response = await fetch(apiUrl, {
            credentials: 'include', 
            headers: {
                'Accept': 'application/json'
            }
        });
        
        if (!response.ok) {
            if (response.status === 401) {
                window.location.href = '/index.html?redirect=reportOrder.html';
                return;
            }
            throw new Error(`Erro na requisição: ${response.status}`);
        }
        
        const data = await response.json();

        // Oculta o indicador de carregamento
        document.getElementById('loading').style.display = 'none';
        
        // Exibe as seções de conteúdo
        document.getElementById('summarySection').style.display = 'flex';
        document.getElementById('tabContainer').style.display = 'block';
        
        renderSummary(data);
        renderProfessionals(data.infos || []);
        renderOrders(data.orders || []);
        
    } catch (error) {
        console.error('Erro ao carregar dados:', error);
        
        document.getElementById('loading').style.display = 'none';
        showError(`Ocorreu um erro ao carregar os dados: ${error.message}. Por favor, tente novamente mais tarde.`);
    }
}

/**
 * Exibe uma mensagem de erro
 * @param {string} message - Mensagem de erro a ser exibida
 */
function showError(message) {
    const errorElement = document.getElementById('errorMessage');
    errorElement.textContent = message;
    errorElement.style.display = 'block';
}

/**
 * Renderiza o resumo de dados
 * @param {Object} data - Dados recebidos da API
 */
function renderSummary(data) {
    document.getElementById('totalOrders').textContent = data.totalOrders || 0;
    document.getElementById('totalAmount').textContent = formatCurrency(data.totalAmount || 0);
    document.getElementById('totalCard').textContent = formatCurrency(data.totalAmountCard || 0);
    document.getElementById('totalCash').textContent = formatCurrency(data.totalAmountCash || 0);
}

/**
 * Renderiza os cards de profissionais
 * @param {Array} professionals - Array de dados dos profissionais
 */
function renderProfessionals(professionals) {
    const container = document.getElementById('professionalsGrid');
    container.innerHTML = '';
    
    if (!professionals || professionals.length === 0) {
        container.innerHTML = '<p class="text-center text-muted py-4">Nenhum profissional encontrado no período selecionado.</p>';
        return;
    }
    
    professionals.forEach(professional => {
        const card = document.createElement('div');
        card.className = 'professional-card';
        
        const name = sanitizeText(professional.name || 'Nome não informado');
        const amount = professional.amount || 0;
        const amountCard = professional.amountCard || 0;
        const amountCash = professional.amountCash || 0;
        const amountComission = professional.amountComission || 0;
        
        card.innerHTML = `
            <div class="professional-name">${name}</div>
            <div class="professional-details">
                <div class="detail-item">
                    <div class="detail-label">TOTAL</div>
                    <div class="detail-value">${formatCurrency(amount)}</div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">CARTÃO</div>
                    <div class="detail-value">${formatCurrency(amountCard)}</div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">DINHEIRO</div>
                    <div class="detail-value">${formatCurrency(amountCash)}</div>
                </div>
                <div class="detail-item">
                    <div class="detail-label">COMISSÃO</div>
                    <div class="detail-value">${formatCurrency(amountComission)}</div>
                </div>
            </div>
        `;
        
        container.appendChild(card);
    });
}

/**
 * Renderiza a tabela de pedidos
 * @param {Array} orders - Array de dados dos pedidos
 */
function renderOrders(orders) {
    const tableBody = document.getElementById('ordersTableBody');
    tableBody.innerHTML = '';
    
    if (!orders || orders.length === 0) {
        const emptyRow = document.createElement('tr');
        emptyRow.innerHTML = `<td colspan="7" style="text-align: center; padding: 20px;">Nenhum pedido encontrado para o período selecionado</td>`;
        tableBody.appendChild(emptyRow);
        return;
    }
    
    orders.forEach(order => {
        const row = document.createElement('tr');
        
        let dateCreate = new Date();
        try {
            dateCreate = new Date(order.dateCreate);
        } catch (e) {
            console.warn('Erro ao parsear data:', e);
        }
        
        const formattedDate = dateCreate.toLocaleDateString('pt-BR');
        const formattedTime = dateCreate.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
        
        const professionalName = sanitizeText(order.professional?.name || 'Não informado');
        const customerName = sanitizeText(order.customer?.name || 'Não identificado');
        
        const productDescription = sanitizeText(order.items?.[0]?.product?.description || 'Não especificado');
        const paymentType = order.payments?.[0]?.paymentType || 'Não especificado';
        const paymentClass = paymentType === 'Dinheiro' ? 'status-cash' : 'status-card';
        
        row.innerHTML = `
            <td>${formattedDate}<br><small>${formattedTime}</small></td>
            <td>${professionalName}</td>
            <td>${customerName}</td>
            <td>${productDescription}</td>
            <td>${formatCurrency(order.amount || 0)}</td>
            <td><span class="status-badge ${paymentClass}">${sanitizeText(paymentType)}</span></td>
            <td>${formatCurrency(order.amountCommission || 0)}</td>
        `;
        
        tableBody.appendChild(row);
    });
}

/**
 * Sanitiza texto para prevenir XSS
 * @param {string} text - Texto a ser sanitizado
 * @returns {string} Texto sanitizado
 */
function sanitizeText(text) {
    if (!text) return '';
    const element = document.createElement('div');
    element.textContent = text;
    return element.innerHTML;
}

/**
 * Formata um valor numérico como moeda
 * @param {number} value - Valor a ser formatado
 * @returns {string} Valor formatado como moeda (€)
 */
function formatCurrency(value) {
    // Verifica se o valor é um número válido
    const numericValue = parseFloat(value);
    if (isNaN(numericValue)) {
        return '€0,00';
    }
    
    return '€' + numericValue.toFixed(2).replace('.', ',');
}