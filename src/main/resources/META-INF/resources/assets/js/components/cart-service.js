/**
 * Serviço de Carrinho - Gerencia itens do carrinho de compras
 */
class CartService {
    constructor() {
        this.items = [];
        this.storageKey = CONFIG.STORAGE.CART_KEY;
        this.onChangeCallbacks = [];
        this.loadFromStorage();
    }

    /**
     * Carrega os itens do carrinho do localStorage
     */
    loadFromStorage() {
        const storedItems = localStorage.getItem(this.storageKey);
        if (storedItems) {
            try {
                this.items = JSON.parse(storedItems);
            } catch (e) {
                console.error('Erro ao carregar itens do carrinho:', e);
                this.items = [];
            }
        }
    }

    /**
     * Salva os itens do carrinho no localStorage
     */
    saveToStorage() {
        localStorage.setItem(this.storageKey, JSON.stringify(this.items));
        this.notifyChange();
    }

    /**
     * Adiciona um callback para ser executado quando o carrinho mudar
     * @param {Function} callback - Função a ser executada
     */
    onChange(callback) {
        if (typeof callback === 'function') {
            this.onChangeCallbacks.push(callback);
        }
    }

    /**
     * Notifica todas as callbacks registradas sobre mudanças no carrinho
     */
    notifyChange() {
        this.onChangeCallbacks.forEach(callback => {
            try {
                callback(this.items);
            } catch (e) {
                console.error('Erro ao executar callback de mudança do carrinho:', e);
            }
        });
    }

    /**
     * Obtém todos os itens do carrinho
     * @returns {Array} Lista de itens do carrinho
     */
    getItems() {
        return [...this.items];
    }

    /**
     * Adiciona um item ao carrinho
     * @param {Object} item - Item a ser adicionado
     */
    addItem(item) {
        // Verifica se o item já existe no carrinho
        const existingItemIndex = this.items.findIndex(
            i => i.id === item.id && i.type === item.type
        );

        if (existingItemIndex >= 0) {
            // Se existir, incrementa a quantidade
            this.items[existingItemIndex].quantity += 1;
        } else {
            // Se não existir, adiciona como novo item
            this.items.push({
                ...item,
                quantity: 1,
                cartItemId: Helpers.generateId() // ID único para o item no carrinho
            });
        }

        this.saveToStorage();
    }

    /**
     * Remove um item do carrinho
     * @param {String} cartItemId - ID do item no carrinho
     */
    removeItem(cartItemId) {
        this.items = this.items.filter(item => item.cartItemId !== cartItemId);
        this.saveToStorage();
    }

    /**
     * Atualiza a quantidade de um item
     * @param {String} cartItemId - ID do item no carrinho
     * @param {Number} quantity - Nova quantidade
     */
    updateQuantity(cartItemId, quantity) {
        const item = this.items.find(item => item.cartItemId === cartItemId);
        
        if (item) {
            if (quantity <= 0) {
                // Se a quantidade for 0 ou negativa, remove o item
                this.removeItem(cartItemId);
            } else {
                // Atualiza a quantidade
                item.quantity = quantity;
                this.saveToStorage();
            }
        }
    }

    /**
     * Limpa todos os itens do carrinho
     */
    clearCart() {
        this.items = [];
        this.saveToStorage();
    }

    /**
     * Calcula o total do carrinho
     * @returns {Number} Valor total dos itens no carrinho
     */
    getTotal() {
        return this.items.reduce((total, item) => {
            return total + (item.price * item.quantity);
        }, 0);
    }

    /**
     * Verifica se o carrinho está vazio
     * @returns {Boolean} True se o carrinho estiver vazio
     */
    isEmpty() {
        return this.items.length === 0;
    }

    /**
     * Conta o número total de itens no carrinho (considerando quantidades)
     * @returns {Number} Número total de itens
     */
    getTotalItems() {
        return this.items.reduce((count, item) => count + item.quantity, 0);
    }

    /**
     * Prepara os dados do carrinho para enviar ao backend
     * @returns {Object} Dados do carrinho formatados para API
     */
    prepareOrderData() {
        const items = this.items.map(item => ({
            id: item.id,
            type: item.type,
            quantity: item.quantity,
            price: item.price,
            name: item.name
        }));

        return {
            items,
            total: this.getTotal(),
            createdAt: new Date().toISOString()
        };
    }
}

// Cria uma instância única do serviço para uso global
const Cart = new CartService();

// Exportar para uso global
if (typeof module !== 'undefined' && module.exports) {
    module.exports = Cart;
} 