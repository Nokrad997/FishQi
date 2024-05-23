/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useState } from 'react';
import './SetViewModal.scss';
import { FishQCard } from '../FishQCard/FishQCard';
import arrow from '../../assets/icons/arrow.png';
import star from '../../assets/icons/star.png';
import StarRating from '../StarRating/StarRating';

interface Props {
  isOpen: boolean;
  onClose: () => void;
  initialData?: {
    title: string;
    fishQs: { [key: string]: string };
    wasRated: boolean;
    rating: number | null;
    onRatingChange: (rating: number) => void;
  } | null;
}

export const SetViewModal: React.FC<Props> = ({ isOpen, onClose, initialData }) => {
  if (!isOpen || !initialData) {
    return null;
  }
  const { wasRated, rating, onRatingChange, title, fishQs } = initialData;
  const [currentFishQIndex, setCurrentFishQIndex] = useState(0);
  const [isFlipped, setIsFlipped] = useState(Array(Object.keys(fishQs).length).fill(false));

  const fishQsArray = Object.entries(fishQs).map(([word, translation], index) => (
    <FishQCard key={index} fishQ={[word, translation]} flip={isFlipped[index]} />
  ));

  const handlePrevClick = () => {
    setCurrentFishQIndex((prevIndex) => {
      const newIndex = prevIndex > 0 ? prevIndex - 1 : fishQsArray.length - 1;
      setIsFlipped((prevFlipped) => {
        const newFlipped = [...prevFlipped];
        newFlipped[newIndex] = false;
        return newFlipped;
      });
      return newIndex;
    });
  };

  const handleNextClick = () => {
    setCurrentFishQIndex((prevIndex) => {
      const newIndex = prevIndex < fishQsArray.length - 1 ? prevIndex + 1 : 0;
      setIsFlipped((prevFlipped) => {
        const newFlipped = [...prevFlipped];
        newFlipped[newIndex] = false;
        return newFlipped;
      });
      return newIndex;
    });
  };

  return (
    <div className="setView-modal">
      <div className="setView-card">
        <img src="src/assets/icons/reject.png" alt="reject" className="reject-icon" width={40} onClick={onClose} />
        <div className="setView-card-content">
          <div className="setView-card-header">
            <h1>{title}</h1>
          </div>
          <div className="setView-card-block">
            {true && (
              <div>
                <img className="prevArrow" src={arrow} width={40} height={40} alt="arrow" onClick={handlePrevClick} />
              </div>
            )}
            <div className="setView-card-block-fishQ">{fishQsArray[currentFishQIndex]}</div>
            <img src={arrow} width={40} height={40} alt="arrow" onClick={handleNextClick} />
          </div>
          <div className="setView-card-footer">
            {wasRated ? (
              <StarRating initialRating={rating} onRatingChange={onRatingChange} />
            ) : (
              <StarRating initialRating={undefined} onRatingChange={onRatingChange} />
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
