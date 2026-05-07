import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { 
  UtensilsCrossed, 
  LogOut, 
  LayoutDashboard, 
  ClipboardList, 
  Settings, 
  User as UserIcon,
  Plus,
  Search,
  CheckCircle2,
  Clock,
  AlertCircle
} from 'lucide-react';
import { tableService, Table } from '../services/tableService';
import { orderService } from '../services/orderService';
import { Link, useNavigate } from 'react-router-dom';

const Dashboard: React.FC = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [tables, setTables] = useState<Table[]>([]);
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedTable, setSelectedTable] = useState<number | null>(null);

  const fetchTables = async () => {
    setLoading(true);
    try {
      const data = await tableService.getTables();
      const sorted = [...data].sort((a, b) => a.tableNumber - b.tableNumber);
      setTables(sorted);
    } catch (error) {
      console.error("Erro ao buscar mesas", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTables();
  }, []);

  const handleOpenTable = (tableNumber: number) => {
    setSelectedTable(tableNumber);
    setIsModalOpen(true);
  };

  const handleConfirmOpenOrder = async () => {
    if (selectedTable && user?.id) {
      try {
        await orderService.openOrder(selectedTable, { userId: user.id });
        setIsModalOpen(false);
        await fetchTables();
        navigate('/orders');
      } catch (error: any) {
        console.error("Erro ao abrir mesa", error);
        alert(error.response?.data?.detail || "Erro ao abrir mesa");
      }
    }
  };

  return (
    <div className="flex min-h-screen bg-gray-50 font-sans">
      {/* Sidebar */}
      <aside className="w-64 bg-white border-r border-gray-200 hidden md:flex flex-col">
        <div className="p-6 flex items-center gap-3 border-b border-gray-100">
          <div className="bg-orange-600 p-2 rounded-lg text-white">
            <UtensilsCrossed size={20} />
          </div>
          <span className="text-xl font-black tracking-tighter text-gray-800">FoodFlow</span>
        </div>

        <nav className="flex-1 p-4 space-y-2">
          <NavItem to="/dashboard" icon={<LayoutDashboard size={20} />} label="Mesas" active />
          <NavItem to="/orders" icon={<ClipboardList size={20} />} label="Comandas" />
          <NavItem to="#" icon={<Settings size={20} />} label="Configurações" />
        </nav>

        <div className="p-4 border-t border-gray-100">
          <div className="flex items-center gap-3 p-3 rounded-xl bg-gray-50 mb-4">
            <div className="bg-orange-100 text-orange-600 p-2 rounded-full">
              <UserIcon size={18} />
            </div>
            <div className="overflow-hidden">
              <p className="text-sm font-bold text-gray-800 truncate">{user?.username || 'Usuário'}</p>
              <p className="text-xs text-gray-500 uppercase font-semibold">Garçom</p>
            </div>
          </div>
          <button 
            onClick={logout}
            className="w-full flex items-center gap-3 px-4 py-2 text-gray-600 hover:text-red-600 hover:bg-red-50 rounded-xl transition-colors"
          >
            <LogOut size={18} />
            <span className="text-sm font-semibold">Sair</span>
          </button>
        </div>
      </aside>

      {/* Main Content */}
      <main className="flex-1 flex flex-col h-screen overflow-hidden">
        {/* Header */}
        <header className="h-16 bg-white border-b border-gray-200 flex items-center justify-between px-8">
          <h2 className="text-lg font-bold text-gray-800">Gestão de Mesas</h2>
          <div className="flex items-center gap-4">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={16} />
              <input 
                type="text" 
                placeholder="Buscar mesa..." 
                className="pl-9 pr-4 py-1.5 bg-gray-100 border-transparent rounded-lg text-sm focus:bg-white focus:ring-2 focus:ring-orange-500/20 outline-none transition-all"
              />
            </div>
          </div>
        </header>

        {/* Content Area */}
        <div className="flex-1 overflow-y-auto p-8">
          <div className="max-w-6xl mx-auto">
            <div className="flex items-center justify-between mb-8">
              <div>
                <h1 className="text-2xl font-bold text-gray-900">Visão Geral</h1>
                <p className="text-gray-500">Selecione uma mesa disponível para abrir uma nova comanda.</p>
              </div>
              <div className="flex gap-2">
                <StatusBadge color="green" label="Livre" />
                <StatusBadge color="red" label="Ocupada" />
                <StatusBadge color="orange" label="Reservada" />
              </div>
            </div>

            {loading ? (
              <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-6 animate-pulse">
                {[1, 2, 3, 4, 5, 6, 7, 8].map((i) => (
                  <div key={i} className="h-32 bg-gray-200 rounded-2xl"></div>
                ))}
              </div>
            ) : (
              <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-6">
                {tables.map((table) => (
                  <TableCard 
                    key={table.tableNumber} 
                    table={table} 
                    onClick={() => table.status === 'AVAILABLE' && handleOpenTable(table.tableNumber)}
                  />
                ))}
              </div>
            )}
          </div>
        </div>
      </main>

      {/* Modal - Abrir Comanda */}
      {isModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/50 backdrop-blur-sm animate-in fade-in duration-200">
          <div className="bg-white w-full max-sm rounded-3xl shadow-2xl overflow-hidden animate-in zoom-in-95 duration-200">
            <div className="p-8 text-center">
              <div className="w-20 h-20 bg-orange-100 text-orange-600 rounded-full flex items-center justify-center mx-auto mb-6">
                <Plus size={40} />
              </div>
              <h3 className="text-xl font-bold text-gray-900 mb-2">Abrir Mesa {selectedTable}</h3>
              <p className="text-gray-500 mb-8">Você deseja iniciar uma nova comanda para esta mesa?</p>
              
              <div className="flex flex-col gap-3">
                <button 
                  onClick={handleConfirmOpenOrder}
                  className="w-full py-3 bg-orange-600 text-white font-bold rounded-2xl hover:bg-orange-700 transition-colors shadow-lg shadow-orange-600/20"
                >
                  Sim, Abrir Comanda
                </button>
                <button 
                  onClick={() => setIsModalOpen(false)}
                  className="w-full py-3 bg-gray-100 text-gray-600 font-bold rounded-2xl hover:bg-gray-200 transition-colors"
                >
                  Cancelar
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

const NavItem = ({ to, icon, label, active = false }: { to: string, icon: React.ReactNode, label: string, active?: boolean }) => (
  <Link 
    to={to} 
    className={`flex items-center gap-3 px-4 py-2.5 rounded-xl text-sm font-semibold transition-all ${
      active 
        ? 'bg-orange-50 text-orange-600 shadow-sm shadow-orange-600/5' 
        : 'text-gray-500 hover:bg-gray-50 hover:text-gray-900'
    }`}
  >
    {icon}
    {label}
  </Link>
);

const StatusBadge = ({ color, label }: { color: 'green' | 'red' | 'orange', label: string }) => {
  const colors = {
    green: 'bg-emerald-100 text-emerald-700',
    red: 'bg-rose-100 text-rose-700',
    orange: 'bg-amber-100 text-amber-700',
  };
  return (
    <span className={`px-3 py-1 rounded-full text-xs font-bold uppercase tracking-wider ${colors[color]}`}>
      {label}
    </span>
  );
};

const TableCard = ({ table, onClick }: { table: Table, onClick: () => void }) => {
  const isAvailable = table.status === 'AVAILABLE';
  const isOccupied = table.status === 'OCCUPIED';

  return (
    <button 
      onClick={onClick}
      disabled={!isAvailable}
      className={`relative group p-6 rounded-3xl border-2 transition-all text-left flex flex-col justify-between h-40 ${
        isAvailable 
          ? 'bg-white border-gray-100 hover:border-orange-200 hover:shadow-xl hover:shadow-orange-600/5 active:scale-95' 
          : 'bg-gray-50 border-transparent cursor-not-allowed opacity-80'
      }`}
    >
      <div className="flex justify-between items-start">
        <span className="text-4xl font-black text-gray-800 tracking-tighter">
          #{table.tableNumber}
        </span>
        {isAvailable ? (
          <div className="bg-emerald-500 text-white p-1.5 rounded-full shadow-sm">
            <CheckCircle2 size={16} strokeWidth={3} />
          </div>
        ) : isOccupied ? (
          <div className="bg-rose-500 text-white p-1.5 rounded-full shadow-sm">
            <Clock size={16} strokeWidth={3} />
          </div>
        ) : (
          <div className="bg-amber-500 text-white p-1.5 rounded-full shadow-sm">
            <AlertCircle size={16} strokeWidth={3} />
          </div>
        )}
      </div>
      
      <div>
        <p className={`text-xs font-bold uppercase tracking-widest ${isAvailable ? 'text-emerald-600' : isOccupied ? 'text-rose-600' : 'text-amber-600'}`}>
          {table.status === 'AVAILABLE' ? 'Disponível' : table.status === 'OCCUPIED' ? 'Ocupada' : 'Reservada'}
        </p>
      </div>
    </button>
  );
};

export default Dashboard;
