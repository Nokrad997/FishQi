import { api } from "./axios";

export async function getRatings () {
    try {
        const response = await api.get('/rating/')
        console.log(response);
        return response;
    } catch(error: any) {
        console.log('Failed in retreiving ratings: ', error);
        
        throw new Error(error.message || 'Failed in retreiving ratings');
    }
}

export async function sendRating (data: RatingData) {
    try {
        console.log(data);
        const response = await api.post('/rating/', data, {
            headers: {
                'Content-Type': 'multipart/form-data',
            }
        
        })
    
        return response.data;
    } catch(error: any) {
        console.log('Failed in sending rating: ', error);
        
        throw new Error(error.message || 'Failed in sending rating');
    }
}

export async function editRating (data: RatingData) {
    try {
        console.log("edit")
        const response = await api.put(`/rating/`, data, {
            headers: {
                'Content-Type': 'multipart/form-data',
            }
        
        })
    
        return response.data;
    } catch(error: any) {
        console.log('Failed in updating rating: ', error);
        
        throw new Error(error.message || 'Failed in updating rating');
    }
}