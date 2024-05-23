import { api } from './axios';

export async function send(data: FishQDbData) {
    try {
        const response = await api.post('fishq/', data);

        return response.data;
    } catch (error: any) {
        console.log('Failed in sending fishQ: ', error);

        throw new Error(error.message || 'Failed in sending fishQ');
    }
}

export async function getFishq() {
    try {
        const response = await api.get(`fishq/`);

        return response.data;
    } catch (error: any) {
        console.log('Failed in getting fishQ: ', error);

        throw new Error(error.message || 'Failed in getting fishQ');
    }
}