import { api } from './axios';

export async function sendFishQSet(set: SetData) {
  try {
    const formData = new FormData();

    formData.append('title', set.title);
    formData.append('language', set.language);
    formData.append('visibility', set.visibility);
    formData.append('description', set.description);

    if (set.photo) {
      console.log(set.photo)
      formData.append('photo', set.photo, set.photo.name);
    }
    const response = await api.post('fishqset/', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    return response;
  } catch (error: any) {
    console.log('Failed in sending fishq set: ', error);

    throw new Error(error.message || 'Failed in sending fishq set');
  }
}
