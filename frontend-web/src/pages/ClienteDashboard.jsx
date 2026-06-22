import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';
import './ClienteDashboard.css';

function ClienteDashboard() {
  const [activeTab, setActiveTab] = useState('menu');
  const [cardapio, setCardapio] = useState([]);
  const [cesta, setCesta] = useState([]);
  const [historico, setHistorico] = useState(() => {
    const salvo = sessionStorage.getItem('historico_pedidos');
    return salvo ? JSON.parse(salvo) : [];
  });
  const navigate = useNavigate();

  useEffect(() => {
    setCardapio(api.getCardapioMock());
  }, []);

  useEffect(() => {
    sessionStorage.setItem('historico_pedidos', JSON.stringify(historico));
  }, [historico]);

  const handleLogout = () => {
    sessionStorage.clear();
    navigate('/login');
  };

  const adicionarItem = (produto) => {
    setCesta(prev => {
      const existing = prev.find(i => i.id === produto.id);
      if (existing) {
        return prev.map(i => i.id === produto.id ? { ...i, quantidade: i.quantidade + 1 } : i);
      }
      return [...prev, { ...produto, quantidade: 1 }];
    });
    alert(`${produto.nome} adicionado!`);
  };

  const submeterPedido = async () => {
    if(cesta.length === 0) return alert('Cesta vazia!');
    try {
      const res = await api.submeterPedido(cesta);
      alert('Pedido submetido com sucesso!');
      setHistorico(prev => [res, ...prev]);
      setCesta([]);
      setActiveTab('history');
    } catch(err) {
      alert('Falha ao submeter: ' + err.message);
    }
  };

  const atualizarStatus = async (id) => {
    try {
      const atualizado = await api.consultarStatus(id);
      setHistorico(prev => prev.map(p => p.id === id ? atualizado : p));
    } catch (e) {
      console.error(e);
      alert('Erro ao atualizar status: ' + e.message);
    }
  };

  const pagarPedido = async (id) => {
    try {
      const atualizado = await api.pagarPedido(id);
      alert('Pagamento aprovado!');
      setHistorico(prev => prev.map(p => p.id === id ? atualizado : p));
    } catch (e) {
      console.error(e);
      alert('Erro ao pagar pedido: ' + e.message);
    }
  };

  const subtotal = cesta.reduce((acc, item) => acc + (item.preco * item.quantidade), 0);

  return (
    <div className="dashboard-wrapper">
      <div className="sidebar">
          <div className="logo">🍕 Tele-Entrega</div>
          <button className={`nav-btn ${activeTab === 'menu' ? 'active' : ''}`} onClick={() => setActiveTab('menu')}>Cardápio</button>
          <button className={`nav-btn ${activeTab === 'checkout' ? 'active' : ''}`} onClick={() => setActiveTab('checkout')}>Meu Pedido ({cesta.length})</button>
          <button className={`nav-btn ${activeTab === 'history' ? 'active' : ''}`} onClick={() => setActiveTab('history')}>Acompanhar Status</button>
          <div style={{flex: 1}}></div>
          <button className="nav-btn" onClick={handleLogout}>Sair</button>
      </div>

      <div className="content">
          {activeTab === 'menu' && (
            <div className="section animate-fade">
                <h1>O que vamos pedir hoje?</h1>
                <div className="grid">
                    {cardapio.map(prod => (
                      <div className="card" key={prod.id}>
                          <img src={prod.nome.includes('Coca') ? "https://images.unsplash.com/photo-1622483767028-3f66f32aef97?auto=format&fit=crop&w=400&q=80" : "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?auto=format&fit=crop&w=400&q=80"} alt={prod.nome} />
                          <h3>{prod.nome}</h3>
                          <p className="desc">{prod.descricao}</p>
                          <div className="price-row">
                              <span className="price">R$ {prod.preco.toFixed(2)}</span>
                          </div>
                          <button className="btn-add" onClick={() => adicionarItem(prod)}>Adicionar</button>
                      </div>
                    ))}
                </div>
            </div>
          )}

          {activeTab === 'checkout' && (
            <div className="section animate-fade">
                <h1>Resumo do Pedido</h1>
                <div className="checkout-box">
                    {cesta.map((item, idx) => (
                      <div className="checkout-item" key={idx}>
                          <div className="item-info">
                              <h4>{item.nome}</h4>
                              <p>Preço: R$ {item.preco.toFixed(2)} &nbsp;|&nbsp; Qtd: {item.quantidade}</p>
                          </div>
                          <div className="item-price">R$ {(item.preco * item.quantidade).toFixed(2)}</div>
                      </div>
                    ))}
                    
                    <div className="totals">
                        <div className="total-line final">
                            <span>Subtotal Estimado</span>
                            <span>R$ {subtotal.toFixed(2)}</span>
                        </div>
                        <p style={{fontSize: '12px', color: '#888', marginTop: '10px'}}>O valor final real será calculado pelo backend (impostos e descontos aplicados na aprovação).</p>
                    </div>
                    <button className="btn btn-primary btn-submit" onClick={submeterPedido}>Confirmar e Submeter</button>
                </div>
            </div>
          )}

          {activeTab === 'history' && (
            <div className="section animate-fade">
                <h1>Acompanhar Status</h1>
                <div className="history-list">
                    {historico.length === 0 && <p>Nenhum pedido feito ainda.</p>}
                    {historico.map((ped, idx) => (
                      <div className="history-card" key={idx} style={{borderColor: 'var(--info)'}}>
                          <div className="h-info">
                              <h4>Pedido #{ped.id || 'N/A'}</h4>
                              <p>Custo Itens: R$ {ped.valorBase ?? 0} | Imposto: R$ {ped.impostos ?? 0} | Total: R$ {ped.valorFinal ?? 0}</p>
                          </div>
                          <div className="actions" style={{display: 'flex', flexDirection: 'column', gap: '10px', alignItems: 'flex-end'}}>
                              <span className={`status-badge`}>{ped.status || 'NOVO'}</span>
                              <div style={{display: 'flex', gap: '5px'}}>
                                  {(ped.status === 'NOVO' || ped.status === 'APROVADO') && (
                                      <button className="btn btn-success" style={{padding: '5px 10px', fontSize: '12px'}} onClick={() => pagarPedido(ped.id)}>💲 Pagar</button>
                                  )}
                                  <button className="btn-outline" style={{padding: '5px 10px', fontSize: '12px'}} onClick={() => atualizarStatus(ped.id)}>🔄 Atualizar</button>
                              </div>
                          </div>
                      </div>
                    ))}
                </div>
            </div>
          )}
      </div>
    </div>
  );
}

export default ClienteDashboard;
