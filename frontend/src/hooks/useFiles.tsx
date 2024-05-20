import { getPhoto, getWords, send, updateFilesOnFtp } from "../api/filesData";
import defaultPhoto from '../assets/icons/image.png';

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

            return defaultPhoto;
        }
    }

    const updateFiles = async (data: FilesData) => {
        try{
            const response = await updateFilesOnFtp(data);

            return response;
        }catch(error:any){
            console.log('Failed in updating files: ', error);

            throw new Error(error.message || 'Failed in updating files');
        }
    };

    const getWordsFromFtp = async (ftpPath: string) => {
        try {
            const response = await getWords(ftpPath);
            
            return response;
        } catch (error: any) {
            console.log('Failed in getting words: ', error);

            return [];
        }
    };

    return { sendFiles, getPhotoFromFtp, getWordsFromFtp, updateFiles };
};

export default useFiles;