import React from 'react';
import './HomeCard.scss';
import star from '../../assets/icons/star.png';
interface Props {
  title: string;
  owner: string;
  description: string;
  language: string;
  rating?: number;
  photo?: string;
  onEditClick?: () => void;
  onViewClick?: () => void;
  onDeleteClick?: () => void;
  mySets: boolean;
}

const HomeCard: React.FC<Props> = ({ title, owner, description, rating, photo, language, mySets, onEditClick, onViewClick, onDeleteClick }) => {
  return (
    <div className="card">
      <div className="card-body">
        {photo && <img src={photo} alt="set" width={100} height={100} />}
        <div className="car-header">
          <h3 className="card-title">{title}</h3>
          <p className="card-owner">Created by: {owner}</p>
        </div>

        <p className="card-description">{description}</p>
        <p className="card-language"> {language} </p>
        {rating ? (
          <div className="card-rating">
            <img src={star} width={30} />
            <span>{rating}</span>
          </div>
        ) : (
          <div className="card-rating">
            <img src={star} width={30} />
            <span>0</span>
          </div>
        )}
        {mySets && (
          <button className="card-button" onClick={onEditClick} >Edit</button>
        )}
        <button className="card-button" onClick={onViewClick}>View</button>
        {mySets && (
          <button className='card-button' onClick={onDeleteClick} style={{backgroundColor: "red"}}>Delete</button>
        )}
      </div>
    </div>
  );
};

export default HomeCard;
