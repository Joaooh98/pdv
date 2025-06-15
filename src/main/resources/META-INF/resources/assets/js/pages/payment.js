/**
 * Controlador da página de pagamento (finish-payment.html)
 * Gerencia finalização de pedidos, aplicação de descontos e cálculos de valores
 */
class PaymentController {
    constructor() {
        this.carrinhoItems = [];
        this.valorTotalOriginal = 0;
        this.valorComDesconto = 0;
        this.dadosPagamento = null;
        this.dadosContribuinte = null;
        
        this.initialize();
    }
    
    /**
     * Inicializa a página de pagamento
     */
    initialize() {
        // Verificar autenticação
        if (!Auth.checkAuth()) return;
        
        this.carregarDadosCarrinho();
        this.setupEventListeners();
        this.setupModalEventListeners();
    }
    
    /**
     * Carrega os dados do carrinho da sessão
     */
    carregarDadosCarrinho() {
        try {
            const pedidoAtual = sessionStorage.getItem('orderData');
            if (!pedidoAtual) {
                window.location.href = 'order.html';
                return;
            }
            
            const pedido = JSON.parse(pedidoAtual);
            this.carrinhoItems = pedido.items;
            this.valorTotalOriginal = pedido.total;
            this.valorComDesconto = this.valorTotalOriginal;
            
            this.atualizarResumoPagamento();
            this.atualizarValores();
        } catch (error) {
            console.error('Erro ao carregar carrinho:', error);
            this.mostrarErro('Erro ao carregar dados do carrinho');
        }
    }
    
    /**
     * Configura os event listeners para os campos de formulário
     */
    setupEventListeners() {
        // Adiciona listeners para os inputs de valor
        ['valorDinheiro', 'valorCartao'].forEach(id => {
            const element = document.getElementById(id);
            if (element) {
                element.addEventListener('input', () => this.atualizarValores());
            }
        });
        
        // Adiciona listener para o select de tipo de desconto
        const tipoDescontoElement = document.getElementById('tipoDesconto');
        if (tipoDescontoElement) {
            tipoDescontoElement.addEventListener('change', () => {
                const valorDescontoElement = document.getElementById('valorDesconto');
                if (valorDescontoElement) {
                    valorDescontoElement.value = '';
                }
                this.atualizarValores();
            });
        }
    }
    
    /**
     * Configura os event listeners para o modal de contribuinte
     */
    setupModalEventListeners() {
        // Botão "Sim" - mostrar formulário
        const btnSimAdicionarContribuinte = document.getElementById('btnSimAdicionarContribuinte');
        if (btnSimAdicionarContribuinte) {
            btnSimAdicionarContribuinte.addEventListener('click', () => {
                this.mostrarFormularioContribuinte();
            });
        }
        
        // Botão "Não" - fechar modal e prosseguir sem dados
        const btnNaoAdicionarContribuinte = document.getElementById('btnNaoAdicionarContribuinte');
        if (btnNaoAdicionarContribuinte) {
            btnNaoAdicionarContribuinte.addEventListener('click', () => {
                this.prosseguirSemContribuinte();
            });
        }
        
        // Botão "Voltar" no formulário
        const btnVoltarEscolha = document.getElementById('btnVoltarEscolha');
        if (btnVoltarEscolha) {
            btnVoltarEscolha.addEventListener('click', () => {
                this.voltarParaEscolha();
            });
        }
        
        // Botão "Confirmar" do formulário
        const btnConfirmarContribuinte = document.getElementById('btnConfirmarContribuinte');
        if (btnConfirmarContribuinte) {
            btnConfirmarContribuinte.addEventListener('click', () => {
                this.confirmarDadosContribuinte();
            });
        }
        
        // Validação em tempo real do NIF
        const nifInput = document.getElementById('nifContribuinte');
        if (nifInput) {
            nifInput.addEventListener('input', (e) => {
                // Remove caracteres não numéricos
                e.target.value = e.target.value.replace(/\D/g, '');
                this.validarNIF(e.target.value);
            });
        }
        
        // Validação em tempo real do nome
        const nomeInput = document.getElementById('nomeContribuinte');
        if (nomeInput) {
            nomeInput.addEventListener('input', (e) => {
                this.validarNome(e.target.value);
            });
        }
        
        // Validação em tempo real do telemóvel
        const telemovelInput = document.getElementById('telemovelContribuinte');
        if (telemovelInput) {
            telemovelInput.addEventListener('input', (e) => {
                // Remove caracteres não numéricos
                e.target.value = e.target.value.replace(/\D/g, '');
                this.validarTelemovel(e.target.value);
            });
        }
    }
    
    /**
     * Atualiza a tabela de resumo de pagamento
     */
    atualizarResumoPagamento() {
        const resumoEl = document.getElementById('resumoPagamento');
        if (!resumoEl) return;
        
        if (!this.carrinhoItems || this.carrinhoItems.length === 0) {
            resumoEl.innerHTML = '<p class="text-center text-muted">Nenhum item no carrinho</p>';
            return;
        }
        
        let html = `
            <thead>
                <tr>
                    <th>Item</th>
                    <th class="text-center">Qtd</th>
                    <th class="text-end">Valor</th>
                </tr>
            </thead>
            <tbody>
        `;
        
        this.carrinhoItems.forEach(item => {
            html += `
                <tr>
                    <td>${item.name}</td>
                    <td class="text-center">${item.quantity}</td>
                    <td class="text-end">${Helpers.formatCurrency(item.price * item.quantity)}</td>
                </tr>
            `;
        });
        
        html += '</tbody>';
        resumoEl.innerHTML = html;
    }
    
    /**
     * Atualiza os valores exibidos na tela
     */
    atualizarValores() {
        const valorDinheiroEl = document.getElementById('valorDinheiro');
        const valorCartaoEl = document.getElementById('valorCartao');
        
        if (!valorDinheiroEl || !valorCartaoEl) return;
        
        const valorDinheiro = parseFloat(valorDinheiroEl.value || '0');
        const valorCartao = parseFloat(valorCartaoEl.value || '0');
        const totalPago = valorDinheiro + valorCartao;
        
        // Atualiza os valores na interface
        this.updateElement('totalOriginal', Helpers.formatCurrency(this.valorTotalOriginal));
        this.updateElement('totalDinheiro', Helpers.formatCurrency(valorDinheiro));
        this.updateElement('totalCartao', Helpers.formatCurrency(valorCartao));
        
        // Calcula o troco baseado no valor com desconto
        const troco = Math.max(0, valorDinheiro - (this.valorComDesconto - valorCartao));
        this.updateElement('totalTroco', Helpers.formatCurrency(troco));
        this.updateElement('totalFinal', Helpers.formatCurrency(this.valorComDesconto));
        
        this.validarValoresPagamento(totalPago, troco);
    }
    
    /**
     * Atualiza um elemento na interface
     */
    updateElement(id, value) {
        const element = document.getElementById(id);
        if (element) {
            element.textContent = value;
        }
    }
    
    /**
     * Valida se os valores de pagamento são suficientes
     */
    validarValoresPagamento(totalPago, troco) {
        const btnFinalizar = document.querySelector('.btn-success');
        if (!btnFinalizar) return;
        
        const valorEfetivo = totalPago - troco;
        
        if (valorEfetivo < this.valorComDesconto) {
            btnFinalizar.disabled = true;
            this.mostrarErro(`Valor pago insuficiente. Faltam ${Helpers.formatCurrency(this.valorComDesconto - valorEfetivo)}`);
        } else {
            btnFinalizar.disabled = false;
            this.limparErros();
        }
    }
    
    /**
     * Aplica o desconto ao valor total
     */
    aplicarDesconto() {
        const tipoDescontoEl = document.getElementById('tipoDesconto');
        const valorDescontoEl = document.getElementById('valorDesconto');
        
        if (!tipoDescontoEl || !valorDescontoEl) return;
        
        const tipoDesconto = tipoDescontoEl.value;
        const valorDesconto = parseFloat(valorDescontoEl.value);
        
        if (!tipoDesconto) {
            this.mostrarErro('Selecione um tipo de desconto');
            return;
        }
        
        if (isNaN(valorDesconto) || valorDesconto <= 0) {
            this.mostrarErro('Informe um valor de desconto válido');
            return;
        }
        
        if (tipoDesconto === 'PERCENTUAL' && valorDesconto > 100) {
            this.mostrarErro('Percentual de desconto não pode ser maior que 100%');
            return;
        }
        
        if (tipoDesconto === 'FIXO' && valorDesconto >= this.valorTotalOriginal) {
            this.mostrarErro('Desconto não pode ser maior que o valor total');
            return;
        }
        
        // Calcula o novo valor com desconto
        this.valorComDesconto = tipoDesconto === 'PERCENTUAL'
            ? this.valorTotalOriginal * (1 - (valorDesconto / 100))
            : this.valorTotalOriginal - valorDesconto;
        
        // Atualiza o valor do desconto na tela
        const descontoAplicado = this.valorTotalOriginal - this.valorComDesconto;
        this.updateElement('totalDesconto', Helpers.formatCurrency(descontoAplicado));
        this.updateElement('totalFinal', Helpers.formatCurrency(this.valorComDesconto));
        
        // Recalcula os valores
        this.atualizarValores();
    }
    
    /**
     * Solicita dados do contribuinte através de modal
     * @returns {Promise} Promise que resolve com os dados do contribuinte ou null
     */
    async solicitarDadosContribuinteModal() {
        return new Promise((resolve) => {
            const modal = document.getElementById('contribuinteModal');
            if (!modal) {
                resolve(null);
                return;
            }
            
            // Reset do modal para estado inicial
            this.resetarModal();
            
            // Armazenar resolve para usar nos event handlers
            this.modalResolve = resolve;
            
            // Mostrar modal
            const contribuinteModal = new bootstrap.Modal(modal);
            contribuinteModal.show();
            
            // Listener para quando o modal for fechado sem resposta
            modal.addEventListener('hidden.bs.modal', () => {
                if (this.modalResolve) {
                    this.modalResolve(null);
                    this.modalResolve = null;
                }
            }, { once: true });
        });
    }
    
    /**
     * Reseta o modal para o estado inicial
     */
    resetarModal() {
        // Limpar campos
        const nomeInput = document.getElementById('nomeContribuinte');
        const nifInput = document.getElementById('nifContribuinte');
        const telemovelInput = document.getElementById('telemovelContribuinte');
        if (nomeInput) nomeInput.value = '';
        if (nifInput) nifInput.value = '';
        if (telemovelInput) telemovelInput.value = '';
        
        // Remover classes de validação
        this.removerValidacaoVisual();
        
        // Mostrar botões de escolha, esconder formulário
        this.mostrarBotoesEscolha();
        this.esconderFormulario();
    }
    
    /**
     * Mostra o formulário de dados do contribuinte
     */
    mostrarFormularioContribuinte() {
        const dadosForm = document.getElementById('dadosContribuinteForm');
        const botoesEscolha = document.getElementById('botoesEscolha');
        const botoesFormulario = document.getElementById('botoesFormulario');
        
        if (dadosForm) dadosForm.classList.remove('d-none');
        if (botoesEscolha) botoesEscolha.classList.add('d-none');
        if (botoesFormulario) botoesFormulario.classList.remove('d-none');
        
        // Focar no primeiro campo
        const nomeInput = document.getElementById('nomeContribuinte');
        if (nomeInput) {
            setTimeout(() => nomeInput.focus(), 300);
        }
    }
    
    /**
     * Volta para a escolha inicial (Sim/Não)
     */
    voltarParaEscolha() {
        this.mostrarBotoesEscolha();
        this.esconderFormulario();
        this.removerValidacaoVisual();
    }
    
    /**
     * Mostra os botões de escolha inicial
     */
    mostrarBotoesEscolha() {
        const botoesEscolha = document.getElementById('botoesEscolha');
        const botoesFormulario = document.getElementById('botoesFormulario');
        
        if (botoesEscolha) botoesEscolha.classList.remove('d-none');
        if (botoesFormulario) botoesFormulario.classList.add('d-none');
    }
    
    /**
     * Esconde o formulário de dados
     */
    esconderFormulario() {
        const dadosForm = document.getElementById('dadosContribuinteForm');
        if (dadosForm) dadosForm.classList.add('d-none');
    }
    
    /**
     * Prossegue sem dados do contribuinte
     */
    prosseguirSemContribuinte() {
        this.fecharModal();
        if (this.modalResolve) {
            this.modalResolve(null);
            this.modalResolve = null;
        }
    }
    
    /**
     * Confirma os dados do contribuinte após validação
     */
    confirmarDadosContribuinte() {
        const nome = document.getElementById('nomeContribuinte').value.trim();
        const nif = document.getElementById('nifContribuinte').value.trim();
        const telemovel = document.getElementById('telemovelContribuinte').value.trim();
        
        if (this.validarFormularioContribuinte(nome, nif, telemovel)) {
            const dadosCliente = {
                name: nome,
                document: nif,
                phone: telemovel
            };
            
            this.fecharModal();
            if (this.modalResolve) {
                this.modalResolve(dadosCliente);
                this.modalResolve = null;
            }
        }
    }
    
    /**
     * Valida o formulário completo do contribuinte
     */
    validarFormularioContribuinte(nome, nif, telemovel) {
        let isValid = true;
        
        // Validar nome
        if (!this.validarNome(nome)) {
            isValid = false;
        }
        
        // Validar NIF
        if (!this.validarNIF(nif)) {
            isValid = false;
        }
        
        // Validar telemóvel
        if (!this.validarTelemovel(telemovel)) {
            isValid = false;
        }
        
        return isValid;
    }
    
    /**
     * Valida o nome do contribuinte
     */
    validarNome(nome) {
        const nomeInput = document.getElementById('nomeContribuinte');
        const invalidFeedback = nomeInput.parentElement.querySelector('.invalid-feedback');
        
        if (!nome || nome.length < 2) {
            nomeInput.classList.add('is-invalid');
            invalidFeedback.textContent = 'Nome deve ter pelo menos 2 caracteres';
            return false;
        }
        
        nomeInput.classList.remove('is-invalid');
        nomeInput.classList.add('is-valid');
        return true;
    }
    
    /**
     * Valida o NIF do contribuinte
     */
    validarNIF(nif) {
        const nifInput = document.getElementById('nifContribuinte');
        const invalidFeedback = nifInput.parentElement.querySelector('.invalid-feedback');
        
        if (!nif || nif.length !== 9) {
            nifInput.classList.add('is-invalid');
            invalidFeedback.textContent = 'NIF deve ter exatamente 9 dígitos';
            return false;
        }
        
        if (!/^\d{9}$/.test(nif)) {
            nifInput.classList.add('is-invalid');
            invalidFeedback.textContent = 'NIF deve conter apenas números';
            return false;
        }
        
        // Validação básica de NIF português
        if (!this.validarNIFPortugues(nif)) {
            nifInput.classList.add('is-invalid');
            invalidFeedback.textContent = 'NIF inválido';
            return false;
        }
        
        nifInput.classList.remove('is-invalid');
        nifInput.classList.add('is-valid');
        return true;
    }
    
    /**
     * Validação específica para NIF português
     */
    validarNIFPortugues(nif) {
        const multiplicadores = [9, 8, 7, 6, 5, 4, 3, 2];
        let soma = 0;
        
        for (let i = 0; i < 8; i++) {
            soma += parseInt(nif[i]) * multiplicadores[i];
        }
        
        const resto = soma % 11;
        const digitoVerificacao = resto < 2 ? 0 : 11 - resto;
        
        return digitoVerificacao === parseInt(nif[8]);
    }
    
    /**
     * Valida o telemóvel português
     */
    validarTelemovel(telemovel) {
        const telemovelInput = document.getElementById('telemovelContribuinte');
        const invalidFeedback = telemovelInput.parentElement.querySelector('.invalid-feedback');
        
        if (!telemovel || telemovel.length !== 9) {
            telemovelInput.classList.add('is-invalid');
            invalidFeedback.textContent = 'Telemóvel deve ter exatamente 9 dígitos';
            return false;
        }
        
        if (!/^\d{9}$/.test(telemovel)) {
            telemovelInput.classList.add('is-invalid');
            invalidFeedback.textContent = 'Telemóvel deve conter apenas números';
            return false;
        }
        
        // Validação específica para números portugueses (começam com 9)
        if (!telemovel.startsWith('9')) {
            telemovelInput.classList.add('is-invalid');
            invalidFeedback.textContent = 'Número de telemóvel deve começar com 9';
            return false;
        }
        
        // Validação adicional para prefixos válidos em Portugal
        const prefixosValidos = ['91', '92', '93', '96'];
        const prefixo = telemovel.substring(0, 2);
        
        if (!prefixosValidos.includes(prefixo)) {
            telemovelInput.classList.add('is-invalid');
            invalidFeedback.textContent = 'Prefixo de telemóvel inválido (deve ser 91, 92, 93 ou 96)';
            return false;
        }
        
        telemovelInput.classList.remove('is-invalid');
        telemovelInput.classList.add('is-valid');
        return true;
    }
    
    /**
     * Remove validação visual dos campos
     */
    removerValidacaoVisual() {
        const inputs = ['nomeContribuinte', 'nifContribuinte', 'telemovelContribuinte'];
        inputs.forEach(id => {
            const input = document.getElementById(id);
            if (input) {
                input.classList.remove('is-valid', 'is-invalid');
            }
        });
    }
    
    /**
     * Fecha o modal de contribuinte
     */
    fecharModal() {
        const modal = document.getElementById('contribuinteModal');
        if (modal) {
            const contribuinteModal = bootstrap.Modal.getInstance(modal);
            if (contribuinteModal) {
                contribuinteModal.hide();
            }
        }
    }
    
    /**
     * Finaliza o pagamento e envia para o backend
     */
    async finalizarPagamento() {
        try {
            // Validar valores primeiro
            const valorDinheiro = parseFloat(document.getElementById('valorDinheiro').value || '0');
            const valorCartao = parseFloat(document.getElementById('valorCartao').value || '0');
            const descontoText = document.getElementById('totalDesconto').textContent;
            const trocoText = document.getElementById('totalTroco').textContent;
            
            const desconto = Helpers.parseCurrency(descontoText) || 0;
            const troco = Helpers.parseCurrency(trocoText) || 0;
            
            const totalPago = valorDinheiro + valorCartao;
            const totalFinal = this.valorTotalOriginal - desconto;
            
            if (totalPago - troco < totalFinal) {
                this.mostrarErro('O valor pago é insuficiente.');
                return;
            }
            
            // Solicitar dados do contribuinte através do modal
            this.dadosContribuinte = await this.solicitarDadosContribuinteModal();
            
            // Prosseguir com a finalização após o modal
            await this.processarPagamento();
            
        } catch (error) {
            console.error('Erro ao finalizar pagamento:', error);
            this.mostrarErroModal(
                'Ocorreu um erro ao finalizar o pagamento. Detalhes do erro abaixo:',
                {
                    dadosEnviados: this.dadosPagamento,
                    erro: error.message || 'Erro desconhecido'
                }
            );
        }
    }
    
    /**
     * Processa o pagamento enviando os dados para o backend
     */
    async processarPagamento() {
        const professionalId = Auth.getUserId();
        if (!professionalId) {
            this.mostrarErro('Sessão inválida. Por favor, faça login novamente.');
            return;
        }
        
        const tipoDescontoEl = document.getElementById('tipoDesconto');
        const valorDescontoEl = document.getElementById('valorDesconto');
        const valorDinheiro = parseFloat(document.getElementById('valorDinheiro').value || '0');
        const valorCartao = parseFloat(document.getElementById('valorCartao').value || '0');
        
        const pagamento = {
            professionalId: professionalId,
            amount: this.valorComDesconto,
            discount: {
                type: tipoDescontoEl ? tipoDescontoEl.value || null : null,
                amount: valorDescontoEl ? parseFloat(valorDescontoEl.value || '0') : 0
            },
            payments: [
                ...(valorCartao > 0 ? [{
                    paymentType: 'CARD',
                    amount: valorCartao
                }] : []),
                ...(valorDinheiro > 0 ? [{
                    paymentType: 'CASH',
                    amount: valorDinheiro
                }] : [])
            ],
            items: this.carrinhoItems.map(item => ({
                product: {
                    id: item.id,
                    description: item.name,
                    amount: item.price,
                    quantity: item.quantity
                }
            }))
        };
        
        // Adicionar dados do cliente se fornecidos
        if (this.dadosContribuinte) {
            pagamento.customer = this.dadosContribuinte;
        }
        
        // Armazena os dados do pagamento
        this.dadosPagamento = pagamento;
        
        await API.post('/checkout-api/order', pagamento);
        
        // Limpa dados da sessão e do carrinho após finalizar o pagamento
        sessionStorage.removeItem('orderData');
        sessionStorage.removeItem('carrinho');
        localStorage.removeItem(CONFIG.STORAGE.CART_KEY);
        Cart.clearCart();

        Helpers.showToast('Pedido realizado com sucesso!', 'success');
        setTimeout(() => {
            window.location.href = 'order.html';
        }, 1500);
    }
    
    /**
     * Exibe erro em um modal
     */
    mostrarErroModal(mensagem, dados) {
        const modal = document.getElementById('errorModal');
        if (modal) {
            const errorMessage = document.getElementById('errorMessage');
            const requestData = document.getElementById('requestData');
            
            if (errorMessage) errorMessage.textContent = mensagem;
            if (requestData) requestData.textContent = JSON.stringify(dados, null, 2);
            
            const errorModal = new bootstrap.Modal(modal);
            errorModal.show();
        }
    }
    
    /**
     * Exibe mensagem de erro inline
     */
    mostrarErro(mensagem) {
        const paymentForm = document.querySelector('.payment-form');
        let errorDiv = document.querySelector('.alert-danger');
        
        if (!errorDiv && paymentForm) {
            errorDiv = document.createElement('div');
            errorDiv.className = 'alert alert-danger mt-3';
            paymentForm.appendChild(errorDiv);
        }
        
        if (errorDiv) {
            errorDiv.textContent = mensagem;
        }
    }
    
    /**
     * Remove mensagens de erro
     */
    limparErros() {
        const errorDiv = document.querySelector('.alert-danger');
        if (errorDiv) {
            errorDiv.remove();
        }
    }
    
    /**
     * Copia os dados de erro para a área de transferência
     */
    async copiarErro() {
        const requestData = document.getElementById('requestData');
        if (requestData) {
            try {
                await navigator.clipboard.writeText(requestData.textContent);
                Helpers.showToast('JSON copiado para a área de transferência', 'success');
            } catch (error) {
                console.error('Erro ao copiar:', error);
                Helpers.showToast('Erro ao copiar o JSON', 'danger');
            }
        }
    }
    
    /**
     * Volta para a tela de pedidos
     */
    voltarParaPedido() {
        window.location.href = 'order.html';
    }
    
    /**
     * Volta para a tela de carrinhos após erro
     */
    voltarAoCarrinho() {
        window.location.href = 'order.html';
    }
}

// Inicializa a página quando o DOM estiver pronto
document.addEventListener('DOMContentLoaded', () => {
    // Verifica autenticação primeiro
    Auth.initialize();
    
    // Se o usuário estiver autenticado, inicializa o controller
    if (Auth.isAuthenticated) {
        window.Payment = new PaymentController();
    }
});