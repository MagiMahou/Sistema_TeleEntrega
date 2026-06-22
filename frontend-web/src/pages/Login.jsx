import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../services/api';
import './Login.css';

function Login() {
  const [isRegistering, setIsRegistering] = useState(false);
  const [perfil, setPerfil] = useState('cliente');
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  
  // Register fields
  const [nome, setNome] = useState('');
  const [cpf, setCpf] = useState('');
  const [celular, setCelular] = useState('');
  const [endereco, setEndereco] = useState('');

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      if (perfil === 'admin') {
         sessionStorage.setItem('token', 'simulated_admin_token');
         sessionStorage.setItem('perfil', perfil);
         navigate('/admin');
         return;
      }
      const response = await api.login(perfil.charAt(0).toUpperCase() + perfil.slice(1), email, senha);
      sessionStorage.setItem('token', response.token);
      sessionStorage.setItem('perfil', perfil);
      
      if(perfil === 'cliente') navigate('/cliente');
      else if(perfil === 'cozinha') navigate('/cozinha');
      else if(perfil === 'entregador') navigate('/entregador');
    } catch(err) {
      alert("Falha no login: " + err.message);
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      await api.cadastro({ cpf, nome, celular, endereco, email, senha });
      alert("Conta criada com sucesso! Faça login agora.");
      setIsRegistering(false);
    } catch (err) {
      alert("Erro ao cadastrar: " + err.message);
    }
  };

  return (
    <div className="login-wrapper">
      <div className="login-container animate-fade">
          <div className="login-image"></div>
          <div className="login-form-wrapper" style={{ overflowY: 'auto' }}>
              <div className="logo">🍕 Tele-Entrega</div>
              <h1>{isRegistering ? 'Criar Nova Conta' : 'Bem-vindo de volta!'}</h1>
              <p className="subtitle">{isRegistering ? 'Cadastre-se para realizar seus pedidos.' : 'Acesse o sistema para realizar pedidos ou gerenciar suas demandas.'}</p>

              <form onSubmit={isRegistering ? handleRegister : handleLogin}>
                  {!isRegistering && (
                    <div className="input-group">
                        <label>Perfil de Acesso</label>
                        <select value={perfil} onChange={(e) => setPerfil(e.target.value)}>
                            <option value="cliente">Cliente</option>
                            <option value="cozinha">Equipe da Cozinha</option>
                            <option value="entregador">Entregador</option>
                            <option value="admin">Administrador</option>
                        </select>
                    </div>
                  )}

                  {isRegistering && (
                    <>
                      <div className="input-group">
                          <label>Nome Completo</label>
                          <input type="text" placeholder="Seu nome" value={nome} onChange={(e) => setNome(e.target.value)} required />
                      </div>
                      <div style={{display:'flex', gap:'10px'}}>
                        <div className="input-group" style={{flex: 1}}>
                            <label>CPF</label>
                            <input type="text" placeholder="11122233344" value={cpf} onChange={(e) => setCpf(e.target.value)} required />
                        </div>
                        <div className="input-group" style={{flex: 1}}>
                            <label>Celular</label>
                            <input type="text" placeholder="5199999999" value={celular} onChange={(e) => setCelular(e.target.value)} required />
                        </div>
                      </div>
                      <div className="input-group">
                          <label>Endereço de Entrega</label>
                          <input type="text" placeholder="Rua ABC, 123" value={endereco} onChange={(e) => setEndereco(e.target.value)} required />
                      </div>
                    </>
                  )}
                  
                  <div className="input-group">
                      <label>E-mail</label>
                      <input 
                        type="email" 
                        placeholder="Digite seu e-mail" 
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                      />
                  </div>
                  
                  <div className="input-group">
                      <label>Senha</label>
                      <input 
                        type="password" 
                        placeholder="Digite sua senha" 
                        value={senha}
                        onChange={(e) => setSenha(e.target.value)}
                        required 
                      />
                  </div>

                  {isRegistering ? (
                    <>
                      <button type="submit" className="btn btn-primary">Cadastrar</button>
                      <button type="button" className="btn btn-outline" onClick={() => setIsRegistering(false)}>Voltar para Login</button>
                    </>
                  ) : (
                    <>
                      <button type="submit" className="btn btn-primary">Entrar no Sistema</button>
                      <button type="button" className="btn btn-outline" onClick={() => { setIsRegistering(true); setPerfil('cliente'); }}>Criar nova conta (Cliente)</button>
                    </>
                  )}
              </form>
          </div>
      </div>
    </div>
  );
}

export default Login;
