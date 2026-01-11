import api from "./api";
import type { Reservation, CreateReservationRequest } from "../types";

export const reservationService = {
  async createReservation(data: CreateReservationRequest) {
    const response = await api.post<Reservation>("/reservations", data);
    return response.data;
  },

  async getReservationsByRoom(roomId: number, date: string) {
    const response = await api.get<Reservation[]>("/reservations/by-room", {
      params: { roomId, date },
    });
    return response.data;
  },

  async deleteReservation(id: number) {
    await api.delete(`/reservations/${id}`);
  },
};
