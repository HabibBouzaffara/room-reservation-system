// ============================================
// User Related Types
// ============================================

export enum UserRole {
  USER = "USER",
  ADMIN = "ADMIN",
}

export enum ApprovalStatus {
  PENDING = "PENDING",
  APPROVED = "APPROVED",
  REJECTED = "REJECTED",
}

export interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  teamName: string;
  role: UserRole;
  approvalStatus: ApprovalStatus;
  approvedBy?: User;
  createdAt: string; // ISO 8601
  updatedAt: string;
}

export interface SignUpRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  teamName: string;
}

export interface SignInRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  accessToken: string;
  tokenType: string;
  expiresIn: number;
  user: User;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
}

// ============================================
// Room Related Types
// ============================================

export type RoomName = "IPB" | "BCP" | "BDC";

export interface Room {
  id: number;
  name: RoomName;
  description: string;
  capacity: number;
  createdAt: string;
  updatedAt: string;
}

// ============================================
// Reservation Related Types
// ============================================

export enum ActivityType {
  COVERAGE = "COVERAGE",
  FULL_VALIDATION = "FULL_VALIDATION",
  QC = "QC",
}

export enum HardwareType {
  D5 = "D5",
  D4 = "D4",
}

export interface Reservation {
  id: number;
  roomId: number;
  userId: number;
  startTime: string; // ISO 8601
  endTime: string; // ISO 8601
  activityType: ActivityType;
  hardwareType: HardwareType;
  softwareType: string;
  teamName: string;
  isAdminSlot: boolean;
  createdBy?: User;
  createdAt: string;
  updatedAt: string;
}

export interface CreateReservationRequest {
  roomId: number;
  startTime: string;
  endTime: string;
  activityType: ActivityType;
  hardwareType: HardwareType;
  softwareType: string;
  teamName: string;
}

export interface UpdateReservationRequest {
  startTime?: string;
  endTime?: string;
  activityType?: ActivityType;
  hardwareType?: HardwareType;
  softwareType?: string;
  teamName?: string;
}

export interface TimeSlot {
  startTime: string; // HH:mm format
  endTime: string; // HH:mm format
  durationMinutes: number;
  isAvailable: boolean;
}

export interface AvailableSlotsResponse {
  roomId: number;
  date: string; // YYYY-MM-DD
  availableSlots: TimeSlot[];
}

// ============================================
// Admin Related Types
// ============================================

export interface BlockedSlot {
  id: number;
  roomId: number;
  startTime: string;
  endTime: string;
  reason: string;
  blockedBy?: User;
  createdAt: string;
}

export interface CreateBlockedSlotRequest {
  roomId: number;
  startTime: string;
  endTime: string;
  reason: string;
}

// ============================================
// API Error Types
// ============================================

export interface ApiError {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  path: string;
  details?: Record<string, unknown>;
}

export interface ValidationError extends ApiError {
  fieldErrors: Record<string, string[]>;
}

// ============================================
// Dashboard Types
// ============================================

export interface DashboardStats {
  totalReservations: number;
  totalUsers: number;
  pendingApprovals: number;
  reservationsThisWeek: number;
}

export interface ReservationStats {
  byActivityType: Record<ActivityType, number>;
  byHardwareType: Record<HardwareType, number>;
  byRoom: Record<RoomName, number>;
}
