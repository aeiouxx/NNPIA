export interface BaseEntity {
  id: number | string;
}

export interface CategorySummary extends BaseEntity {
  name: string;
  activityCount: number;
  entryCount: number;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalPages: number;
}

export interface FetchParameters {
  page: number;
  size: number;

  filter?: string;
  sortField?: string;
  sortOrder?: string;
}

export interface ColumnMetadata<T, K> {
  key: keyof T;
  header: string;
  sortable?: boolean;
  editable?: boolean;

  renderCell?: (value: K) => React.ReactNode;
  renderEditCell?: (value: K, onChange: (newValue: K) => void) => React.ReactNode;
}

export interface ManagerProps<T> {
  managedItemName: string;
  columns: Array<ColumnMetadata<T, any>>;
  getItemId: (item: T) => number | string;
  fetchItems: (params: FetchParameters) => Promise<PaginatedResponse<T>>;
  createItem: (item: Partial<T>) => Promise<void>;
  deleteItem: (id: number | string) => Promise<void>;
  editItem?: (id: number | string, item: Partial<T>) => Promise<void>;
  FormComponent: React.FC<{
    onSubmit: (item: Partial<T>) => void;
    initialValues?: Partial<T>;
  }>;

  itemTransform?: (item: T) => T; 
}