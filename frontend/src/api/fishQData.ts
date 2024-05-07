import { api } from './axios';

type setPromise = {
  set_id: number;
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
