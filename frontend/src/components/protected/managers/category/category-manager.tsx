import { BaseEntity, CategorySummary, ColumnMetadata, FetchParameters, PaginatedResponse } from "../../../../types";
import protectedAxios from "../../../../utils/axios-token";
import { mapFieldToId } from "../../../../utils/mapFieldToId";
import ManagerBase from "../base/manager-base";



const fetchCategories = async (params: FetchParameters): Promise<PaginatedResponse<CategorySummary>> => {
  const response = await protectedAxios.get('/categories/summary', { params });
  return response.data;
};

const createCategory = async (category: Partial<CategorySummary>): Promise<void> => {
  await protectedAxios.post('/categories', category);
};

const deleteCategory = async (id: number | string): Promise<void> => {
  await protectedAxios.delete(`/categories/${id}`);
};

const editCategory = async (id: number | string, category: Partial<CategorySummary>): Promise<void> => {
  await protectedAxios.put(`/categories/${id}`, category);
};

const CategoryManager: React.FC = () => {
  const columns: Array<ColumnMetadata<CategorySummary, any>> = [
    { 
      key: 'name', header: 'Name', sortable: true, editable: true 
    },
    { 
      key: 'activityCount', header: 'Total Activities', sortable: true, editable: false, 
    },
    { 
      key: 'entryCount', header: 'Total Entries', sortable: true, editable: false,
      renderCell: (value) => {
        return <div>{value}</div>
      }
    }
  ];

  return (
    <ManagerBase
      managedItemName="Category"
      getItemId={(item) => item.name}
      columns={columns}
      fetchItems={fetchCategories}
      createItem={createCategory}
      deleteItem={deleteCategory}
      editItem={editCategory}
      FormComponent={() => <div>Form</div>}
      itemTransform={(item) => mapFieldToId('name', item)}
      />
  )
}

export default CategoryManager;