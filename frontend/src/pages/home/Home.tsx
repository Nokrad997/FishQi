import { useEffect, useState } from 'react';
import HomeCard from "../../components/homeCard/HomeCard";
import useFiles from "../../hooks/useFiles";
import useFishQSet from "../../hooks/useFisQSet";
import './Home.scss';
import { openTab } from "./Tabs";
import defaultImage from '../../assets/icons/image.png';

const Home = () => {
    const { getSets } = useFishQSet();
    const { getPhotoFromFtp } = useFiles();
    const [sets, setSets] = useState([]);

    useEffect(() => {
        const fetchSetsAndPhotos = async () => {
            try {
                const setsData = await getSets();
                const photosPromises = setsData.map(set =>
                    set.ftpImagePath ? getPhotoFromFtp(set.ftpImagePath) : defaultImage
                );
                const photosResults = await Promise.all(photosPromises);
                const setsWithPhotos = setsData.map((set, index) => ({
                    ...set,
                    photo: photosResults[index]
                }));
                setSets(setsWithPhotos);
            } catch (error) {
                console.error("Error fetching sets or photos:", error);
            }
        };

        fetchSetsAndPhotos();
    }, [getSets, getPhotoFromFtp]);

    return (
        <div className="homePage">
            <div className="tabs">
                <button className="tab" onClick={() => openTab('mostPopularCard')}>Most Popular</button>
                <button className="tab" onClick={() => openTab('highestRatingCard')}>Highest Rating</button>
                <button className="tab" onClick={() => openTab('mySetsCard')}>My sets</button>
                <button className="tab" onClick={() => openTab('myStarredSetsCard')}>My starred</button>
            </div>
            <div className="homeCards">
                <div className='mostPopularCard'>
                    {sets.map((set, index) => (
                        <HomeCard
                            key={index}
                            title={set.title}
                            owner={set.owner_id}
                            description={set.description}
                            photo={set.photo}  // Passing the photo URL to the HomeCard component
                        />
                    ))}
                </div>
            </div>
        </div>
    );
};

export default Home;
