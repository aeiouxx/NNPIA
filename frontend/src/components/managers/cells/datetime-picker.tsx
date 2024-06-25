import React from 'react';
import { TextField } from '@mui/material';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { Dayjs } from 'dayjs';

interface DatePickerCellProps {
  value: Dayjs | null;
  onChange: (value: Dayjs | null) => void;
  label: string;
}

const DatePickerCell: React.FC<DatePickerCellProps> = ({ value, onChange, label }) => {
  return (
    <DateTimePicker
      label={label}
      value={value}
      onChange={onChange}
      slots={{ textField: (params) => <TextField {...params} fullWidth /> }}
    />
  );
};

export default DatePickerCell;