import { useEffect, useState } from "react";
import protectedAxios from "../../../utils/axios-token";
import { Alert, Button } from "@mui/material";
import { mapErrorToMessage } from "../../../utils/axios-get-error";

interface Category {
  name: string;
  totalActivities: number;
  totalEntries: number;
}

const CategoryManager: React.FC = () => {
  const [categories, setCategories] = useState<Category[]>([]);
  const [newCategoryName, setNewCategoryName] = useState<string>('');
  const [filterText, setFilterText] = useState<string>('');
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [sortField, setSortField] = useState<string>('name');
  const [sortOrder, setSortOrder] = useState<string>('asc');
  const itemsPerPage = 10;

  useEffect(() => {
    fetchCategories();
  }, [currentPage, filterText, sortField, sortOrder]);

  const fetchCategories = async () => {
    try {
      const response = await protectedAxios.get('/categories/summary', {
        params: {
          page: currentPage - 1,
          size: itemsPerPage,
          filter: filterText,
          sortField: sortField,
          sortOrder: sortOrder,
        },
      });
      console.log(JSON.stringify(response.data.content));
      setCategories(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      setErrorMessage(mapErrorToMessage(error));
    }
  };

  const createCategory = async () => {
    try {
      await protectedAxios.post('/categories', { name: newCategoryName });
      fetchCategories();
      setNewCategoryName('');
    } catch (error) {
      setErrorMessage(mapErrorToMessage(error, {
        409: 'Category already exists',
      }));
    }
  };

  const deleteCategory = async (categoryName: string) => {
    try {
      console.log("Deleting category: ", categoryName)
      await protectedAxios.delete(`/categories/${categoryName}`);
      fetchCategories();
    } catch (error) {
      setErrorMessage(mapErrorToMessage(error, {
        404: 'Category not found',
      }));
    }
  };

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  const handleSort = (field: string) => {
    if (sortField === field) {
      setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
    }
    else {
      setSortField(field);
      setSortOrder('asc');
    }
  }

  return (
    <div className="p-4">
      <h2 className="text-2xl mb-4 flex">Category Manager</h2>

      <div className="mb-4 flex items-center">
        <input
          type="text"
          value={newCategoryName}
          onChange={(e) => setNewCategoryName(e.target.value)}
          placeholder="New category name"
          className="p-2 border rounded mr-2"
        />
        <button onClick={createCategory} className="bg-blue-500 hover:bg-blue-600 text-white p-2 rounded mr-4">
          Add Category
        </button>
        <input
          type="text"
          value={filterText}
          onChange={(e) => setFilterText(e.target.value)}
          placeholder="Filter categories"
          className="p-2 border rounded"
        />
      </div>

      {errorMessage && 
      <div className="flex mb-4 flex-1">
        <Alert severity="error">{errorMessage}
          <Button onClick={() => setErrorMessage(null)} className="text-white p-2 rounded ml-4">Close</Button>
        </Alert>
      </div>
      }

      <table className="min-w-full bg-white">
        <thead>
          <tr>
            <th className="py-2 px-4 border-b cursor-pointer" onClick={() => handleSort('name')}>
              Name {sortField === 'name' && (sortOrder === 'asc' ? '↑' : '↓')}
            </th>
            <th className="py-2 px-4 border-b">
              Activities
            </th>
            <th className="py-2 px-4 border-b">
              Entries
            </th>
            <th className="py-2 px-4 border-b">Actions</th>
          </tr>
        </thead>
        <tbody>
          {categories.map((category) => (
            <tr key={`${category.name}`}>
              <td className="py-2 px-4 border-b">{category.name}</td>
              <td className="py-2 px-4 border-b">{category.totalActivities}</td>
              <td className="py-2 px-4 border-b">{category.totalEntries}</td>
              <td className="py-2 px-4 border-b">
                <button onClick={() => deleteCategory(category.name)} className="bg-red-500 hover:bg-red-600 text-white p-2 rounded">
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="flex justify-between items-center mt-4">
        <button
          onClick={() => handlePageChange(currentPage - 1)}
          disabled={currentPage <= 1}
          className="bg-gray-300 hover:bg-gray-400 text-black p-2 rounded disabled:opacity-50"
        >
          Previous
        </button>
        <span>
          Page {currentPage} of {totalPages}
        </span>
        <button
          onClick={() => handlePageChange(currentPage + 1)}
          disabled={currentPage >= totalPages}
          className="bg-gray-300 hover:bg-gray-400 text-black p-2 rounded disabled:opacity-50"
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default CategoryManager;