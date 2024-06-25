import { PaginatedResponse, Activity, FetchParameters } from '../types/entities';
import protectedAxios from '../utils/axios-token';

export const fetchAllActivities = async (): Promise<Activity[]> => {
  const response = await protectedAxios.get('/activities/all');
  return response.data;
}
export const fetchActivities = async (params: FetchParameters): Promise<PaginatedResponse<Activity>> => {
  const response = await protectedAxios.get('/activities', { params });
  return response.data;
};
export const createActivity = async (activity: Partial<Activity>): Promise<void> => {
  await protectedAxios.post('/activities', activity);
};
export const deleteActivity = async (id: number | string): Promise<void> => {
  var encoded = encodeURIComponent(id);
  await protectedAxios.delete(`/activities/${encoded}`);
};
export const editActivity = async (id: number | string, activity: Partial<Activity>): Promise<void> => {
  var encoded = encodeURIComponent(id);
  await protectedAxios.put(`/activities/${encoded}`, activity);
};