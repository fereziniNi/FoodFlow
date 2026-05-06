import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { authService } from '../services/authService';
import { UtensilsCrossed, User, Lock, Loader2, AlertCircle } from 'lucide-react';

const Login: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const response = await authService.login({ username, password });
      login(response.token, response.userId, username);
      navigate('/');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Falha ao realizar login. Verifique suas credenciais.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex font-sans text-gray-900 bg-white">
      {/* Left Side: Image (Hidden on mobile) */}
      <div className="hidden lg:flex lg:w-1/2 relative">
        <img
          src="https://images.unsplash.com/photo-1555396273-367ea4eb4db5?q=80&w=1974&auto=format&fit=crop"
          alt="Restaurant Background"
          className="absolute inset-0 w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-t from-black/70 to-black/20 flex flex-col justify-end p-12">
          <div className="text-white">
            <h1 className="text-5xl font-extrabold mb-4">FoodFlow</h1>
            <p className="text-xl text-gray-200 max-w-md">
              A gestão inteligente para o seu restaurante. Simples, rápido e eficiente.
            </p>
          </div>
        </div>
      </div>

      {/* Right Side: Form */}
      <div className="w-full lg:w-1/2 flex items-center justify-center p-8 sm:p-12">
        <div className="max-w-md w-full">
          <div className="lg:hidden flex justify-center mb-8">
            <div className="flex items-center gap-2 text-orange-600">
              <UtensilsCrossed size={40} strokeWidth={2.5} />
              <span className="text-3xl font-black tracking-tighter">FoodFlow</span>
            </div>
          </div>

          <div className="mb-10 text-center lg:text-left">
            <h2 className="text-3xl font-bold tracking-tight mb-2">Bem-vindo de volta!</h2>
            <p className="text-gray-500">Por favor, insira suas credenciais para acessar o sistema.</p>
          </div>

          {error && (
            <div className="bg-red-50 border-l-4 border-red-500 p-4 mb-6 rounded flex items-start gap-3 animate-in fade-in slide-in-from-top-1">
              <AlertCircle className="text-red-500 shrink-0" size={20} />
              <p className="text-sm text-red-700 font-medium">{error}</p>
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-5">
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-1.5" htmlFor="username">
                Usuário
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none">
                  <User className="text-gray-400" size={18} />
                </div>
                <input
                  id="username"
                  type="text"
                  className="block w-full pl-10 pr-3 py-2.5 bg-gray-50 border border-gray-200 rounded-xl text-gray-900 focus:ring-2 focus:ring-orange-500/20 focus:border-orange-500 transition-all outline-none sm:text-sm"
                  placeholder="Digite seu usuário"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  required
                />
              </div>
            </div>

            <div>
              <div className="flex justify-between mb-1.5">
                <label className="block text-sm font-semibold text-gray-700" htmlFor="password">
                  Senha
                </label>
              </div>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none">
                  <Lock className="text-gray-400" size={18} />
                </div>
                <input
                  id="password"
                  type="password"
                  className="block w-full pl-10 pr-3 py-2.5 bg-gray-50 border border-gray-200 rounded-xl text-gray-900 focus:ring-2 focus:ring-orange-500/20 focus:border-orange-500 transition-all outline-none sm:text-sm"
                  placeholder="••••••••"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </div>
            </div>

            <button
              className={`w-full flex justify-center items-center py-3 px-4 border border-transparent rounded-xl shadow-sm text-sm font-bold text-white bg-orange-600 hover:bg-orange-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-orange-500 transition-all transform active:scale-[0.98] ${loading ? 'opacity-70 cursor-not-allowed' : ''}`}
              type="submit"
              disabled={loading}
            >
              {loading ? (
                <>
                  <Loader2 className="animate-spin mr-2" size={18} />
                  Entrando...
                </>
              ) : (
                'Entrar no sistema'
              )}
            </button>
          </form>

          <div className="mt-8 text-center">
            <p className="text-sm text-gray-500">
              Ainda não tem acesso?{' '}
              <Link to="/register" className="font-bold text-orange-600 hover:text-orange-500 transition-colors">
                Solicite seu cadastro
              </Link>
            </p>
          </div>

          <div className="mt-12 flex items-center justify-center gap-6 opacity-30 grayscale">
            <span className="text-xs font-bold uppercase tracking-widest text-gray-400">Poder por FoodFlow v1.0</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
