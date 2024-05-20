import { api } from './axios';

export async function send(data: FilesData) {
  try {
    const formData = new FormData();
    formData.append('setId', data.setId.toString());
    if (data.photo) formData.append('photo', data.photo, data.photo.name);
    formData.append('fishqs', JSON.stringify(data.fishqs));

    const response = await api.post('files/', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    return response.data;
  } catch (error: any) {
    console.log('Failed in sending files: ', error);

    throw new Error(error.message || 'Failed in sending files');
  }
}

export async function getPhoto(ftpPath: string) {
  try {
    const response = await api.get(`files/getphoto?filePath=${ftpPath}`, {
      responseType: 'blob',
    });

    return response;
  } catch (error: any) {
    console.log('Failed in getting photo: ', error);

    throw new Error(error.message || 'Failed in getting photo');
  }
}

export async function getWords(ftpPath: string) {
  try {
    const response = await api.get(`files/getwords?filePath=${ftpPath}`);

    return response.data;
  } catch (error: any) {
    console.log('Failed in getting words: ', error);

    throw new Error(error.message || 'Failed in getting words');
  }
}

export async function updateFilesOnFtp(data: FilesData) {
  try {
    const formData = new FormData();
    formData.append('setId', data.setId.toString());
    if (data.photo) formData.append('photo', data.photo, data.photo.name);
    formData.append('fishqs', JSON.stringify(data.fishqs));

    const response = api.put(`files/update/${data.setId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    
    });

    return response.data;

  } catch(error: any) {
    console.log('Failed in updating files: ', error);

    throw new Error(error.message || 'Failed in updating files');
  }
}