import { Box, Container, Typography } from "@mui/material";
import { Navbar } from "../components/Common/Navbar";
import { useAuthStore } from "../store/authStore";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

export const Dashboard = () => {
  const { user } = useAuthStore();
  const navigate = useNavigate();

  useEffect(() => {
    if (!user) {
      navigate("/login");
    }
  }, [user, navigate]);

  return (
    <Box>
      <Navbar />
      <Container maxWidth='lg' sx={{ mt: 4 }}>
        <Typography variant='h4' gutterBottom>
          Welcome, {user?.firstName}!
        </Typography>
        <Typography variant='body1'>
          Select a room to make a reservation.
        </Typography>
        {/* Add room selector and calendar here in next phase */}
      </Container>
    </Box>
  );
};
