import {
  Box,
  Button,
  TextField,
  Typography,
  Alert,
  CircularProgress,
} from "@mui/material";
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { authService } from "../../services/authService";
import { useAuthStore } from "../../store/authStore";
import type { SignInRequest } from "../../types";

export const SignInForm = () => {
  const [formData, setFormData] = useState<SignInRequest>({
    email: "",
    password: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const { setAuth } = useAuthStore();
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      const response = await authService.signin(formData);
      setAuth(response);
      navigate("/dashboard");
    } catch (err: any) {
      setError(err.response?.data?.message || "Sign in failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box
      sx={{
        maxWidth: 400,
        mx: "auto",
        mt: 4,
        p: 3,
        boxShadow: 1,
        borderRadius: 1,
      }}
    >
      <Typography variant='h5' gutterBottom>
        Sign In
      </Typography>
      {error && (
        <Alert severity='error' sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}
      <form onSubmit={handleSubmit}>
        <TextField
          fullWidth
          label='Email'
          name='email'
          type='email'
          value={formData.email}
          onChange={handleChange}
          margin='normal'
          required
        />
        <TextField
          fullWidth
          label='Password'
          name='password'
          type='password'
          value={formData.password}
          onChange={handleChange}
          margin='normal'
          required
        />
        <Button
          fullWidth
          variant='contained'
          type='submit'
          sx={{ mt: 2 }}
          disabled={loading}
        >
          {loading ? <CircularProgress size={24} /> : "Sign In"}
        </Button>
      </form>
      <Typography variant='body2' sx={{ mt: 2 }}>
        Don't have an account? <Link to='/signup'>Sign up</Link>
      </Typography>
    </Box>
  );
};
