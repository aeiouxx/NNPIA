import { set, useForm } from 'react-hook-form';
import { useLocation, useNavigate } from 'react-router-dom';
import { TextField, Button, Typography, Container, Paper, Box, Alert, CircularProgress } from '@mui/material';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useEffect, useState } from 'react';
import { useAuthentication } from '../security/authentication-context';
import config from '../config';
import axios, { AxiosError } from 'axios';
import { mapErrorToMessage } from '../utils/axios-get-error';
import FullPageSpinner from './page-spinner';

const loginSchema =   z.object({
  username: z.string().min(1, "Username is required"),
  password: z.string().min(1, "Password is required"),
});

const registerSchema = loginSchema.extend({
  confirmPassword: z.string().min(1, "Password confirmation is required")
}).superRefine((data, context) => {
  if (data.password !== data.confirmPassword) {
    context.addIssue({
      path: [...context.path, "confirmPassword"],
      code: z.ZodIssueCode.custom,
      message: "Passwords do not match"
    })
  }
});

interface AuthFormData {
  username: string;
  password: string;
  confirmPassword?: string;
}

const AuthPage = () => {
  const [isLogin, setIsLogin] = useState(true);
  const navigate = useNavigate();
  const location = useLocation();
  const {login, isAuthenticated } = useAuthentication();
  const from = location.state?.from?.pathname || "/home";
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const {register, handleSubmit, formState: {errors}} = useForm<AuthFormData>({
    resolver: zodResolver(isLogin ? loginSchema : registerSchema)
  });

  useEffect(() => {
    if (isAuthenticated) {
      navigate(from);
    }
  });

  const onSubmit = async (data : AuthFormData) => {
    console.log(data);
    console.log("Should redirect to " + from);
    console.log("Submitting request to: " + (isLogin ? "authenticate" : "register"));
    try {
      setIsSubmitting(true);
      const endpoint = isLogin ? "/authenticate" : "/register";
      const response = await axios.post(
        `${config.authBaseUrl}${endpoint}`,
        {
          username: data.username,
          password: data.password
        }
      );
      var token = response.data.token;
      login(token);
      navigate(from);
    } 
    catch (error : any) {
      console.log(JSON.stringify(error));
      var message = mapErrorToMessage(error,
        {
          409: "User already exists",
          401: "Invalid credentials",
          403: "Invalid credentials"
        });
        setErrorMessage(message);
    }
    finally{
      setIsSubmitting(false);
    }
  }
  const toggleAuthType = () => {
    setIsLogin(!isLogin);
    setErrorMessage(null);
  }
  return (
    <Container maxWidth="sm">
      <Box 
        position="absolute"
        top="50%"
        left="50%"
        style={{ transform: 'translate(-50%, -50%)' }}
        width="100%"
        maxWidth="sm"
      >
        <Paper elevation={3} className="p-6 space-y-6">
          <Typography variant="h5" className="text-center font-bold">
            {isLogin ? 'Login' : 'Register'}
          </Typography>
          {errorMessage && <Alert severity="error">{errorMessage}</Alert>}
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <TextField
              label="Username"
              variant="outlined"
              fullWidth
              error={Boolean(errors.username)}
              helperText={errors.username?.message}
              {...register('username')}
              className="w-full"
              disabled={isSubmitting}
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
              disabled={isSubmitting}
            />
            {!isLogin && (
              <TextField
                label="Confirm Password"
                type="password"
                variant="outlined"
                fullWidth
                error={Boolean(errors.confirmPassword)}
                helperText={errors.confirmPassword?.message}
                {...register('confirmPassword')}
                className="w-full"
                disabled={isSubmitting}
              />
            )}
            <Box position="relative">
              <Button 
                type="submit" 
                variant="contained" 
                color="primary" 
                fullWidth 
                className="mt-4 bg-blue-600 hover:bg-blue-700" 
                disabled={isSubmitting}
              >
                {isSubmitting ? <CircularProgress size={24} /> : (isLogin ? 'Log In' : 'Register')}
              </Button>
            </Box>
          </form>
          <Button onClick={toggleAuthType} variant="text" color="secondary" fullWidth disabled={isSubmitting}>
            {isLogin ? 'Need an account? Register' : 'Already have an account? Log In'}
          </Button>
        </Paper>
      </Box>
    </Container>
  );
}

export default AuthPage;