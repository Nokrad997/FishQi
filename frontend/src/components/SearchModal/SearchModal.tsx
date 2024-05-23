import { useEffect, useState } from 'react';
import './SearchModal.scss';
import useUserDetails from '../../hooks/useUserDetails';

interface Props {
  isOpen: boolean;
  onClose: () => void;
  setsData: any;
}

export const SearchModal: React.FC<Props> = ({ isOpen, onClose, setsData }) => {
  if (!isOpen) {
    return null;
  }

  const [data, setData] = useState<any>(null);
  const [searchResults, setSearchResults] = useState<any>(null);
  const { getUserId } = useUserDetails();

  useEffect(() => {
    const getSetsData = async () => {    
      const userDataPromises = setsData.map((set: any) => {
        return getUserId(set.owner_id);
      })

      const resolvedUserData = await Promise.all(userDataPromises);
      const completeSetData = setsData.map((set: any, index: number) => {
        return {
          ...set,
          owner: resolvedUserData[index].username,
        }
      });

      console.log(completeSetData)
      setData(completeSetData);
    };

    getSetsData();
  }, []);

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const searchValue = event.target.value;
    const select = document.getElementById('select') as HTMLSelectElement;

    setSearchResults(
        data.filter((set: any) => {
            return set[select.value.toLowerCase()].includes(searchValue);
        })
    )

    console.log(searchResults)
  };

  return (
    <div className="search-modal">
      <div className="search-card">
        <img src="src/assets/icons/reject.png" alt="reject" className="reject-icon" width={40} onClick={onClose} />
        <h1> Search </h1>
        <div className="search-bar">
          <input
            type="text"
            placeholder="Search for a FishQ"
            onChange={handleSearchChange}
          />
          <select id="select">
            <option value="Title">Title</option>
            <option value="Owner">Author</option>
            <option value="Language">Language</option>
          </select>
          <div className="search-results">
            {searchResults?.map((set: any) => (
              <div className="search-result">
                <h2>{set.Title}</h2>
                <h3>Author: {set.Owner}</h3>
                <h3>Language: {set.Language}</h3>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};
