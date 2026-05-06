import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { authService, UserRole } from '../services/authService';
import { UtensilsCrossed, User, Mail, Lock, Briefcase, Loader2, AlertCircle, ChevronLeft } from 'lucide-react';

const Register: React.FC = () => {
  const [formData, setFormData] = useState({
    name: '',
    username: '',
    email: '',
    password: '',
    role: 'WAITER' as UserRole,
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await authService.register(formData);
      navigate('/login');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Falha ao realizar cadastro. Tente novamente.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex font-sans text-gray-900 bg-white">
      {/* Left Side: Form */}
      <div className="w-full lg:w-1/2 flex flex-col justify-center p-8 sm:p-12 lg:p-20 overflow-y-auto">
        <div className="max-w-md w-full mx-auto">
          <Link 
            to="/login" 
            className="inline-flex items-center text-sm font-medium text-gray-500 hover:text-orange-600 transition-colors mb-8 group"
          >
            <ChevronLeft size={16} className="mr-1 transform group-hover:-translate-x-1 transition-transform" />
            Voltar para o login
          </Link>

          <div className="mb-8">
            <h2 className="text-3xl font-bold tracking-tight mb-2">Criar nova conta</h2>
            <p className="text-gray-500">Junte-se à equipe do FoodFlow e comece a gerenciar com eficiência.</p>
          </div>

          {error && (
            <div className="bg-red-50 border-l-4 border-red-500 p-4 mb-6 rounded flex items-start gap-3 animate-in fade-in slide-in-from-top-1">
              <AlertCircle className="text-red-500 shrink-0" size={20} />
              <p className="text-sm text-red-700 font-medium">{error}</p>
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-semibold text-gray-700 mb-1.5" htmlFor="name">
                  Nome Completo
                </label>
                <div className="relative">
                  <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none">
                    <User className="text-gray-400" size={18} />
                  </div>
                  <input
                    id="name"
                    name="name"
                    type="text"
                    className="block w-full pl-10 pr-3 py-2.5 bg-gray-50 border border-gray-200 rounded-xl text-gray-900 focus:ring-2 focus:ring-orange-500/20 focus:border-orange-500 transition-all outline-none sm:text-sm"
                    placeholder="João Silva"
                    value={formData.name}
                    onChange={handleChange}
                    required
                  />
                </div>
              </div>

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
                    name="username"
                    type="text"
                    className="block w-full pl-10 pr-3 py-2.5 bg-gray-50 border border-gray-200 rounded-xl text-gray-900 focus:ring-2 focus:ring-orange-500/20 focus:border-orange-500 transition-all outline-none sm:text-sm"
                    placeholder="joao.silva"
                    value={formData.username}
                    onChange={handleChange}
                    required
                  />
                </div>
              </div>
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-1.5" htmlFor="email">
                E-mail Profissional
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none">
                  <Mail className="text-gray-400" size={18} />
                </div>
                <input
                  id="email"
                  name="email"
                  type="email"
                  className="block w-full pl-10 pr-3 py-2.5 bg-gray-50 border border-gray-200 rounded-xl text-gray-900 focus:ring-2 focus:ring-orange-500/20 focus:border-orange-500 transition-all outline-none sm:text-sm"
                  placeholder="exemplo@foodflow.com"
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-1.5" htmlFor="password">
                Senha de Acesso
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none">
                  <Lock className="text-gray-400" size={18} />
                </div>
                <input
                  id="password"
                  name="password"
                  type="password"
                  className="block w-full pl-10 pr-3 py-2.5 bg-gray-50 border border-gray-200 rounded-xl text-gray-900 focus:ring-2 focus:ring-orange-500/20 focus:border-orange-500 transition-all outline-none sm:text-sm"
                  placeholder="••••••••"
                  value={formData.password}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-1.5" htmlFor="role">
                Cargo / Função
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none">
                  <Briefcase className="text-gray-400" size={18} />
                </div>
                <select
                  id="role"
                  name="role"
                  className="block w-full pl-10 pr-3 py-2.5 bg-gray-50 border border-gray-200 rounded-xl text-gray-900 focus:ring-2 focus:ring-orange-500/20 focus:border-orange-500 transition-all outline-none sm:text-sm appearance-none cursor-pointer"
                  value={formData.role}
                  onChange={handleChange}
                  required
                >
                  <option value="WAITER">Garçom</option>
                  <option value="COOK">Cozinheiro</option>
                  <option value="CASHIER">Caixa</option>
                  <option value="ADMIN">Administrador</option>
                </select>
                <div className="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none">
                  <svg className="h-5 w-5 text-gray-400" viewBox="0 0 20 20" fill="currentColor">
                    <path fillRule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clipRule="evenodd" />
                  </svg>
                </div>
              </div>
            </div>

            <button
              className={`w-full flex justify-center items-center py-3 px-4 border border-transparent rounded-xl shadow-sm text-sm font-bold text-white bg-orange-600 hover:bg-orange-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-orange-500 transition-all transform active:scale-[0.98] mt-6 ${loading ? 'opacity-70 cursor-not-allowed' : ''}`}
              type="submit"
              disabled={loading}
            >
              {loading ? (
                <>
                  <Loader2 className="animate-spin mr-2" size={18} />
                  Processando...
                </>
              ) : (
                'Finalizar Cadastro'
              )}
            </button>
          </form>

          <div className="mt-8 text-center lg:hidden">
            <p className="text-sm text-gray-500">
              Já possui uma conta?{' '}
              <Link to="/login" className="font-bold text-orange-600 hover:text-orange-500 transition-colors">
                Faça login
              </Link>
            </p>
          </div>
        </div>
      </div>

      {/* Right Side: Image (Hidden on mobile) */}
      <div className="hidden lg:flex lg:w-1/2 relative">
        <img
          src="https://images.unsplash.com/photo-1504674900247-0877df9cc836?q=80&w=2070&auto=format&fit=crop"
          alt="Food Presentation"
          className="absolute inset-0 w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-l from-black/60 to-transparent flex flex-col justify-center p-20 text-right">
          <div className="text-white">
            <div className="flex justify-end mb-6">
              <div className="bg-orange-600 p-3 rounded-2xl shadow-xl">
                <UtensilsCrossed size={32} />
              </div>
            </div>
            <h1 className="text-5xl font-extrabold mb-4 leading-tight">Excelência em cada detalhe.</h1>
            <p className="text-xl text-gray-200 max-w-md ml-auto">
              Otimize sua operação e foque no que realmente importa: a experiência do seu cliente.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;
