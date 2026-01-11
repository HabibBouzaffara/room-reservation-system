import { CssBaseline, Container, Typography } from '@mui/material';

function App() {
  return (
    <>
      <CssBaseline />
      <Container maxWidth="lg" sx={{ mt: 4 }}>
        <Typography variant="h4" gutterBottom>
          Room Reservation System
        </Typography>
        <Typography>
          Backend should be running on http://localhost:8080/api
        </Typography>
      </Container>
    </>
  );
}

export default App;
