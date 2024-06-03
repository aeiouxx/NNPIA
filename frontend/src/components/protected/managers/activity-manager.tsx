
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Dialog, DialogActions, DialogContent, DialogTitle, TextField, Select, MenuItem, FormControl, InputLabel, IconButton } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';

interface Category {
  id: number;
  name: string;
}
interface Activity {
  id: number;
  name: string;
  description: string;
  category: Category
}
const ActivityManager: React.FC = () => {
  const [activities, setActivities] = useState<Activity[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [activityName, setActivityName] = useState<string>('');
  const [activityDescription, setActivityDescription] = useState<string>('');
  const [selectedCategoryId, setSelectedCategoryId] = useState<number | null>(null);
  const [currentActivityId, setCurrentActivityId] = useState<number | null>(null);
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [showModal, setShowModal] = useState<boolean>(false);
  const itemsPerPage = 10;

  useEffect(() => {
    fetchActivities();
    fetchCategories();
  }, [currentPage]);

  const fetchActivities = async () => {
    try {
      const response = await axios.get('/activities', {
        params: {
          page: currentPage - 1,
          size: itemsPerPage,
        },
      });
      setActivities(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      setErrorMessage('Failed to fetch activities');
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await axios.get('/categories');
      setCategories(response.data);
    } catch (error) {
      setErrorMessage('Failed to fetch categories');
    }
  };

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  const handleOpenModal = (activity?: Activity) => {
    if (activity) {
      setCurrentActivityId(activity.id);
      setActivityName(activity.name);
      setActivityDescription(activity.description);
      setSelectedCategoryId(activity.category.id);
    } else {
      setCurrentActivityId(null);
      setActivityName('');
      setActivityDescription('');
      setSelectedCategoryId(null);
    }
    setShowModal(true);
  };

  const handleSubmit = async () => {
    try {
      if (currentActivityId) {
        await axios.put(`/activities/${currentActivityId}`, {
          name: activityName,
          description: activityDescription,
          categoryId: selectedCategoryId,
        });
      } else {
        await axios.post('/activities', {
          name: activityName,
          description: activityDescription,
          categoryId: selectedCategoryId,
        });
      }
      setShowModal(false);
      fetchActivities();
    } catch (error) {
      setErrorMessage('Failed to save activity');
    }
  };

  const handleDelete = async (activityId: number) => {
    try {
      await axios.delete(`/activities/${activityId}`);
      fetchActivities();
    } catch (error) {
      setErrorMessage('Failed to delete activity');
    }
  };

  return (
    <div className="p-4">
      <h2 className="text-2xl mb-4">Activity Manager</h2>

      <Button
        variant="contained"
        color="primary"
        startIcon={<AddIcon />}
        onClick={() => handleOpenModal()}
      >
        Add Activity
      </Button>

      {errorMessage && <div style={{ color: 'red' }}>{errorMessage}</div>}

      <TableContainer component={Paper} className="mt-4">
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Name</TableCell>
              <TableCell>Description</TableCell>
              <TableCell>Category</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {activities.map((activity) => (
              <TableRow key={activity.id}>
                <TableCell>{activity.name}</TableCell>
                <TableCell>{activity.description}</TableCell>
                <TableCell>{activity.category.name}</TableCell>
                <TableCell>
                  <IconButton color="primary" onClick={() => handleOpenModal(activity)}>
                    <EditIcon />
                  </IconButton>
                  <IconButton color="secondary" onClick={() => handleDelete(activity.id)}>
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <div className="flex justify-between items-center mt-4">
        <Button
          onClick={() => handlePageChange(currentPage - 1)}
          disabled={currentPage <= 1}
          variant="contained"
        >
          Previous
        </Button>
        <span>
          Page {currentPage} of {totalPages}
        </span>
        <Button
          onClick={() => handlePageChange(currentPage + 1)}
          disabled={currentPage >= totalPages}
          variant="contained"
        >
          Next
        </Button>
      </div>

      <Dialog open={showModal} onClose={() => setShowModal(false)}>
        <DialogTitle>{currentActivityId ? 'Edit Activity' : 'Add Activity'}</DialogTitle>
        <DialogContent>
          <TextField
            margin="dense"
            label="Activity Name"
            type="text"
            fullWidth
            value={activityName}
            onChange={(e) => setActivityName(e.target.value)}
          />
          <TextField
            margin="dense"
            label="Activity Description"
            type="text"
            fullWidth
            value={activityDescription}
            onChange={(e) => setActivityDescription(e.target.value)}
          />
          <FormControl fullWidth margin="dense">
            <InputLabel>Category</InputLabel>
            <Select
              value={selectedCategoryId ?? ''}
              onChange={(e) => setSelectedCategoryId(Number(e.target.value))}
            >
              <MenuItem value="" disabled>
                Select category
              </MenuItem>
              {categories.map((category) => (
                <MenuItem key={category.id} value={category.id}>
                  {category.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setShowModal(false)} color="primary">
            Cancel
          </Button>
          <Button onClick={handleSubmit} color="primary">
            {currentActivityId ? 'Save' : 'Add'}
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default ActivityManager;