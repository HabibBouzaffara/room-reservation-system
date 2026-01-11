import { AppBar, Toolbar, Typography, Button, Box } from "@mui/material";
import { useAuthStore } from "../../store/authStore";
import { useNavigate } from "react-router-dom";

export const Navbar = () => {
  const { user, clearAuth } = useAuthStore();
  const navigate = useNavigate();

  const handleLogout = () => {
    clearAuth();
    localStorage.removeItem("accessToken");
    navigate("/login");
  };

  return (
    <AppBar position='static'>
      <Toolbar>
        <Typography variant='h6' sx={{ flexGrow: 1 }}>
          Room Reservation System
        </Typography>
        {user && (
          <Box sx={{ display: "flex", gap: 2, alignItems: "center" }}>
            <Typography variant='body2'>{user.email}</Typography>
            <Button color='inherit' onClick={handleLogout}>
              Logout
            </Button>
          </Box>
        )}
      </Toolbar>
    </AppBar>
  );
};
