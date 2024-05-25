import { deleteFishQSet, getFishqSets, sendFishQSet, updateFishQSet } from "../api/fishQSetData";

const useFishQSet = () => {
	const sendSet = async (set: SetData) => {
		try {
			console.log('set: ', set)
			const response = await sendFishQSet(set);
			console.log(response)

			return response;
		} catch (error: any) {
			console.log('set failed: ', error);

			throw new Error(error.message || 'set failed');
		}
	};

	const updateSet = async (set: SetData) => {
		try{
			const response = await updateFishQSet(set);
			console.log(response);

			return response;
		}catch (error: any) {
			console.log('set failed: ', error);

			throw new Error(error.message || 'set failed');
		}
	};

	const getSets = async () => {
		try {
			const response = await getFishqSets();

			return response;
		} catch (error: any) { 
			console.log('Failed in getting fishq sets: ', error);

			throw new Error(error.message || 'Failed in getting fishq sets');
		}
	};

	const deleteSet = async (id: number) => {
		try{
			const response = await deleteFishQSet(id);

			return response;
		}catch(error: any) {
			console.log('Failed in deleting fishq set: ', error);

			throw new Error(error.message || 'Failed in deleting fishq set');
		}
	};

	return { sendSet, updateSet, getSets, deleteSet };
};

export default useFishQSet;
