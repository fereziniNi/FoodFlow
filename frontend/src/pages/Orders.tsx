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
  Clock,
  ChevronRight,
  Filter,
  DollarSign
} from 'lucide-react';
import { orderService, OrderDetailsResponse, AddItemRequest } from '../services/orderService';
import { menuService, MenuItem, AddOn } from '../services/menuService';
import { Link, useNavigate } from 'react-router-dom';

const Orders: React.FC = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [orders, setOrders] = useState<OrderDetailsResponse[]>([]);
  const [menuItems, setMenuItems] = useState<MenuItem[]>([]);
  const [addOns, setAddOns] = useState<AddOn[]>([]);
  const [loading, setLoading] = useState(true);
  const [isAddItemModalOpen, setIsAddItemModalOpen] = useState(false);
  const [selectedOrder, setSelectedOrder] = useState<string | null>(null);
  const [selectedTableNumber, setSelectedTableNumber] = useState<number | null>(null);
  
  // Form state for adding item
  const [selectedMenuItem, setSelectedMenuItem] = useState<string>('');
  const [observations, setObservations] = useState('');
  const [selectedAddOns, setSelectedAddOns] = useState<string[]>([]);
  const [searchTerm, setSearchTerm] = useState('');

  const filteredMenuItems = menuItems.filter(item => 
    item.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    item.description.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const selectedItemData = menuItems.find(item => item.id === selectedMenuItem);

  const fetchData = async () => {
    setLoading(true);
    try {
      const [ordersData, menuData, addOnsData] = await Promise.all([
        orderService.listActiveOrders(),
        menuService.getItems(),
        menuService.getAddOns()
      ]);
      setOrders(ordersData);
      setMenuItems(menuData);
      setAddOns(addOnsData);
    } catch (error) {
      console.error("Erro ao buscar dados", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleOpenAddItemModal = (orderId: string, tableNumber: number) => {
    setSelectedOrder(orderId);
    setSelectedTableNumber(tableNumber);
    setIsAddItemModalOpen(true);
    resetForm();
  };

  const handleCloseOrder = async (orderId: string) => {
  try {
    await orderService.closeOrder(orderId);
    fetchData(); // recarrega lista
  } catch (error) {
    console.error("Erro ao fechar comanda", error);
    alert("Erro ao fechar comanda");
  }
};

  const handleAddItem = async () => {
    if (selectedOrder && selectedMenuItem && user?.id) {
      const request: AddItemRequest = {
        menuItemId: selectedMenuItem,
        observations,
        addOnIds: selectedAddOns,
        waiterId: user.id
      };

      try {
        await orderService.addItemToOrder(selectedOrder, request);
        setIsAddItemModalOpen(false);
        resetForm();
        fetchData();
      } catch (error) {
        console.error("Erro ao adicionar item", error);
        alert("Erro ao adicionar item ao pedido");
      }
    }
  };

  const resetForm = () => {
    setSelectedMenuItem('');
    setObservations('');
    setSelectedAddOns([]);
    setSearchTerm('');
  };

  const toggleAddOn = (id: string) => {
    setSelectedAddOns(prev => 
      prev.includes(id) ? prev.filter(item => item !== id) : [...prev, id]
    );
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
          <NavItem to="/dashboard" icon={<LayoutDashboard size={20} />} label="Mesas" />
          <NavItem to="/orders" icon={<ClipboardList size={20} />} label="Comandas" active />
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
          <h2 className="text-lg font-bold text-gray-800">Gerenciamento de Comandas</h2>
          <div className="flex items-center gap-4">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={16} />
              <input 
                type="text" 
                placeholder="Buscar comanda ou mesa..." 
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
                <h1 className="text-2xl font-bold text-gray-900">Comandas Ativas</h1>
                <p className="text-gray-500">Acompanhe todos os pedidos em aberto e adicione novos itens.</p>
              </div>
              <button className="flex items-center gap-2 px-4 py-2 bg-white border border-gray-200 rounded-xl text-sm font-bold text-gray-700 hover:bg-gray-50 transition-colors">
                <Filter size={16} />
                Filtrar
              </button>
            </div>

            {loading ? (
              <div className="space-y-4 animate-pulse">
                {[1, 2, 3].map((i) => (
                  <div key={i} className="h-40 bg-gray-200 rounded-3xl"></div>
                ))}
              </div>
            ) : orders.length === 0 ? (
              <div className="text-center py-20 bg-white rounded-3xl border-2 border-dashed border-gray-200">
                <div className="w-16 h-16 bg-gray-100 text-gray-400 rounded-full flex items-center justify-center mx-auto mb-4">
                  <ClipboardList size={32} />
                </div>
                <h3 className="text-lg font-bold text-gray-800">Nenhuma comanda ativa</h3>
                <p className="text-gray-500">Abra uma mesa para iniciar uma nova comanda.</p>
                <button 
                  onClick={() => navigate('/dashboard')}
                  className="mt-6 px-6 py-2 bg-orange-600 text-white font-bold rounded-xl hover:bg-orange-700 transition-colors"
                >
                  Ir para Mesas
                </button>
              </div>
            ) : (
              <div className="grid grid-cols-1 gap-6">
                {orders.map((order) => (
                  <OrderCard 
                    key={order.orderId} 
                    order={order} 
                    onAddItem={() => handleOpenAddItemModal(order.orderId, order.tableNumber)}
                  />
                ))}
              </div>
            )}
          </div>
        </div>
      </main>

      {/* Modal - Adicionar Item */}
      {isAddItemModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/50 backdrop-blur-sm animate-in fade-in duration-200">
          <div className="bg-white w-full max-w-xl rounded-3xl shadow-2xl overflow-hidden animate-in zoom-in-95 duration-200">
            <div className="p-6 border-b border-gray-100 flex items-center justify-between">
              <div>
                <h3 className="text-xl font-bold text-gray-900">Adicionar Item</h3>
                <p className="text-xs font-bold text-orange-600 uppercase tracking-widest">Mesa {selectedTableNumber}</p>
              </div>
              <button onClick={() => setIsAddItemModalOpen(false)} className="text-gray-400 hover:text-gray-600 transition-colors">
                <Plus className="rotate-45" size={24} />
              </button>
            </div>
            
            <div className="p-6 space-y-6 max-h-[75vh] overflow-y-auto custom-scrollbar">
              {/* Item Search and Selection */}
              <div className="space-y-4">
                <div className="flex items-center justify-between">
                  <label className="block text-sm font-bold text-gray-700">O que deseja lançar?</label>
                  <span className="text-xs font-bold text-gray-400 uppercase">{filteredMenuItems.length} itens encontrados</span>
                </div>
                
                <div className="relative">
                  <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={18} />
                  <input 
                    type="text" 
                    placeholder="Pesquise por nome ou descrição..." 
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="w-full pl-10 pr-4 py-3 bg-gray-50 border border-gray-200 rounded-2xl text-sm focus:bg-white focus:ring-2 focus:ring-orange-500/20 outline-none transition-all"
                  />
                </div>

                <div className="grid grid-cols-1 gap-2 max-h-64 overflow-y-auto pr-2 custom-scrollbar">
                  {filteredMenuItems.length === 0 ? (
                    <div className="text-center py-10 bg-gray-50 rounded-3xl border-2 border-dashed border-gray-200">
                      <p className="text-sm text-gray-500 font-medium">Ops! Não encontramos esse item.</p>
                    </div>
                  ) : (
                    filteredMenuItems.map((item) => (
                      <button
                        key={item.id}
                        onClick={() => setSelectedMenuItem(item.id)}
                        className={`flex items-center justify-between p-4 rounded-2xl border-2 transition-all ${
                          selectedMenuItem === item.id 
                            ? 'border-orange-600 bg-orange-50 shadow-sm' 
                            : 'border-gray-100 hover:border-orange-200 bg-white'
                        }`}
                      >
                        <div className="text-left">
                          <p className="font-bold text-gray-800">{item.name}</p>
                          <p className="text-[11px] text-gray-500 line-clamp-1">{item.description}</p>
                        </div>
                        <span className="font-black text-orange-600 ml-4 whitespace-nowrap">R$ {item.price.toFixed(2)}</span>
                      </button>
                    ))
                  )}
                </div>
              </div>

              {/* Add-ons Section - Only visible if an item is selected */}
              {selectedMenuItem && (
                <div className="animate-in slide-in-from-top-4 duration-300">
                  <div className="p-6 bg-orange-50/50 rounded-3xl border border-orange-100">
                    <div className="flex items-center gap-2 mb-4">
                      <div className="bg-orange-600 p-1.5 rounded-lg text-white">
                        <Plus size={16} />
                      </div>
                      <label className="text-sm font-black text-gray-800 uppercase tracking-tight">Personalize seu {selectedItemData?.name}</label>
                    </div>
                    
                    <div className="grid grid-cols-2 gap-3">
                      {addOns.map((addOn) => (
                        <button
                          key={addOn.id}
                          onClick={() => toggleAddOn(addOn.id)}
                          className={`flex items-center justify-between px-4 py-3 rounded-2xl text-[11px] font-bold border-2 transition-all ${
                            selectedAddOns.includes(addOn.id)
                              ? 'bg-orange-600 border-orange-600 text-white shadow-lg shadow-orange-600/20'
                              : 'bg-white border-gray-200 text-gray-600 hover:border-orange-300'
                          }`}
                        >
                          <span>{addOn.name}</span>
                          <span className={selectedAddOns.includes(addOn.id) ? 'text-orange-100' : 'text-orange-600 font-black'}>
                            +R${addOn.price.toFixed(2)}
                          </span>
                        </button>
                      ))}
                    </div>
                  </div>
                </div>
              )}

              {/* Observations */}
              <div>
                <label className="block text-sm font-bold text-gray-700 mb-2">Observações Adicionais</label>
                <textarea
                  value={observations}
                  onChange={(e) => setObservations(e.target.value)}
                  placeholder="Ex: Ponto da carne, sem gelo, etc..."
                  className="w-full px-4 py-3 bg-gray-50 border border-gray-200 rounded-2xl text-sm focus:bg-white focus:ring-2 focus:ring-orange-500/20 outline-none transition-all resize-none h-24"
                />
              </div>
            </div>

            <div className="p-6 bg-gray-50 border-t border-gray-100 flex items-center justify-between gap-4">
               <div className="hidden sm:block">
                  <p className="text-[10px] font-bold text-gray-400 uppercase">Subtotal do Item</p>
                  <p className="text-lg font-black text-gray-800">
                    R$ {(
                      (selectedItemData?.price || 0) + 
                      selectedAddOns.reduce((acc, id) => {
                        const addon = addOns.find(a => a.id === id);
                        return acc + (addon?.price || 0);
                      }, 0)
                    ).toFixed(2)}
                  </p>
               </div>
              <button 
                onClick={handleAddItem}
                disabled={!selectedMenuItem}
                className="flex-1 py-4 bg-orange-600 text-white font-bold rounded-2xl hover:bg-orange-700 transition-colors shadow-lg shadow-orange-600/20 disabled:opacity-50 disabled:cursor-not-allowed flex items-center justify-center gap-2"
              >
                <Plus size={20} />
                Lançar na Comanda
              </button>
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

const OrderCard = ({ order, onAddItem }: { order: OrderDetailsResponse, onAddItem: () => void }) => {
  return (
    <div className="bg-white rounded-3xl border border-gray-200 overflow-hidden shadow-sm hover:shadow-md transition-shadow">
      <div className="p-6 border-b border-gray-100 flex items-center justify-between bg-gray-50/50">
        <div className="flex items-center gap-4">
          <div className="w-14 h-14 bg-orange-600 text-white rounded-2xl flex items-center justify-center font-black text-2xl shadow-lg shadow-orange-600/20">
            {order.tableNumber}
          </div>
          <div>
            <p className="text-xs font-bold text-gray-400 uppercase tracking-widest">Mesa</p>
            <p className="text-sm font-bold text-gray-800">Aberta por {order.userName}</p>
          </div>
        </div>
        <div className="text-right">
          <p className="text-xs font-bold text-gray-400 uppercase tracking-widest">Total</p>
          <p className="text-xl font-black text-orange-600">R$ {order.total.toFixed(2)}</p>
        </div>
      </div>

      <div className="p-6">
        <div className="flex items-center justify-between mb-4">
          <h4 className="text-sm font-bold text-gray-900 flex items-center gap-2">
            <Clock size={16} className="text-gray-400" />
            Itens da Comanda
          </h4>
          <button 
            onClick={onAddItem}
            className="flex items-center gap-2 text-sm font-bold text-orange-600 hover:text-orange-700 transition-colors"
          >
            <Plus size={16} />
            Adicionar Item
          </button>
        </div>

        <div className="space-y-3">
          {order.items.length === 0 ? (
            <p className="text-sm text-gray-400 italic py-2">Nenhum item lançado ainda.</p>
          ) : (
            order.items.map((item) => (
              <div key={item.id} className="flex items-center justify-between p-3 rounded-xl bg-gray-50 border border-gray-100 group">
                <div className="flex items-center gap-3">
                  <div className={`w-2 h-2 rounded-full ${
                    item.status === 'FINISHED' ? 'bg-emerald-500' : item.status === 'PREPARATION' ? 'bg-amber-500' : 'bg-gray-300'
                  }`} />
                  <div>
                    <p className="text-sm font-bold text-gray-800">{item.name}</p>
                    {item.observations && (
                      <p className="text-[11px] text-gray-500 italic mb-1">{item.observations}</p>
                    )}
                    <p className="text-[10px] font-bold text-gray-400 uppercase">Anotado por: {item.waiterName}</p>
                  </div>
                </div>
                <div className="flex items-center gap-4">
                  <span className="text-sm font-bold text-gray-600">R$ {item.price.toFixed(2)}</span>
                  <StatusBadge status={item.status} />
                </div>
              </div>
            ))
          )}
        </div>
      </div>

      <div className="px-6 py-4 bg-gray-50/50 border-t border-gray-100 flex items-center justify-between">
        <div className="flex items-center gap-2 text-xs text-gray-500">
          <Clock size={14} />
          Iniciada em {new Date(order.createdAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
        </div>
        <button className="flex items-center gap-2 text-sm font-bold text-gray-600 hover:text-orange-600 transition-colors">
          Detalhes
          <ChevronRight size={16} />
        </button>
      </div>
    </div>
  );
};

const StatusBadge = ({ status }: { status: OrderItemResponse['status'] }) => {
  const config = {
    PENDING: { label: 'Pendente', color: 'bg-gray-100 text-gray-600' },
    PREPARATION: { label: 'Preparando', color: 'bg-amber-100 text-amber-700' },
    FINISHED: { label: 'Pronto', color: 'bg-emerald-100 text-emerald-700' },
  };

  const { label, color } = config[status];

  return (
    <span className={`px-2 py-0.5 rounded-lg text-[10px] font-bold uppercase tracking-wider ${color}`}>
      {label}
    </span>
  );
};

export default Orders;
