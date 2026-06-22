import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import ClienteDashboard from './pages/ClienteDashboard';
import CozinhaDashboard from './pages/CozinhaDashboard';
import EntregadorDashboard from './pages/EntregadorDashboard';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<Login />} />
        <Route path="/cliente/*" element={<ClienteDashboard />} />
        <Route path="/cozinha/*" element={<CozinhaDashboard />} />
        <Route path="/entregador/*" element={<EntregadorDashboard />} />
      </Routes>
    </Router>
  );
}

export default App;
