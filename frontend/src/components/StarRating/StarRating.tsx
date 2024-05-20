import React, { useEffect, useState } from 'react';
import Star from './Star';
import './StarRating.scss';

const StarRating = ({ onRatingChange, initialRating }) => {
    const [rating, setRating] = useState(initialRating || 0);
  
    useEffect(() => {
      if (initialRating !== undefined) {
        setRating(initialRating);
      }
    }, [initialRating]);
  
    const handleClick = (newRating) => {
      setRating(newRating);
      if (onRatingChange) {
        onRatingChange(newRating);
      }
    };
  
    return (
      <div className="star-rating">
        {[1, 2, 3, 4, 5].map((starNumber) => (
          <Star
            key={starNumber}
            selected={starNumber <= rating}
            onClick={() => handleClick(starNumber)}
          />
        ))}
      </div>
    );
  };
  
  export default StarRating;
