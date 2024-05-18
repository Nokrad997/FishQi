import { getRatings } from "../api/rating";

const useRating = () => {
  const getAllRatings = async () => {
    try {
      const response = await getRatings();
     
      return response.data;
    } catch (error: any) {
      console.log('Failed in retreiving ratings: ', error);
     
      throw new Error(error.message || 'Failed in retreiving ratings');
    }
  };
  return { getAllRatings };
};

export default useRating;
