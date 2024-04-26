// src/components/Home.tsx

import HomeCard from "../../components/homeCard/HomeCard";
import './Home.scss';
import { openTab } from "./Tabs";

const Home = () => {
  return (
    <div className="homePage">
      <div className="tabs">
        <button className="tab" onClick={() => openTab('mostPopularCard')}>Most Popular</button>
        <button className="tab" onClick={() => openTab('highestRatingCard')}>Highest Rating</button>
        <button className="tab" onClick={() => openTab('justAddedCard')}>Just Added</button>
        <button className="tab" onClick={() => openTab('mySetsCard')}>My sets</button>
        <button className="tab" onClick={() => openTab('myStarredSetsCard')}>My starred</button>
      </div>
      <div className='homeCards'>
        <div className='mostPopularCard'>
          <HomeCard
            title='Most Popular'
          />
        </div>
        <div className='highestRatingCard' style={{ display: 'none' }}>
          <HomeCard
            title='Highest Rating'
          />
        </div>
        <div className='justAddedCard' style={{ display: 'none' }}>
          <HomeCard
            title='Just Added'
          />
        </div>
        <div className='mySetsCard' style={{ display: 'none' }}>
          <HomeCard
            title='My sets'
          />
        </div>
        <div className='myStarredSetsCard' style={{ display: 'none' }}>
          <HomeCard
            title='My starred sets'
          />
        </div>
      </div>
    </div>
  );
};

export default Home;
