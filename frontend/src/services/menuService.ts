import api from './api';

export interface MenuItem {
  id: string;
  name: string;
  description: string;
  price: number;
}

export interface AddOn {
  id: string;
  name: string;
  price: number;
}

export const menuService = {
  getItems: async (): Promise<MenuItem[]> => {
    const response = await api.get('/menu/items');
    return response.data;
  },

  getAddOns: async (): Promise<AddOn[]> => {
    const response = await api.get('/menu/addons');
    return response.data;
  }
};
