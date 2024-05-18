import { api } from "./axios";

export async function getRatings () {
    try {
        const response = api.get('/rating/')
        console.log(response);
        return response;
    } catch(error: any) {
        console.log('Failed in retreiving ratings: ', error);
        
        throw new Error(error.message || 'Failed in retreiving ratings');
    }
}