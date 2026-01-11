import { create } from 'zustand';
import type { User, AuthResponse } from '../types';

interface AuthState {
  user: User | null;
  token: string | null;
  setAuth: (payload: AuthResponse) => void;
  clearAuth: () => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  user: null,
  token: null,
  setAuth: (payload) =>
    set({
      user: payload.user,
      token: payload.accessToken,
    }),
  clearAuth: () => set({ user: null, token: null }),
}));
