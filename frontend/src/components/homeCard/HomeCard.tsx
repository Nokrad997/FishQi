import React from "react";
import "./HomeCard.scss";
interface Props {
    title: string;
    owner: string;
    description: string;
    rating?: number;
    photo?: string;
}

const HomeCard: React.FC<Props> = ({title, owner, description, rating, photo}) => {
    return (
        <div className="card">
            <div className="card-body">
                {photo && <img src={photo} alt="set" width={100}/>}
                <h3 className="card-title">{title}</h3>
                <p className="card-owner">Created by: {owner}</p>
                <p className="card-description">{description}</p>
                {rating && <p className="card-rating">{rating}</p>}
            </div>
        </div>
    );
};

export default HomeCard;