import api from './api';

export type TableStatus = 'AVAILABLE' | 'OCCUPIED' | 'RESERVED';

export interface Table {
  id?: string;
  tableNumber: number;
  status: TableStatus;
}

export const tableService = {
  getTables: async (): Promise<Table[]> => {
    const response = await api.get('/tables'); // I'll verify this endpoint
    return response.data;
  },

  getTableByNumber: async (tableNumber: number): Promise<Table> => {
    const response = await api.get(`/tables/${tableNumber}`);
    return response.data;
  }
};
