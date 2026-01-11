import api from "./api";
import type { SignUpRequest, SignInRequest, AuthResponse } from "../types";

export const authService = {
  async signup(data: SignUpRequest) {
    const response = await api.post<AuthResponse>("/auth/signup", data);
    return response.data;
  },

  async signin(data: SignInRequest) {
    const response = await api.post<AuthResponse>("/auth/signin", data);
    if (response.data.accessToken) {
      localStorage.setItem("accessToken", response.data.accessToken);
    }
    return response.data;
  },

  logout() {
    localStorage.removeItem("accessToken");
  },
};
