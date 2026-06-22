import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';
import './EntregadorDashboard.css';

function EntregadorDashboard() {
  const [activeTab, setActiveTab] = useState('entregas');
  const [entregas, setEntregas] = useState([]);
  const navigate = useNavigate();

  const carregarEntregas = async () => {
    try {
      const data = await api.getEntregasPendentes();
      setEntregas(data);
    } catch (e) {
      console.error(e);
    }
  };

  useEffect(() => {
    carregarEntregas();
    const interval = setInterval(carregarEntregas, 10000);
    return () => clearInterval(interval);
  }, []);

  const handleLogout = () => {
    sessionStorage.clear();
    navigate('/login');
  };

  const marcarEntregue = async (id) => {
    try {
      await api.marcarEntregue(id);
      carregarEntregas();
      alert('Entrega finalizada!');
    } catch(err) {
      alert('Erro ao marcar como entregue: ' + err.message);
    }
  };

  return (
    <div className="dashboard-wrapper">
      <div className="sidebar">
          <div className="logo">🛵 Painel Entregador</div>
          <button className={`nav-btn ${activeTab === 'entregas' ? 'active' : ''}`} onClick={() => setActiveTab('entregas')}>Entregas Pendentes</button>
          <div style={{flex: 1}}></div>
          <button className="nav-btn" onClick={handleLogout}>Sair</button>
      </div>

      <div className="content">
          {activeTab === 'entregas' && (
            <div className="section animate-fade">
                <div style={{display:'flex', justifyContent:'space-between', alignItems:'center'}}>
                    <h1>Prontos para Entrega</h1>
                    <button className="btn-outline" onClick={carregarEntregas}>🔄 Atualizar</button>
                </div>
                <div className="delivery-grid">
                    {entregas.length === 0 && <p>Nenhuma entrega pendente.</p>}
                    {entregas.map(ent => (
                      <div className="delivery-card" key={ent.id}>
                          <div className="delivery-header">
                              <h3>Pedido #{ent.pedidoId}</h3>
                              <span className="status-badge status-pronto">{ent.status}</span>
                          </div>
                          <div className="delivery-body">
                              <p className="address">📍 {ent.enderecoEntrega || 'Endereço não informado'}</p>
                              <p className="client">Data Atualização: {new Date(ent.dataHoraAtualizacao).toLocaleString()}</p>
                          </div>
                          <div className="delivery-actions">
                              <button className="btn btn-success" onClick={() => marcarEntregue(ent.id)}>Marcar como Entregue</button>
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

export default EntregadorDashboard;
