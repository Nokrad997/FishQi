import { api } from './axios';

type setPromise = {
  setId: number;
  title: string;
  language: string;
  visibility: string;
  owner_id: number;
  description: string;
};

export async function sendFishQSet(set: SetData): Promise<setPromise> {
  try {
    const response = await api.post('fishqset/', set);

    return response.data;
  } catch (error: any) {
    console.log('Failed in sending fishq set: ', error);

    throw new Error(error.message || 'Failed in sending fishq set');
  }
}

export async function updateFishQSet(set: SetData): Promise<setPromise> {
  try {
    const response = await api.put(`fishqset/${set.setId}`, set);

    return response.data;
  } catch (error: any) {
    console.log('Failed in updating fishq set: ', error);

    throw new Error(error.message || 'Failed in updating fishq set');
  }
}
