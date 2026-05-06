import api from './api';

export interface OpenOrderRequest {
  userId: string;
}

export interface OrderResponse {
  id: string;
  tableNumber: number;
  createdAt: string;
}

export const orderService = {
  openOrder: async (tableNumber: number, data: OpenOrderRequest): Promise<OrderResponse> => {
    const response = await api.post(`/orders/${tableNumber}/open`, data);
    return response.data;
  },

  getOrderByTable: async (tableNumber: number): Promise<OrderResponse> => {
    const response = await api.get(`/orders/table/${tableNumber}`);
    return response.data;
  }
};
