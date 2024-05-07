import { send } from "../api/filesData";

const useFiles = () => {
    const sendFiles = async (data: FilesData) => {
        try {
            
            const response = await send(data);
            console.log('data: ', response);
            return response;
        } catch (error: any) {
            console.log('Failed in sending files: ', error);

            throw new Error(error.message || 'Failed in sending files');
        }
    };

    return { sendFiles };
};

export default useFiles;