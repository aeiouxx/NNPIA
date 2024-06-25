import { PaginatedResponse, Activity, FetchParameters, ActivityEntry } from '../types/entities';
import protectedAxios from '../utils/axios-token';

export const fetchActivityEntries = async (params: FetchParameters): Promise<PaginatedResponse<ActivityEntry>> => {
  const response = await protectedAxios.get('/activity-entries', { params });
  return response.data;
};

export const createActivityEntry = async (activityEntry: Partial<ActivityEntry>): Promise<void> => {
  console.log("Creating activity entry: ", JSON.stringify(activityEntry));
  await protectedAxios.post('/activity-entries', {
    activity: activityEntry.activity!,
    startTime: activityEntry.startTime!.toISOString(),
    endTime: activityEntry.endTime!.toISOString(),
  });
};

export const updateActivityEntry = async (id: number | string, activityEntry: Partial<ActivityEntry>): Promise<void> => {
  console.log("Updating activity entry: ", JSON.stringify(activityEntry));
  await protectedAxios.put(`/activity-entries/${id}`, {
    activity: activityEntry.activity!,
    startTime: activityEntry.startTime!.toISOString(),
    endTime: activityEntry.endTime!.toISOString(),
  });
};

export const deleteActivityEntry = async (id: number | string): Promise<void> => {
  console.log("Deleting activity entry: ", id);
  await protectedAxios.delete(`/activity-entries/${id}`);
}