import { useEffect, useState } from 'react';
import HomeCard from '../../components/homeCard/HomeCard';
import useFiles from '../../hooks/useFiles';
import './Home.scss';
import { openTab } from './Tabs';
import defaultImage from '../../assets/icons/image.png';
import useUserDetails from '../../hooks/useUserDetails';
import useRating from '../../hooks/useRating';
import useFishQ from '../../hooks/useFishQ';

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
  onViewClick: (data: ViewData) => void;
  setsDatas: any;
}

const Home: React.FC<HomeProps> = ({ onEditClick, onViewClick, setsDatas }) => {
  const { getPhotoFromFtp, getWordsFromFtp } = useFiles();
  const { retrieveFishqs } = useFishQ();
  const { getUserId } = useUserDetails();
  const { getAllRatings, createRating, updateRating } = useRating();

  const [userId, setUserId] = useState(localStorage.getItem('userId') || null);
  const [sets, setSets] = useState([]);
  const [mostPopularSets, setMostPopularSets] = useState([]);
  const [highestRatingSets, setHighestRatingSets] = useState([]);
  const [mySets, setMySets] = useState([]);
  const [myStarredSets, setMyStarredSets] = useState([]);
  const setsData = setsDatas;

  useEffect(() => {
    const fetchSetsAndPhotos = async () => {
      try {
        const photosPromises = setsData.map((set: any) =>
          set.ftpImagePath ? getPhotoFromFtp(set.ftpImagePath) : defaultImage,
        );
        const photosResults = await Promise.all(photosPromises);

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

        const calculatedRatings = Object.entries(ratingsMap).map(([setId, { sum, count, ratedBy, userRating }]) => ({
          setId: parseInt(setId),
          score: count > 0 ? sum / count : 0,
          count: count,
          ratedBy: ratedBy,
          userRating: userRating,
        }));

        const setsWithPhotosAndOwners = await Promise.all(
          setsData.map(async (set, index) => {
            const ownerUsername = await getUserId(set.owner_id);
            const rating = calculatedRatings.find((r: any) => r.setId === set.setId) || {
              score: 0,
              count: 0,
              ratedBy: [],
              userRating: [],
            };
            return {
              ...set,
              photo: photosResults[index],
              owner: ownerUsername.username,
              rating: rating,
            };
          }),
        );

        // console.log(setsWithPhotosAndOwners);
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
        if (userId != null) {
          const mySets = Array.from(sets).filter((set) => set.owner_id == localStorage.getItem('userId'));
          setMySets(mySets);
        }
      } catch (error) {
        console.error('Error fetching my sets:', error);
      }
    };

    const fetchMyStarred = async () => {
      try {
        if (userId != null) {
          const ratings = await getAllRatings();
          const myStarredSets = Array.from(sets).filter((set) => {
            return ratings.some(
              (rating: any) => rating.customer.userId == userId && rating.fishQSet.setId === set.setId,
            );
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

  const handleRatingChange = (setId: number, newRating: number) => {
    if (userId === null) {
      console.log('User not logged in');
      return;
    }
    let data = {
      fishQSetId: setId,
      customerId: Number.parseInt(userId),
      score: newRating,
    };
    console.log('data', data);

    setSets((prevSets) => {
      const newSets = prevSets.map((set) => {
        if (set.setId === setId) {
          const userIndex = set.rating.ratedBy.indexOf(Number.parseInt(userId));
          console.log(userIndex);
          let updatedUserRating;
          let updatedRatedBy;

          if (userIndex >= 0) {
            updatedUserRating = set.rating.userRating.map((rating, index) =>
              index === userIndex ? newRating : rating,
            );
            updatedRatedBy = set.rating.ratedBy;
            updateRating(data);
          } else {
            updatedUserRating = [...set.rating.userRating, newRating];
            updatedRatedBy = [...set.rating.ratedBy, Number.parseInt(userId)];
            createRating(data);
          }

          const newRatingSum = updatedUserRating.reduce((acc, curr) => acc + curr, 0);
          const newRatingCount = updatedUserRating.length;
          const newScore = newRatingCount > 0 ? newRatingSum / newRatingCount : 0;

          const updatedSet = {
            ...set,
            rating: {
              ...set.rating,
              userRating: updatedUserRating,
              ratedBy: updatedRatedBy,
              count: newRatingCount,
              score: Math.round(newScore),
            },
          };

          return updatedSet;
        }
        return set;
      });
      return newSets;
    });
    console.log(sets);
  };

  const handleEditClick = (
    title: string,
    language: string,
    visibility: string,
    description: string,
    img: string,
    words: any,
    ftpWordsPath: string,
    ftpImagePath: string,
    setId: number,
  ) => {
    const data = {
      title: title,
      description: description,
      language: language,
      visibility: visibility,
      img: img,
      words: words,
      ftpWordsPath: ftpWordsPath,
      ftpImagePath: ftpImagePath,
      setId: setId,
    };
    // console.log(data);
    onEditClick(data);
  };

  const handleViewClick = (set: any) => {
    console.log('words', set.words);

    const wasRated = userId === null || set.rating.ratedBy.includes(Number.parseInt(userId));
    const userRating =
      userId === null ? 0 : set.rating.userRating[set.rating.ratedBy.indexOf(Number.parseInt(userId))] || 0;
    const data = {
      title: set.title,
      fishQs: set.words,
      wasRated: wasRated,
      rating: userRating,
      onRatingChange: (newRating: number) => handleRatingChange(set.setId, newRating),
    };
    onViewClick(data);
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
          {mostPopularSets.map((set, index) => {
            return (
              <HomeCard
                key={index}
                title={set.title}
                owner={set.owner}
                description={set.description}
                photo={set.photo}
                rating={set.rating.score}
                language={set.language}
                mySets={false}
                onViewClick={() => handleViewClick(set)}
              />
            );
          })}
        </div>
        <div className="highestRatingCard" style={{ display: 'none' }}>
          {highestRatingSets.map((set, index) => {
            return (
              <HomeCard
                key={index}
                title={set.title}
                owner={set.owner}
                description={set.description}
                photo={set.photo}
                rating={set.rating.score}
                language={set.language}
                mySets={false}
                onViewClick={() => handleViewClick(set)}
              />
            );
          })}
        </div>
        <div className="mySetsCard" style={{ display: 'none' }}>
          {mySets.map((set, index) => {
            return (
              <HomeCard
                key={index}
                title={set.title}
                owner={set.owner}
                description={set.description}
                photo={set.photo}
                rating={set.rating.score}
                language={set.language}
                mySets={true}
                onEditClick={() =>
                  handleEditClick(
                    set.title,
                    set.language,
                    set.visibility,
                    set.description,
                    set.photo,
                    set.words,
                    set.ftpWordsPath,
                    set.ftpImagePath,
                    set.setId,
                  )
                }
                onViewClick={() => handleViewClick(set)}
              />
            );
          })}
        </div>
        <div className="myStarredSetsCard" style={{ display: 'none' }}>
          {myStarredSets.map((set, index) => {
            return (
              <HomeCard
                key={index}
                title={set.title}
                owner={set.owner}
                description={set.description}
                language={set.language}
                photo={set.photo}
                rating={set.rating.score}
                mySets={false}
                onViewClick={() => handleViewClick(set)}
              />
            );
          })}
        </div>
      </div>
    </div>
  );
};

export default Home;
