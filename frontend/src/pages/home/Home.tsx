import { useEffect, useState } from 'react';
import HomeCard from '../../components/homeCard/HomeCard';
import useFiles from '../../hooks/useFiles';
import useFishQSet from '../../hooks/useFisQSet';
import './Home.scss';
import { openTab } from './Tabs';
import defaultImage from '../../assets/icons/image.png';
import useUserDetails from '../../hooks/useUserDetails';
import useRating from '../../hooks/useRating';
import useFishQ from '../../hooks/useFishQ';
import { getWords } from '../../api/filesData';

interface editData {
  title: string;
  description: string;
  language: string;
  visibility: string;
  img: string;
  words: FishQData[];
}
interface HomeProps {
  onEditClick: (data: editData) => void;
}

const Home: React.FC<HomeProps> = ({ onEditClick }) => {
  const { getSets } = useFishQSet();
  const { getPhotoFromFtp, getWordsFromFtp } = useFiles();
  const { retrieveFishqs } = useFishQ();
  const { getUserId } = useUserDetails();
  const { getAllRatings } = useRating();

  const [sets, setSets] = useState([]);
  const [mostPopularSets, setMostPopularSets] = useState([]);
  const [highestRatingSets, setHighestRatingSets] = useState([]);
  const [mySets, setMySets] = useState([]);
  const [myStarredSets, setMyStarredSets] = useState([]);

  useEffect(() => {
    const fetchSetsAndPhotos = async () => {
      try {
        const setsData = await getSets();
        const photosPromises = setsData.map((set) =>
          set.ftpImagePath ? getPhotoFromFtp(set.ftpImagePath) : defaultImage,
        );
        const photosResults = await Promise.all(photosPromises);

        let ratings = await getAllRatings();
        const ratingsMap = ratings.reduce((acc: any, rating: any) => {
          if (!acc[rating.fishQSet.setId]) {
            acc[rating.fishQSet.setId] = {
              sum: 0,
              count: 0,
            };
          }
          acc[rating.fishQSet.setId].sum += rating.score;
          acc[rating.fishQSet.setId].count++;
          return acc;
        }, {});

        ratings = Object.entries(ratingsMap).map(([setId, { sum, count }]) => ({
          setId: setId,
          score: count > 0 ? sum / count : 0,
          count: count,
        }));

        const setsWithPhotosAndOwners = await Promise.all(
          setsData.map(async (set, index) => {
            const ownerUsername = await getUserId(set.owner_id);
            return {
              ...set,
              photo: photosResults[index],
              owner: ownerUsername.username,
              rating: ratings.find((rating: any) => rating.setId == set.setId) || 0,
            };
          }),
        );

        const fishqs = await retrieveFishqs();

        const setsWithFtpPath = setsWithPhotosAndOwners.map((set) => {
          const ftpPath = fishqs.find((fishq: any) => fishq.setId == set.setId)?.ftpWordsPath;
          return {
            ...set,
            ftpWordsPath: ftpPath,
          };
        });

        const wordsPromises = setsWithFtpPath.map((set) => (set.ftpWordsPath ? getWordsFromFtp(set.ftpWordsPath) : []));
        const wordsResults = await Promise.all(wordsPromises);
        const SetsWithAllData = setsWithPhotosAndOwners.map((set, index) => {
          return {
            ...set,
            words: wordsResults[index],
          };
        });
  
        setSets(SetsWithAllData);      
      } catch (error) {
        console.error('Error fetching sets or photos:', error);
      }
    };

    fetchSetsAndPhotos();
  }, []);

  useEffect(() => {
    const fetchMostPopularSets = async () => {
      try {
        const popularSets = Array.from(sets).sort((a, b) => b.rating.count - a.rating.count);
        setMostPopularSets(popularSets);
      } catch (error) {
        console.error('Error fetching most popular sets:', error);
      }
    };

    const fetchHighestRatingSets = async () => {
      try {
        const highestSets = Array.from(sets).sort((a, b) => b.rating.score - a.rating.score);
        setHighestRatingSets(highestSets);
      } catch (error) {
        console.error('Error fetching highest rating sets:', error);
      }
    };

    const fetchMySets = async () => {
      try {
        if (localStorage.getItem('user_id') != null) {
          const mySets = Array.from(sets).filter((set) => set.owner_id == localStorage.getItem('user_id'));
          console.log(mySets);
          setMySets(mySets);
        }
      } catch (error) {
        console.error('Error fetching my sets:', error);
      }
    };

    const fetchMyStarred = async () => {
      try {
        if (localStorage.getItem('user_id') != null) {
          const userId = localStorage.getItem('user_id');
          const ratings = await getAllRatings();
          const myStarredSets = Array.from(sets).filter((set) => {
            return ratings.some((rating) => rating.customer.user_id == userId && rating.fishQSet.setId === set.setId);
          });
          setMyStarredSets(myStarredSets);
        }
      } catch (error) {
        console.error('Error fetching my starred sets:', error);
      }
    };

    fetchMostPopularSets();
    fetchHighestRatingSets();
    fetchMySets();
    fetchMyStarred();
  }, [sets]);

  const handleEditClick = (title, language, visibility, description, img, words) => {
    const data = {
      title: title,
      description: description,
      language: language,
      visibility: visibility,
      img: img,
      words: words,
    };
    console.log(data);
    onEditClick(data);
  };

  return (
    <div className="homePage">
      <div className="tabs">
        <button className="tab" onClick={() => openTab('mostPopularCard')}>
          Most Popular
        </button>
        <button className="tab" onClick={() => openTab('highestRatingCard')}>
          Highest Rating
        </button>
        <button className="tab" onClick={() => openTab('mySetsCard')}>
          My sets
        </button>
        <button className="tab" onClick={() => openTab('myStarredSetsCard')}>
          My starred
        </button>
      </div>
      <div className="homeCards">
        <div className="mostPopularCard">
          {mostPopularSets.map((set, index) => (
            <HomeCard
              key={index}
              title={set.title}
              owner={set.owner}
              description={set.description}
              photo={set.photo}
              rating={set.rating.score}
              mySets={false}
            />
          ))}
        </div>
        <div className="highestRatingCard" style={{ display: 'none' }}>
          {highestRatingSets.map((set, index) => (
            <HomeCard
              key={index}
              title={set.title}
              owner={set.owner}
              description={set.description}
              photo={set.photo}
              rating={set.rating.score}
              mySets={false}
            />
          ))}
        </div>
        <div className="mySetsCard" style={{ display: 'none' }}>
          {mySets.map((set, index) => (
            <HomeCard
              key={index}
              title={set.title}
              owner={set.owner}
              description={set.description}
              photo={set.photo}
              rating={set.rating.score}
              mySets={true}
              onEditClick={() =>
                handleEditClick(set.title, set.language, set.visibility, set.description, set.photo, set.words)
              }
            />
          ))}
        </div>
        <div className="myStarredSetsCard" style={{ display: 'none' }}>
          {myStarredSets.map((set, index) => (
            <HomeCard
              key={index}
              title={set.title}
              owner={set.owner}
              description={set.description}
              photo={set.photo}
              rating={set.rating.score}
              mySets={false}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default Home;
