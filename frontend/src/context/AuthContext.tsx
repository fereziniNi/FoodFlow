import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';

interface User {
  id: string;
  username: string;
}

interface AuthContextData {
  signed: boolean;
  user: User | null;
  login(token: string, userId: string, username: string): void;
  logout(): void;
  loading: boolean;
}

const AuthContext = createContext<AuthContextData>({} as AuthContextData);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const storagedToken = localStorage.getItem('@FoodFlow:token');
    const storagedUser = localStorage.getItem('@FoodFlow:user');

    if (storagedToken && storagedUser) {
      setUser(JSON.parse(storagedUser));
    }
    setLoading(false);
  }, []);

  function login(token: string, userId: string, username: string) {
    const userData = { id: userId, username };
    localStorage.setItem('@FoodFlow:token', token);
    localStorage.setItem('@FoodFlow:user', JSON.stringify(userData));
    setUser(userData);
  }

  function logout() {
    localStorage.removeItem('@FoodFlow:token');
    localStorage.removeItem('@FoodFlow:user');
    setUser(null);
  }

  return (
    <AuthContext.Provider value={{ signed: !!user, user, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
};

export function useAuth() {
  const context = useContext(AuthContext);
  return context;
}
