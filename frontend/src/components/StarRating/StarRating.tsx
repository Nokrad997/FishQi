import React, { useEffect, useState } from 'react';
import Star from './Star';
import './StarRating.scss';

interface Props {
  onRatingChange?: (rating: number) => void;
  initialRating?: number | null;

}

const StarRating: React.FC<Props> = ({ onRatingChange, initialRating }) => {
    const [rating, setRating] = useState(initialRating || 0);
  
    useEffect(() => {
      if (initialRating !== undefined && initialRating !== null) {
        setRating(initialRating);
      }
    }, [initialRating]);
  
    const handleClick = (newRating: number) => {
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
