import React, { useEffect, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { Button, TextField, MenuItem, Select, FormControl, InputLabel, Grid, FormHelperText } from '@mui/material';
import { Activity, Category } from '../../../types/entities';
import { fetchCategories } from '../../../services/category-service';
import { mapFieldToId } from '../../../utils/mapFieldToId';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';

interface ActivityFormProps {
  onSubmit: (activity: Partial<Activity>) => void;
}

const ActivitySchema = z.object({
  name: z.string().min(1, 'Name is required'),
  category: z.string().min(1, 'Category is required'),
  description: z.string().optional(),
});

const ActivityForm: React.FC<ActivityFormProps> = ({ onSubmit }) => {
  const { register, handleSubmit, control, formState : {errors}} = useForm<Partial<Activity>>({
    resolver: zodResolver(ActivitySchema),
  });
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    const loadCategories = async () => {
      try {
        const categories = await fetchCategories();
        const mapped = categories.map(
          (category) => mapFieldToId('name', category)
        );
        setCategories(mapped);
      } catch (error) {
        console.error('Failed to fetch categories', error);
      }
    };

    loadCategories();
  }, []);

  return (
    <form onSubmit={handleSubmit(onSubmit)}
          className='mx-8 mb-8'>
      <Grid container spacing={2}>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Name"
            {...register('name')}
            fullWidth
            margin="normal"
            error={!!errors.name}
            helperText={errors.name?.message}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <FormControl fullWidth margin="normal" error={!!errors.category}>
            <InputLabel id="category-label">Category</InputLabel>
            <Controller
              name="category"
              control={control}
              render={({ field }) => (
                <Select
                  labelId="category-label"
                  {...field}
                  defaultValue=""
                  label="Category"
                >
                  {categories.map((category) => (
                    <MenuItem key={category.id} value={category.name}>
                      {category.name}
                    </MenuItem>
                  ))}
                </Select>
              )}
            />
            {errors.category && (
              <FormHelperText>{errors.category.message}</FormHelperText>
            )}
          </FormControl>
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="Description"
            {...register('description')}
            multiline
            rows={4}
            fullWidth
            margin="normal"
            error={!!errors.description}
            helperText={errors.description?.message}
          />
        </Grid>
        <Grid item xs={12}>
          <Button type="submit" variant="contained" color="primary">
            Submit
          </Button>
        </Grid>
      </Grid>
    </form>
  );
};
export default ActivityForm;
