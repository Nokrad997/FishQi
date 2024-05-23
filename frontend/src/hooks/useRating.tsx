import { editRating, getRatings, sendRating } from '../api/rating';

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

  const createRating = async (data: RatingData) => {
    try {
      const response = await sendRating(data);

      return response;
    } catch (error: any) {
      console.log('Failed in sending rating: ', error);

      throw new Error(error.message || 'Failed in sending rating');
    }
  };

  const updateRating = async (data: RatingData) => {
    try {
      const response = await editRating(data);

      return response;
    } catch (error: any) {
      console.log('Failed in updating rating: ', error);

      throw new Error(error.message || 'Failed in updating rating');
    }
  };
  return { getAllRatings, createRating, updateRating };
};

export default useRating;
