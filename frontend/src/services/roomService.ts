import api from "./api";
import type { Room } from "../types";

export const roomService = {
  async getAllRooms() {
    const response = await api.get<Room[]>("/rooms");
    return response.data;
  },

  async getRoomById(id: number) {
    const response = await api.get<Room>(`/rooms/${id}`);
    return response.data;
  },
};
