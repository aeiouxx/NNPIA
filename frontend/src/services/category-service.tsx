import { Category, CategorySummary, FetchParameters, PaginatedResponse } from "../types/entities";
import protectedAxios from "../utils/axios-token";

export const fetchCategorySummaries = async (params: FetchParameters): Promise<PaginatedResponse<CategorySummary>> => {
  const response = await protectedAxios.get('/categories/summary', { params });
  return response.data;
};

export const fetchCategories = async () : Promise<Category[]> => {
  const response = await protectedAxios.get('/categories');
  return response.data;
} 

export const createCategory = async (category: Partial<CategorySummary>): Promise<void> => {
  await protectedAxios.post('/categories', category);
};

export const deleteCategory = async (id: number | string): Promise<void> => {
  var encoded = encodeURIComponent(id);
  await protectedAxios.delete(`/categories/${encoded}`);
};

export const editCategory = async (id: number | string, category: Partial<CategorySummary>): Promise<void> => {
  var encoded = encodeURIComponent(id);
  await protectedAxios.put(`/categories/${encoded}`, category);
};