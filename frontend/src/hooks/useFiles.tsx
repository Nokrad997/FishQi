import { getPhoto, send } from "../api/filesData";

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

    const getPhotoFromFtp = async (ftpPath: string) => {
        try {
            const response = await getPhoto(ftpPath);

            return URL.createObjectURL(response.data);
        } catch (error: any) {
            console.log('Failed in getting photo: ', error);

            throw new Error(error.message || 'Failed in getting photo');
        }
    }

    return { sendFiles, getPhotoFromFtp };
};

export default useFiles;