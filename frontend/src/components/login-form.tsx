// src/components/LoginForm.tsx
import React, { useContext } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { loginSchema } from '../validation/authSchemas';
import { AuthContext } from '../context/auth-context';
import { useNavigate } from 'react-router-dom';

interface FormData {
  email: string;
  password: string;
}

const LoginForm = (): JSX.Element => {
  const { setAuthed } = useContext(AuthContext);
  const navigate = useNavigate();
  const { register, handleSubmit, formState: { errors } } = useForm<FormData>({
    resolver: zodResolver(loginSchema)
  });

  const onSubmit = async (data: FormData) => {
    const response = await fetch(`${process.env.REACT_APP_API_URL}/auth/authenticate`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });

    if (response.ok) {
      const result = await response.json();
      localStorage.setItem('token', result.token);
      setIsAuthenticated(true);
      navigate('/dashboard');
    } else {
      alert('Login failed!');
      setIsAuthenticated(false);
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
      <input type="email" {...register('email')} placeholder="Email" className="block w-full p-3 border rounded shadow-sm" />
      {errors.email && <p className="text-red-500">{errors.email.message}</p>}
      <input type="password" {...register('password')} placeholder="Password" className="block w-full p-3 border rounded shadow-sm" />
      {errors.password && <p className="text-red-500">{errors.password.message}</p>}
      <button type="submit" className="w-full p-3 bg-blue-500 text-white rounded hover:bg-blue-600">Log In</button>
    </form>
  );
};

export default LoginForm;
