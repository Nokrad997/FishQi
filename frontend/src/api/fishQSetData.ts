import { api } from './axios';

type setPromise = {
  setId: number;
  title: string;
  language: string;
  visibility: string;
  owner_id: number;
  description: string;
  ftpImagePath?: string;
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

export async function getFishqSets(): Promise<setPromise[]> {
  try {
    const response = await api.get('fishqset/');

    return response.data;
  } catch (error: any) {
    console.log('Failed in getting fishq sets: ', error);

    throw new Error(error.message || 'Failed in getting fishq sets');
  }
}
