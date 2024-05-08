import { send } from "../api/fishQData";


const useFishQ = () => {
    const sendFishQ = async (data: FishQDbData) => {
        try {
            const response = await send(data);

            return response;
        } catch {
            throw new Error('Failed to send fishQ');
        }
    };

    return { sendFishQ };
}

export default useFishQ;