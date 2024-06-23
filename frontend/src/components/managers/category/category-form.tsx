import { useState } from "react";
import { CategorySummary } from "../../../types/entities";
import { Button, TextField } from "@mui/material";
interface CategoryFormProps {
  onSubmit: (item : Partial<CategorySummary>) => void;
}

const CategoryForm : React.FC<CategoryFormProps> = ( { onSubmit }) => {
  const [name, setName] = useState<string>("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (name.trim()) {
      onSubmit({ name });
      setName("");
    }
   }

   return (
    <form
      onSubmit={handleSubmit}
      className="mb-4 flex items-center space-x-4">
      <TextField
        value={name}
        onChange={(e) => setName(e.target.value)}
        placeholder = "New category name"
        variant="outlined"
        className="mr-2"
        />

      <Button
        type="submit"
        variant="contained"
        color="primary"
        disabled={!name.trim()}>
        Add category
      </Button>
      </form>
   )
}

export default CategoryForm;
