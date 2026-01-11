package com.roomreservation.dto;

import com.roomreservation.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private UserDTO user;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserDTO {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
        private String teamName;
        private String role;
        private String approvalStatus;

        public static UserDTO fromUser(User user) {
            return UserDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .teamName(user.getTeamName())
                    .role(user.getRole().toString())
                    .approvalStatus(user.getApprovalStatus().toString())
                    .build();
        }
    }
}
