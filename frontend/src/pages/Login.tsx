import { Box, Container } from "@mui/material";
import { SignInForm } from "../components/Auth/SignInForm";
import { Navbar } from "../components/Common/Navbar";

export const Login = () => {
  return (
    <Box>
      <Navbar />
      <Container maxWidth='sm'>
        <SignInForm />
      </Container>
    </Box>
  );
};
