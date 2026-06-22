const BASE_URL = '/api';

function getHeaders() {
  const token = sessionStorage.getItem('token');
  return {
    'Content-Type': 'application/json',
    ...(token && { 'token': token })
  };
}

export const api = {
  login: async (perfil, email, senha) => {
    let endpoint = '';
    if (perfil === 'Cliente') endpoint = '/clientes/login';
    else if (perfil === 'Cozinha') endpoint = '/cozinha/login';
    else if (perfil === 'Entregador') endpoint = '/entrega/login';

    const res = await fetch(`${BASE_URL}${endpoint}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, senha })
    });
    
    if (!res.ok) throw new Error(await res.text());
    return res.json();
  },

  cadastro: async (cliente) => {
    try {
      const res = await fetch(`${BASE_URL}/clientes/cadastro`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(cliente)
      });
      if (!res.ok) {
        const errorText = await res.text();
        throw new Error(errorText || `Erro HTTP ${res.status}`);
      }
      return await res.text();
    } catch (e) {
      console.error("Erro no fetch de cadastro:", e);
      throw e;
    }
  },

  getCardapioMock: () => {
    return [
      { id: 1, nome: "Pizza de Calabresa", descricao: "Molho, mussarela e calabresa.", preco: 45.0, estoque: 10 },
      { id: 2, nome: "Pizza Quatro Queijos", descricao: "Mussarela, provolone, parmesão e gorgonzola.", preco: 55.0, estoque: 8 },
      { id: 3, nome: "Coca-Cola 2L", descricao: "Refrigerante Gelado.", preco: 12.0, estoque: 20 }
    ];
  },

  submeterPedido: async (itens) => {
    const payload = { itens: itens.map(i => ({ produtoId: i.id, quantidade: i.quantidade })) };
    const res = await fetch(`${BASE_URL}/pedidos/submeter`, {
      method: 'POST',
      headers: getHeaders(),
      body: JSON.stringify(payload)
    });
    if (!res.ok) throw new Error(await res.text());
    return res.json();
  },

  pagarPedido: async (id) => {
    const res = await fetch(`${BASE_URL}/pedidos/${id}/pagar`, {
      method: 'PATCH',
      headers: getHeaders()
    });
    if (!res.ok) throw new Error(await res.text());
    return res.json();
  },

  consultarStatus: async (id) => {
    const res = await fetch(`${BASE_URL}/pedidos/${id}/status`, {
      headers: getHeaders()
    });
    if (!res.ok) throw new Error(await res.text());
    return res.json();
  },

  getPedidosCozinha: async () => {
    const res = await fetch(`${BASE_URL}/cozinha/pedidos`, { headers: getHeaders() });
    if (!res.ok) throw new Error(await res.text());
    return res.json();
  },

  atualizarStatusCozinha: async (id, status) => {
    const res = await fetch(`${BASE_URL}/cozinha/pedidos/${id}/status?status=${status}`, {
      method: 'PATCH',
      headers: getHeaders()
    });
    if (!res.ok) throw new Error(await res.text());
    return res.json();
  },

  getEntregasPendentes: async () => {
    const res = await fetch(`${BASE_URL}/entrega/pendentes`, { headers: getHeaders() });
    if (!res.ok) throw new Error(await res.text());
    return res.json();
  },

  marcarEntregue: async (id) => {
    const res = await fetch(`${BASE_URL}/entrega/${id}/entregue`, {
      method: 'PATCH',
      headers: getHeaders()
    });
    if (!res.ok) throw new Error(await res.text());
    return res.json();
  }
};
