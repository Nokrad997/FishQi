import { sendFishQSet } from "../api/fishQData";

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

	return { sendSet };
};

export default useFishQSet;
