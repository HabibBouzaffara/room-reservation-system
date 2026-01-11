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
import type { SignUpRequest } from "../../types";

export const SignUpForm = () => {
  const [formData, setFormData] = useState<SignUpRequest>({
    email: "",
    password: "",
    firstName: "",
    lastName: "",
    teamName: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
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
    setSuccess("");

    try {
      await authService.signup(formData);
      setSuccess(
        "Signup successful! Admin approval pending. Redirecting to login..."
      );
      setTimeout(() => navigate("/login"), 2000);
    } catch (err: any) {
      setError(err.response?.data?.message || "Signup failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box
      sx={{
        maxWidth: 500,
        mx: "auto",
        mt: 4,
        p: 3,
        boxShadow: 1,
        borderRadius: 1,
      }}
    >
      <Typography variant='h5' gutterBottom>
        Create Account
      </Typography>
      {error && (
        <Alert severity='error' sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}
      {success && (
        <Alert severity='success' sx={{ mb: 2 }}>
          {success}
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
          label='First Name'
          name='firstName'
          value={formData.firstName}
          onChange={handleChange}
          margin='normal'
          required
        />
        <TextField
          fullWidth
          label='Last Name'
          name='lastName'
          value={formData.lastName}
          onChange={handleChange}
          margin='normal'
          required
        />
        <TextField
          fullWidth
          label='Team Name'
          name='teamName'
          value={formData.teamName}
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
          {loading ? <CircularProgress size={24} /> : "Sign Up"}
        </Button>
      </form>
      <Typography variant='body2' sx={{ mt: 2 }}>
        Already have an account? <Link to='/login'>Sign in</Link>
      </Typography>
    </Box>
  );
};
