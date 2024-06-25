import { z } from "zod";
import { Activity, ActivityEntry } from "../../../types/entities";
import dayjs, { Dayjs } from "dayjs";
import { Controller, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect, useState } from "react";
import { fetchAllActivities } from "../../../services/activity-service";
import { Grid, FormControl, InputLabel, Select, MenuItem, FormHelperText, TextField, Button, CircularProgress, Box } from "@mui/material";
import { DateTimePicker } from "@mui/x-date-pickers";
import { mapFieldToId } from "../../../utils/mapFieldToId";

interface ActivityEntryFormProps {
  onSubmit: (item: Partial<ActivityEntry>) => void;
};
const ActivityEntrySchema = z.object({
  activity: z.string().min(1, 'Activity is required'),
  startTime: z.custom<Dayjs>((value) => value instanceof dayjs, 'Start time is required'),
  endTime: z.custom<Dayjs>((value) => value instanceof dayjs, 'End time is required'),
}).superRefine((data, ctx) => {
  if (data.startTime && data.endTime &&
    (data.startTime.isAfter(data.endTime) || data.startTime.isSame(data.endTime))
  ) {
    ctx.addIssue({
      code: z.ZodIssueCode.custom,
      message: 'Start time must be before end time',
      path: ['startTime'],
    });
    ctx.addIssue({
      code: z.ZodIssueCode.custom,
      message: 'End time must be after start time',
      path: ['endTime'],
    });
  }
});
const DateTimePickerTextField = (props: any) => <TextField {...props} fullWidth />;
const ActivityEntryForm: React.FC<ActivityEntryFormProps> = ({ onSubmit }) => {
  const {
    register,
    handleSubmit,
    control,
    formState: { errors, isSubmitting },
    setValue,
    getValues,
    reset
  } = useForm<Partial<ActivityEntry>>({
    resolver: zodResolver(ActivityEntrySchema),
  });
  const [activities, setActivites] = useState<Activity[]>([]);
  useEffect(() => {
    const loadActivities = async () => {
      try {
        const activities = await fetchAllActivities();
        const mapped = activities.map(
          (activity) => mapFieldToId('name', activity)
        );
        setActivites(mapped);
      } catch (error) {
        console.error("Error loading activities", error);
      }
    };
    loadActivities();
  }, []);

  const onSubmitHandler = async (data: Partial<ActivityEntry>) => {
    onSubmit(data);
    reset();
  };

  const handleStartTimeChange = (newValue: Dayjs | null) => {
    if (newValue) {
      setValue('startTime', newValue);
      const currentEndTime = getValues('endTime');
      if (!currentEndTime || newValue.isAfter(currentEndTime)) {
        setValue('endTime', newValue);
      }
    }
  }

  return (
    <form onSubmit={handleSubmit(onSubmitHandler)} className='mx-8 mb-8'>
      <Grid container spacing={2}>
        <Grid item xs={12} sm={4}>
          <Box>
            <FormControl fullWidth margin="normal" error={!!errors.activity} disabled={isSubmitting}>
              <InputLabel id="activity-label">Activity</InputLabel>
              <Controller
                name="activity"
                control={control}
                render={({ field }) => (
                  <Select
                    labelId="activity-label"
                    {...field}
                    value={field.value || ''}
                    label="Activity"
                    {...register('activity')}
                  >
                    {activities.map((activity) => (
                      <MenuItem key={activity.id} value={activity.id}>
                        {activity.name}
                      </MenuItem>
                    ))}
                  </Select>
                )}
              />
              <FormHelperText className="min-h-5">{errors.activity?.message}</FormHelperText>
            </FormControl>
          </Box>
        </Grid>
        <Grid item xs={12} sm={4}>
          <Box>
            <FormControl fullWidth margin="normal" error={!!errors.startTime}>
              <Controller
                name="startTime"
                control={control}
                render={({ field }) => (
                  <DateTimePicker
                    label="Start Time"
                    value={field.value || null}
                    onChange={(date) => {
                      field.onChange(date);
                      handleStartTimeChange(date);
                    }}
                    slots={{ textField: DateTimePickerTextField }}
                  />
                )}
              />
              <FormHelperText error className="min-h-5">{errors.startTime?.message}</FormHelperText>
            </FormControl>
          </Box>
        </Grid>
        <Grid item xs={12} sm={4}>
          <Box>
            <FormControl fullWidth margin="normal" error={!!errors.endTime}>
              <Controller
                name="endTime"
                control={control}
                render={({ field }) => (
                  <DateTimePicker
                    label="End Time"
                    value={field.value || null}
                    onChange={(date) => field.onChange(date)}
                    slots={{ textField: DateTimePickerTextField }}
                  />
                )}
              />
              <FormHelperText error className="min-h-5">{errors.endTime?.message}</FormHelperText>
            </FormControl>
          </Box>
        </Grid>
        <Grid item xs={12} sm={12} className="flex justify-end">
          <Button type="submit" variant="contained" color="primary" disabled={isSubmitting} style={{ width: 'fit-content' }}>
            {isSubmitting ? <CircularProgress size={24} /> : 'Submit'}
          </Button>
        </Grid>
      </Grid>
    </form>
  );
};

export default ActivityEntryForm;