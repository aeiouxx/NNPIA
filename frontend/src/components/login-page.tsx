import { useForm } from 'react-hook-form';
import { useLocation, useNavigate } from 'react-router-dom';
import { TextField, Button, Typography, Container, Paper } from '@mui/material';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';

const loginSchema = z.object({
  username: z.string().min(1, "Username is required"),
  password: z.string().min(1, "Password is required"),
});

interface LoginFormData {
  username: string;
  password: string;
}

export const LoginPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || '/home'; 
  const { register, handleSubmit, formState: { errors } } = useForm<LoginFormData>({
    resolver: zodResolver(loginSchema)
  });

  const onSubmit = async (data: LoginFormData) => {
    console.log(data);
    console.log("Should redirect to", from);
    navigate(from);
  };

  return (
    <Container maxWidth="sm" className="mt-20">
      <Paper elevation={3} className="p-6 space-y-6">
        <Typography variant="h5" className="text-center font-bold">Login</Typography>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <TextField
            label="Username"
            variant="outlined"
            fullWidth
            error={Boolean(errors.username)}
            helperText={errors.username?.message}
            {...register('username')}
            className="w-full"
          />
          <TextField
            label="Password"
            type="password"
            variant="outlined"
            fullWidth
            error={Boolean(errors.password)}
            helperText={errors.password?.message}
            {...register('password')}
            className="w-full"
          />
          <Button type="submit" variant="contained" color="primary" fullWidth className="mt-4 bg-blue-600 hover:bg-blue-700">
            Log In
          </Button>
        </form>
      </Paper>
    </Container>
  );
};

export default LoginPage;
