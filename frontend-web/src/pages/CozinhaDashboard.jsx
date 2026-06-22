import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';
import './CozinhaDashboard.css';

function CozinhaDashboard() {
  const [activeTab, setActiveTab] = useState('pedidos');
  const [pedidos, setPedidos] = useState([]);
  const navigate = useNavigate();

  const carregarPedidos = async () => {
    try {
      const data = await api.getPedidosCozinha();
      setPedidos(data);
    } catch (e) {
      console.error(e);
    }
  };

  useEffect(() => {
    carregarPedidos();
    const interval = setInterval(carregarPedidos, 10000);
    return () => clearInterval(interval);
  }, []);

  const handleLogout = () => {
    sessionStorage.clear();
    navigate('/login');
  };

  const mudarStatus = async (id, novoStatus) => {
    try {
      await api.atualizarStatusCozinha(id, novoStatus);
      carregarPedidos();
    } catch (e) {
      alert("Erro ao mudar status: " + e.message);
    }
  };

  return (
    <div className="dashboard-wrapper">
      <div className="sidebar">
          <div className="logo">👨‍🍳 Painel Cozinha</div>
          <button className={`nav-btn ${activeTab === 'pedidos' ? 'active' : ''}`} onClick={() => setActiveTab('pedidos')}>Pedidos Pendentes</button>
          <div style={{flex: 1}}></div>
          <button className="nav-btn" onClick={handleLogout}>Sair</button>
      </div>

      <div className="content">
          {activeTab === 'pedidos' && (
            <div className="section animate-fade">
                <div style={{display:'flex', justifyContent:'space-between', alignItems:'center'}}>
                    <h1>Pedidos na Fila</h1>
                    <button className="btn-outline" onClick={carregarPedidos}>🔄 Atualizar</button>
                </div>
                <div className="kitchen-grid">
                    {pedidos.length === 0 && <p>Nenhum pedido pendente.</p>}
                    {pedidos.map(ped => (
                      <div className={`kitchen-card ${ped.status === 'PREPARACAO' ? 'border-warning' : ''}`} key={ped.id}>
                          <div className="kitchen-header">
                              <h3>Pedido #{ped.id}</h3>
                              <span className={`status-badge`}>{ped.status}</span>
                          </div>
                          <div className="kitchen-body">
                              <ul>
                                  {ped.itens && ped.itens.map((item, idx) => (
                                    <li key={idx}>{item.quantidade}x Produto {item.produtoId}</li>
                                  ))}
                              </ul>
                          </div>
                          <div className="kitchen-actions">
                              {ped.status === 'PAGO' || ped.status === 'NOVO' ? (
                                <button className="btn btn-warning" onClick={() => mudarStatus(ped.id, 'PREPARACAO')}>Iniciar Preparo</button>
                              ) : ped.status === 'PREPARACAO' ? (
                                <button className="btn btn-primary" onClick={() => mudarStatus(ped.id, 'PRONTO')}>Marcar como Pronto</button>
                              ) : (
                                <span style={{fontSize: '14px', color: 'var(--success)'}}>Finalizado</span>
                              )}
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

export default CozinhaDashboard;
