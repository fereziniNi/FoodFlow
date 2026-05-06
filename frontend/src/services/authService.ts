import api from './api';

export type UserRole = 'ADMIN' | 'WAITER' | 'COOK' | 'CASHIER';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export interface RegisterRequest {
  name: string;
  username: string;
  email: string;
  password: string;
  role: UserRole;
}

export const authService = {
  async login(data: LoginRequest): Promise<LoginResponse> {
    const response = await api.post<LoginResponse>('/auth/login', data);
    return response.data;
  },

  async register(data: RegisterRequest): Promise<void> {
    await api.post('/auth/register', data);
  },
};
