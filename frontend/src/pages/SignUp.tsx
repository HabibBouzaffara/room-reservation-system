import { Box, Container } from "@mui/material";
import { SignUpForm } from "../components/Auth/SignUpForm";
import { Navbar } from "../components/Common/Navbar";

export const SignUp = () => {
  return (
    <Box>
      <Navbar />
      <Container maxWidth='sm'>
        <SignUpForm />
      </Container>
    </Box>
  );
};
