import { PaginatedResponse, FetchParameters, ActivityEntry } from '../types/entities';
import protectedAxios from '../utils/axios-token';
import dayjs from '../utils/dayjs-setup';

export const fetchActivityEntries = async (params: FetchParameters): Promise<PaginatedResponse<ActivityEntry>> => {
  const response = await protectedAxios.get('/activity-entries', { params });
  return response.data;
};

export const createActivityEntry = async (activityEntry: Partial<ActivityEntry>): Promise<void> => {
  await protectedAxios.post('/activity-entries', {
    activity: activityEntry.activity!,
    startTime: activityEntry.startTime!.toISOString(),
    endTime: activityEntry.endTime!.toISOString(),
  });
};

export const updateActivityEntry = async (id: number | string, activityEntry: Partial<ActivityEntry>): Promise<void> => {
    await protectedAxios.put(`/activity-entries/${id}`, {
      activity: activityEntry.activity!,
      startTime: dayjs(activityEntry.startTime).toISOString(),
      endTime: dayjs(activityEntry.endTime).toISOString()
    });
};

export const deleteActivityEntry = async (id: number | string): Promise<void> => {
  await protectedAxios.delete(`/activity-entries/${id}`);
}