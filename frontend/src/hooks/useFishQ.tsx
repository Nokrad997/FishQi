import { getFishq, send } from "../api/fishQData";


const useFishQ = () => {
    const sendFishQ = async (data: FishQDbData) => {
        try {
            const response = await send(data);

            return response;
        } catch {
            throw new Error('Failed to send fishQ');
        }
    };

    const retrieveFishqs = async () => {
        try{
            const response = await getFishq();
            
            return response;
        } catch {
            throw new Error('Failed to get fishQs');
        }    
    };

    return { sendFishQ, retrieveFishqs };

}

export default useFishQ;