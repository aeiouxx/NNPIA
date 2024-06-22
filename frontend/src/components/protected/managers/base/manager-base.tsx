import { useCallback, useEffect, useState } from "react";
import { BaseEntity, ManagerProps, PaginatedResponse } from "../../../../types";
import { mapErrorToMessage } from "../../../../utils/axios-get-error";
import { Alert, Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField } from "@mui/material";
import PaginationControls from "./pagination-controls";


const ManagerBase = <T extends BaseEntity>({
  managedItemName,
  columns,
  fetchItems,
  createItem,
  deleteItem,
  editItem,
  FormComponent,
  itemTransform
}: ManagerProps<T>) => {
  const [items, setItems] = useState<T[]>([]);  
  const [filterText, setFilterText] = useState<string>('');
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [sortField, setSortField] = useState<keyof T | undefined>(columns.find(column => column.sortable)?.key);
  const [sortOrder, setSortOrder] = useState<string>('asc');
  const [editingItem, setEditingItem] = useState<T | null>(null);
  const itemsPerPage = 10;
  const fetchData = useCallback(() => {
    fetchItems({
      page: currentPage - 1,
      size: itemsPerPage,
      filter: filterText,
      sortField: sortField as string,
      sortOrder})
      .then((response : PaginatedResponse<T>) => {
        const mapped = itemTransform ? response.content.map(itemTransform) : response.content;
        setItems(mapped);
        setTotalPages(response.totalPages);
      })
      .catch((error) => setErrorMessage(mapErrorToMessage(error)));
  }, [currentPage, filterText, sortField, sortOrder]);
  useEffect(() => fetchData(), [fetchData]);  
  const handleCreate = async (item: Partial<T>) => {
    try {
      await createItem(item);
      fetchData();
    } catch (error) {
      setErrorMessage(mapErrorToMessage(error));
    }
  };
  const handleDelete = async (id: number | string) => {
    try {
      await deleteItem(id);
      fetchData();
    } catch (error) {
      setErrorMessage(mapErrorToMessage(error));
    }
  };
  const handleEdit = (item: T) => {
    setEditingItem(item);
  };

  const handleSaveEdit = async(id: number | string, updatedItem: Partial<T>) => {
    if (!editItem) {return;}
    try {
      await editItem(id, updatedItem);
      fetchData();
      setEditingItem(null);
    } catch (error) { 
      setErrorMessage(mapErrorToMessage(error)); 
    } 
  };

  const handleSort = (field: keyof T) => { 
    if (sortField === field) {
      setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
    } else {
      setSortField(field);
      setSortOrder('asc');
    }
  };

  return (
    <div className="p-4">
      <h2 className="text-2xl mb-4">{managedItemName} Manager</h2>
      <FormComponent
        onSubmit={handleCreate}
        />
      {errorMessage &&
      <div className="flex mb-4 flex-1">
        <Alert severity="error">{errorMessage}
          <Button onClick={() => setErrorMessage(null)} className="text-white p-2 rounded ml-4">Close</Button>
        </Alert>
      </div>
      }
      <TextField
        value={filterText}
        onChange={(e) => setFilterText(e.target.value)}
        placeholder="Filter items"
        variant="outlined"
        fullWidth
        margin="normal"
        />
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              {
                columns.map(
                  (column) => (
                    <TableCell 
                      key={column.key as string} 
                      onClick={column.sortable ? () => handleSort(column.key) : undefined}
                      style={{cursor: column.sortable ? 'pointer' : 'default'}}>
                        {column.header}
                        {column.sortable && sortField === column.key && (sortOrder === 'asc' ? ' ↑' : ' ↓')}
                    </TableCell>
                  )
                )
              }
            <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
              {
                items.map(
                  (item) => (
                    <TableRow key={item.id}>
                      {columns.map((column) => (
                        <TableCell key={column.key as string}>
                        {
                          editingItem && editingItem.id === item.id && column.editable
                            ? (
                              column.renderEditCell
                                ?  column.renderEditCell(editingItem[column.key], 
                                  (newValue) => setEditingItem({...editingItem, [column.key]: newValue}))
                                : <TextField
                                    value={editingItem[column.key] as string}
                                    onChange={(e) => setEditingItem({...editingItem, [column.key]: e.target.value})}/>
                            ) 
                            : column.renderCell
                              ? column.renderCell(item[column.key])
                              : item[column.key] as string
                        }
                        </TableCell>
                      ))}
                      <TableCell>
                        {
                          editingItem && editingItem.id === item.id 
                            ? (
                              <>
                                <Button variant="contained" color="primary" onClick={() => handleSaveEdit(item.id, editingItem)}>
                                  Save
                                </Button>
                                <Button variant="contained" color="secondary" onClick={() => setEditingItem(null)}>
                                  Cancel
                                </Button>
                              </>
                          ) 
                            : (
                              <>
                              <Button variant="contained" color="primary" onClick={() => handleEdit(item)}>
                                Edit
                              </Button>
                              <Button variant="contained" color="error" onClick={() => handleDelete(item.id)}>
                                Delete
                              </Button>
                            </>)
                        }
                      </TableCell>
                    </TableRow>
                  )
                )
              }
          </TableBody>
        </Table>
      </TableContainer>
      <PaginationControls
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={setCurrentPage}
        />
    </div>
  )
}

export default ManagerBase;