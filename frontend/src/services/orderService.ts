import api from './api';

export interface OpenOrderRequest {
  userId: string;
}

export interface OrderItemResponse {
  id: string;
  name: string;
  observations: string;
  price: number;
  status: 'PENDING' | 'PREPARATION' | 'FINISHED';
  waiterName: string;
}

export interface OrderDetailsResponse {
  orderId: string;
  tableNumber: number;
  userName: string;
  createdAt: string;
  active: boolean;
  total: number;
  discount: number;
  items: OrderItemResponse[];
}

export interface OrderResponse {
  orderId: string;
  tableNumber: number;
  createdAt: string;
  active: boolean;
  total: number;
}

export interface AddItemRequest {
  menuItemId: string;
  observations: string;
  addOnIds: string[];
  waiterId: string;
}

export const orderService = {
  openOrder: async (tableNumber: number, data: OpenOrderRequest): Promise<OrderResponse> => {
    const response = await api.post(`/orders/${tableNumber}/open`, data);
    return response.data;
  },

  getOrderByTable: async (tableNumber: number): Promise<OrderDetailsResponse> => {
    const response = await api.get(`/orders/tables/${tableNumber}/order`);
    return response.data;
  },

  listActiveOrders: async (): Promise<OrderDetailsResponse[]> => {
    const response = await api.get('/orders');
    return response.data;
  },

  addItemToOrder: async (orderId: string, data: AddItemRequest): Promise<OrderResponse> => {
    const response = await api.post(`/orders/${orderId}/items`, data);
    return response.data;
  }
};
