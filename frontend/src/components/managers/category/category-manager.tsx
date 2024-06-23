import { BaseEntity, CategorySummary, ColumnMetadata, FetchParameters, PaginatedResponse } from "../../../types/entities";
import protectedAxios from "../../../utils/axios-token";
import { mapFieldToId } from "../../../utils/mapFieldToId";
import ManagerBase from "../manager-base";
import CategoryForm from "./category-form";
import {
  createCategory,
  deleteCategory,
  editCategory,
  fetchCategorySummaries
} from "../../../services/category-service";




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
      fetchItems={fetchCategorySummaries}
      createItem={createCategory}
      deleteItem={deleteCategory}
      editItem={editCategory}
      FormComponent={CategoryForm}
      itemTransform={(item) => mapFieldToId('name', item)}
      />
  )
}

export default CategoryManager;