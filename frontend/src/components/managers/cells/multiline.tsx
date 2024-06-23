import { Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material";
import { useEffect, useState } from "react";

interface MultilineEditDialog {
  open: boolean;
  onClose: () => void;
  initialValue: string;
  onSave: (value: string) => void;
  label: string;
}

const MultilineEditDialog : React.FC<MultilineEditDialog> = (
{
  open,
  onClose,
  initialValue,
  onSave,
  label
}) => {
  const [value, setValue] = useState<string>(initialValue);

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  const handleSave = () => {
    onSave(value);
    onClose();
  }


  return (
    <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth>
      <DialogContent
        className="mt-4">
        <TextField
          value={value}
          onChange={(e) => setValue(e.target.value)}
          multiline
          rows={8}
          fullWidth
          label={label}
          variant="filled"
          />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} color="error">Cancel</Button>
        <Button onClick={handleSave} color="success">Save</Button>
      </DialogActions>
    </Dialog>
  )
};
export default MultilineEditDialog;