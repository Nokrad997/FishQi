import { useEffect, useState } from 'react';
import './SearchModal.scss';
import useUserDetails from '../../hooks/useUserDetails';
import useRating from '../../hooks/useRating';
import useFiles from '../../hooks/useFiles';

interface Props {
  isOpen: boolean;
  onClose: () => void;
  setsData: any;
  onViewClick: (data: ViewData) => void;
}

export const SearchModal: React.FC<Props> = ({ isOpen, onClose, setsData, onViewClick }) => {
  if (!isOpen) {
    return null;
  }

  const [data, setData] = useState<any>(null);
  const [searchResults, setSearchResults] = useState<any>(null);
  const { getUserId } = useUserDetails();
  const { getAllRatings, createRating, updateRating } = useRating();
  const { getWordsFromFtp } = useFiles();

  useEffect(() => {
    const getSetsData = async () => {
      const userDataPromises = setsData.map((set: any) => {
        return getUserId(set.owner_id);
      });

      const resolvedUserData = await Promise.all(userDataPromises);
      const completeSetData = setsData.map((set: any, index: number) => {
        return {
          ...set,
          owner: resolvedUserData[index].username,
        };
      });

      console.log(completeSetData);
      setData(completeSetData);
    };

    getSetsData();
  }, [setsData]);

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const searchValue = event.target.value.toLowerCase();
    const select = document.getElementById('select') as HTMLSelectElement;

    if (data && searchValue.length != 0) {
      setSearchResults(
        data.filter((set: any) => {
          return set[select.value.toLowerCase()].toLowerCase().includes(searchValue);
        }),
      );
    }
  };

  const handleViewClick = async (index: number) => {
    const choosenSet = searchResults[index];
    const loggedUser = localStorage.getItem('userId') || '';
    console.log(choosenSet);
    const ftpWordsPath = 'FISHQI/' + choosenSet.owner_id + '/' + choosenSet.setId + '/words.json';
    let ratings = await getAllRatings();
    const ratingsMap = ratings.reduce((acc: any, rating: any) => {
      if (!acc[rating.fishQSet.setId]) {
        acc[rating.fishQSet.setId] = {
          sum: 0,
          count: 0,
          ratedBy: [],
          userRating: [],
        };
      }
      acc[rating.fishQSet.setId].sum += rating.score;
      acc[rating.fishQSet.setId].count++;
      acc[rating.fishQSet.setId].ratedBy.push(rating.customer.userId);
      acc[rating.fishQSet.setId].userRating.push(rating.score);
      return acc;
    }, {});

    const calculatedRatings = Object.entries(ratingsMap)
      .map(([setId, { sum, count, ratedBy, userRating }]) => {
        if (choosenSet.setId == setId) {
          return {
            setId: parseInt(setId),
            score: count > 0 ? sum / count : 0,
            count: count,
            ratedBy: ratedBy,
            userRating: userRating,
          };
        }
        return null;
      })
      .filter((item) => item !== null);
    const fishQs = await getWordsFromFtp(ftpWordsPath);
      const wasRated = calculatedRatings[0] != null ? calculatedRatings[0].ratedBy.includes(loggedUser != '' ? parseInt(loggedUser) : -1) : false;
      const rating = calculatedRatings[0] != null ? calculatedRatings[0].score : 0;
    const viewData = {
      title: choosenSet.title,
      fishQs: fishQs,
      wasRated: wasRated,
      rating: rating,
      onRatingChange: (newRating: number)=>{handleRatingChange(choosenSet.setId, wasRated, newRating)},
    };
    onClose();
    onViewClick(viewData);
  };

  const handleRatingChange = (setId: number, wasRated: boolean, newRating: number) => {
    const userId = localStorage.getItem('userId') || '';
    if(wasRated && userId != ''){
      updateRating({ fishQSetId: setId, score: newRating, customerId: parseInt(userId) });
    } else {
      createRating({ fishQSetId: setId, score: newRating, customerId: parseInt(userId) });
    }
  }

  return (
    <div className="search-modal">
      <div className="search-card">
        <img src="src/assets/icons/reject.png" alt="reject" className="reject-icon" width={40} onClick={onClose} />
        <h1>Search</h1>
        <div className="search-bar">
          <input type="text" placeholder="Search for a FishQ" onChange={handleSearchChange} />
          <select id="select">
            <option value="Title">Title</option>
            <option value="Owner">Author</option>
            <option value="Language">Language</option>
          </select>
          <div className="search-results">
            {searchResults?.map((set: any, index: number) => (
              <div key={index} className="search-result">
                <h2>{set.title}</h2>
                <div className="setDetails">
                  <h3>Author: {set.owner}</h3>
                  <h3>Language: {set.language}</h3>
                </div>
                <p>{set.description}</p>
                <button onClick={() => handleViewClick(index)}>View</button>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};
