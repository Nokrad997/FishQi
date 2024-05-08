import { sendFishQSet, updateFishQSet } from "../api/fishQSetData";

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

	return { sendSet, updateSet };
};

export default useFishQSet;
