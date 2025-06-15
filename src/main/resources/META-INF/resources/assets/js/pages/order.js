/**
 * Controlador da página de pedidos (order.html)
 * Gerencia produtos, serviços e carrinho de compras
 */
class OrderController {
    constructor() {
        this.productsContainer = document.getElementById('listaProdutos');
        this.servicesContainer = document.getElementById('listaServicos');
        this.cartContainer = document.getElementById('resumoItens');
        this.totalElement = document.getElementById('totalPedido');
        this.checkoutButton = document.querySelector('.btn-finalizar');

        this.products = [];
        this.services = [];

        // Inicializa a página
        this.initialize();
    }

    /**
     * Inicializa a página de pedidos
     */
    initialize() {
        // Limpa o carrinho ao carregar a página de pedidos
        Cart.clearCart();

        // Carrega produtos e serviços
        this.loadProducts();
        this.loadServices();

        // Configura o botão de finalizar pedido
        this.setupCheckoutButton();

        // Configura o carrinho e atualiza a UI
        this.setupCart();

        // Verifica se o checkout deve ser habilitado
        this.updateCheckoutButton();
    }

    /**
     * Carrega a lista de produtos da API
     */
    async loadProducts() {
        try {
            this.productsContainer.innerHTML = this.getLoadingHTML();

            // Fazendo a chamada à API para obter os produtos
            const response = await fetch(`${CONFIG.API.ENDPOINTS.PRODUCTS}?type=PRODUCT`);
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Erro ao buscar produtos da API');
            }

            const products = await response.json();

            this.products = products.map(product => ({
                id: product.id || '',
                name: product.description || 'Produto sem nome',
                price: product.amountSale || 0
            }));

            this.renderProducts();
        } catch (error) {
            console.error('Erro ao carregar produtos:', error);
            this.productsContainer.innerHTML = this.getErrorHTML('Não foi possível carregar os produtos');
        }
    }

    /**
     * Carrega a lista de serviços da API
     */
    async loadServices() {
        try {
            this.servicesContainer.innerHTML = this.getLoadingHTML();

            const response = await fetch(`${CONFIG.API.ENDPOINTS.PRODUCTS}?type=SERVICE`);

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Erro ao buscar servicos da API');
            }

            const services = await response.json();

            this.services = services.map(service => ({
                id: service.id || '',
                name: service.description || 'Produto sem nome',
                price: service.amountSale || 0
            }));


            this.renderServices();
        } catch (error) {
            console.error('Erro ao carregar serviços:', error);
            this.servicesContainer.innerHTML = this.getErrorHTML('Não foi possível carregar os serviços');
        }
    }

    /**
     * Renderiza a lista de produtos na UI
     */
    renderProducts() {
        if (!this.products.length) {
            this.productsContainer.innerHTML = '<p class="text-muted text-center p-3">Nenhum produto encontrado</p>';
            return;
        }

        const html = this.products.map(product => this.createItemHTML(product, 'product')).join('');
        this.productsContainer.innerHTML = html;

        // Adiciona eventos de click aos botões
        this.addEventListeners('product');
    }

    /**
     * Renderiza a lista de serviços na UI
     */
    renderServices() {
        if (!this.services.length) {
            this.servicesContainer.innerHTML = '<p class="text-muted text-center p-3">Nenhum serviço encontrado</p>';
            return;
        }

        const html = this.services.map(service => this.createItemHTML(service, 'service')).join('');
        this.servicesContainer.innerHTML = html;

        // Adiciona eventos de click aos botões
        this.addEventListeners('service');
    }

    /**
     * Cria o HTML para um item (produto ou serviço)
     * @param {Object} item - Item a ser renderizado
     * @param {String} type - Tipo do item (product ou service)
     * @returns {String} HTML do item
     */
    createItemHTML(item, type) {
        const formattedPrice = Helpers.formatCurrency(item.price);

        return `
            <div class="item-row" data-id="${item.id}" data-type="${type}">
                <div class="item-info">
                    <div class="item-name">${item.name}</div>
                </div>
                <div class="item-price">${formattedPrice}</div>
                <button class="btn btn-add" data-id="${item.id}" data-type="${type}">
                    <i class="fas fa-plus"></i>
                </button>
            </div>
        `;
    }

    /**
     * Adiciona event listeners aos botões de adicionar
     * @param {String} type - Tipo dos itens (product ou service)
     */
    addEventListeners(type) {
        const buttons = document.querySelectorAll(`.btn-add[data-type="${type}"]`);

        buttons.forEach(button => {
            button.addEventListener('click', () => {
                const id = button.getAttribute('data-id');
                this.addToCart(id, type);
            });
        });
    }

    /**
     * Adiciona um item ao carrinho
     * @param {String} id - ID do item
     * @param {String} type - Tipo do item (product ou service)
     */
    addToCart(id, type) {
        let item;

        if (type === 'product') {
            item = this.products.find(p => p.id == id);
        } else {
            item = this.services.find(s => s.id == id);
        }

        if (item) {
            Cart.addItem({
                id,
                type,
                name: item.name,
                price: item.price
            });

            // Feedback visual para o usuário
            Helpers.showToast(`${item.name} adicionado ao carrinho`, 'success');
        }
    }

    /**
     * Configura o carrinho e seus listeners
     */
    setupCart() {
        // Atualiza a UI do carrinho quando for alterado
        Cart.onChange(items => {
            this.renderCart(items);
            this.updateTotal();
            this.updateCheckoutButton();
        });

        // Renderiza o carrinho inicialmente
        this.renderCart(Cart.getItems());
        this.updateTotal();
    }

    /**
     * Renderiza os itens do carrinho na UI
     * @param {Array} items - Itens do carrinho
     */
    renderCart(items) {
        if (!items.length) {
            this.cartContainer.innerHTML = '<p class="text-muted text-center p-3">Nenhum item adicionado ao pedido</p>';
            return;
        }

        const html = items.map(item => `
            <div class="cart-item" data-id="${item.cartItemId}">
                <div class="cart-item-name">${item.name}</div>
                <div class="cart-item-quantity">${item.quantity}</div>
                <div class="cart-item-price">${Helpers.formatCurrency(item.price * item.quantity)}</div>
                <button class="btn btn-danger btn-sm cart-item-remove" data-id="${item.cartItemId}">
                    <i class="fas fa-times"></i>
                </button>
            </div>
        `).join('');

        this.cartContainer.innerHTML = html;

        // Adiciona eventos aos botões de remover
        const removeButtons = document.querySelectorAll('.cart-item-remove');
        removeButtons.forEach(button => {
            button.addEventListener('click', () => {
                const id = button.getAttribute('data-id');
                Cart.removeItem(id);
            });
        });
    }

    /**
     * Atualiza o total exibido
     */
    updateTotal() {
        const total = Cart.getTotal();
        this.totalElement.textContent = `Total do Pedido: ${Helpers.formatCurrency(total)}`;
    }

    /**
     * Atualiza o botão de finalizar pedido (habilita/desabilita)
     */
    updateCheckoutButton() {
        const isEmpty = Cart.isEmpty();
        this.checkoutButton.disabled = isEmpty;
    }

    /**
     * Configura o botão de finalizar pedido
     */
    setupCheckoutButton() {
        this.checkoutButton.addEventListener('click', () => {
            // Em uma implementação real, isso seria manipulado por uma função
            // que redireciona para a página de pagamento
            this.goToPayment();
        });
    }

    /**
     * Redireciona para a página de pagamento
     */
    goToPayment() {
        // Salva dados do pedido na sessão, se necessário
        sessionStorage.setItem('orderData', JSON.stringify(Cart.prepareOrderData()));

        // Redireciona para a página de pagamento
        window.location.href = '/finish-payment.html';
    }

    /**
     * Retorna o HTML para indicar carregamento
     * @returns {String} HTML do indicador de carregamento
     */
    getLoadingHTML() {
        return `
            <div class="text-center p-3">
                <div class="spinner-border text-info" role="status">
                    <span class="visually-hidden">Carregando...</span>
                </div>
            </div>
        `;
    }

    /**
     * Retorna o HTML para indicar erro
     * @param {String} message - Mensagem de erro
     * @returns {String} HTML do indicador de erro
     */
    getErrorHTML(message) {
        return `
            <div class="text-center p-3 text-danger">
                <i class="fas fa-exclamation-circle"></i> ${message}
            </div>
        `;
    }

}

// Inicializa o controller quando o DOM estiver pronto
document.addEventListener('DOMContentLoaded', () => {
    // Verifica autenticação primeiro
    Auth.initialize();

    // Se o usuário estiver autenticado, inicializa o controller
    if (Auth.isAuthenticated) {
        window.orderController = new OrderController();
    }
});